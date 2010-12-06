package com.kshun;

import java.text.SimpleDateFormat;

import com.kshun.droidcalendar.model.DayModel;
import com.kshun.droidcalendar.view.AbstractCalendarCellView;
import com.kshun.droidcalendar.view.CalendarView;
import com.kshun.droidcalendar.view.CalendarViewBuilder;
import com.kshun.droidcalendar.view.DefaultCalendarCellView;
import com.kshun.droidcalendar.view.OnCalendarCellSelectedListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i("app", "onCreate");
		CalendarViewBuilder.setCalendarCellClass(DefaultCalendarCellView.class);
		CalendarViewBuilder.setDateOfWeekHedder(new String[]{"ì˙","åé", "âŒ","êÖ","ñÿ","ã‡","ìy"});
		CalendarViewBuilder.setMonthTitleHedder(new SimpleDateFormat("yyyyîN MMåé"));
		CalendarViewBuilder.setFlickAnimetionOn(true);
		CalendarViewBuilder.setOnCalendarCellSelectedListener(new OnCalendarCellSelectedListener() {
			@Override
			public void onCalendarCellSelectedListener(AbstractCalendarCellView view) {
				DayModel dayModel = view.getDayModel();
				Toast.makeText(getApplicationContext(), dayModel.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		CalendarView view = CalendarViewBuilder.build(getApplicationContext(), getResources());

		((LinearLayout) findViewById(R.id.root)).addView(view);
	}
}