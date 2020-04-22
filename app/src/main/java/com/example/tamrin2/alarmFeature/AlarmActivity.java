package com.example.tamrin2.alarmFeature;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tamrin2.R;

public class AlarmActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Toast.makeText(this, "another activity started!", Toast.LENGTH_LONG).show();
    }
}
