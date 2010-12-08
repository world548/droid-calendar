package com.kshun.droidcalendar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFactory {

	private static Calendar _calendar = Calendar.getInstance();
	private static MonthModel _shownMonth = null;
	private static Map<Integer, DayModel>_dayModelChache = new HashMap<Integer, DayModel>();
	private static Map<Integer, MonthModel>_monthModelChache = new HashMap<Integer, MonthModel>();
	private static Map<Integer, YearModel>_yearModelChache = new HashMap<Integer, YearModel>();

	static DayModel provideDayModel(int... yyyymmdd){
		int key = generateKey(yyyymmdd);
		if(_dayModelChache.containsKey(key)){
			return _dayModelChache.get(key);
		}else{
			DayModel model = new DayModel(yyyymmdd[0], yyyymmdd[1], yyyymmdd[2]);
			_dayModelChache.put(key, model);
			model.getParentMonthModel().setDayModels(CalendarFactory.provideChildDays(yyyymmdd[0], yyyymmdd[1]));
			return model;
		}
	}

	static MonthModel provideMonthModel(int... yyyymm){
		int key = generateKey(yyyymm);
		if(_monthModelChache.containsKey(key)){
			return _monthModelChache.get(key);
		}else{
			MonthModel model = new MonthModel(yyyymm[0], yyyymm[1]);
			_monthModelChache.put(key, model);
			model.getParentYearModel().setMonthModels(CalendarFactory.provideChildMonths(yyyymm[0]));
			return model;
		}
	}

	static YearModel provideYearModel(int... yyyy){
		int key = generateKey(yyyy);
		if(_yearModelChache.containsKey(key)){
			return _yearModelChache.get(key);
		}else{
			YearModel model = new YearModel(yyyy[0]);
			_yearModelChache.put(key, model);
			return model;
		}
	}

	static List<DayModel> provideChildDays(int... yyyymm){
		_calendar.clear();
		_calendar.set(Calendar.YEAR, yyyymm[0]);
		_calendar.set(Calendar.MONTH, yyyymm[1]);
		List<DayModel> resList = new ArrayList<DayModel>();
		for(int i=1 ; i<=_calendar.getMaximum(Calendar.DAY_OF_MONTH) ; i++){
			resList.add(provideDayModel(yyyymm[0], yyyymm[1], i));
		}
		return resList;
	}

	static List<MonthModel> provideChildMonths(int... yyyy){
		_calendar.clear();
		_calendar.set(Calendar.YEAR, yyyy[0]);
		List<MonthModel> resList = new ArrayList<MonthModel>();
		for(int i=1 ; i<=_calendar.getMaximum(Calendar.MONTH) + 1 ; i++){
			resList.add(provideMonthModel(yyyy[0], i));
		}
		return resList;
	}

	private static int generateKey(int... yyyymmdd){
		if(yyyymmdd.length == 3){
			return yyyymmdd[0] * 10000 + yyyymmdd[1] * 100 + yyyymmdd[2];
		}else if(yyyymmdd.length == 2){
			return yyyymmdd[0] * 10000 + yyyymmdd[1] * 100;
		}else if(yyyymmdd.length == 1){
			return yyyymmdd[0] * 10000;
		}else{
			return 0;
		}
	}

	public static void setDayModel(DayModel dayModel){
		MonthModel monthMondel = dayModel.getParentMonthModel();
		YearModel yearModel = monthMondel.getParentYearModel();
		_dayModelChache.put(generateKey(yearModel.getYear(), monthMondel.getMonth(), dayModel.getDayOfMonth()), dayModel);
		_monthModelChache.put(generateKey(yearModel.getYear(), monthMondel.getMonth()), monthMondel);
		_yearModelChache.put(generateKey(yearModel.getYear()), yearModel);
	}

	public static DayModel getDayModel(int yyyy, int mm, int dd){
		return provideDayModel(yyyy, mm, dd);
	}

	public static DayModel getNextMonthDayModel(DayModel model){
		return getAddedDay(model, Calendar.MONTH, 1);
	}

	public static DayModel getLastMonthDayModel(DayModel model){
		return getAddedDay(model, Calendar.MONTH, -1);
	}

	public static DayModel getToday(){
		Calendar today = Calendar.getInstance();
		DayModel model = provideDayModel(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1 , today.get(Calendar.DAY_OF_MONTH));
		return model;
	}

	public static DayModel getCalendarStartSunDay(DayModel model){
		DayModel sameMonth1Day = getSameMonth1Day(model);
		return getAddedDay(sameMonth1Day, Calendar.DAY_OF_MONTH, 1 - sameMonth1Day.getDayOfWeek());
	}

	public static DayModel getCalendarEndSaturDay(DayModel model){
		DayModel nextMonth1Day = getAddedDay(getSameMonth1Day(model), Calendar.MONTH, 1);
		return getAddedDay(nextMonth1Day, Calendar.DAY_OF_MONTH, 7 - nextMonth1Day.getDayOfWeek());
	}

	private static DayModel getSameMonth1Day(DayModel model){
		return getAddedDay(model, Calendar.DAY_OF_MONTH, 1 - model.getDayOfMonth());
	}

	public static int getDayOfWeek(int yyyy, int mm, int dd){
		_calendar.clear();
		_calendar.set(Calendar.YEAR, yyyy);
		_calendar.set(Calendar.MONTH, mm - 1);
		_calendar.set(Calendar.DAY_OF_MONTH, dd);
		return _calendar.get(Calendar.DAY_OF_WEEK);
	}

	private static DayModel getAddedDay(DayModel model, int unit, int add){
		_calendar.clear();
		_calendar.set(Calendar.YEAR, model.getParentMonthModel().getParentYearModel().getYear());
		_calendar.set(Calendar.MONTH, model.getParentMonthModel().getMonth() -1);
		_calendar.set(Calendar.DAY_OF_MONTH, model.getDayOfMonth());
		_calendar.add(unit, add);
		return provideDayModel(_calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH) + 1, _calendar.get(Calendar.DAY_OF_MONTH));
	}

	public static DayModel getNextDay(DayModel model){
		return getAddedDay(model, Calendar.DAY_OF_MONTH, 1);
	}

	public static void setShownMonth(MonthModel shownMonth){
		_shownMonth = shownMonth;
	}

	public static boolean isShownMonth(MonthModel monthModel){
		return monthModel.equals(_shownMonth);
	}
//
//	public static DayModel getLastDay(){
//
//	}

//	public static MonthModel getNextMonth(){
//
//	}
//
//	public static MonthModel getLastMonth(){
//
//	}
//
//	public static YearModel getNextYear(){
//
//	}
//
//	public static YearModel getLastYear(){
//
//	}
}
