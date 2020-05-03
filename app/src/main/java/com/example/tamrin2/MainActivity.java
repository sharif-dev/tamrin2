package com.example.tamrin2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tamrin2.alarmFeature.AlarmFragment;
import com.example.tamrin2.alarmFeature.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private ToggleButton toggleButton;
    private int velocityLimit;
    private EditText vLimitEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        loadFragment(new AlarmFragment(), R.id.first_fragment);



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void loadFragment(Fragment fragment, int layout) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout, fragment);
        ft.commit();

    }
}
