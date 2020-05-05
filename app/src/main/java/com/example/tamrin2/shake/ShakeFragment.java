package com.example.tamrin2.shake;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tamrin2.R;

import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class ShakeFragment extends Fragment {

    private boolean activatedBoolean = false;
    private boolean stopped  = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final boolean isRunning = isMyServiceRunning(MySensorService.class);
        View shakeView =inflater.inflate(R.layout.second_fragment, container, false);
        final TextView activeStatus  = shakeView.findViewById(R.id.active_status);
        activeStatus.setVisibility(View.GONE);
        final TextView deActiveStatus = shakeView.findViewById(R.id.deactive_status);
        deActiveStatus.setVisibility(View.GONE);
        final Button deactiveButton = shakeView.findViewById(R.id.deactive_btn);
        deactiveButton.setVisibility(View.GONE);
        final Button activated = (Button) shakeView.findViewById(R.id.active_btn);
        activeStatus.setVisibility(View.GONE);

        if (!isRunning){
            deActiveStatus.setVisibility(View.VISIBLE);
            activated.setVisibility(View.VISIBLE);
            deactiveButton.setVisibility(View.GONE);
        }
        if (isRunning){
            activeStatus.setVisibility(View.VISIBLE);
           deactiveButton.setVisibility(View.VISIBLE);
           activated.setVisibility(View.GONE);
        }


        Log.d("SERVICE_STATUS + : " , isMyServiceRunning(MySensorService.class) + " ");



        activated.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!isMyServiceRunning(MySensorService.class)){
                        initService();
                        activeStatus.setVisibility(View.VISIBLE);
                        activated.setVisibility(View.GONE);
                        deactiveButton.setVisibility(View.VISIBLE);
                        deActiveStatus.setVisibility(View.GONE);
                        activatedBoolean=true;
                        stopped = false;


                }

            }
        });
        deactiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMyServiceRunning(MySensorService.class)){
                    killService();
                    deActiveStatus.setVisibility(View.VISIBLE);
                    deactiveButton.setVisibility(View.GONE);
                    activeStatus.setVisibility(View.GONE);
                    activated.setVisibility(View.VISIBLE);
                }

            }
        });
        return shakeView;
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) this.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("activatedStatus" , activatedBoolean);
        outState.putBoolean("stopped" , stopped);
        super.onSaveInstanceState(outState);
    }

    private void initService(){
        Log.d(getClass().getSimpleName() , "initservice Function called");
        Objects.requireNonNull(getActivity()).startService(new Intent(getActivity() , MySensorService.class));
    }
    private void killService(){
        Log.d(getClass().getSimpleName() , "killserver Function called");
        Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity() , MySensorService.class));
    }
}
