package com.kshun;

import com.kshun.droidcalendar.view.CalendarView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i("app", "onCreate");
        ((LinearLayout)findViewById(R.id.root)).addView(new CalendarView(getApplicationContext()));
    }
}