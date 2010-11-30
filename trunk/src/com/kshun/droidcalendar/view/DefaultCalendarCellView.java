package com.kshun.droidcalendar.view;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

public class DefaultCalendarCellView extends AbstractCalendarCellView {
	private final int _c_touched = Color.rgb(255, 180, 80);
	private final int _c_backGround = Color.rgb(20, 20, 20);
	private final int _c_today = Color.rgb(255, 128, 80);
	private final int _c_sunday = Color.rgb(255, 180, 180);
	private final int _c_saturday = Color.rgb(180, 180, 255);
	private final int _c_day = Color.rgb(255, 255, 255);
	private static FrameLayout.LayoutParams FW = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT,
			FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP
					| Gravity.CENTER_HORIZONTAL);
	private static FrameLayout.LayoutParams BG = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT, 60, Gravity.CENTER);
	private DayModel _model = null;
	private TextView _dayOnMonth = null;
	private View _backGround = null;
	private int _backGroundColor = _c_backGround;

	private CalendarView _parent = null;
	private GestureDetector _gestureDetector = null;

	public DefaultCalendarCellView(Context context) {
		super(context);
		setWillNotDraw(false);
		_backGround = new View(context) {
			protected void onDraw(Canvas canvas) {
				Paint back = new Paint();
				back.setStyle(Paint.Style.FILL);
				back.setColor(_backGroundColor);
				canvas.drawRect(0, 0, getWidth(), getHeight(), back);
				Paint border = new Paint();
				border.setStyle(Paint.Style.STROKE);
				border.setStrokeWidth(0.1f);
				border.setARGB(255, 255, 255, 255);
				//Log.i("app", getWidth() + ":" + getHeight());
				canvas.drawRect(0, 0, getWidth(), getHeight(), border);
			}
		};
		_backGround.setWillNotDraw(false);
		_gestureDetector = new GestureDetector(context,	new CellGestureListener());
		_backGround.setOnTouchListener(new CellOnTouchListener());
		addView(_backGround, 0, BG);
		_dayOnMonth = new TextView(context);
		_dayOnMonth.setGravity(Gravity.CENTER);
		// _dayOnMonth.setGravity(Gravity.CENTER);
		FW.setMargins(2, 2, 2, 2);
		addView(_dayOnMonth, 1, FW);
	}

	public DefaultCalendarCellView(Context context, CalendarView parent) {
		this(context);
		_parent = parent;
	}

	@Override
	public void setDayModel(DayModel model) {
		_model = model;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Log.i("app", "onDraw");
		_dayOnMonth.setText(Integer.toString(_model.getDayOfMonth()));
		setTextColor();
		setTextSize();
	}

	private void setBGColor() {
		if (_model.isToday()) {
			_backGroundColor = _c_today;
		} else {
			_backGroundColor = _c_backGround;
		}
	}

	private void setTextSize() {
		if (CalendarFactory.isShownMonth(_model.getParentMonthModel())) {
			_dayOnMonth.setTextSize(20);
		} else {
			_dayOnMonth.setTextSize(10);
		}
	}

	private void setTextColor() {
		if (Calendar.SUNDAY == _model.getDayOfWeek()) {
			_dayOnMonth.setTextColor(_c_sunday);
		} else if (Calendar.SATURDAY == _model.getDayOfWeek()) {
			_dayOnMonth.setTextColor(_c_saturday);
		} else {
			_dayOnMonth.setTextColor(_c_day);
		}
	}

	class CellGestureListener implements GestureDetector.OnGestureListener{
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			Log.i("app", "onDown");
			_backGroundColor = _c_today;
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			Log.i("app", "onFling");
			if (_parent != null) {
				if (velocityX > 0) {
					_parent.toLastMonth();
				} else {
					_parent.toNextMonth();
				}
				setBGColor();
			}
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
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			Log.i("app", "onShowPress");
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("app", "onSingleTapUp");
			setBGColor();
			return true;
		}
	}

	class CellOnTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO 自動生成されたメソッド・スタブ
			if (_gestureDetector.onTouchEvent(event)) {

			} else {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					Log.i("app", "ACTION_UP");

					break;
				case MotionEvent.ACTION_DOWN:
					Log.i("app", "ACTION_DOWN");

					break;
				}

			}
			_backGround.invalidate();
			return true;
		}
	}
}