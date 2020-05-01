package com.example.tamrin2.alarmFeature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tamrin2.R;

import java.util.Calendar;

public class AlarmFragment extends Fragment {

    private View fragmentView;

    private AlarmManager alarmManager;

    private ToggleButton toggleButton;
    private EditText vLimitEditText;

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.alarm_fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setNumberPickers();

        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (toggleButton.isChecked()) {
                    enableAlarm();
                } else {
                    cancelAlarm();
                }
            }
        });

        vLimitEditText = view.findViewById(R.id.vLimit);

        Button setAlarmBtn = view.findViewById(R.id.setAlarmBtn);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });



    }

    public void setNumberPickers() {
        hourPicker = fragmentView.findViewById(R.id.hour_picker);
        minutePicker = fragmentView.findViewById(R.id.minute_picker);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);

        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);

    }

    public void cancelAlarm() {
        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
        intent.setAction("AlarmStarted");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity().getApplicationContext(), 234324243, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void enableAlarm() {
        setAlarm();
    }

    public void setAlarm() {
        int hour = hourPicker.getValue();
        int minute = minutePicker.getValue();
        int second = 0;

        Calendar calendar = Calendar.getInstance();
        int hourDist = hour - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = minute - calendar.get(Calendar.MINUTE);
        int secondDist = second - calendar.get(Calendar.SECOND);

        final int seconds = 3600 * hourDist + 60 * minuteDist + secondDist;


        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
        intent.setAction("AlarmStarted");
        int vl = Integer.parseInt(vLimitEditText.getText().toString());
        intent.putExtra("velocity limit", vl);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity().getApplicationContext(), 234324243, intent, 0);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (seconds * 1000), pendingIntent);
        toggleButton.setChecked(true);


        Toast.makeText(getActivity(), "Alarm set in " + seconds + " seconds", Toast.LENGTH_LONG).show();

    }

}


