package com.example.erik.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //the launcher activity, makes the user choose what sensor they want to observe
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button accelerometerButton = (Button)findViewById(R.id.accelerometerButton);
        Button compassButton = (Button)findViewById(R.id.compassButton);

        accelerometerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(),Accelerometer.class);
                        startActivity(myIntent);
                    }
                }
        );
        compassButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(),Compass.class);
                        startActivity(myIntent);
                    }
                }
        );
    }
}
