package com.kshun.droidcalendar.view;

import android.content.Context;
import android.widget.FrameLayout;

import com.kshun.droidcalendar.model.DayModel;

public abstract class AbstractCalendarCellView extends FrameLayout{

	public AbstractCalendarCellView(Context context, CalendarView parent) {
		super(context);
	}

	public abstract void setDayModel(DayModel model);

	public abstract DayModel getDayModel();

	public abstract void setOnCalendarCellSelectedListener(OnCalendarCellSelectedListener listener);

}
