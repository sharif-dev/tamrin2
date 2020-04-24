package com.example.tamrin2;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.view.WindowManager;
import android.widget.Toast;

public class SleepMode extends Service implements SensorEventListener {
    private SensorManager sensorManager = null;
    private DevicePolicyManager deviceManger;
    //private Looper serviceLooper;
    //private ServiceHandler serviceHandler;

    /*private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper){
            super(looper);
        }
        public void handleMessage(Message message){
            //when enabled is turned off stop self
        }
    }*/


    public SleepMode() {
    }

    /*public void onCreate(){
        HandlerThread thread = new HandlerThread("StartServiceArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }*/

    public int onStartCommand(Intent intent, int flags, int startId){
        deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensor == null) {
            Toast.makeText(this, R.string.no_sensor, Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY) {
            if (event.values[0] == 0) {
                deviceManger.lockNow();
                stopSelf();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onDestroy(){
        sensorManager.unregisterListener(this);
    }
}