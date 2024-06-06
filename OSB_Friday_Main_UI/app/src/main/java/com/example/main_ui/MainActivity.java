package com.example.main_ui;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import com.example.main_ui.Scraping; // 스크레이핑 import

public class MainActivity extends AppCompatActivity
        implements Scraping.OnScrapingCompleteListener,
        getCalorie.getCalCompleteListener{
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
    List<String> list = new ArrayList<>();



    //저장할 파일
    private static final String LUNCH_FILE = "lunch_menu.txt";
    private static final String CAL_FILE = "calorie.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
}

