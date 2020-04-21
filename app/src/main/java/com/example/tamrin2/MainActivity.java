package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.tamrin2.alarmFeature.AlarmReceiver;
import com.example.tamrin2.alarmFeature.AlarmService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String hour = "15";
        String minute = "59";
        String second = "00";

        Calendar calendar = Calendar.getInstance();
        int hourDist = Integer.parseInt(hour) - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = Integer.parseInt(minute) - calendar.get(Calendar.MINUTE);
        int secondDist = Integer.parseInt(second) - calendar.get(Calendar.SECOND);

        int seconds = 3600 * hourDist + 60 * minuteDist + secondDist;


        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (seconds * 1000), pendingIntent);

//        System.out.println("herererererer ========= " + seconds);

        Toast.makeText(this, "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();


//        Intent intent = new Intent(this, AlarmService.class);
//        intent.putExtra("hour", hour);
//        intent.putExtra("minute", minute);
//        intent.putExtra("second", second);
//        startService(intent);
    }
}
