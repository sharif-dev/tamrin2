package com.example.tamrin2.alarmFeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if ("AlarmStarted".equals(action)) {
            Intent secondActivityIntent = new Intent(context, AlarmActivity.class);
            double vl = intent.getDoubleExtra("velocity limit", 0);
            secondActivityIntent.putExtra("velocity limit", vl);
            secondActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(secondActivityIntent);

        }

    }
}
