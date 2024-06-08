package com.example.main_ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, CalendarAdapter.ItemClickListener{
    private TextView tv_month;

    // 캘린더뷰 어댑터
    private CalendarAdapter calendarAdapter;
    private WeekAdapter weeklistAdapter;

    // 요일 리스트
    private ArrayList<Day> dayList;
    private ArrayList<String> day_of_weekList;

    // 그리드뷰
    private RecyclerView rv_calendar;
    private RecyclerView rv_weeklist;

    private Calendar calendar;

    public static Context context;

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DATE = "date";

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        tv_month = findViewById(R.id.tv_month);

        rv_calendar = findViewById(R.id.rv_calendar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        rv_calendar.setLayoutManager(gridLayoutManager);
        rv_calendar.setItemViewCacheSize(42);

        rv_weeklist = findViewById(R.id.rv_day_of_week);
        GridLayoutManager gridLayoutManager_week = new GridLayoutManager(this, 7);
        rv_weeklist.setLayoutManager(gridLayoutManager_week);
        rv_weeklist.setItemViewCacheSize(7);

        setWeeklistbar();
        setCalendarView(getCalendar(savedInstanceState));
    }

    // 캘린더 인스턴스 생성 또는 불러오기
    private Calendar getCalendar(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        if (savedInstanceState != null) {
            calendar.set(savedInstanceState.getInt(YEAR), savedInstanceState.getInt(MONTH), 1);
        } else
            calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        setCalendarView(calendar);
    }

    // 요일 바 설정
    private void setWeeklistbar() {
        //gridview_day_of_week에 요일 리스트 표시
        day_of_weekList = new ArrayList<String>();
        day_of_weekList.add("SUN");
        day_of_weekList.add("MON");
        day_of_weekList.add("TUE");
        day_of_weekList.add("WED");
        day_of_weekList.add("THU");
        day_of_weekList.add("FRI");
        day_of_weekList.add("SAT");

        weeklistAdapter = new WeekAdapter(this, day_of_weekList);
        rv_weeklist.setAdapter(weeklistAdapter);

        dayList = new ArrayList<Day>();
    }

    // 달력 세팅
    private void setCalendarView(Calendar calendar) {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        dayList.clear();

        // 이번달 시작 요일
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);

        // 지난달 마지막 일자
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);

        lastMonthStartDay -= (dayOfMonth - 1) - 1;

        // 년월 표시
        tv_month.setText((this.calendar.get(Calendar.MONTH) + 1) + "");

        Day day;
        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;
            day = new Day();
            day.setDay(Integer.toString(date));
            day.setInMonth(false);

            dayList.add(day);
        }

        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new Day();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);

            dayList.add(day);
        }

        for (int i = 1; i < 35 - (thisMonthLastDay + dayOfMonth) + 1; i++) {
            day = new Day();
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
            dayList.add(day);
        }

        initCalendarAdapter();
    }

    // 캘린더뷰 어댑터 초기화
    private void initCalendarAdapter() {
        calendarAdapter = new CalendarAdapter(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dayList);
        calendarAdapter.setClickListener(this);
        rv_month.setAdapter(calendarAdapter);
    }

    // 캘린더뷰 아이템 클릭 리스너
    @Override
    public void onItemClick(View view, String day, boolean isInMonth) {
        Calendar itemCal = calendar;
        itemCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        String date = new SimpleDateFormat("yyyy.MM.dd.").format(itemCal.getTime());

        if (isInMonth) {
            Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
            intent.putExtra(IS_TODAY, false);
            intent.putExtra(DATE, date);
            startActivity(intent);
        }
    }
}
