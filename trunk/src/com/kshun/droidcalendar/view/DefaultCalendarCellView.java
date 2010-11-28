package com.kshun.droidcalendar.view;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

public class DefaultCalendarCellView extends AbstractCalendarCellView{
	private static LinearLayout.LayoutParams FW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	private DayModel _model = null;
	private TextView _dayOnMonth = null;

	public DefaultCalendarCellView(Context context) {
		super(context);
		_dayOnMonth = new TextView(context);
		_dayOnMonth.setLayoutParams(FW);
		_dayOnMonth.setGravity(Gravity.CENTER);
		addView(_dayOnMonth);
	}

	@Override
	public void setDayModel(DayModel model) {
		_model = model;

		if(Calendar.SUNDAY == _model.getDayOfWeek()){
			_dayOnMonth.setTextColor(Color.RED);
		}else if(Calendar.SATURDAY == _model.getDayOfWeek()){
			_dayOnMonth.setTextColor(Color.BLUE);
		}
		if(CalendarFactory.isShownMonth(_model.getParentMonthModel())){
			_dayOnMonth.setTextSize(15);
		}else{
			_dayOnMonth.setTextSize(10);
		}
		if(_model.isToday()){
			setBackgroundColor(Color.DKGRAY);
		}else{
			setBackgroundColor(Color.LTGRAY);
		}

	}


	@Override
	protected void onDraw (Canvas canvas){
		super.onDraw(canvas);
		_dayOnMonth.setText(Integer.toString(_model.getDayOfMonth()));
	}
}
