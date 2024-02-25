package com.example.accelerometer;

import static java.lang.Math.abs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.BreakIterator;

public class MyForegroundService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Vibrator vibrator;

    private TextView xText, yText, zText;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification;

        notification = new NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle("Posture Detection")
                .setContentText("Monitoring posture...")
                // Uncomment and replace 'R.drawable.ic_notification' with your actual icon
                 .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        // Register your sensor listener here
        // Assuming 'sensorManager' and 'accelerometer' are already defined and initialized

        return START_STICKY;
    }

    private void createNotificationChannel() {
        CharSequence name = "Posture Detection Service";
        String description = "Used for posture detection";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("ForegroundServiceChannel", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        startForeground(1, new NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle("Sensor Service")
                .setContentText("This service is running in the foreground")
                .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your icon
                .build());
        Log.d("Activated!", "Empty message");

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        // Your existing logic
        if(abs(event.values[0]) < 6) {
            vibrator.vibrate(1000);
            Log.d("Logging Vibration", "Vibration activated!!");
//            MainActivity::setColor(0);

        }
        Log.d("Logging Vibration", "X val: " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Implement if needed
    }
}
