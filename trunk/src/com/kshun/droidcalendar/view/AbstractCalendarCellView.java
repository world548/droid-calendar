package com.kshun.droidcalendar.view;

import android.content.Context;
import android.widget.LinearLayout;

import com.kshun.droidcalendar.model.DayModel;

public abstract class AbstractCalendarCellView extends LinearLayout{

	public AbstractCalendarCellView(Context context) {
		super(context);
		setWillNotDraw(false);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public abstract void setDayModel(DayModel model);


}
