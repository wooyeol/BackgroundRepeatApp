package com.rightline.backgroundrepeatapp;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Context context = getApplicationContext();

        Utils.JobSchedulerWrite("MyJobService onStartJob : isIgnoringBatteryOptimizations : "
                +Utils.isIgnoringBatteryOptimizations(context));

        long last = context.getSharedPreferences("myPreference", 0).getLong(Utils.LAST_TIME, 0);
        if(last + ((Utils.INTERVAL_MINUTE - 1) * 60 * 1000) > System.currentTimeMillis()) {
            Utils.JobSchedulerWrite("INTERVAL TOO SHORT...");
            return true;
        }
        Utils.JobSchedulerWrite("START MyService");

        try {
            Intent service = new Intent(context, MyService.class);
            context.startService(service);
        } catch (Exception e) {
            Utils.JobSchedulerWrite("MyReceiver ERROR "+e.toString());
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
