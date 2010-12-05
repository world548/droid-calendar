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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarView<T extends AbstractCalendarCellView> extends LinearLayout {
	private static final String[] DEFAULT_DATE_OF_WEEK_HEDDER = {"SUN","MON", "TUE","WED","THU","FRI","SAT"};
	private static final SimpleDateFormat DEFUALT_SDF = new SimpleDateFormat("yyyy/MM");
	private String[] _dateOfWeekHedder = null;
	private TextView _title = null;
	private SimpleDateFormat _titleSdf = null;
	private float _titleSize = 15f;
	private DayModel _currentDay = null;
	private TableLayout _layout = null;
	private AbstractCalendarCellView[][] _cells = new AbstractCalendarCellView[6][7];
	private AnimationSet _toNextManth = null;
	private AnimationSet _toLastManth = null;

	public CalendarView(Context context, Class<T> clazz, String[] dateOfWeekHedder, SimpleDateFormat titleSdf) {
		super(context);
		_dateOfWeekHedder = dateOfWeekHedder;
		_titleSdf = titleSdf;
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
		TableRow hedder = new TableRow(getContext());
		for(String str : _dateOfWeekHedder){
			TextView text = new TextView(getContext());
			text.setText(str);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(15);
			hedder.addView(text);
		}
		_layout.addView(hedder);

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

	public CalendarView(Context context, Class<T> clazz) {
		this(context, clazz, DEFAULT_DATE_OF_WEEK_HEDDER, DEFUALT_SDF);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (_currentDay == null) {
			_currentDay = CalendarFactory.getToday();
			repaintCalendar(_currentDay);
		}
	}

	public void setDateOfWeekHedder(String[] hedder){
		_dateOfWeekHedder = hedder;
	}

	void toNextMonth() {
		_currentDay = CalendarFactory.getNextMonthDayModel(_currentDay);
		this.startAnimation(_toNextManth);
	}

	void toLastMonth() {
		_currentDay = CalendarFactory.getLastMonthDayModel(_currentDay);
		this.startAnimation(_toLastManth);
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
				cell.setOnCalendarCellSelectedListener(new OnCalendarCellSelectedListener() {
					@Override
					public void onCalendarCellSelectedListener(AbstractCalendarCellView view) {
						DayModel dayModel = view.getDayModel();
						Toast.makeText(getContext(), dayModel.toString(), Toast.LENGTH_SHORT).show();
					}
				});
				targetDay = CalendarFactory.getNextDay(targetDay);
			}
		}
	}
}
