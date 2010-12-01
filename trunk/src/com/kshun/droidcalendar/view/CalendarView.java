package com.kshun.droidcalendar.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CalendarView<T extends AbstractCalendarCellView> extends LinearLayout {
	private static final SimpleDateFormat DEFUALT_SDF = new SimpleDateFormat("yyyy/MM");
	private TextView _title = null;
	private SimpleDateFormat _titleSdf = DEFUALT_SDF;
	private float _titleSize = 20f;
	private DayModel _currentDay = null;
	private TableLayout _layout = null;
	private AbstractCalendarCellView[][] _cells = new AbstractCalendarCellView[5][7];

	public CalendarView(Context context, Class<T> clazz) {
		super(context);
		setWillNotDraw(false);
		setOrientation(LinearLayout.VERTICAL);
		_title = new TextView(context);
		_title.setGravity(Gravity.CENTER);
		_title.setTextSize(_titleSize);
		addView(_title);
		_layout = new TableLayout(getContext());
		_layout.setStretchAllColumns(true);

		Class<?>[] types = { Context.class, CalendarView.class };
		Object[] args = { context, this };
		Constructor<T> constructor;
		try {
			constructor = clazz.getConstructor(types);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < _cells.length; i++) {
			TableRow tableRow = new TableRow(getContext());
			for (int j = 0; j < _cells[0].length; j++) {
				try {
					_cells[i][j]  = constructor.newInstance(args);
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
			_layout.addView(tableRow);
		}
		addView(_layout);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (_currentDay == null) {
			_currentDay = CalendarFactory.getToday();
		}
		repaintCalendar(_currentDay);
	}

	void toNextMonth(){
		_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
		repaintCalendar(_currentDay);
	}

	void toLastMonth(){
		_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
		repaintCalendar(_currentDay);
	}

	public void setTitleSimpleDateFormat(SimpleDateFormat sdf){
		_titleSdf = sdf;
	}

	public void setTitleTextView(TextView title){
		_title = title;
	}

	public TextView getTitleTextView(){
		return _title;
	}

	private void repaintCalendar(DayModel dayModel) {
		_title.setText(_titleSdf.format(dayModel.getTime()));
		CalendarFactory.setShownMonth(dayModel.getParentMonthModel());
		DayModel targetDay = CalendarFactory.getCalendarStartSunDay(dayModel);
		for (AbstractCalendarCellView[] cellRow : _cells) {
			for (AbstractCalendarCellView cell : cellRow) {
				cell.setDayModel(targetDay);
				targetDay = CalendarFactory.getNextDay(targetDay);
			}
		}
	}
}
