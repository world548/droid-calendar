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
	private GestureDetector _gestureDetector = null;

	public CalendarView(Context context) {
		super(context);
		setWillNotDraw(false);
		setOrientation(LinearLayout.VERTICAL);
		_title = new TextView(context);
		_title.setGravity(Gravity.CENTER);
		addView(_title);
		// TODO 自動生成されたコンストラクター・スタブ
		_layout = new TableLayout(getContext());
		_layout.setStretchAllColumns(true);
		for (int i = 0; i < _cells.length; i++) {
			TableRow tableRow = new TableRow(getContext());
			for (int j = 0; j < _cells[0].length; j++) {
				_cells[i][j] = new DefaultCalendarCellView(context);
				tableRow.addView(_cells[i][j]);
			}
			_layout.addView(tableRow);
		}
		addView(_layout);

		_gestureDetector = new GestureDetector(context,
				new GestureDetector.OnGestureListener() {

					@Override
					public boolean onDown(MotionEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						Log.i("app", "onDown");
						return true;
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						Log.i("app", "onFling");
						if(velocityX > 0){
							//前の月へ
							_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
						}else{
							//次の月へ
							_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
						}
						repaintCalendar(_currentDay);
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						Log.i("app", "onLongPress");

					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						Log.i("app", "onScroll");
						return true;
					}

					@Override
					public void onShowPress(MotionEvent e) {
						Log.i("app", "onShowPress");
					}

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						Log.i("app", "onSingleTapUp");
						return true;
					}

				});
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自動生成されたメソッド・スタブ
				if(_gestureDetector.onTouchEvent(event)){

				}else if(v instanceof CalendarView){
					Log.i("app", "CalendarView");
				}else{
					Log.i("app", "else");
				}
				return true;
			}
		});
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
