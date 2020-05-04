package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar sBar;
    private TextView tView;
    static final int RESULT_ENABLE = 1 ;
    static DevicePolicyManager deviceManger ;
    ComponentName compName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceManger = (DevicePolicyManager) getSystemService(Context. DEVICE_POLICY_SERVICE ) ;
        compName = new ComponentName( this, DeviceAdmin. class ) ;

        viewInitializer();
    }

    private boolean isMyServiceRunning(Class<?> SleepMode) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SleepMode.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void viewInitializer() {
        CheckBox check = findViewById(R.id.Ethird);
        sBar = findViewById(R.id.degrees);
        tView =  findViewById(R.id.seekBar_result);
        if(isMyServiceRunning(SleepMode.class)){
            check.setChecked(true);
            sBar.setProgress(SleepMode.degree);
            tView.setText(String.valueOf(SleepMode.degree));
        }
        else{
            SleepMode.degree = sBar.getProgress();
            tView.setText("45");
        }
        seekBarListener();
        checkBoxListener(check);
    }

    private void seekBarListener() {
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
                SleepMode.degree = pval;
                tView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //...
            }
        });
    }

    private void checkBoxListener(CheckBox check) {
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enablePhone();
                    SleepMode.run = true;
                    Intent intent = new Intent(getApplicationContext(), SleepMode.class);
                    startService(intent);
                }
                else{
                    SleepMode.stop();CheckBox check = findViewById(R.id.Ethird);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enablePhone();
                    Intent intent = new Intent(getApplicationContext(), SleepMode.class);
                    startService(intent);
                }
                else{
                    SleepMode.stop();
                }
            }
        });
                }
            }
        });
    }

    public void enablePhone(){
        boolean active = deviceManger.isAdminActive( compName ) ;
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , R.string.admin_premission_explanation ) ;
            startActivityForResult(intent , RESULT_ENABLE ) ;
        }
    }
}
