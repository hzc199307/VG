package com.ne.vg.main;

import com.ne.vg.FetchDataActivity;
import com.ne.vg.R;
import com.ne.vg.util.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class SettingFragment extends AnimationFragment {

	private final static String TAG = "SettingFragment";
	private View setting_title_left;
	private RelativeLayout setting_item_moreapp;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		LogUtil.d(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_setting, container,false);
		setting_title_left = (View)view.findViewById(R.id.setting_title_left);
		setting_item_moreapp = (RelativeLayout)view.findViewById(R.id.setting_item_moreapp);
		setting_item_moreapp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(), FetchDataActivity.class);
				startActivity(myIntent);
			}
		});
		super.setAnimView(setting_title_left);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		LogUtil.d(TAG, "onDestroyView");
	}
}
