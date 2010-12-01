package com.kshun.droidcalendar.model;

import java.util.Calendar;
import java.util.Date;

public class DayModel {
	private MonthModel parentMonthModel = null;
	private int dayOfMonth = 0;
	private int dayOfWeek = 0;
	private boolean isHoliday = false;
	private boolean isNationalHoliday = false;
	private int[] mark = null;
	private String memo = null;

	DayModel(int yyyy, int mm, int dd){
		dayOfMonth = dd;
		parentMonthModel = CalendarFactory.provideMonthModel(yyyy, mm);
		dayOfWeek = CalendarFactory.getDayOfWeek(yyyy, mm, dd);
	}

	public MonthModel getParentMonthModel() {
		return parentMonthModel;
	}
	public void setParentMonthModel(MonthModel parentMonthModel) {
		this.parentMonthModel = parentMonthModel;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
//	public void setDayOfMonth(int dayOfMonth) {
//		this.dayOfMonth = dayOfMonth;
//	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
//	public void setDayOfWeek(int dayOfWeek) {
//		this.dayOfWeek = dayOfWeek;
//	}
	public boolean isToday() {
		return CalendarFactory.getToday().equals(this);
	}
//	public void setToday(boolean isToday) {
//		this.isToday = isToday;
//	}
	public boolean isHoliday() {
		return isHoliday;
	}
	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	public boolean isNationalHoliday() {
		return isNationalHoliday;
	}
	public void setNationalHoliday(boolean isNationalHoliday) {
		this.isNationalHoliday = isNationalHoliday;
	}
	public int[] getMark() {
		return mark;
	}
	public void setMark(int[] mark) {
		this.mark = mark;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String toString(){
		return parentMonthModel.toString() + "/" + dayOfMonth;
	}

	public Date getTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, parentMonthModel.getParentYearModel().getYear());
		cal.set(Calendar.MONTH, parentMonthModel.getMonth() -1);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return cal.getTime();
	}

	public boolean equals(Object model){
		if(model instanceof DayModel){
			DayModel daymodel = (DayModel)model;
			if(this.getParentMonthModel().getParentYearModel().getYear() == daymodel.getParentMonthModel().getParentYearModel().getYear()){
				if(this.getParentMonthModel().getMonth() == daymodel.getParentMonthModel().getMonth()){
					if(this.getDayOfMonth() == daymodel.getDayOfMonth()){
						return true;
					}
				}
			}
		}
		return false;
	}
}
