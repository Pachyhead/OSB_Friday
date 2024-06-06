package com.example.main_ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WeekMenuActivity extends AppCompatActivity {

    private ListView[] weekMenuListArr = new ListView[5];
    private int[] menuListViewIds = {
            R.id.Weekly_Menu_Monday_list,
            R.id.Weekly_Menu_Tuesday_list,
            R.id.Weekly_Menu_Wednesday_list,
            R.id.Weekly_Menu_Thursday_list,
            R.id.Weekly_Menu_Friday_list
    };
    private ArrayAdapter<String>[] menuAdapters = new ArrayAdapter[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_week_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve the meals data from the intent
        String[] meals = getIntent().getStringArrayExtra("meals");

        if (meals == null) {
            meals = new String[] {
                    "Pasta Salad Soup Bread", // Monday
                    "Chicken Rice Beans Dessert", // Tuesday
                    "Fish Chips Peas", // Wednesday
                    "Beef MashedPotatoes Gravy Pie", // Thursday
                    "Pizza Fries Soda IceCream" // Friday
            };
        }

        String[][] menuDatabase = new String[5][];
        for (int i = 0; i < meals.length; i++) {
            menuDatabase[i] = meals[i].split(" ");
        }

        for (int i = 0; i < 5; i++) {
            weekMenuListArr[i] = findViewById(menuListViewIds[i]);
            menuAdapters[i] = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, menuDatabase[i]);
            weekMenuListArr[i].setAdapter(menuAdapters[i]);
        }
    }
}