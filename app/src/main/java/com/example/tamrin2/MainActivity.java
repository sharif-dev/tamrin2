package com.example.tamrin2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

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
//        setContentView(R.layout.set_alarm_activity);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        loadFragment(new AlarmFragment(), R.id.first_fragment);



//        toggleButton = findViewById(R.id.toggleButton);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (toggleButton.isChecked()) {
//                    enableBroadcastReceiver();
//                } else {
//                    disableBroadcastReceiver();
//                }
//            }
//        });
//
//        vLimitEditText = findViewById(R.id.vLimit);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setAlarm(View view) {
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
        intent.setAction("start service");
        int vl = Integer.parseInt(vLimitEditText.getText().toString());
        intent.putExtra("velocity limit", vl);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (seconds * 1000), pendingIntent);
        toggleButton.setChecked(true);


        Toast.makeText(this, "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();

    }

    public void enableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(this, AlarmReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
    }

    public void disableBroadcastReceiver(){
        ComponentName receiver = new ComponentName(this, AlarmReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainActivity.context = context;
    }

    public void loadFragment(Fragment fragment, int layout) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout, fragment);
        ft.commit();

    }
}
