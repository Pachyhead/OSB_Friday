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
        loadFromAssets(context, fileName);
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

    // input 음식 이름의 맨 뒷 음절을 temp에 추가하며 매칭된 값을 찾는 함수
    public String findMatchingValue(String input) {
        String temp = "";
        String lastMatchedValue = null;

        // HashMap의 value 목록 가져오기
        for (int i = input.length() - 1; i >= 0; i--) {
            // temp에 현재 문자를 추가
            temp = input.charAt(i) + temp;

            // temp가 HashMap의 value 목록에 존재하는지 확인
            for (String value : menuMap.values()) {
                if (value.equals(temp)) {
                    lastMatchedValue = value;
                }
            }
        }

        // 마지막으로 체크된 value 반환
        return lastMatchedValue;
    }
}