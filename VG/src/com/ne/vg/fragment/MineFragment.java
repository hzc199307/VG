package com.ne.vg.fragment;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
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
import android.widget.Button;
import android.widget.Toast;


public class MineFragment extends Fragment {

	private final static String TAG = "MineFragment";

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_mine, container,false);
		return view;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
