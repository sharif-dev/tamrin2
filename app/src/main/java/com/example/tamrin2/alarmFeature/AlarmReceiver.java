package com.example.tamrin2.alarmFeature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.tamrin2.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

        Intent n = new Intent(context, AlarmService.class);
        n.putExtra("uri", intent.getExtras().getString("uri"));
        context.startService(n);

    }
}
