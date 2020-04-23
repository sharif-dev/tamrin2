package com.example.tamrin2.alarmFeature;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

public class AlarmService extends Service implements MediaPlayer.OnPreparedListener {
    private PowerManager.WakeLock wl;
    private Ringtone ringtone;
    private MediaPlayer player;// = MainActivity.getPlayer();
    private Vibrator vibrator;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
        player.setVolume(100, 100);
        player.setLooping(true);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("+++++++++++++++++++" + player);

        player.start();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(10000);
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.stop();
        player.release();
        vibrator.cancel();
        sendBroadcast(new Intent("finishAlarmReceived"));
    }

    public void onPrepared(final MediaPlayer player) {
        System.out.println("_________________________");
        player.start();
        System.out.println("Player song started!");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                stopSelf();
            }
        }, player.getDuration());
    }
}