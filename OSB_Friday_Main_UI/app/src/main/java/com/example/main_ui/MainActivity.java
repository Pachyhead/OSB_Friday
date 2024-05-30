package com.example.main_ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements Scraping.OnScrapingCompleteListener{
    private LinearLayout selectionLayout;
    private ConstraintLayout menuDisplayLayout;
    private TextView[] menuTextViews;
    private MenuFileManager menuFileManager;

    //저장할 파일들
    private static final String BREAKFAST_FILE = "breakfast_menu.txt";
    private static final String LUNCH_FILE = "lunch_menu.txt";
    private static final String DINNER_FILE = "dinner_menu.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //파일 읽고 쓰기

        menuFileManager = new MenuFileManager(this);
        //스크레이핑
        new Scraping(this, "breakfast").execute();
        new Scraping(this, "lunch").execute();
        new Scraping(this, "dinner").execute();

        //레이아웃 불러오기. selectionLayout: 버튼 선택. menuDisplayLayout: 메뉴 출력
        selectionLayout = findViewById(R.id.selection_layout);
        menuDisplayLayout = findViewById(R.id.menu_display_layout);

        //메뉴 출력을 위한 각각의 레이아웃 불러오기
        menuTextViews = new TextView[]{
                findViewById(R.id.text_monday),
                findViewById(R.id.text_tuesday),
                findViewById(R.id.text_wednesday),
                findViewById(R.id.text_thursday),
                findViewById(R.id.text_friday)
        };
        //버튼을 누를 경우 불러오는 기능
        Button buttonBreakfast = findViewById(R.id.button_breakfast);
        Button buttonLunch = findViewById(R.id.button_lunch);
        Button buttonDinner = findViewById(R.id.button_dinner);
        Button buttonBack = findViewById(R.id.button_back);

        //저장된 파일을 읽어 출력
        buttonBreakfast.setOnClickListener(v -> loadAndShowMenu(BREAKFAST_FILE));
        buttonLunch.setOnClickListener(v -> loadAndShowMenu(LUNCH_FILE));
        buttonDinner.setOnClickListener(v -> loadAndShowMenu(DINNER_FILE));
        buttonBack.setOnClickListener(v -> showSelectionScreen());



    }
    //메뉴 출력
    private void loadAndShowMenu(String fileName) {
        menuFileManager.loadFromFile(fileName, result -> {
            selectionLayout.setVisibility(View.GONE);
            menuDisplayLayout.setVisibility(View.VISIBLE);
            String[] menu = result.split("\n");
            for (int i = 0; i < menuTextViews.length; i++) {
                menuTextViews[i].setText(menu.length > i ? menu[i] : "");
            }
        });
    }

    //메뉴 선택창 출력
    private void showSelectionScreen() {
        menuDisplayLayout.setVisibility(View.GONE);
        selectionLayout.setVisibility(View.VISIBLE);
    }

    // 스크래이핑 결과를 각각의 변수에 넘김.
    @Override
    public void onScrapingComplete(String[] result, String mealType) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String menu : result) {
            stringBuilder.append(menu).append("\n");
        }

        if ("breakfast".equals(mealType)) {
            menuFileManager.saveToFile(BREAKFAST_FILE, stringBuilder.toString());
        } else if ("lunch".equals(mealType)) {
            menuFileManager.saveToFile(LUNCH_FILE, stringBuilder.toString());
        } else if ("dinner".equals(mealType)) {
            menuFileManager.saveToFile(DINNER_FILE, stringBuilder.toString());
        }
    }
}

