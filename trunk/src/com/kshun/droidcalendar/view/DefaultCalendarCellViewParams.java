package com.kshun.droidcalendar.view;

import android.graphics.Color;

public class DefaultCalendarCellViewParams implements CalendarCellViewParam{

	public static final int DEFAULT_BG_COLOR = Color.rgb(20, 20, 20);
	public static final int DEFAULT_BG_COLOR_SELECTED = Color.rgb(255, 180, 80);
	public static final int DEFAULT_BG_COLOR_TODAY = Color.rgb(255, 128, 80);
	public static final int DEFAULT_FONT_COLOR_OTHER = Color.rgb(255, 255, 255);
	public static final int DEFAULT_FONT_COLOR_SUNDAY = Color.rgb(255, 180, 180);
	public static final int DEFAULT_FONT_COLOR_SATURDAY = Color.rgb(180, 180, 255);
	public static final int DEFAULT_FONT_COLOR_PTRIOR = Color.TRANSPARENT;
	public static final int DEFAULT_COLOR_CELL_BORDER = Color.rgb(255, 255, 255);
	public static final float DEFAULT_FONT_SIZE_THIS_MONTH = 18f;
	public static final float DEFAULT_FONT_SIZE_OTHER_MONTH = 12f;
	public static final float DEFAULT_WIDTH_CELL_BORDER = 0.1f;
	private int _bgColor = DEFAULT_BG_COLOR;
	private int _bgColorSelected = DEFAULT_BG_COLOR_SELECTED;
	private int _bgColorToday = DEFAULT_BG_COLOR_TODAY;
	private int _fontColorOther = DEFAULT_FONT_COLOR_OTHER;
	private int _fontColorSunday = DEFAULT_FONT_COLOR_SUNDAY;
	private int _fontColorSaturday = DEFAULT_FONT_COLOR_SATURDAY;
	private int _fontColorPrior = DEFAULT_FONT_COLOR_PTRIOR;
	private int _colorCellBorder = DEFAULT_COLOR_CELL_BORDER;
	private float _fontSizeThisMonth = DEFAULT_FONT_SIZE_THIS_MONTH;
	private float _fontSizeOtherMonth = DEFAULT_FONT_SIZE_OTHER_MONTH;
	private float _widthCellBorder = DEFAULT_WIDTH_CELL_BORDER;

	public DefaultCalendarCellViewParams(){

	}

	public int getBgColor() {
		return _bgColor;
	}

	public void setBgColor(int bgColor) {
		_bgColor = bgColor;
	}

	public int getBgColorSelected() {
		return _bgColorSelected;
	}

	public void setBgColorSelected(int bgColorSelected) {
		_bgColorSelected = bgColorSelected;
	}

	public int getBgColorToday() {
		return _bgColorToday;
	}

	public void setBgColorToday(int bgColorToday) {
		_bgColorToday = bgColorToday;
	}

	public int getFontColorOther() {
		return _fontColorOther;
	}

	public void setFontColorOther(int fontColor) {
		_fontColorOther = fontColor;
	}

	public int getFontColorSunday() {
		return _fontColorSunday;
	}

	public void setFontColorSunday(int fontColor) {
		_fontColorSunday = fontColor;
	}

	public int getFontColorSaturday() {
		return _fontColorSaturday;
	}

	public void setFontColorSaturday(int fontColor) {
		_fontColorSaturday = fontColor;
	}

	public int getFontColorPrior() {
		return _fontColorPrior;
	}

	public void setFontColorPrior(int fontColor) {
		this._fontColorPrior = fontColor;
	}

	public int getColorCellBorder() {
		return _colorCellBorder;
	}

	public void setColorCellBorder(int colorCellBorder) {
		this._colorCellBorder = colorCellBorder;
	}

	public float getFontSizeThisMonth() {
		return _fontSizeThisMonth;
	}

	public void setFontSizeThisMonth(float fontSizeThisMonth) {
		this._fontSizeThisMonth = fontSizeThisMonth;
	}

	public float getFontSizeOtherMonth() {
		return _fontSizeOtherMonth;
	}

	public void setFontSizeOtherMonth(float fontSizeOtherMonth) {
		this._fontSizeOtherMonth = fontSizeOtherMonth;
	}

	public float getWidthCellBorder() {
		return _widthCellBorder;
	}

	public void setWidthCellBorder(float widthCellBorder) {
		this._widthCellBorder = widthCellBorder;
	}
}
