package com.example.main_ui;

import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.main_ui.R;
import com.example.main_ui.WeekMenuActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WeekMenuActivityTest {

    private ActivityScenario<WeekMenuActivity> scenario;

    private final String[][] expectedMeals = {
            {"Pasta", "Salad", "Soup", "Bread"},
            {"Chicken", "Rice", "Beans", "Dessert"},
            {"Fish", "Chips", "Peas", "Tart"},
            {"Beef", "MashedPotatoes", "Gravy", "Pie"},
            {"Pizza", "Fries", "Soda", "IceCream"}
    };

    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(WeekMenuActivity.class);
    }

    @After
    public void tearDown() {
        scenario.close();
    }

    @Test
    public void testActivityInitialization() {
        scenario.onActivity(activity -> {
            // 활동이 null이 아닌지 확인합니다
            assertNotNull("Activity should not be null", activity);

            // 각 ListView가 올바르게 초기화되었는지 확인합니다
            for (int i = 0; i < 5; i++) {
                ListView listView = activity.findViewById(activity.menuListViewIds[i]);
                assertNotNull("ListView should not be null for index " + i, listView);

                // ListView의 어댑터가 MenuAdapter 인스턴스인지 확인합니다
                ListAdapter adapter = listView.getAdapter();
                assertTrue("Adapter should be an instance of MenuAdapter for index " + i, adapter instanceof MenuAdapter);

                // 어댑터에 최소한 3개의 아이템이 있는지 확인합니다
                MenuAdapter menuAdapter = (MenuAdapter) adapter;
                assertTrue("Adapter item count should be at least 3 for index " + i, menuAdapter.getCount() >= 3);
            }
        });
    }
    @Test
    public void testWeekMenu() {
        scenario.onActivity(activity -> {
            // 주중 각 요일의 메뉴 항목이 올바르게 설정되었는지 확인합니다
            for (int i = 0; i < 5; i++) {
                ListView listView = activity.findViewById(activity.menuListViewIds[i]);
                MenuAdapter adapter = (MenuAdapter) listView.getAdapter();
                String[] expectedMenu = expectedMeals[i];

                // 각 요일의 메뉴 항목이 예상된 값과 일치하는지 확인합니다
                for (int j = 0; j < expectedMenu.length; j++) {
                    MenuItem menuItem = (MenuItem) adapter.getItem(j);
                    assertEquals("Menu item should match expected value", expectedMenu[j], menuItem.getItem());
                }
            }
        });
    }
    @Test
    public void testMondayMenu() {
        scenario.onActivity(activity -> {
            // 월요일의 메뉴 항목이 올바르게 설정되었는지 확인합니다
            ListView listView = activity.findViewById(R.id.Weekly_Menu_Monday_list);
            MenuAdapter adapter = (MenuAdapter) listView.getAdapter();
            String[] expectedMondayMenu = {"Pasta", "Salad", "Soup", "Bread"};

            // 월요일의 각 메뉴 항목이 예상된 값과 일치하는지 확인합니다
            for (int i = 0; i < expectedMondayMenu.length; i++) {
                MenuItem menuItem = (MenuItem) adapter.getItem(i);
                assertEquals("Monday menu item should match expected value", expectedMondayMenu[i], menuItem.getItem());
            }
        });
    }
}
