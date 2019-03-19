package com.rightline.backgroundrepeatapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {

    public static final String PERMISSION_REPEAT = "com.rightline.backgroundrepeatapp.permission.ACTION_REPEAT";
    public static final String REPEAT_START = "com.rightline.backgroundrepeatapp.REPEAT_START";
    public static final String REPEAT_STOP = "com.rightline.backgroundrepeatapp.REPEAT_STOP";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            Utils.AlarmManagerWrite("MyReceiver onReceive : "+intent.getAction()+", isIgnoringBatteryOptimizations : "
                    +Utils.isIgnoringBatteryOptimizations(context));

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent schedule = new Intent(REPEAT_START);
            schedule.setClass(context, MyReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, schedule, 0);

            if(intent.getAction().equals(REPEAT_START)) {

                long last = context.getSharedPreferences("myPreference", 0).getLong(Utils.LAST_TIME, 0);
                if(last + ((Utils.INTERVAL_MINUTE - 1) * 60 * 1000) > System.currentTimeMillis()) {
                    Utils.AlarmManagerWrite("INTERVAL TOO SHORT...");
                } else {
                    Utils.AlarmManagerWrite("START MyService");
                    try {
                        Intent service = new Intent(context, MyService.class);
                        context.startService(service);
                    } catch (Exception e) {
                        Utils.AlarmManagerWrite("MyReceiver ERROR "+e.toString());
                    }
                }

                // Alarm 재등록
                Calendar interval = Calendar.getInstance();
                interval.setTimeInMillis(System.currentTimeMillis());
                interval.add(Calendar.MINUTE, Utils.INTERVAL_MINUTE);

                alarmManager.cancel(sender);

                if(Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, interval.getTimeInMillis(), sender);
                } else if(Build.VERSION.SDK_INT >= 19){
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, interval.getTimeInMillis(), sender);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, interval.getTimeInMillis(), sender);
                }

            } else if(intent.getAction().equals(REPEAT_STOP)) {
                alarmManager.cancel(sender);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    scheduler.cancelAll();
                }
            }
        }
    }
}
