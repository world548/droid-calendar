package com.kshun.droidcalendar.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CalendarView extends LinearLayout {

	private String[] _dateOfWeekHedder = null;
	private TextView _monthTitle = null;
	private SimpleDateFormat _monthTitleSDF = null;
	private float _monthTitleSize = 15f;
	private DayModel _currentDay = null;
	private TableLayout _calendarTable = null;
	private AbstractCalendarCellView[][] _cells = new AbstractCalendarCellView[6][7];
	private AnimationSet _toNextManth = null;
	private AnimationSet _toLastManth = null;
	private Class<?> _clazz = null;
	private OnCalendarCellSelectedListener _onCalendarCellSelectedListener = null;

	CalendarView(Context context, Class<?> clazz, String[] dateOfWeekHedder, SimpleDateFormat monthTitleSDF) {
		super(context);
		_clazz = clazz;
		_dateOfWeekHedder = dateOfWeekHedder;
		_monthTitleSDF = monthTitleSDF;
		setWillNotDraw(false);
		setOrientation(LinearLayout.VERTICAL);
	}

	void addMonthTitle() {
		_monthTitle = new TextView(getContext());
		_monthTitle.setGravity(Gravity.CENTER);
		_monthTitle.setTextSize(_monthTitleSize);
		addView(_monthTitle);
	}

	void addCalendarTable() {
		_calendarTable = new TableLayout(getContext());
		_calendarTable.setStretchAllColumns(true);
		Class<?>[] types = { Context.class, CalendarView.class };
		Object[] args = { getContext(), this };
		Constructor<?> constructor;
		try {
			constructor = _clazz.getConstructor(types);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		TableRow hedder = new TableRow(getContext());
		for(String str : _dateOfWeekHedder){
			TextView text = new TextView(getContext());
			text.setText(str);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(15);
			hedder.addView(text);
		}
		_calendarTable.addView(hedder);

		for (int i = 0; i < _cells.length; i++) {
			TableRow tableRow = new TableRow(getContext());
			for (int j = 0; j < _cells[0].length; j++) {
				try {
					_cells[i][j]  = (AbstractCalendarCellView)constructor.newInstance(args);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				tableRow.addView(_cells[i][j]);
			}
			_calendarTable.addView(tableRow);
		}
		addView(_calendarTable);
	}

	void initializeFlickAnimetion() {
		AnimationListener aListener = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				repaintCalendar(_currentDay);
			}
		};
		_toNextManth = new AnimationSet(true);
		_toNextManth.addAnimation(new AlphaAnimation(0.9f, 0.2f));
		_toNextManth.addAnimation(new TranslateAnimation(00, -400, 0, 0));
		_toNextManth.setDuration(200);
		_toNextManth.setAnimationListener(aListener);

		_toLastManth = new AnimationSet(true);
		_toLastManth.addAnimation(new AlphaAnimation(0.9f, 0.2f));
		_toLastManth.addAnimation(new TranslateAnimation(00, 400, 0, 0));
		_toLastManth.setDuration(200);
		_toLastManth.setAnimationListener(aListener);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (_currentDay == null) {
			_currentDay = CalendarFactory.getToday();
			repaintCalendar(_currentDay);
		}
	}

	void toNextMonth() {
		_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
		if(_toNextManth != null){
			this.startAnimation(_toNextManth);
		}
	}

	void toLastMonth() {
		_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
		if(_toLastManth != null){
			this.startAnimation(_toLastManth);
		}
	}

	void setOnCalendarCellSelectedListener(OnCalendarCellSelectedListener onCalendarCellSelectedListener){
		_onCalendarCellSelectedListener = onCalendarCellSelectedListener;
	}

	private void repaintCalendar(DayModel dayModel) {
		_monthTitle.setText(_monthTitleSDF.format(dayModel.getTime()));
		CalendarFactory.setShownMonth(dayModel.getParentMonthModel());
		DayModel targetDay = CalendarFactory.getCalendarStartSunDay(dayModel);
		for (AbstractCalendarCellView[] cellRow : _cells) {
			for (AbstractCalendarCellView cell : cellRow) {
				cell.setDayModel(targetDay);
				if(_onCalendarCellSelectedListener != null){
					cell.setOnCalendarCellSelectedListener(_onCalendarCellSelectedListener);
				}
				targetDay = CalendarFactory.getNextDay(targetDay);
			}
		}
	}
}
