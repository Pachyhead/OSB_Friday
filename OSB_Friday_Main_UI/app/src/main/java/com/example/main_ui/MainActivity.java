package com.example.main_ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements Scraping.OnScrapingCompleteListener,
        getCalorie.getCalCompleteListener{
    private static final int REQUEST_SCHEDULE_EXACT_ALARM_PERMISSION = 1;
    private static final int REQUEST_POST_NOTIFICATIONS_PERMISSION = 2;
    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String ALARM_ENABLED_KEY = "alarmEnabled";
    private TextView[] menuTextViews;
    private MenuFileManager menuFileManager;

    private getCalorie getcalorie;
    private String[] meals = {
            "", // Monday
            "", // Tuesday
            "", // Wednesday
            "", // Thursday
            "" // Friday
    };

    CalendarView cal;
    TextView tv_text;
    //알람 버튼
    ImageButton btnComponentAlarm;
    //알람 on, off
    private boolean isAlarmEnabled;

    //저장할 파일
    private static final String LUNCH_FILE = "lunch_menu.txt";
    private static final String CAL_FILE = "calorie.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cal = findViewById(R.id.cal);
        tv_text = findViewById(R.id.tv_text);
        btnComponentAlarm = findViewById(R.id.btn_component_alarm);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_text.setText(year + "년 " + month + "월 " + day + "일");
            }
        });
        // Load preferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isAlarmEnabled = prefs.getBoolean(ALARM_ENABLED_KEY, false);
        updateAlarmButton();

        // Handle alarm button click
        btnComponentAlarm.setOnClickListener(v -> {
            isAlarmEnabled = !isAlarmEnabled;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(ALARM_ENABLED_KEY, isAlarmEnabled);
            editor.apply();
            updateAlarmButton();
            if (isAlarmEnabled) {
                checkAndRequestNotificationPermission();
            } else {
                cancelAlarms();
            }
        });
        //파일 읽고 쓰기
        menuFileManager = new MenuFileManager(this);
        //스크레이핑
        new Scraping(this, "lunch").execute();

        getcalorie = new getCalorie(this);

        ImageButton btnToWeekMenu = findViewById(R.id.btn_to_Week_Menu);
        btnToWeekMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeekMenuActivity.class);
                intent.putExtra("meals", meals);
                //intent.putExtra("calories", totalCal);
                startActivity(intent);
            }
        });



        //메뉴 출력을 위한 각각의 레이아웃 불러오기
        menuTextViews = new TextView[]{
                findViewById(R.id.text_monday),
                findViewById(R.id.text_tuesday),
                findViewById(R.id.text_wednesday),
                findViewById(R.id.text_thursday),
                findViewById(R.id.text_friday)
        };

        //저장된 파일을 읽어 출력
       loadAndShowMenu(LUNCH_FILE);

        meals = loadMealsFromFile();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivityForResult(intent, REQUEST_SCHEDULE_EXACT_ALARM_PERMISSION);
            } else {
                // Check and request the POST_NOTIFICATIONS permission if needed
                checkAndRequestNotificationPermission();
            }
        } else {
            // Check and request the POST_NOTIFICATIONS permission if needed
            checkAndRequestNotificationPermission();
        }
    }

    //메뉴 출력
    private void loadAndShowMenu(String fileName) {
        menuFileManager.loadFromFile(fileName, result -> {
            String[] menu = result.split("\n");
            String[] menu2 = result.split("[ \\n]+");
            for (int i = 0; i < menuTextViews.length; i++) {
                menuTextViews[i].setText(menu.length > i ? menu[i] : "");

            }
            MainActivity.this.meals = menu;
            getcalorie.setWeekMenu(menu2);


        });
        ImageButton btnUserSetting = findViewById(R.id.btn_user_setting);
        btnUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 디버깅용: 버튼 클릭시 유저 정보 토스트
                Properties userInfo = loadUserInfo();
                if (userInfo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No user info found", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder userInfoString = new StringBuilder();
                    for (String key : userInfo.stringPropertyNames()) {
                        userInfoString.append(key).append(": ").append(userInfo.getProperty(key)).append("\n");
                    }
                    showUserInfoDialog(userInfoString.toString());
                }

                Intent intent = new Intent(MainActivity.this, ActivityUserInfo.class);
                startActivity(intent);
            }
        });
    }
    // 스크래이핑 결과를 각각의 변수에 넘김.
    @Override
    public void onScrapingComplete(String[] result, String mealType) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String menu : result) {
            stringBuilder.append(menu).append("\n");
        }
        menuFileManager.saveToFile(LUNCH_FILE, stringBuilder.toString());
    }
    @Override
    public void getCalComplete(String[] result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String menu : result) {
            stringBuilder.append(menu).append("\n");
        }
        menuFileManager.saveToFile(CAL_FILE, stringBuilder.toString());

    }
    private Properties loadUserInfo() {
        Properties properties = new Properties();
        try (FileInputStream fis = openFileInput("user_info.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private void showUserInfoDialog(String userInfo) {
        new AlertDialog.Builder(this)
                .setTitle("User Info")
                .setMessage(userInfo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void updateAlarmButton() {
        if (isAlarmEnabled) {
            btnComponentAlarm.setImageResource(R.drawable.ic_alarm_on); // Set to alarm on icon
        } else {
            btnComponentAlarm.setImageResource(R.drawable.octicon_bell_24); // Set to alarm off icon
        }
    }

    private void cancelAlarms() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        for (int day = Calendar.MONDAY; day <= Calendar.FRIDAY; day++) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, day, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.d(TAG, "Alarm cancelled for day " + day);
            }
        }
    }

    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_POST_NOTIFICATIONS_PERMISSION);
            } else {
                AlarmScheduler.scheduleAlarms(this, meals);
            }
        } else {
            AlarmScheduler.scheduleAlarms(this, meals);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AlarmScheduler.scheduleAlarms(this, meals);
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCHEDULE_EXACT_ALARM_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                    Toast.makeText(this, "Exact alarm permission granted", Toast.LENGTH_SHORT).show();
                    checkAndRequestNotificationPermission();
                } else {
                    Toast.makeText(this, "Exact alarm permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String[] loadMealsFromFile() {
        String[] meals = new String[5];
        try {
            FileInputStream fis = openFileInput(LUNCH_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            for (int i = 0; i < 5; i++) {
                meals[i] = reader.readLine();
                Log.d(TAG, "Loaded meal for day " + i + ": " + meals[i]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
