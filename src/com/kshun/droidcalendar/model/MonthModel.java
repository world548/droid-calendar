package com.kshun.droidcalendar.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthModel {
	private YearModel parentYearModel = null;
	private int month = 0;
	List<DayModel> dayModels = null;

	MonthModel(int yyyy, int mm){
		month = mm;
		parentYearModel = CalendarFactory.provideYearModel(yyyy);
	}

	public YearModel getParentYearModel() {
		return parentYearModel;
	}
	public void setParentYearModel(YearModel parentYearModel) {
		this.parentYearModel = parentYearModel;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int mm) {
		month = mm;
	}
	public List<DayModel> getDayModels() {
		return dayModels;
	}

	public void setDayModels(List<DayModel> dayModels) {
		this.dayModels = dayModels;
	}

	public String toString(){
		return parentYearModel.toString() + "/" + month;
	}

	public Date getTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, parentYearModel.getYear());
		cal.set(Calendar.MONTH, month -1);
		return cal.getTime();
	}

	public boolean isThisMonth() {
		return CalendarFactory.getToday().getParentMonthModel().equals(this);
	}

	public boolean equals(Object model){
		if(model instanceof MonthModel){
			MonthModel monthmodel = (MonthModel)model;
			if(this.getParentYearModel().getYear() == monthmodel.getParentYearModel().getYear()){
				if(this.getMonth() == monthmodel.getMonth()){
					return true;
				}
			}
		}
		return false;
	}
//	public void setDayModels(List<DayModel> dayModels) {
//		this.dayModels = dayModels;
//	}
}
