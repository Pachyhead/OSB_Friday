package com.example.main_ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//메뉴 저장 파일
public class MenuFileManager {

    private Context context;

    public MenuFileManager(Context context) {
        this.context = context;
    }

    // 파일 저장 메소드
    public void saveToFile(String fileName, String data) {
        new SaveFileTask(fileName).execute(data);
    }

    // 파일 읽기 메소드
    public void loadFromFile(String fileName, OnFileLoadCompleteListener listener) {
        new LoadFileTask(fileName, listener).execute();
    }

    // 백그라운드 실행으로 파일 쓰기
    private class SaveFileTask extends AsyncTask<String, Void, Void> {
        private String fileName;

        public SaveFileTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        protected Void doInBackground(String... params) {
            String data = params[0];
            try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
                writer.write(data);
                Log.d("MenuFileManager", "Data saved to file: " + fileName);
            } catch (IOException e) {
                Log.e("MenuFileManager", "Error writing to file: " + fileName, e);
            }
            return null;
        }
    }

    // 백그라운드 실행으로 파일 읽기
    private class LoadFileTask extends AsyncTask<Void, Void, String> {
        private String fileName;
        private OnFileLoadCompleteListener listener;

        public LoadFileTask(String fileName, OnFileLoadCompleteListener listener) {
            this.fileName = fileName;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try (FileInputStream fis = context.openFileInput(fileName);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                Log.d("MenuFileManager", "Data loaded from file: " + fileName);
            } catch (IOException e) {
                Log.e("MenuFileManager", "Error reading from file: " + fileName, e);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (listener != null) {
                listener.onFileLoadComplete(result);
            }
        }
    }

    // Interface for file load completion callback
    public interface OnFileLoadCompleteListener {
        void onFileLoadComplete(String result);
    }
}