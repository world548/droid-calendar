package com.kshun.droidcalendar.view;

import android.content.Context;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class CalendarViewFlipperHolder {
	private static Animation _inFromLeft = null;
	private static Animation _outToRight = null;
	private static Animation _inFromRight = null;
	private static Animation _outToLeft = null;
	private static long DURATION = 400;
	private static CalendarView _currentView = null;
	private static AnimationListener _listener = null;
	private static boolean _isToNextMonth = true;
	static{
		_listener = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.i("month", "onAnimationEnd");
				if(_isToNextMonth){
					_currentView.toNextMonth();
				}else{
					_currentView.toLastMonth();
				}
			}
		};
		_inFromRight =
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 1f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f);
        _inFromRight.setDuration(DURATION);
        _inFromRight.setInterpolator(new AccelerateInterpolator());

        _outToLeft =
            new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, -1f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f);
        _outToLeft.setDuration(DURATION);
        _outToLeft.setAnimationListener(_listener);
        _outToLeft.setInterpolator(new AccelerateInterpolator());

        _inFromLeft =
            new TranslateAnimation(
            		 Animation.RELATIVE_TO_PARENT, -1f,
                     Animation.RELATIVE_TO_PARENT, 0f,
                     Animation.RELATIVE_TO_PARENT, 0f,
                     Animation.RELATIVE_TO_PARENT, 0f);
        _inFromLeft.setDuration(DURATION);
        _inFromLeft.setInterpolator(new AccelerateInterpolator());

        _outToRight =
            new TranslateAnimation(
           		 Animation.RELATIVE_TO_PARENT, 0f,
                 Animation.RELATIVE_TO_PARENT, 1f,
                 Animation.RELATIVE_TO_PARENT, 0f,
                 Animation.RELATIVE_TO_PARENT, 0f);
        _outToRight.setDuration(DURATION);
        _outToRight.setAnimationListener(_listener);
        _outToRight.setInterpolator(new AccelerateInterpolator());
	}

	private ViewFlipper _vf = null;
	private ViewFlipper.LayoutParams FP = new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.FILL_PARENT, ViewFlipper.LayoutParams.FILL_PARENT);
	private CalendarView _view1 = null;
	private CalendarView _view2 = null;

	public CalendarViewFlipperHolder(Context context, CalendarView lView, CalendarView cView){
		_view1 = lView;
		_view2 = cView;
		_vf = new ViewFlipper(context);
		_view1.setCalendarViewFlipperHolder(this);
		_view2.setCalendarViewFlipperHolder(this);
		_vf.addView(_view1, 0, FP);
		_vf.addView(_view2, 1, FP);
	}

	public ViewFlipper getViewFlipper(){
		return _vf;
	}

	private CalendarView getOppsiteCalendarView(CalendarView view){
		if(_view1.equals(view)){
			return _view2;
		}else{
			return _view1;
		}
	}

	void onNextMonth(CalendarView view){
		Log.i("month", "onNextMonth");
		getOppsiteCalendarView(view).toNextMonth();
		_currentView = view;
		_isToNextMonth = true;
		_vf.setInAnimation(_inFromRight);
    	_vf.setOutAnimation(_outToLeft);
    	_vf.showNext();
	}

	void onLastMonth(CalendarView view){
		Log.i("month", "onLastMonth");
		getOppsiteCalendarView(view).toLastMonth();
		_currentView = view;
		_isToNextMonth = false;
		_vf.setInAnimation(_inFromLeft);
    	_vf.setOutAnimation(_outToRight);
    	_vf.showPrevious();
	}
}