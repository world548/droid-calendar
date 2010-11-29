package com.kshun.droidcalendar.view;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

public class DefaultCalendarCellView extends AbstractCalendarCellView{
	private static FrameLayout.LayoutParams FW = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP|Gravity.CENTER_HORIZONTAL);
	private static FrameLayout.LayoutParams BG = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 60, Gravity.CENTER);
	private DayModel _model = null;
	private TextView _dayOnMonth = null;
	private View _backGround = null;
	private int _c_touched = Color.rgb(255, 180, 80);
	private int _c_backGround = Color.rgb(20, 20, 20);
	private int _c_today = Color.rgb(255, 128, 80);
	private int _c_sunday = Color.rgb(255, 180, 180);
	private int _c_saturday = Color.rgb(180, 180, 255);
	private int _c_day = Color.rgb(255, 255, 255);

	public DefaultCalendarCellView(Context context) {
		super(context);
		_backGround = new View(context);
//		_backGround.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//				switch(event.getAction()){
//				case MotionEvent.ACTION_UP:
//					setBGColor();
//					break;
//				case MotionEvent.ACTION_DOWN:
//					_backGround.setBackgroundColor(_c_touched);
//					break;
//				}
//				return true;
//			}
//		});
		addView(_backGround, 0, BG);
		_dayOnMonth = new TextView(context);
		_dayOnMonth.setGravity(Gravity.CENTER);
		//_dayOnMonth.setGravity(Gravity.CENTER);
		FW.setMargins(2, 2, 2, 2);
		addView(_dayOnMonth, 1, FW);
	}

	@Override
	public void setDayModel(DayModel model) {
		_model = model;
	}


	@Override
	protected void onDraw (Canvas canvas){
		super.onDraw(canvas);
		_dayOnMonth.setText(Integer.toString(_model.getDayOfMonth()));
		setTextColor();
		setTextSize();
		setBGColor();
	}

	private void setBGColor(){
		if(_model.isToday()){
			_backGround.setBackgroundColor(_c_today);
		}else{
			_backGround.setBackgroundColor(_c_backGround);
		}
	}

	private void setTextSize() {
		if(CalendarFactory.isShownMonth(_model.getParentMonthModel())){
			_dayOnMonth.setTextSize(20);
		}else{
			_dayOnMonth.setTextSize(10);
		}
	}

	private void setTextColor() {
		if(Calendar.SUNDAY == _model.getDayOfWeek()){
			_dayOnMonth.setTextColor(_c_sunday);
		}else if(Calendar.SATURDAY == _model.getDayOfWeek()){
			_dayOnMonth.setTextColor(_c_saturday);
		}else{
			_dayOnMonth.setTextColor(_c_day);
		}
	}
}
