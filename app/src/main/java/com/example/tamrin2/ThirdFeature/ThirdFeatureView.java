package com.example.tamrin2.ThirdFeature;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tamrin2.MainActivity;
import com.example.tamrin2.R;
import com.example.tamrin2.ThirdFeature.SleepMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFeatureView extends Fragment {
    private SeekBar sBar;
    private TextView tView;

    public ThirdFeatureView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_third_feature_view, container, false);
        viewInitializer(v);
        return v;
    }

    private void viewInitializer(View v) {
        CheckBox check = v.findViewById(R.id.Ethird);
        sBar = v.findViewById(R.id.degrees);
        tView = v.findViewById(R.id.seekBar_result);
        if (MainActivity.m.isMyServiceRunning(SleepMode.class)) {
            check.setChecked(true);
            sBar.setProgress(SleepMode.degree);
            tView.setText(String.valueOf(SleepMode.degree));
        } else {
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

    private void checkBoxListener(final CheckBox check) {
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.m.enablePhone();
                    SleepMode.run = true;
                    Intent intent = new Intent(MainActivity.m.getApplicationContext(), SleepMode.class);
                    MainActivity.m.startService(intent);
                } else {
                    SleepMode.stop();
                    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                MainActivity.m.enablePhone();
                                Intent intent = new Intent(MainActivity.m.getApplicationContext(), SleepMode.class);
                                MainActivity.m.startService(intent);
                            } else {
                                SleepMode.stop();
                            }
                        }
                    });
                }
            }
        });
    }

}
