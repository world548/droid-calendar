package com.kshun.droidcalendar.model;

import java.util.List;

public class YearModel {
	private int year = 0;
	private boolean isLeap = false;
	private List<MonthModel> monthModels = null;

	YearModel(int yyyy){
		year = yyyy;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public boolean isLeap() {
		return isLeap;
	}
	public void setLeap(boolean isLeap) {
		this.isLeap = isLeap;
	}
	public List<MonthModel> getMonthModels() {
		return monthModels;
	}
	public String toString(){
		return Integer.toString(year);
	}
	public void setMonthModels(List<MonthModel> monthModels) {
		this.monthModels = monthModels;
	}
}
