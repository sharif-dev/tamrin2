package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;

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


        sBar = findViewById(R.id.degrees);
        tView =  findViewById(R.id.seekBar_result);
        tView.setText("45");
        SleepMode.degree = sBar.getProgress();
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


        CheckBox check = findViewById(R.id.Ethird);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enablePhone();
                    Intent intent = new Intent(getApplicationContext(), SleepMode.class);
                    startService(intent);
                }
                else{
                    deviceManger .removeActiveAdmin( compName ) ;
                }
            }
        });
    }

    public void enablePhone(){
        boolean active = deviceManger.isAdminActive( compName ) ;
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , "You should enable the app!" ) ;
            startActivityForResult(intent , RESULT_ENABLE ) ;
        }
    }
}
