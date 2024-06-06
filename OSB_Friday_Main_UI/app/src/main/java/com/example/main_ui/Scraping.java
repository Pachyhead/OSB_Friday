package com.example.main_ui;

import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

//백그라운드 실행을 하는 AsynTask
public class Scraping extends AsyncTask<Void, Void, String[]> {

    // Scraping 종료 시 알리기 위한 listener
    private OnScrapingCompleteListener listener;
    private String mealType;

    //listener의 생성자
    public Scraping(OnScrapingCompleteListener listener, String mealType) {
        this.listener = listener;
        this.mealType = mealType;
    }

    @Override
    //백그라운드에서 실행되는 메소드
    protected String[] doInBackground(Void... voids) {
        //요일별 정보 저장하기 위한 String 배열
        String[] weeksLunchMenu = new String[]{"", "", "", "", ""};
        try {
            // 학식 사이트 스크레이핑
            Document doc = Jsoup.connect("https://www.cbnucoop.com/service/restaurant/").get();
            Elements elements = null;

            elements = doc.select(".menu[data-table^=18-8]");


            //반복문의 반복 횟수(일주일은 5일이므로 5)
            int count = 5;
            for (Element element : elements) {
                count--;

                // Html 태그 제거, 문자열만 남김
                String temp = element.text();

                // ￦ 이후의 문자열 삭제 == 가격 정보는 표시 안함
                int index = temp.indexOf('￦');
                if (index != -1)
                    temp = temp.substring(0, index);

                // 남은 문자열(그 날짜의 학식)을 저장
                weeksLunchMenu[count] += temp;


                if (count == 0) break;
            }
        } catch (IOException e) {
            //스크레이핑 실패 시 예외처리
            Log.e("ScrapingTask", "Error scraping website", e);
        }
        //결과 문자열 배열 return
        return weeksLunchMenu;
    }

    @Override
    //doInBackground 이후 실행
    //doInBackground의 return을 인수로 받게 됨
    protected void onPostExecute(String[] result) {
        //onScrapingComplete 메서드를 call
        super.onPostExecute(result);
        if (listener != null){
            listener.onScrapingComplete(result, mealType);
        }
    }

    public interface OnScrapingCompleteListener {
        void onScrapingComplete(String[] result, String mealType);
    }
}