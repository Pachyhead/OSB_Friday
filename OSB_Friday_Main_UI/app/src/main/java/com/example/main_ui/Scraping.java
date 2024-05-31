package com.example.main_ui;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Scraping {
    public void Scrapingresult() {
        try {
            Document doc = Jsoup.connect("https://www.cbnucoop.com/service/restaurant/").get(); //학식 사이트 웹스크롤링

            // Save the HTML content to a local file
            File fileBreakfast = new File("tmp/result1.txt"); //한빛식당 아침 메뉴 데이터를 result1.txt로 저장
            if(fileBreakfast.createNewFile());
            Elements element1 = doc.select(".menu[data-table^=18-9]");
            if (element1 != null) {
                String htmlContent1 = element1.outerHtml();
                FileWriter writer1 = new FileWriter(fileBreakfast);
                writer1.write(htmlContent1);
                System.out.println("HTML content saved to: " + "tmp/result1.txt");
                writer1.close();
            }

            File fileLunch = new File("tmp/result2.txt"); //한빛식당 점심 메뉴 데이터
            if (fileLunch.createNewFile());
            Elements element2 = doc.select(".menu[data-table^=18-8]");
            if (element2 != null) {
                String htmlContent2 = element2.outerHtml();
                FileWriter writer2 = new FileWriter(fileLunch);
                writer2.write(htmlContent2);
                System.out.println("HTML content saved to: " + "tmp/result2.txt");
                writer2.close();
            }

            File fileDinner = new File("tmp/result3.txt"); //한빛식당 저녁 메뉴 데이터
            if (fileDinner.createNewFile());
            Elements element3 = doc.select(".menu[data-table^=18-10]");
            if (element3 != null) {
                String htmlContent3 = element3.outerHtml();
                FileWriter writer3 = new FileWriter(fileDinner);
                writer3.write(htmlContent3);
                System.out.println("HTML content saved to: " + "tmp/result3.txt");
                writer3.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}