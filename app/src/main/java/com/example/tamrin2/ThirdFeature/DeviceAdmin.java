package com.example.tamrin2.ThirdFeature;

import android.app.admin.DeviceAdminReceiver ;
import android.content.Context ;
import android.content.Intent ;
import android.widget.Toast ;

import com.example.tamrin2.R;


public class DeviceAdmin extends DeviceAdminReceiver {
    @Override
    public void onEnabled (Context context , Intent intent) {
        super .onEnabled(context , intent) ;
        Toast. makeText (context , R.string.admin_enabled , Toast. LENGTH_SHORT ).show() ;
    }
    @Override
    public void onDisabled (Context context , Intent intent) {
        super .onDisabled(context , intent) ;
        Toast. makeText (context , R.string.admin_disable , Toast. LENGTH_SHORT ).show() ;
    }
}