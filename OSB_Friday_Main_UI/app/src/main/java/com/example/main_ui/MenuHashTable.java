package com.example.main_ui;

import android.content.Context;

import java.util.HashMap;

public class MenuHashTable {
    private HashMap<String, String> menuMap;

    public MenuHashTable(Context context, String fileName) {
        menuMap = new HashMap<>();
    }

    // 키-값 쌍을 설정하는 함수
    public boolean setMenu(String key, String value) {
        if (key == null || value == null) {
            return false;
        }
        menuMap.put(key, value);
        return true;
    }
}