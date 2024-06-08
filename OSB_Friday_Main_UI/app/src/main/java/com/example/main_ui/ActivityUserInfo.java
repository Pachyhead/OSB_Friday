package com.example.main_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ActivityUserInfo extends AppCompatActivity {

    private EditText inputGender, inputWeight, inputHeight, inputAge, inputActivityLevel;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);

        inputGender = findViewById(R.id.input_gender);
        inputWeight = findViewById(R.id.input_weight);
        inputHeight = findViewById(R.id.input_height);
        inputAge = findViewById(R.id.input_age);
        inputActivityLevel = findViewById(R.id.input_activity_level);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = inputGender.getText().toString();
                String weight = inputWeight.getText().toString();
                String height = inputHeight.getText().toString();
                String age = inputAge.getText().toString();
                String activityLevel = inputActivityLevel.getText().toString();

                saveUserInfo(gender, weight, height, age, activityLevel);
                Toast.makeText(ActivityUserInfo.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveUserInfo(String gender, String weight, String height, String age, String activityLevel) {
        Properties properties = new Properties();
        properties.setProperty("Gender", gender);
        properties.setProperty("Weight", weight);
        properties.setProperty("Height", height);
        properties.setProperty("Age", age);
        properties.setProperty("ActivityLevel", activityLevel);

        try (FileOutputStream fos = openFileOutput("user_info.properties", MODE_PRIVATE)) {
            properties.store(fos, "User Info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}