package com.kshun.droidcalendar.view;

import com.kshun.droidcalendar.model.DayModel;

public interface CalendarCellEventListener {

	public void onSelectionChanged(AbstractCalendarCellView view, boolean isSelected);

	public void onLongPress(AbstractCalendarCellView view);

	public void onNextMonth(DayModel model);

	public void onLastMonth(DayModel model);

}
