package com.kshun.droidcalendar.view;

import java.text.SimpleDateFormat;



import android.content.Context;
import android.content.res.Resources;

public class CalendarViewBuilder {
	public static final String[] DEFAULT_DATE_OF_WEEK_HEDDER = {"SUN","MON", "TUE","WED","THU","FRI","SAT"};
	public static final SimpleDateFormat DEFUALT_MONTH_TITLE_SDF = new SimpleDateFormat("yyyy/MM");
	private static Class<?> _calendarCellClass =  null;
	private static String[] _dateOfWeekHedder = null;
	private static SimpleDateFormat _monthTitleHedder = null;
	private static boolean _hasFlickAnimation = true;
	private static OnCalendarCellSelectedListener _onCalendarCellSelectedListener = null;

	static {
		clearParams();
	}

	public static CalendarView build(Context context, Resources r){
		MarkImageProvider.init(r);
		CalendarView calendarView = new CalendarView(context, _calendarCellClass, _dateOfWeekHedder, _monthTitleHedder);
		calendarView.addMonthTitle();
		calendarView.addCalendarTable();
		calendarView.setOnCalendarCellSelectedListener(_onCalendarCellSelectedListener);
		if(_hasFlickAnimation){
			calendarView.initializeFlickAnimetion();
		}
		return calendarView;
	}

	public static void setCalendarCellClass(Class<?> calendarCellClass){
		_calendarCellClass = calendarCellClass;
	}

	public static void setDateOfWeekHedder(String[] dateOfWeekHedder){
		_dateOfWeekHedder = dateOfWeekHedder;
	}

	public static void setMonthTitleHedder(SimpleDateFormat monthTitleHedder){
		_monthTitleHedder = monthTitleHedder;
	}

	public static void setFlickAnimetionOn(boolean hasFlickAnimation){
		_hasFlickAnimation = hasFlickAnimation;
	}

	public static void setOnCalendarCellSelectedListener(OnCalendarCellSelectedListener onCalendarCellSelectedListener){
		_onCalendarCellSelectedListener = onCalendarCellSelectedListener;
	}

	public static void clearParams(){
		_calendarCellClass =  DefaultCalendarCellView.class;
		_dateOfWeekHedder = DEFAULT_DATE_OF_WEEK_HEDDER;
		_monthTitleHedder = DEFUALT_MONTH_TITLE_SDF;
		_hasFlickAnimation = true;
	}
}