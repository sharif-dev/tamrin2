package com.example.tamrin2;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class SleepMode extends Service{
    public static int degree = 45;
    public static boolean run = false;
    private static Looper serviceLooper;
    private static ServiceHandler serviceHandler;

    public SleepMode() {
    }

    public static Object getName() {
        return "SleepMode";
    }

    private final class ServiceHandler extends Handler implements SensorEventListener{
        private SensorManager sensorManager = null;
        ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if(message.arg2 == 1){
                if(sensorManager == null) {
                    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

                    Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                    if (sensor == null) {
                        Toast.makeText(getBaseContext(), R.string.no_sensor, Toast.LENGTH_SHORT).show();
                    } else {
                        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            }
            if(message.arg2 == 2){
                sensorManager.unregisterListener(this);
                stopSelf();
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_GRAVITY) {
                float[] g = event.values;
                double norm_Of_g = Math.sqrt((g[0] * g[0]) + (g[1] * g[1]) + (g[2] * g[2]));
                g[2] = (float) (g[2] / norm_Of_g);
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(g[2])));
                if (inclination > (180 - degree)){
                    MainActivity.deviceManger.lockNow();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    public void onCreate(){
        HandlerThread thread = new HandlerThread("StartServiceArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.arg2 = 1;//meaning : start service
        serviceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onTaskRemoved(Intent rootIntent){
        if(run) {
            Intent restartIntentService = new Intent(getApplicationContext(), this.getClass());
            restartIntentService.setPackage(getPackageName());
            startService(restartIntentService);
        }
        super.onTaskRemoved(rootIntent);
    }

    public static void stop(){
        run = false;
        Message msg = serviceHandler.obtainMessage();
        msg.arg2 = 2;//meaning : stop service
        serviceHandler.sendMessage(msg);
    }

}