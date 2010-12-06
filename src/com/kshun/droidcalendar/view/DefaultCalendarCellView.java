package com.kshun.droidcalendar.view;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
	private static FrameLayout.LayoutParams WF = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT,
			FrameLayout.LayoutParams.FILL_PARENT);
	private static FrameLayout.LayoutParams BG = null;
	private DayModel _model = null;
	private TextView _dayText = null;
	private View _backGround = null;
	private LinearLayout _foreGround = null;
	private int _backGroundColor = DEFAULT_COLOR_BACKGROUND;
	private OnCalendarCellSelectedListener _selectedListener = null;
	private CalendarView _parent = null;
	private GestureDetector _gestureDetector = null;
	private boolean _isSelected = false;
	private ImageView[] _markImageModel = new ImageView[8];
	private TableRow _row1 = null;
	private TableRow _row2 = null;

	public DefaultCalendarCellView(){
		super(null, null);
	}

	public DefaultCalendarCellView(Context context, CalendarView parent) {
		super(context, parent);
		_parent = parent;
		WindowManager wm = (WindowManager) (getContext().getSystemService(Context.WINDOW_SERVICE));
		Display display = wm.getDefaultDisplay();
		BG = new FrameLayout.LayoutParams(display.getWidth()/7, display.getHeight()/8, Gravity.CENTER);
		setWillNotDraw(false);
		addBackGroundView(context);
		addForeGroundView(context);
	}

	private void addForeGroundView(Context context) {
		_foreGround = new LinearLayout(context);
		_foreGround.setOrientation(LinearLayout.VERTICAL);
		_dayText = new TextView(context);
		_dayText = new TextView(context);
		_dayText.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
		_foreGround.addView(_dayText);
		TableLayout markTable = new TableLayout(context);
		markTable.setStretchAllColumns(true);
		_row1 = new TableRow(getContext());
		_row2 = new TableRow(getContext());
		for(int i=0 ; i<_markImageModel.length ; i++){
			_markImageModel[i] = new ImageView(getContext());
			if(i < 4){
				_row1.addView(_markImageModel[i], i);
			}else{
				_row2.addView(_markImageModel[i], i-4);
			}
		}
		markTable.addView(_row1);
		markTable.addView(_row2);
		_foreGround.addView(markTable, WF);
		addView(_foreGround, 1);
	}

	private void addBackGroundView(Context context) {
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
				canvas.drawRect(0, 0, getWidth(), getHeight(), border);
			}
		};
		_backGround.setWillNotDraw(false);
		_gestureDetector = new GestureDetector(context,	new CellGestureListener(this));
		_backGround.setOnTouchListener(new CellOnTouchListener());
		addView(_backGround, 0, BG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("app", "onDraw:" + _model);
		super.onDraw(canvas);
	}

	@Override
	public void setDayModel(DayModel model) {
		Log.i("app", "setDayModel:" + model);
		_model = model;
		_model.setMark(model.getDayOfMonth());
		_dayText.setText(Integer.toString(_model.getDayOfMonth()));
		setBGColor();
		setTextColor();
		setTextSize();
		if (CalendarFactory.isShownMonth(_model.getParentMonthModel())) {
			List<Bitmap> markList = MarkImageProvider.getMarkBitmapList(_model.getMark());
			for(int i=0 ; i<_markImageModel.length ; i++){
				if(i <= markList.size() -1){
					_markImageModel[i].setImageBitmap(markList.get(i));
				}else{
					_markImageModel[i].setImageBitmap(null);
				}
			}
		}else{
			for(int i=0 ; i<_markImageModel.length ; i++){
				_markImageModel[i].setImageBitmap(null);
			}
		}
	}

	@Override
	public DayModel getDayModel() {
		return _model;
	}

	@Override
	public void setOnCalendarCellSelectedListener(OnCalendarCellSelectedListener listener){
		_selectedListener = listener;
	}

	private void setBGColor() {
		if (_isSelected) {
			_backGroundColor = DEFAULT_COLOR_SELECTED;
		} else if(_model.isToday()){
			_backGroundColor = DEFAULT_COLOR_TODAY;
		} else {
			_backGroundColor = DEFAULT_COLOR_BACKGROUND;
		}
		invalidate();
	}

	private void setTextSize() {
		if (CalendarFactory.isShownMonth(_model.getParentMonthModel())) {
			_dayText.setTextSize(18);
		} else {
			_dayText.setTextSize(12);
		}
	}

	private void setTextColor() {
		if (Calendar.SUNDAY == _model.getDayOfWeek()) {
			_dayText.setTextColor(DEFAULT_COLOR_SUNDAY);
		} else if (Calendar.SATURDAY == _model.getDayOfWeek()) {
			_dayText.setTextColor(DEFAULT_COLOR_SATURDAY);
		} else {
			_dayText.setTextColor(DEFAULT_COLOR_DAY_OF_MONTH);
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
			_isSelected = true;
			setBGColor();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			Log.i("app", "onFling");
			if (_parent != null) {
				if (velocityX > 2) {
					_parent.toLastMonth();
				} else if(velocityX < -2){
					_parent.toNextMonth();
				}else{
					return false;
				}
				_isSelected = false;
				setBGColor();
				return true;
			}else{
				return false;
			}

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
			_isSelected = false;
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
					_isSelected = false;
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