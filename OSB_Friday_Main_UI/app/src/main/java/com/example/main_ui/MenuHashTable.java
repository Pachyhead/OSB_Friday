package com.example.main_ui;

import android.content.Context;

import java.util.HashMap;

public class MenuHashTable {
    private HashMap<String, String> menuMap;

    public MenuHashTable(Context context, String fileName) {
        menuMap = new HashMap<>();
    }
}