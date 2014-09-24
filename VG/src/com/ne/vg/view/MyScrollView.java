package com.ne.vg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 此类为了在BigSceneDetailActivity里面使用  为了解决小米系统的bug
 * @ClassName: MyScrollView 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-24 下午5:09:58
 */
public class MyScrollView extends ScrollView {

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 不阻断scrollView的分发。（小米系统如果调用super的方法会导致ev.getAction()值的改变）
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.d("MyScrollView", "onTouchEvent");
		return super.onTouchEvent(ev);
	}

}


