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
}
