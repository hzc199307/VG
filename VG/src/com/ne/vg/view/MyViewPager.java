package com.ne.vg.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 重写ViewPager.onTouchEvent  能够设置是否滑动
 * @ClassName: MyViewPager 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-21 下午2:53:41
 */
public class MyViewPager extends ViewPager
{
	private boolean canScroll = true;
	public MyViewPager(Context context) {
		super(context);

	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setCanScroll(boolean canScroll) {
		this.canScroll = canScroll;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//Log.d("d", "【MyViewPager】任务<" + Util.actionToString(ev.getAction()) + "> : 需要分派");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//Log.d("d", "【MyViewPager】任务<" + Util.actionToString(ev.getAction()) + "> : 下面的人真没用，下次再也不找你了，我自己来尝试一下。能解决？");

		return super.onTouchEvent(ev);  

	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//Log.d("d", "【MyViewPager】任务<" + Util.actionToString(ev.getAction()) + "> : 拦截吗？" );
		if (canScroll) {  
			return super.onInterceptTouchEvent(ev);  
		} else {  
			return false;  
		} 
	}
}

class Util
{

	public static String actionToString(int action) {
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			return "ACTION_DOWN";
		case MotionEvent.ACTION_UP:
			return "ACTION_UP";
		case MotionEvent.ACTION_CANCEL:
			return "ACTION_CANCEL";
		case MotionEvent.ACTION_OUTSIDE:
			return "ACTION_OUTSIDE";
		case MotionEvent.ACTION_MOVE:
			return "ACTION_MOVE";
		}
		return "";
	}
}
