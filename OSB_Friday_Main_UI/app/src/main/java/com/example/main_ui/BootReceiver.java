package com.example.main_ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";
    private static final String LUNCH_FILE = "lunch_menu.txt";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Load meals from file
            String[] meals = loadMealsFromFile(context);
            if (meals != null) {
                // Schedule alarms again
                AlarmScheduler.scheduleAlarms(context, meals);
            }
        }
    }

    private String[] loadMealsFromFile(Context context) {
        String[] meals = new String[5];
        try {
            FileInputStream fis = context.openFileInput(LUNCH_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            for (int i = 0; i < 5; i++) {
                meals[i] = reader.readLine();
                Log.d(TAG, "Loaded meal for day " + i + ": " + meals[i]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to load meals from file.", e);
            return null;
        }
        return meals;
    }
}