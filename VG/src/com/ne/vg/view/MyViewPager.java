package com.ne.vg.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (canScroll) {  
			return super.onTouchEvent(arg0);  
		} else {  
			return false;  
		}  
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (canScroll) {  
			return super.onInterceptTouchEvent(arg0);  
		} else {  
			return false;  
		} 
	}
}
