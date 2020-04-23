package com.example.tamrin2.alarmFeature;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

import com.example.tamrin2.MainActivity;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer player = MainActivity.getPlayer();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

//        player = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);

        switch (action){
            case "start service":
                Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

                Intent secondActivityIntent = new Intent(context, AlarmActivity.class);
                secondActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(secondActivityIntent);


//                player.setVolume(500, 500);
//                player.setLooping(true);
//                player.start();
//                Intent n = new Intent(context, AlarmService.class);
//                n.putExtra("uri", intent.getExtras().getString("uri"));
//                context.startService(n);
//
//
//
//                setStopAlarmManager(context);
                break;
            case "stop sound":
                try {
                    player.stop();
                    System.out.println("************** ALOOOOOOOOO");
                } catch (Exception e) {
                    System.out.println("&&&& " + e.getMessage());
                }

                break;
//            case "finish second activity":
//                ((Activity)context).finish();
//                break;

        }

    }

//    public void setStopAlarmManager(Context context) {
//        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
//        intent.setAction("stop sound");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context.getApplicationContext(), 223423423, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (10 * 1000), pendingIntent);
//        System.out.println("OY KKKKKKKKKKKKKKKKKKKKKKK");
//    }
}
