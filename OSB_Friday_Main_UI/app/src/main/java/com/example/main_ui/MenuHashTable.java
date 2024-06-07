package com.example.main_ui;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    // 키를 입력받아 값을 반환하는 함수
    public String getMenu(String key) {
        return menuMap.get(key);
    }

    // 메뉴 맵을 반환하는 함수 (디버깅용)
    public HashMap<String, String> getMenuMap() {
        return menuMap;
    }

    // assets 디렉토리에서 파일을 읽어오는 함수
    private void loadFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try (InputStream is = assetManager.open(fileName);
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    setMenu(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}