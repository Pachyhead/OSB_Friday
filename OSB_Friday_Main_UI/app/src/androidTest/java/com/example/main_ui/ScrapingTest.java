package com.example.main_ui;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ScrapingTest {

    private Context context;
    private Scraping scraping;
    private File file1;
    private File file2;
    private File file3;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        scraping = new Scraping(context);

        // 파일 객체 생성
        file1 = new File(context.getFilesDir(), "result1.txt");
        file2 = new File(context.getFilesDir(), "result2.txt");
        file3 = new File(context.getFilesDir(), "result3.txt");

        // 기존에 파일이 존재하면 삭제하여 테스트의 독립성을 유지
        if (file1.exists()) file1.delete();
        if (file2.exists()) file2.delete();
        if (file3.exists()) file3.delete();
    }

    @Test
    public void testScrapingResult_CreatesFiles() {
        // Scrapingresult 메소드 실행
        scraping.Scrapingresult();

        // 파일이 생성되고 데이터가 있는지 확인
        assertTrue(file1.exists() && file1.length() > 0);
        assertTrue(file2.exists() && file2.length() > 0);
        assertTrue(file3.exists() && file3.length() > 0);
    }

    @After
    public void tearDown() {
        // 테스트 후 파일 삭제
        if (file1.exists()) file1.delete();
        if (file2.exists()) file2.delete();
        if (file3.exists()) file3.delete();
    }
}
