package com.example.main_ui;

public class MenuItem {
    private String item;
    private String calories;

    public MenuItem(String item, String calories) {
        this.item = item;
        this.calories = calories;
    }

    public String getItem() {
        return item;
    }

    public String getCalories() {
        return calories;
    }
}
