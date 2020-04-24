package com.example.tamrin2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button shakeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this, MySensorService.class);
//        stopService(intent);
        shakeBtn = (Button) findViewById(R.id.shake_btn);
        shakeBtn.setVisibility(View.VISIBLE);
        shakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shakeBtn.setVisibility(View.INVISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                ShakeFragment shakeFragment = new ShakeFragment();
                transaction.add(R.id.container  ,shakeFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

}
