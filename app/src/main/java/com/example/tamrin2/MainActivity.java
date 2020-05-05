package com.example.tamrin2;

<<<<<<< HEAD
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
=======
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.tamrin2.alarmFeature.AlarmFragment;

import com.example.tamrin2.ThirdFeature.DeviceAdmin;
import com.example.tamrin2.ThirdFeature.ThirdFeatureView;
>>>>>>> 1b4e2a66394848f6a7933b7ce25101121c7f0361

import com.example.tamrin2.shake.ShakeFragment;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    Button shakeBtn;
    ShakeFragment shakeFragment;
    public static DevicePolicyManager deviceManger ;
=======
    private static Context context;

    public static MainActivity m;
    public static final int RESULT_ENABLE = 1 ;
    public static DevicePolicyManager deviceManger ;
    ComponentName compName ;
>>>>>>> 1b4e2a66394848f6a7933b7ce25101121c7f0361
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD


    }
    
    @Override
    public void onBackPressed() {
        shakeBtn.setVisibility(View.VISIBLE);
        super.onBackPressed();
=======
        context = getApplicationContext();

        loadFragment(new AlarmFragment(), R.id.first_fragment);
//        loadFragment(new SecondFragment(), R.id.second_fragment);
        loadFragment(new ThirdFeatureView(), R.id.third_fragment);



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadFragment(Fragment fragment, int layout) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout, fragment);
        ft.commit();

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
>>>>>>> 1b4e2a66394848f6a7933b7ce25101121c7f0361
    }
}