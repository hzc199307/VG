package com.ne.vg.fragment;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BigSceneDetailActivity �µ� �������Fragment 
 * @ClassName: BigSceneIntroFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-19 ����3:08:45
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
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
