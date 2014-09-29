package com.ne.vg.main;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * 需要用到动画的头标题Fragment
 * @ClassName: AnimationFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-29 下午2:41:16
 */
public class AnimationFragment extends Fragment{
 
	private View animView;
	
	/**
	 * 设置需要动画的视图
	 * @param animView
	 */
	public void setAnimView(View animView) {
		this.animView = animView;
	}
	
	/**
	 * 开始标题栏动画
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		if(animView!=null)
			animView.startAnimation(animation);
	}

	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		if(animView!=null&&animView.getAnimation()!=null)
			animView.clearAnimation();
	}

}
