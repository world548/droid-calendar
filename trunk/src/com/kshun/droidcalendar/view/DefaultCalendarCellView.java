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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kshun.droidcalendar.model.CalendarFactory;
import com.kshun.droidcalendar.model.DayModel;

public class DefaultCalendarCellView extends AbstractCalendarCellView {
	public static final int DEFAULT_COLOR_SELECTED = Color.rgb(255, 180, 80);
	public static final int DEFAULT_COLOR_BACKGROUND = Color.rgb(20, 20, 20);
	public static final int DEFAULT_COLOR_TODAY = Color.rgb(255, 128, 80);
	public static final int DEFAULT_COLOR_SUNDAY = Color.rgb(255, 180, 180);
	public static final int DEFAULT_COLOR_SATURDAY = Color.rgb(180, 180, 255);
	public static final int DEFAULT_COLOR_DAY_OF_MONTH = Color.rgb(255, 255, 255);
	private static FrameLayout.LayoutParams FW = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT,
			FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP
					| Gravity.CENTER_HORIZONTAL);
	private static FrameLayout.LayoutParams BG = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT, 60, Gravity.CENTER);
	private DayModel _model = null;
	private TextView _dayOnMonth = null;
	private View _backGround = null;
	private int _backGroundColor = DEFAULT_COLOR_BACKGROUND;
	private OnCalendarCellSelectedListener _selectedListener = null;
	private CalendarView<?> _parent = null;
	private GestureDetector _gestureDetector = null;
	private boolean isSelected = false;
	public DefaultCalendarCellView(){
		super(null, null);
	}

	public DefaultCalendarCellView(Context context, CalendarView<?> parent) {
		super(context, parent);
		_parent = parent;
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
		_gestureDetector = new GestureDetector(context,	new CellGestureListener(this));
		_backGround.setOnTouchListener(new CellOnTouchListener());
		addView(_backGround, 0, BG);
		_dayOnMonth = new TextView(context);
		_dayOnMonth.setGravity(Gravity.CENTER);
		// _dayOnMonth.setGravity(Gravity.CENTER);
		FW.setMargins(2, 2, 2, 2);
		addView(_dayOnMonth, 1, FW);
	}

	@Override
	public void setDayModel(DayModel model) {
		_model = model;
		setBGColor();
		;
	}

	@Override
	public DayModel getDayModel() {
		return _model;
	}

	@Override
	public void setOnCalendarCellSelectedListener(OnCalendarCellSelectedListener listener){
		_selectedListener = listener;
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
		if (isSelected) {
			_backGroundColor = DEFAULT_COLOR_SELECTED;
		} else if(_model.isToday()){
			_backGroundColor = DEFAULT_COLOR_TODAY;
		} else {
			_backGroundColor = DEFAULT_COLOR_BACKGROUND;
		}
		_backGround.invalidate();
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
			_dayOnMonth.setTextColor(DEFAULT_COLOR_SUNDAY);
		} else if (Calendar.SATURDAY == _model.getDayOfWeek()) {
			_dayOnMonth.setTextColor(DEFAULT_COLOR_SATURDAY);
		} else {
			_dayOnMonth.setTextColor(DEFAULT_COLOR_DAY_OF_MONTH);
		}
	}

	class CellGestureListener implements GestureDetector.OnGestureListener{
		DefaultCalendarCellView _view = null;
		CellGestureListener(DefaultCalendarCellView view){
			_view = view;
		}
 		@Override
		public boolean onDown(MotionEvent e) {
			Log.i("app", "onDown");
			isSelected = true;
			setBGColor();
			return true;
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
				isSelected = false;
				setBGColor();
			}
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.i("app", "onLongPress");
			if(_selectedListener != null){
				_selectedListener.onCalendarCellSelectedListener(_view);
			}
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
			isSelected = false;
			setBGColor();
			return true;
		}
	}

	class CellOnTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (_gestureDetector.onTouchEvent(event)) {

			} else {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					Log.i("app", "ACTION_UP");
					isSelected = false;
					setBGColor();
					break;
				case MotionEvent.ACTION_DOWN:
					Log.i("app", "ACTION_DOWN");
					break;
				}
			}
			return true;
		}
	}
}