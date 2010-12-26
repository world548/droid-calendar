package com.kshun.droidcalendar.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CalendarView extends LinearLayout {

	private String[] _dateOfWeekHedder = null;
	private TextView _monthTitle = null;
	private SimpleDateFormat _monthTitleSDF = null;
	private float _monthTitleSize = 15f;
	private DayModel _currentDay = CalendarFactory.getToday();
	private TableLayout _calendarTable = null;
	private AbstractCalendarCellView[][] _cells = new AbstractCalendarCellView[6][7];
	private Class<?> _clazz = null;
	private CalendarCellEventListener _onCalendarCellSelectedListener = null;
	private CalendarCellViewParam _calendarCellViewParam = null;
	private CalendarViewFlipperHolder _viewFlipper = null;
	CalendarView(Context context, Class<?> clazz, String[] dateOfWeekHedder, SimpleDateFormat monthTitleSDF, CalendarCellViewParam calendarCellViewParam) {
		super(context);
		_clazz = clazz;
		_dateOfWeekHedder = dateOfWeekHedder;
		_monthTitleSDF = monthTitleSDF;
		_calendarCellViewParam = calendarCellViewParam;
		setWillNotDraw(false);
		setOrientation(LinearLayout.VERTICAL);
	}

	void setCalendarViewFlipperHolder(CalendarViewFlipperHolder viewFlipper){
		_viewFlipper = viewFlipper;
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

		TableRow hedder = new TableRow(getContext());
		for(String str : _dateOfWeekHedder){
			TextView text = new TextView(getContext());
			text.setText(str);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(15);
			hedder.addView(text);
		}
		_calendarTable.addView(hedder);
		Class<?>[] types = { Context.class, CalendarView.class, CalendarCellViewParam.class};
		Object[] args = { getContext(), this, _calendarCellViewParam};
		Constructor<?> constructor;
		try {
			constructor = _clazz.getConstructor(types);
			for (int i = 0; i < _cells.length; i++) {
				TableRow tableRow = new TableRow(getContext());
				for (int j = 0; j < _cells[0].length; j++) {
					_cells[i][j] = (AbstractCalendarCellView) constructor
							.newInstance(args);

					tableRow.addView(_cells[i][j]);
				}
				_calendarTable.addView(tableRow);
			}
			addView(_calendarTable);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	void onNextMonth() {
		_viewFlipper.onNextMonth(this);
	}

	void onLastMonth() {
		_viewFlipper.onLastMonth(this);
	}

	DayModel getCurrentDay(){
		return _currentDay;
	}

	void setCurrentDay(DayModel day){
		_currentDay = day;
		repaintCalendar();
	}

	void toNextMonth() {
		Log.i("month", "toNextMonth");

		_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
		repaintCalendar();
	}

	void toLastMonth() {
		Log.i("month", "toLastMonth");

		_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
		repaintCalendar();
	}

	void setOnCalendarCellSelectedListener(CalendarCellEventListener onCalendarCellSelectedListener){
		_onCalendarCellSelectedListener = onCalendarCellSelectedListener;
	}

	void repaintCalendar() {
		_monthTitle.setText(_monthTitleSDF.format(_currentDay.getTime()));
		CalendarFactory.setShownMonth(_currentDay.getParentMonthModel());
		DayModel targetDay = CalendarFactory.getCalendarStartSunDay(_currentDay);
		for (AbstractCalendarCellView[] cellRow : _cells) {
			for (AbstractCalendarCellView cell : cellRow) {
				cell.setDayModel(targetDay);
				if(_onCalendarCellSelectedListener != null){
					cell.setCalendarCellEventListener(_onCalendarCellSelectedListener);
				}
				targetDay = CalendarFactory.getNextDay(targetDay);
			}
		}
	}
}
