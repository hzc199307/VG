package com.ne.vg.fragment;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro, container, false);
		return rootView;
	}
}
