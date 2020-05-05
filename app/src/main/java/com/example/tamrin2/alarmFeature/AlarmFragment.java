package com.example.tamrin2.alarmFeature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.tamrin2.R;

import java.sql.Time;
import java.util.Calendar;

public class AlarmFragment extends Fragment {

    private static Context myContext;
    public static int pendingIntentCode = 44444444;

    private View fragmentView;

    private static AlarmManager alarmManager;

    private static ToggleButton toggleButton;
    private EditText vLimitEditText;

    private static boolean hasAlarm;

    private TimePicker timePicker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myContext = getActivity().getApplicationContext();
        fragmentView = inflater.inflate(R.layout.alarm_fragment, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        toggleButton = view.findViewById(R.id.toggleButton);
        vLimitEditText = view.findViewById(R.id.vLimit);

        Button setAlarmBtn = view.findViewById(R.id.setAlarmBtn);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vLimitEditText.getText().toString().equals("")) {
                    toggleButton.setChecked(true);
                } else {
                    Toast.makeText(myContext, "Please enter limit!", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public void onResume() {
        hasAlarm = checkAlarm();
        if (hasAlarm) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked && !hasAlarm) {
                    enableAlarm();
                } else {
                    cancelAlarm();
                }
            }
        });

        super.onResume();
    }


    public boolean checkAlarm() {
        Intent intent = new Intent(myContext, AlarmReceiver.class);//the same as up
        intent.setAction("AlarmStarted");//the same as up
        boolean isWorking = (PendingIntent.getBroadcast(myContext, pendingIntentCode, intent,
                PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag

        return isWorking;

    }



    public void cancelAlarm() {
        Intent intent = new Intent(myContext, AlarmReceiver.class);
        intent.setAction("AlarmStarted");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                myContext, pendingIntentCode, intent, PendingIntent.FLAG_NO_CREATE);
        alarmManager.cancel(pendingIntent);
        hasAlarm = false;
    }

    public void enableAlarm() {
        setAlarm();
    }

    public void setAlarm() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        int second = 0;

        Calendar calendar = Calendar.getInstance();
        int hourDist = hour - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = minute - calendar.get(Calendar.MINUTE);
        int secondDist = second - calendar.get(Calendar.SECOND);

        final int seconds = 3600 * hourDist + 60 * minuteDist + secondDist;


        Intent intent = new Intent(myContext, AlarmReceiver.class);
        intent.setAction("AlarmStarted");
        double vl = Double.parseDouble(vLimitEditText.getText().toString());
        intent.putExtra("velocity limit", vl);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                myContext, pendingIntentCode, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (seconds * 1000), pendingIntent);

        Toast.makeText(getActivity(), "Alarm set in " + (seconds > 0 ? seconds : 0) + " seconds", Toast.LENGTH_LONG).show();

    }

    public static Context getMyContext() {
        return myContext;
    }

    public static AlarmManager getAlarmManager() {
        return alarmManager;
    }
}


