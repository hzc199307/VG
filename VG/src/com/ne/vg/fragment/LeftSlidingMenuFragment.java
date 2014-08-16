package com.ne.vg.fragment;

import com.ne.vg.MainActivity;
import com.ne.vg.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Ö÷³ÌÐò²à±ßÀ¸
 * @ClassName: LeftSlidingMenuFragment 
 * @author ºØÖÇ³¬
 * @Description: TODO 
 * @date 2014-8-12 ÏÂÎç3:20:39
 */
public class LeftSlidingMenuFragment extends Fragment implements View.OnClickListener{

	private final static String TAG = "LeftSlidingMenuFragment";
	
	
	private View home_layout,mine_layout,search_layout,setting_layout;

	private MainActivity mainActivity;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_leftslidingmenu, container,false);
		
		home_layout = (View)view.findViewById(R.id.home_layout);
		home_layout.setOnClickListener(this);
		mine_layout = (View)view.findViewById(R.id.mine_layout);
		mine_layout.setOnClickListener(this);
		search_layout = (View)view.findViewById(R.id.search_layout);
		search_layout.setOnClickListener(this);
		setting_layout = (View)view.findViewById(R.id.setting_layout);
		setting_layout.setOnClickListener(this);
		
		mainActivity = (MainActivity) getActivity();
		
		home_layout.setSelected(true);
		mine_layout.setSelected(false);
		search_layout.setSelected(false);
		setting_layout.setSelected(false);
	
		return view;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.home_layout:
			mainActivity.switchContentToHome();
			home_layout.setSelected(true);
			mine_layout.setSelected(false);
			search_layout.setSelected(false);
			setting_layout.setSelected(false);
			break;
		case R.id.mine_layout:
			mainActivity.switchContentToMine();
			home_layout.setSelected(false);
			mine_layout.setSelected(true);
			search_layout.setSelected(false);
			setting_layout.setSelected(false);
			break;
		case R.id.search_layout:
			mainActivity.switchContentToSearch();
			home_layout.setSelected(false);
			mine_layout.setSelected(false);
			search_layout.setSelected(true);
			setting_layout.setSelected(false);
			break;
		case R.id.setting_layout:
			mainActivity.switchContentToSetting();
			home_layout.setSelected(false);
			mine_layout.setSelected(false);
			search_layout.setSelected(false);
			setting_layout.setSelected(true);
			break;
		default:
			break;
		}
	}
}
