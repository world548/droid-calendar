package com.kshun.droidcalendar.view;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

public class CalendarViewBuilder {
	public static final String[] DEFAULT_DATE_OF_WEEK_HEDDER = {"SUN","MON", "TUE","WED","THU","FRI","SAT"};
	public static final SimpleDateFormat DEFUALT_MONTH_TITLE_SDF = new SimpleDateFormat("yyyy/MM");
	private static Class<?> _calendarCellClass =  null;
	private static String[] _dateOfWeekHedder = null;
	private static SimpleDateFormat _monthTitleHedder = null;
	private static CalendarCellEventListener _onCalendarCellSelectedListener = null;
	private static CalendarCellViewParam _calendarCellViewParam = null;
	static {
		clearParams();
	}

	public static View build(Context context, Resources r){
		MarkImageProvider.init(r);
		CalendarViewFlipperHolder holder = new CalendarViewFlipperHolder(context, createCalendarView(context), createCalendarView(context));
		return holder.getViewFlipper();
	}

	private static CalendarView createCalendarView(Context context){
		CalendarView calendarView = new CalendarView(context, _calendarCellClass, _dateOfWeekHedder, _monthTitleHedder, _calendarCellViewParam);
		calendarView.addMonthTitle();
		calendarView.addCalendarTable();
		calendarView.setOnCalendarCellSelectedListener(_onCalendarCellSelectedListener);
		calendarView.repaintCalendar();
		return calendarView;
	}

	public static void setCalendarCellViewParam(CalendarCellViewParam calendarCellViewParam){
		_calendarCellViewParam = calendarCellViewParam;
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

	public static void setOnCalendarCellSelectedListener(CalendarCellEventListener onCalendarCellSelectedListener){
		_onCalendarCellSelectedListener = onCalendarCellSelectedListener;
	}

	public static void clearParams(){
		_calendarCellClass =  DefaultCalendarCellView.class;
		_dateOfWeekHedder = DEFAULT_DATE_OF_WEEK_HEDDER;
		_monthTitleHedder = DEFUALT_MONTH_TITLE_SDF;
		_calendarCellViewParam = new DefaultCalendarCellViewParams();
	}
}
