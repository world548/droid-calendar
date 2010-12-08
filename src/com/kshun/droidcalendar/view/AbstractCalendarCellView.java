package com.kshun.droidcalendar.view;

import android.content.Context;
import android.widget.FrameLayout;

import com.kshun.droidcalendar.model.DayModel;

public abstract class AbstractCalendarCellView extends FrameLayout{

	public AbstractCalendarCellView(Context context, CalendarView parent) {
		this(context, parent, null);
	}

	public AbstractCalendarCellView(Context context, CalendarView parent, CalendarCellViewParam params) {
		super(context);
	}

	public abstract void setDayModel(DayModel model);

	public abstract DayModel getDayModel();

	public abstract void setCalendarCellEventListener(CalendarCellEventListener listener);
}
