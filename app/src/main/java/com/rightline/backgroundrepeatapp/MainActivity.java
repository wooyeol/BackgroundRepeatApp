package com.rightline.backgroundrepeatapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!Utils.isIgnoringBatteryOptimizations(this)) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
            Utils.ServiceWrite("START : Build.VERSION.SDK_INT : "+Build.VERSION.SDK_INT+", Build.MANUFACTURER : "+Build.MANUFACTURER);

            Intent start = new Intent(MyReceiver.REPEAT_START);
            start.setClass(this, MyReceiver.class);
            sendBroadcast(start, MyReceiver.PERMISSION_REPEAT);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                scheduler.cancelAll();
                scheduler.schedule(new JobInfo.Builder(Utils.JOB_ID, new ComponentName(this, MyJobService.class))
                        .setPeriodic(Utils.INTERVAL_MINUTE * 60 * 1000)
                        .build());
            }
        }
    }
}
