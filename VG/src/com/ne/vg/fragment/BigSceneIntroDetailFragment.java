package com.ne.vg.fragment;

import com.ne.vg.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BigSceneDetailActivity ÏÂµÄ ¾°µãÏêÏ¸½éÉÜFragment 
 * @ClassName: BigSceneIntroDetailFragment 
 * @author ºØÖÇ³¬
 * @Description: TODO 
 * @date 2014-8-19 ÏÂÎç3:08:45
 */
public class BigSceneIntroDetailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro_detail, container, false);
		return rootView;
	}
}
