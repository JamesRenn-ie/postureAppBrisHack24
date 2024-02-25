package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.slider.Slider;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private Vibrator vibrator;
    private Float postureVal = 0.0f;

    private Float postureVal = 0.0f;


    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.values[0] < 0) {
//            xText.setText("Posture Score: " + -Math.round(event.values[0]));
//        }
//        else {
//            xText.setText("Posture Score: " + Math.round(event.values[0]));
//        }


        xText.setText("Posture Score: " + Math.abs(Math.round(event.values[0])));

//        yText.setText("Y " + Math.round(event.values[1]));
//        zText.setText("Z " + Math.round(event.values[2]));

        if(Math.abs(event.values[0]) < postureVal) {
            vibrator.vibrate(1000);
            findViewById(R.id.rlVar1).setBackgroundColor(getResources().getColor(R.color.warning));
        }else findViewById(R.id.rlVar1).setBackgroundColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use
    }




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //vibrator
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            //Create our Sensor Manager
            SM = (SensorManager) getSystemService(SENSOR_SERVICE);

            //Accelerometer Sensor
            mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //Register sensor listener
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

            //Assign TextView
            xText = (TextView) findViewById(R.id.xText);

            Button startServiceButton = findViewById(R.id.startServiceButton);
            Button stopServiceButton = findViewById(R.id.stopServiceButton);

            Slider slider = findViewById(R.id.slider);
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(Slider slider, float value, boolean fromUser) {
                    // This method is called when the value of the slider changes.
                    // You can access the current value of the slider using the 'value' parameter.
                    // Do something with the value...
                    // For example, you can display it in a TextView:
                    postureVal = value;
                }
            });




        // Onclick listeners
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the foreground service
                Intent startIntent = new Intent(MainActivity.this, MyForegroundService.class);
                ContextCompat.startForegroundService(MainActivity.this, startIntent);
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the foreground service
                Intent stopIntent = new Intent(MainActivity.this, MyForegroundService.class);
                stopService(stopIntent);
            }
        });


        



    }

    private void startForegroundService(){
        Intent serviceIntent = new Intent(this, MyForegroundService.class);
        Toast.makeText(this, "Detection started", Toast.LENGTH_SHORT).show();
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopForegroundService(){
        Intent serviceIntent = new Intent(this, MyForegroundService.class);
        Toast.makeText(this, "Detection started", Toast.LENGTH_SHORT).show();
        stopService(serviceIntent);
    }
}