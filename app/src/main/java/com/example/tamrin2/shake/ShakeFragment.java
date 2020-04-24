package com.example.tamrin2.shake;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.tamrin2.R;
import com.example.tamrin2.shake.MySensorService;

import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShakeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View shakeView =inflater.inflate(R.layout.shake_detector_activity , container, false);

         final ToggleButton activated = (ToggleButton) shakeView.findViewById(R.id.active_btn);
        activated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activated.isChecked()){
                    initService();
                }
                else {
                    killService();
                }
            }
        });
        return shakeView;
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
