package com.ne.vg.main;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

public class SettingFragment extends AnimationFragment {

	private final static String TAG = "SettingFragment";
	private View setting_title_left;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_setting, container,false);
		setting_title_left = (View)view.findViewById(R.id.setting_title_left);
		super.setAnimView(setting_title_left);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
