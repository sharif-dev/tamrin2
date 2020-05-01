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

        final int vl = intent.getIntExtra("velocity limit", 0);

        player.start();

        int endTimeMilliSeconds = 60 * 10 * 1000;

        long[] pattern = {0, 1000, 100};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            //deprecated in API 26
            vibrator.vibrate(endTimeMilliSeconds);
        }

        final Handler handler = new Handler();
//
//        Thread activityThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("^^^^^^^^^^^^^^^^^^^");
//                        Intent secondActivityIntent = new Intent(getApplicationContext(), AlarmActivity.class);
//                        secondActivityIntent.putExtra("velocity limit", vl);
//                        secondActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(secondActivityIntent);
//                    }
//                });
//            }
//        });
//
//        activityThread.start();




//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("^^^^^^^^^^^^^^^^^^^");
//                Intent secondActivityIntent = new Intent(getApplicationContext(), AlarmActivity.class);
//                secondActivityIntent.putExtra("velocity limit", vl);
//                secondActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(secondActivityIntent);
//            }
//        });


        return START_STICKY;
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