package com.example.tamrin2.alarmFeature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tamrin2.R;

import java.util.Calendar;
import java.util.Date;

public class AlarmFragment extends Fragment {

    View fragmentView;

    AlarmManager alarmManager;

    String hour;
    String minute;
    String second;

    private ToggleButton toggleButton;
    private int velocityLimit;
    private EditText vLimitEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        fragmentView = inflater.inflate(R.layout.alarm_fragment, container, false);
        fragmentView = inflater.inflate(R.layout.alarm_fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (toggleButton.isChecked()) {
//                    enableBroadcastReceiver();
                    enableAlarm();

                } else {
//                    disableBroadcastReceiver();
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


//        super.onViewCreated(view, savedInstanceState);
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


//        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
//        intent.setAction("AlarmStarted");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                getActivity().getApplicationContext(), 234324243, intent, 0);


    }

    public void setAlarm() {
        EditText hourEditText = fragmentView.findViewById(R.id.hour_text);
        EditText minuteEditText = fragmentView.findViewById(R.id.minute_text);


        hour = hourEditText.getText().toString();
        minute = minuteEditText.getText().toString();
        second = "00";

        Calendar calendar = Calendar.getInstance();
        int hourDist = Integer.parseInt(hour) - calendar.get(Calendar.HOUR_OF_DAY);
        int minuteDist = Integer.parseInt(minute) - calendar.get(Calendar.MINUTE);
        int secondDist = Integer.parseInt(second) - calendar.get(Calendar.SECOND);

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


        Toast.makeText(getActivity(), "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();

    }

    public void enableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(getActivity(), AlarmReceiver.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
    }

    public void disableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(getActivity(), AlarmReceiver.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
    }
}


