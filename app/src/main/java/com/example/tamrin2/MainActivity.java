package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.tamrin2.alarmFeature.AlarmReceiver;
import com.example.tamrin2.alarmFeature.AlarmService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_activity);

        context = getApplicationContext();

//        Intent intent = new Intent(this, AlarmService.class);
//        startService(intent);



//        Intent intent = new Intent(this, AlarmService.class);
//        intent.putExtra("hour", hour);
//        intent.putExtra("minute", minute);
//        intent.putExtra("second", second);
//        startService(intent);
    }

    public void setAlarm(View view) throws InterruptedException {
        EditText hourEditText = findViewById(R.id.hour_text);
        EditText minuteEditText = findViewById(R.id.minute_text);




        String hour = hourEditText.getText().toString();
        String minute = minuteEditText.getText().toString();
        String second = "00";

        Calendar calendar = Calendar.getInstance();
        int hourDist = Integer.parseInt(hour) - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = Integer.parseInt(minute) - calendar.get(Calendar.MINUTE);
        int secondDist = Integer.parseInt(second) - calendar.get(Calendar.SECOND);

        final int seconds = 3600 * hourDist + 60 * minuteDist + secondDist;


        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (seconds * 1000), pendingIntent);


        Toast.makeText(this, "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainActivity.context = context;
    }
}
