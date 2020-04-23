package com.example.tamrin2.alarmFeature;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.Nullable;

import com.example.tamrin2.MainActivity;

public class AlarmServiceTwo extends IntentService {

    private PowerManager.WakeLock wl;
    private Ringtone ringtone;
    private MediaPlayer player;// = MainActivity.getPlayer();
    private Vibrator vibrator;

    public AlarmServiceTwo() {
        super("alarm service");
    }

    @Override
    public void onCreate() {
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        player = MainActivity.getPlayer();
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        System.out.println("+++++++++++++++++++ -> 2 : " + vibrator);

        player.setVolume(100, 100);
        player.setLooping(true);
        player.start();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE));
//        } else {
//            //deprecated in API 26
//            vibrator.vibrate(10000);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
//        vibrator.cancel();
        sendBroadcast(new Intent("finishAlarmReceived"));
    }
}
