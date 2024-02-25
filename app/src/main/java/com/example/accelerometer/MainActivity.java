package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.slider.Slider;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;
    private Vibrator vibrator;
    private Float postureVal;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] < 0) {
            xText.setText("Posture Score: " + -Math.round(event.values[0]));
        }
        else {
            xText.setText("Posture Score: " + Math.round(event.values[0]));
        }

//        yText.setText("Y " + Math.round(event.values[1]));
//        zText.setText("Z " + Math.round(event.values[2]));

        if(Math.abs(event.values[0]) < 6) {
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
//        yText = (TextView) findViewById(R.id.yText);
//        zText = (TextView) findViewById(R.id.zText);
        

        }

    private void startForegroundService(){


    }
}