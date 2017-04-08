package com.example.erik.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private TextView xText, yText, zText;
    private SensorManager sm;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        Button goBackButton = (Button)findViewById(R.id.goBackButton);

        goBackButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                        startActivity(myIntent);
                    }
                }
        );




    }

    @Override
    public void onSensorChanged(SensorEvent event) { //updates the GUI with all the accelerator-readings
        xText.setText("X:  " + String.valueOf(Math.round(event.values[0])));
        yText.setText("Y:  " + String.valueOf(Math.round(event.values[1])));
        zText.setText("Z:  " + String.valueOf(Math.round(event.values[2])));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // not used
    }
}
