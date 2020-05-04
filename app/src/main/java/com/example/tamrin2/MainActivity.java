package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.tamrin2.ThirdFeature.DeviceAdmin;
import com.example.tamrin2.ThirdFeature.ThirdFeatureView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity m;
    public static final int RESULT_ENABLE = 1 ;
    public static DevicePolicyManager deviceManger ;
    ComponentName compName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceManger = (DevicePolicyManager) getSystemService(Context. DEVICE_POLICY_SERVICE ) ;
        compName = new ComponentName( this, DeviceAdmin. class ) ;

    }

    public boolean isMyServiceRunning(Class<?> SleepMode) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SleepMode.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void enablePhone(){
        boolean active = deviceManger.isAdminActive( compName ) ;
        if (!active) {
            Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , R.string.admin_permission_explanation ) ;
            startActivityForResult(intent , RESULT_ENABLE ) ;
        }
    }
}
