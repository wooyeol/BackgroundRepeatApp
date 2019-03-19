package com.rightline.backgroundrepeatapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class MyService extends Service {

    private Context context;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Utils.ServiceWrite("START MyService");

            SharedPreferences.Editor editor = context.getSharedPreferences("myPreference", 0).edit();
            editor.putLong(Utils.LAST_TIME, System.currentTimeMillis());
            editor.apply();

        } catch (Exception e) {
            Utils.ServiceWrite("MyService ERROR : "+e.toString());
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
