package com.ne.vg.main;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.activity.RouteActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * °∞À—À˜°±œÍ«È“≥£®‘› ±”√¿¥≤‚ ‘”√£©
 * @ClassName: SearchFragment 
 * @author ∫ÿ÷«≥¨
 * @Description: TODO 
 * @date 2014-8-12 œ¬ŒÁ3:21:19
 */
public class SearchFragment extends AnimationFragment implements View.OnClickListener{

	
	
private final static String TAG = "SearchFragment";
	

private View search_title_all;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_search, container,false);

		search_title_all = (View)view.findViewById(R.id.search_title_all);
		super.setAnimView(search_title_all);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		default:
			break;
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
