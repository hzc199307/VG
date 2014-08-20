package com.ne.vg.fragment;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BigSceneDetailActivity 下的 景点介绍Fragment 
 * @ClassName: BigSceneIntroFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-19 下午3:08:45
 */
public class BigSceneIntroFragment extends Fragment {

	private final static String TAG= "BigSceneIntroFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro, container, false);
		return rootView;
	}
}
