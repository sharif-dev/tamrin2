package com.example.tamrin2.alarmFeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer player;// = MainActivity.getPlayer();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        System.out.println("*________________* helloooooooooooooooo " + action);

//        player = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);



        if ("AlarmStarted".equals(action)) {
            Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.startService(serviceIntent);


//            Intent secondActivityIntent = new Intent(context, AlarmActivity.class);
//            int vl = intent.getIntExtra("velocity limit", 0);
//            secondActivityIntent.putExtra("velocity limit", vl);
//            secondActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(secondActivityIntent);
        }

    }
}
