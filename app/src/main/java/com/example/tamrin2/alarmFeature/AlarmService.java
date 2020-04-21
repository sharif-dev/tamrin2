package com.example.tamrin2.alarmFeature;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.Nullable;

import com.example.tamrin2.MainActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service implements MediaPlayer.OnPreparedListener {
    private PowerManager.WakeLock wl;
    private Ringtone ringtone;
    private MediaPlayer player;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here

        System.out.println("+++++++++++++++++++");

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(10000);
        }

//        Intent intent1 = new Intent(MainActivity.getContext(), AlarmActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        MainActivity.getContext().startActivity(intent1);
//        Uri uri = Uri.parse(intent.getExtras().getString("uri"));
//
//        player = new MediaPlayer();
//        try {
//            player.setDataSource(getApplicationContext(),uri);
//            player.setVolume(100,100);
//            player.setLooping(true);
//            player.setOnPreparedListener(this);
//            player.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                AudioManager am =
//                        (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                am.setStreamVolume(
//                        AudioManager.STREAM_MUSIC,
//                        am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
//                        0);
//            }
//        },0,2000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.stop();
        player.release();
        sendBroadcast(new Intent("finishAlarmReceived"));
    }

    public void onPrepared(final MediaPlayer player) {
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