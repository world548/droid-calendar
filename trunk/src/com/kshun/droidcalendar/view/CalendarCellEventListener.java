package com.kshun.droidcalendar.view;

public interface CalendarCellEventListener {

	public void onSelectionChanged(AbstractCalendarCellView view, boolean isSelected);

	public void onLongPress(AbstractCalendarCellView view);

}
