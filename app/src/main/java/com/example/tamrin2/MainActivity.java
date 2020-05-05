package com.example.tamrin2;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tamrin2.shake.ShakeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    Button shakeBtn;
    ShakeFragment shakeFragment;
    public static DevicePolicyManager deviceManger ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    
    @Override
    public void onBackPressed() {
        shakeBtn.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}