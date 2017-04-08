package com.example.erik.testapp;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends Activity implements SensorEventListener {
    private SensorManager sm;
    private ImageView image;
    private TextView tv;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;
    private static final float ALPHA = 0.25f;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //instanciating all the variables and objects
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        image = (ImageView) findViewById(R.id.compass);
        tv = (TextView) findViewById(R.id.header);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        image = (ImageView) findViewById(R.id.compass);
        Button goBackButton2 = (Button)findViewById(R.id.goBackButton2);
        goBackButton2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                        startActivity(myIntent);
                    }
                }
        );


    }


    protected float[] lowPass(float[] input, float[] output) { //to reduce the amount of "flickering" in the compass
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }


    @Override //much of this code is based on the tutorial that were handed to us through live@lund
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            mLastAccelerometer = lowPass(event.values.clone(), mLastAccelerometer);
        } else if (event.sensor == magnetometer) {
            mLastMagnetometer = lowPass(event.values.clone(), mLastMagnetometer);
        }
        if (mLastAccelerometer != null && mLastMagnetometer != null) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float radians = mOrientation[0];
            float degrees = (float) (Math.toDegrees(radians) + 360) % 360;
            tv.setText("" + degrees);
            RotateAnimation animation = new RotateAnimation(
                    mCurrentDegree,
                    -degrees,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            animation.setDuration(250);

            animation.setFillAfter(true);

            image.startAnimation(animation);
            mCurrentDegree = -degrees;
        }

    }

    protected void onResume() { //to resume when going from pause to active
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() { //to save battery when device is passive
        super.onPause();
        sm.unregisterListener(this, accelerometer);
        sm.unregisterListener(this, magnetometer);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use
    }
}
