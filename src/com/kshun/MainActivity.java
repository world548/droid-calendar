package com.kshun;

import java.text.SimpleDateFormat;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;
import com.kshun.droidcalendar.view.AbstractCalendarCellView;
import com.kshun.droidcalendar.view.CalendarViewBuilder;
import com.kshun.droidcalendar.view.DefaultCalendarCellView;
import com.kshun.droidcalendar.view.CalendarCellEventListener;
import com.kshun.droidcalendar.view.DefaultCalendarCellViewParams;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	TextView _memo = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i("app", "onCreate");
		DefaultCalendarCellViewParams xMasParam = new DefaultCalendarCellViewParams();
		xMasParam.setFontColorPrior(Color.RED);
		xMasParam.setBgColor(Color.rgb(0, 128, 0));
		xMasParam.setColorCellBorder(Color.RED);

		DayModel xMas = CalendarFactory.getDayModel(2010, 12, 25);
		xMas.setCalendarCellViewParam(xMasParam);

		CalendarViewBuilder.setCalendarCellClass(DefaultCalendarCellView.class);
		CalendarViewBuilder.setDateOfWeekHedder(new String[]{"ì˙","åé", "âŒ","êÖ","ñÿ","ã‡","ìy"});
		CalendarViewBuilder.setMonthTitleHedder(new SimpleDateFormat("yyyyîN MMåé"));
		CalendarViewBuilder.setOnCalendarCellSelectedListener(new CalendarCellEventListener() {
			@Override
			public void onSelectionChanged(AbstractCalendarCellView view, boolean isSelected) {
				_memo.setText(view.getDayModel() + " isSelected=" +  isSelected);
			}
			@Override
			public void onLongPress(AbstractCalendarCellView view) {
				DayModel dayModel = view.getDayModel();
				Toast.makeText(getApplicationContext(), dayModel.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		CalendarViewBuilder.setCalendarCellViewParam(new DefaultCalendarCellViewParams());
		View view = CalendarViewBuilder.build(getApplicationContext(), getResources());
		LinearLayout layout = (LinearLayout) findViewById(R.id.root);
		layout.addView(view);
		_memo = new TextView(getApplicationContext());
		_memo.setEnabled(false);
		layout.addView(_memo, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
	}
}