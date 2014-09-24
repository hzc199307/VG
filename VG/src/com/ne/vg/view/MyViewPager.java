package com.ne.vg.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * ��дViewPager.onTouchEvent  �ܹ������Ƿ񻬶�
 * @ClassName: MyViewPager 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-21 ����2:53:41
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
		//Log.d("d", "��MyViewPager������<" + Util.actionToString(ev.getAction()) + "> : ��Ҫ����");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//Log.d("d", "��MyViewPager������<" + Util.actionToString(ev.getAction()) + "> : ���������û�ã��´���Ҳ�������ˣ����Լ�������һ�¡��ܽ����");

		return super.onTouchEvent(ev);  

	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//Log.d("d", "��MyViewPager������<" + Util.actionToString(ev.getAction()) + "> : ������" );
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
