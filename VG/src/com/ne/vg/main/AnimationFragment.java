package com.ne.vg.main;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * ��Ҫ�õ�������ͷ����Fragment
 * @ClassName: AnimationFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-9-29 ����2:41:16
 */
public class AnimationFragment extends Fragment{
 
	private View animView;
	
	/**
	 * ������Ҫ��������ͼ
	 * @param animView
	 */
	public void setAnimView(View animView) {
		this.animView = animView;
	}
	
	/**
	 * ��ʼ����������
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		if(animView!=null)
			animView.startAnimation(animation);
	}

	/**
	 * �رձ���������
	 */
	public void clearAnimation() {
		if(animView!=null&&animView.getAnimation()!=null)
			animView.clearAnimation();
	}

}
