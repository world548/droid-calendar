package com.kshun.droidcalendar.view;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CalendarView extends LinearLayout {
	private TextView _title = null;
	private DayModel _currentDay = null;
	private TableLayout _layout = null;
	private AbstractCalendarCellView[][] _cells = new AbstractCalendarCellView[5][7];


	public CalendarView(Context context) {
		super(context);
		setWillNotDraw(false);
		setOrientation(LinearLayout.VERTICAL);
		_title = new TextView(context);
		_title.setGravity(Gravity.CENTER);
		_title.setTextSize(20);
		addView(_title);
		// TODO 自動生成されたコンストラクター・スタブ
		_layout = new TableLayout(getContext());
		_layout.setStretchAllColumns(true);
		for (int i = 0; i < _cells.length; i++) {
			TableRow tableRow = new TableRow(getContext());
			for (int j = 0; j < _cells[0].length; j++) {
				_cells[i][j] = new DefaultCalendarCellView(context, this);
				tableRow.addView(_cells[i][j]);
			}
			_layout.addView(tableRow);
		}
		//LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		//llp.setMargins(3, 3, 3, 3);
		addView(_layout);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (_currentDay == null) {
			_currentDay = CalendarFactory.getToday();
		}
		repaintCalendar(_currentDay);
		// invalidate();
	}

	void toNextMonth(){
		//次の月へ
		_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
		repaintCalendar(_currentDay);
	}

	void toLastMonth(){
		//前の月へ
		_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
		repaintCalendar(_currentDay);
	}

	private void repaintCalendar(DayModel dayModel) {
		_title.setText(dayModel.getParentMonthModel().toString());
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
