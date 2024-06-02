
package com.example.main_ui;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.util.ArrayList;
import java.util.List;

public class WeekMenuActivity extends AppCompatActivity {

    private ListView[] weekMenuListArr = new ListView[5];
    private int[] menuListViewIds = {
            R.id.Weekly_Menu_Monday_list,
            R.id.Weekly_Menu_Tuesday_list,
            R.id.Weekly_Menu_Wednesday_list,
            R.id.Weekly_Menu_Thursday_list,
            R.id.Weekly_Menu_Friday_list
    };
    private MenuAdapter[] menuAdapters = new MenuAdapter[5];
    private String[][] meals = new String[5][];
    private String[][] calories = new String[5][];

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

        // Initialize meals and calories arrays with default values
        meals[0] = new String[]{"Pasta", "Salad", "Soup", "Bread"}; // Monday
        calories[0] = new String[]{"400", "150", "100", "200"}; // Monday

        meals[1] = new String[]{"Chicken", "Rice", "Beans", "Dessert"}; // Tuesday
        calories[1] = new String[]{"450", "200", "150", "300"}; // Tuesday

        meals[2] = new String[]{"Fish", "Chips", "Peas", "Tart"}; // Wednesday
        calories[2] = new String[]{"500", "250", "100", "350"}; // Wednesday

        meals[3] = new String[]{"Beef", "MashedPotatoes", "Gravy", "Pie"}; // Thursday
        calories[3] = new String[]{"600", "300", "100", "400"}; // Thursday

        meals[4] = new String[]{"Pizza", "Fries", "Soda", "IceCream"}; // Friday
        calories[4] = new String[]{"700", "350", "150", "500"}; // Friday

        for (int i = 0; i < 5; i++) {
            weekMenuListArr[i] = findViewById(menuListViewIds[i]);
            List<MenuItem> menuItems = new ArrayList<>();
            for (int j = 0; j < meals[i].length; j++) {
                menuItems.add(new MenuItem(meals[i][j], calories[i][j]));
            }
            menuAdapters[i] = new MenuAdapter(this, menuItems);
            weekMenuListArr[i].setAdapter(menuAdapters[i]);
        }
    }

    // Function to update the meals and calories arrays and refresh the ListView elements
    public void updateMeals(String[] newMeals, String[] newCalories) {
        if (newMeals.length != 5 || newCalories.length != 5) {
            throw new IllegalArgumentException("Meals and calories arrays must contain exactly 5 elements each.");
        }
        for (int i = 0; i < 5; i++) {
            meals[i] = newMeals[i].split(" ");
            calories[i] = newCalories[i].split(" ");
            List<MenuItem> updatedMenuItems = new ArrayList<>();
            for (int j = 0; j < meals[i].length; j++) {
                updatedMenuItems.add(new MenuItem(meals[i][j], calories[i][j]));
            }
            updateMenuListData(i, updatedMenuItems);
        }
    }

    private void updateMenuListData(int index, List<MenuItem> newData) {
        if (index >= 0 && index < menuAdapters.length) {
            MenuAdapter adapter = menuAdapters[index];
            adapter.clear();
            adapter.addAll(newData);
            adapter.notifyDataSetChanged();
        }
    }
}
