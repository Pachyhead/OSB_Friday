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
}
