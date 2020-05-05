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

import androidx.annotation.RequiresApi;

public class AlarmService extends Service implements MediaPlayer.OnPreparedListener {
    private MediaPlayer player;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

        long[] pattern = {0, 1000, 100};

        vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));


        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.stop();
        player.release();
        vibrator.cancel();
    }

    public void onPrepared(final MediaPlayer player) {
        player.start();
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