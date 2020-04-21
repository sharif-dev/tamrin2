package com.example.tamrin2.alarmFeature;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String hour = intent.getStringExtra("hour");
        String minute = intent.getStringExtra("minute");


        Calendar calendar = Calendar.getInstance();
        int hourDist = Integer.parseInt(hour) - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = Integer.parseInt(minute) - calendar.get(Calendar.MINUTE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),
                280192, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourDist);
        calendar.set(Calendar.MINUTE, minuteDist);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, pendingIntent);

        Toast.makeText(this.getApplicationContext(), "Alarm will vibrate at time specified",
                Toast.LENGTH_SHORT).show();


//        Calendar calendar;
//
//        while (true) {
//            calendar = Calendar.getInstance();
//
//            String curHour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
//            String curMinute = Integer.toString(calendar.get(Calendar.MINUTE));
//
////            System.out.println(curHour + " " + curMinute);
//
//            if (curHour.equals(hour) && curMinute.equals(minute)) {
//                System.out.println(curHour + " " + curMinute + " nowwwwwwwww++++++++++++");
//                break;
//            }
//
//            calendar = null;
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
