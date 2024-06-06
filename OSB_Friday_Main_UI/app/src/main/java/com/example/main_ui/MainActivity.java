package com.example.main_ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.example.main_ui.Scraping; // 스크레이핑 import

public class MainActivity extends AppCompatActivity {

    CalendarView cal;
    TextView tv_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cal = findViewById(R.id.cal);
        tv_text = findViewById(R.id.tv_text);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

            }
        });

        ImageButton btnToWeekMenu = findViewById(R.id.btn_to_Week_Menu);
        btnToWeekMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeekMenuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnUserSetting = findViewById(R.id.btn_user_setting);
        btnUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityUserInfo.class);
                startActivity(intent);
            }
        });
    }
}