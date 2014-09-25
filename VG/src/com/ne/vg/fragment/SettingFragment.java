package com.ne.vg.fragment;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

public class SettingFragment extends Fragment {

	private final static String TAG = "SettingFragment";
	private View setting_title_left;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_setting, container,false);
		setting_title_left = (View)view.findViewById(R.id.setting_title_left);
		return view;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
	
	/**
	 * 开始标题栏动画
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		if(setting_title_left!=null)
			setting_title_left.startAnimation(animation);
	}

	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		if(setting_title_left!=null&&setting_title_left.getAnimation()!=null)
			setting_title_left.clearAnimation();
	}
}
