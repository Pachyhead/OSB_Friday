package com.example.main_ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmScheduler {

    private static final String TAG = "AlarmScheduler";

    public static void scheduleAlarms(Context context, String[] meals) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (meals == null) {
            Log.e(TAG, "Meals not provided.");
            return;
        }

        for (int day = Calendar.MONDAY; day <= Calendar.FRIDAY; day++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("menu", meals[day - Calendar.MONDAY]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, day, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            if (alarmManager != null) {
                try {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Log.d(TAG, "Alarm set for day " + day + " with menu: " + meals[day - Calendar.MONDAY]);
                } catch (SecurityException e) {
                    Log.e(TAG, "Failed to set exact alarm. Permission not granted.", e);
                }
            }
        }
    }
}