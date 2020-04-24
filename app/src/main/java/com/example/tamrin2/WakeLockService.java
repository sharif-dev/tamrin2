package com.example.tamrin2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WakeLockService extends Service {
    private static final int ONGOING_NOTIFICATION_ID =1;
    private final String TAG = getClass().getSimpleName();
    private PowerManager.WakeLock wakeLock;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG , "wake up service nCreate");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        assert powerManager != null;
        wakeLock  = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK , "MyWakeLockTag");
        initForeground();
        acquireWakeLock();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG , "wake up service onStartCommand");
        Toast.makeText(this , "Service Started !" , Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initForeground(){
        Log.d(TAG , "Starting Foreground");
        Intent notificationIntent = new Intent(this , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , notificationIntent , 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("SHAKE NOTIFICATION")
                .setContentText("DEVICE SHAKE DETECTED")
                .setSmallIcon(R.drawable.ic_chat_bubble_black_24dp)
                .setContentIntent(pendingIntent)
                .setTicker("THIS IS THICKER")
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        startForeground(ONGOING_NOTIFICATION_ID , notification);
        Log.d(TAG , "Foreground started");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG , "wake up service onDestroy");
        super.onDestroy();
        releaseWakeLock();
        Toast.makeText(this , "service stopped" , Toast.LENGTH_LONG).show();
    }

    private void releaseWakeLock(){
        if (wakeLock.isHeld()) wakeLock.release();
    }

    private void acquireWakeLock(){
        if (wakeLock!=null && !wakeLock.isHeld()) wakeLock.acquire();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
