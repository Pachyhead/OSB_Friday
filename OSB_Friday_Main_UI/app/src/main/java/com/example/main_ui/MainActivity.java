package com.example.main_ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements Scraping.OnScrapingCompleteListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Scraping task = new Scraping(this);
        task.execute();

    }
    @Override
    public void onScrapingComplete(String[] weeks) {
        // 스크래이핑 결과를 ui에 출력
        TextView monday = findViewById(R.id.text_monday);
        monday.setText(weeks[0]);
        TextView tuesday = findViewById(R.id.text_tuesday);
        tuesday.setText(weeks[1]);
        TextView wednesday = findViewById(R.id.text_wednesday);
        wednesday.setText(weeks[2]);
        TextView thursday = findViewById(R.id.text_thursday);
        thursday.setText(weeks[3]);
        TextView friday = findViewById(R.id.text_friday);
        friday.setText(weeks[4]);
    }

}

