package com.example.main_ui;

import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class getCalorie {
    // 메뉴 자체를 저장하는 배열
    private String[] weekMenu;
    private getCalCompleteListener listener;
    public getCalorie(getCalCompleteListener listener) {
        this.listener = listener;
    }

    public void setWeekMenu(String[] k){
        weekMenu = k;
        new weekMenuSave().execute();
    }
    private class weekMenuSave extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {


            // fatsecret url
            String gettingUrl = "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/search?q=";


            String[] totalCal = new String[weekMenu.length];


            //fatsecret url에 검색 결과가 나오게 붙임
            try {
                for (int i = 0; i < weekMenu.length; i++) {
                    String searchUrl = gettingUrl + weekMenu[i];
                    Document doc = Jsoup.connect(searchUrl).get();
                    Element element = doc.select("div.smallText.greyText.greyLink").first();
                    if (element != null) {
                        String fullText = element.text();
                        Pattern pattern = Pattern.compile("(칼로리.*단백질: \\d+\\.\\d+g).*");
                        Matcher matcher = pattern.matcher(fullText);
                        if (matcher.find()) {
                            totalCal[i] = matcher.group(1);
                        }
                    } else {
                        totalCal[i] = "정보 없음"; // No information found
                    }
                }
            } catch (IOException e) {
                // 스크레이핑 실패 시 예외처리
                Log.e("getCalorieTask", "Error scraping website", e);
            }
            return totalCal;
        }


        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if (listener != null) {
                listener.getCalComplete(result);
            }
        }
    }
    public interface getCalCompleteListener {
        void getCalComplete(String[] result);
    }
}
