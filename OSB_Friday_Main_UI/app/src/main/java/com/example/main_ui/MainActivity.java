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


public class MainActivity extends AppCompatActivity implements Scraping.OnScrapingCompleteListener{
    private LinearLayout selectionLayout;
    private ConstraintLayout menuDisplayLayout;
    private TextView[] menuTextViews;
    private String[] breakfastMenu;
    private String[] lunchMenu;
    private String[] dinnerMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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

        //onScrapingComplete로 받아온 메뉴를 각각 메뉴 버튼을 누르면 불러옴, back 버튼은 다시 선택창으로
        buttonBreakfast.setOnClickListener(v -> showMenu(breakfastMenu));
        buttonLunch.setOnClickListener(v -> showMenu(lunchMenu));
        buttonDinner.setOnClickListener(v -> showMenu(dinnerMenu));
        buttonBack.setOnClickListener(v -> showSelectionScreen());



    }
    //메뉴 출력
    private void showMenu(String[] menu) {
        selectionLayout.setVisibility(View.GONE);
        menuDisplayLayout.setVisibility(View.VISIBLE);

        if (menu != null) {
            for (int i = 0; i < menu.length; i++) {
                menuTextViews[i].setText(menu[i]);
            }
        }
    }

    //메뉴 선택창 출력
    private void showSelectionScreen() {
        menuDisplayLayout.setVisibility(View.GONE);
        selectionLayout.setVisibility(View.VISIBLE);
    }

    // 스크래이핑 결과를 각각의 변수에 넘김.
    @Override
    public void onScrapingComplete(String[] result, String mealType) {
        if ("breakfast".equals(mealType)) {
            breakfastMenu = result;
        } else if ("lunch".equals(mealType)) {
            lunchMenu = result;
        } else if ("dinner".equals(mealType)) {
            dinnerMenu = result;
        }

    }
}

