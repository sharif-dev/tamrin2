package com.example.tamrin2.alarmFeature;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
    double velocityLimit;

    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        animateBell();

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        velocityLimit = intent.getDoubleExtra("velocity limit", 0);

        sensorEventListener = new SensorEventListener() {
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

        startService();
        handleSensors();

    }

    public void animateBell() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_clock);
        ImageView imgBell= findViewById(R.id.clock);
        imgBell.setImageResource(R.mipmap.clock);
        imgBell.setAnimation(shake);
    }

    public void startService() {
        myServiceIntent = new Intent(this, AlarmService.class);
        startService(myServiceIntent);
    }

    public void handleSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        list = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if(list.size()>0){
            sensorManager.registerListener(sensorEventListener,
                    (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int milliSeconds = 10*60*1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, milliSeconds);
    }

    protected void onStop() {
        if(list.size()>0){
            sensorManager.unregisterListener(sensorEventListener);
        }
        stopService(myServiceIntent);

        super.onStop();
    }
}
