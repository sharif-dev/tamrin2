package com.example.tamrin2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.io.PipedOutputStream;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MySensorService extends Service implements SensorEventListener {

    float xAccel, yAccel, zAccel;
    float xPreviousAccel, yPreviousAccel, zPreviousAccel;
    private final String TAG = getClass().getSimpleName();
    boolean firstUpdate = true;
    boolean shakeInitiated = false;
    float shakeThreshhold = 10f;
    boolean start = true;
    private PowerManager.WakeLock wakeLock;
    private boolean pendingWakeUp;

    Sensor accelerometer;
    SensorManager sm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
        super.onCreate();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        updateAccelParameters(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);

        if (!shakeInitiated && isAccelerationChanged()) {
            shakeInitiated = true;

        } else if (shakeInitiated && isAccelerationChanged()) {
            executeShakeAction();

        } else if (shakeInitiated && !isAccelerationChanged()) {
            shakeInitiated = false;

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started ! SHAKE IT :)", Toast.LENGTH_LONG).show();
        start = true;
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped !", Toast.LENGTH_LONG).show();
        start = false;
        wakeLock.release();
        super.onDestroy();
    }

    @SuppressLint("InvalidWakeLockTag")
    private void executeShakeAction() {
        if (start) {
            Log.w("mpp", "SCREEN OFF WORKED ");
            Toast.makeText(this, "SHAKE DETECTED", Toast.LENGTH_LONG).show();
            Intent wakeup = new Intent(this, WakeLockService.class);

            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
            assert pm != null;
            wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "MyWakeUP");
            wakeLock.acquire();
        }


    }

    private boolean isAccelerationChanged() {
        float deltax = Math.abs(xPreviousAccel - xAccel);
        float deltaY = Math.abs(yPreviousAccel - yAccel);
        float deltaZ = Math.abs(zPreviousAccel - zAccel);
        return (deltax > shakeThreshhold && deltaY > shakeThreshhold) ||
                (deltax > shakeThreshhold && deltaZ > shakeThreshhold) ||
                (deltaY > shakeThreshhold && deltaZ > shakeThreshhold);
    }

    private void updateAccelParameters(float xNewAccel, float yNewAccel, float zNewAccel) {
        if (firstUpdate) {
            xPreviousAccel = xNewAccel;
            yPreviousAccel = yNewAccel;
            zPreviousAccel = zNewAccel;
            firstUpdate = false;
        } else {
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;

        }
        xAccel = xNewAccel;
        yAccel = yNewAccel;
        zAccel = zNewAccel;

    }

    private void showNotification() {
        final NotificationManager mgr = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder note = new NotificationCompat.Builder(this);
        note.setContentTitle("Device Accelerometer Notification");
        note.setTicker("New Message Alert!");
        note.setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX);
        // to set default sound/light/vibrate or all
        note.setDefaults(Notification.DEFAULT_ALL);
        // Icon to be set on Notification
        note.setSmallIcon(R.drawable.ic_launcher_background);
        // This pending intent will open after notification click
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                MainActivity.class), 0);
        // set pending intent to notification builder
        note.setContentIntent(pi);
        mgr.notify(101, note.build());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
