package com.ne.vg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ����Ϊ����BigSceneDetailActivity����ʹ��  Ϊ�˽��С��ϵͳ��bug
 * @ClassName: MyScrollView 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-9-24 ����5:09:58
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
	 * �����scrollView�ķַ�����С��ϵͳ�������super�ķ����ᵼ��ev.getAction()ֵ�ĸı䣩
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


