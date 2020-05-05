package com.example.tamrin2.shake;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import com.example.tamrin2.R;

import androidx.annotation.Nullable;

public class MySensorService extends Service implements SensorEventListener {

    float xAccel, yAccel, zAccel;
    boolean run  = false;
    float xPreviousAccel, yPreviousAccel, zPreviousAccel;
    private final String TAG = getClass().getSimpleName();
    boolean firstUpdate = true;
    boolean shakeInitiated = false;
    float shakeThreshhold = 6f;
    boolean start = true;
    private PowerManager.WakeLock wakeLock;

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
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), R.string.sheka_stop, Toast.LENGTH_LONG).show();
        start = false;

        super.onDestroy();
    }

    @SuppressLint("InvalidWakeLockTag")
    private void executeShakeAction() {
        Toast.makeText(getApplicationContext(), R.string.shake_detected, Toast.LENGTH_SHORT).show();
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        assert pm != null;
        wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "MyWakeUP");
        wakeLock.acquire();


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


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
