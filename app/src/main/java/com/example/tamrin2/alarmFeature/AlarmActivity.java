package com.example.tamrin2.alarmFeature;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tamrin2.R;

import java.util.List;

public class AlarmActivity extends Activity {

    SensorManager sensorManager = null;
    TextView textView = null;
    List list;
    Intent myServiceIntent;
    float zAxisVelocity;
    int velocityLimit = 2;

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            textView.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
            zAxisVelocity = values[2] < 0 ? -values[2] : values[2];

            if (zAxisVelocity >= velocityLimit) {
                finish();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent serviceIntent = new Intent(this, AlarmService.class);
//        startService(serviceIntent);

        myServiceIntent = new Intent(this, AlarmService.class);
        startService(myServiceIntent);

        textView = findViewById(R.id.textView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        list = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if(list.size()>0){
            sensorManager.registerListener(sensorEventListener,
                    (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }

        setStopAlarmManager();

//        setFinishActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);
    }

    protected void onStop() {
        if(list.size()>0){
            sensorManager.unregisterListener(sensorEventListener);
        }
        stopService(myServiceIntent);

        super.onStop();
    }

    public void setStopAlarmManager() {
        Intent intent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent.setAction("stop sound");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 223423423, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (10 * 1000), pendingIntent);
    }

    public void setFinishActivity() {
        Intent intent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent.setAction("finish second activity");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 233423423, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (11 * 1000), pendingIntent);
    }
}
