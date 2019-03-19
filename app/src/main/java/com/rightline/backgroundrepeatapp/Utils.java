package com.rightline.backgroundrepeatapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

class Utils {

    static final int INTERVAL_MINUTE = 10;
    static final int JOB_ID = 1000;
    static final String LAST_TIME = "LAST_TIME";

    static void ServiceWrite(Object result) {
        FileWriter writer = null;
        try {
            JSONObject object = new JSONObject();
            Calendar c = Calendar.getInstance();
            object.put("date",
                    String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH)+1,
                            c.get(Calendar.DAY_OF_MONTH),
                            c.get(Calendar.HOUR_OF_DAY),
                            c.get(Calendar.MINUTE),
                            c.get(Calendar.SECOND)));

            object.put("result", result);
            String path = Environment.getExternalStorageDirectory() + File.separator + "service.txt";
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(object.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            if(writer != null) {
                try {
                    writer.write(e.toString()+"\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void AlarmManagerWrite(Object result) {
        FileWriter writer = null;
        try {
            JSONObject object = new JSONObject();
            Calendar c = Calendar.getInstance();
            object.put("date",
                    String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH)+1,
                            c.get(Calendar.DAY_OF_MONTH),
                            c.get(Calendar.HOUR_OF_DAY),
                            c.get(Calendar.MINUTE),
                            c.get(Calendar.SECOND)));

            object.put("result", result);
            String path = Environment.getExternalStorageDirectory() + File.separator + "alarmmanager.txt";
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(object.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            if(writer != null) {
                try {
                    writer.write(e.toString()+"\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void JobSchedulerWrite(Object result) {
        FileWriter writer = null;
        try {
            JSONObject object = new JSONObject();
            Calendar c = Calendar.getInstance();
            object.put("date",
                    String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH)+1,
                            c.get(Calendar.DAY_OF_MONTH),
                            c.get(Calendar.HOUR_OF_DAY),
                            c.get(Calendar.MINUTE),
                            c.get(Calendar.SECOND)));

            object.put("result", result);
            String path = Environment.getExternalStorageDirectory() + File.separator + "jobscheduler.txt";
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(object.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            if(writer != null) {
                try {
                    writer.write(e.toString()+"\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static boolean isIgnoringBatteryOptimizations(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return true;
    }
}
