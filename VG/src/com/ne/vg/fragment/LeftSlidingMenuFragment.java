package com.ne.vg.fragment;

import com.ne.vg.MainActivity;
import com.ne.vg.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LeftSlidingMenuFragment extends Fragment implements View.OnClickListener{

	private final static String TAG = "LeftSlidingMenuFragment";
	
	private Button btn_home,btn_mine;
	private MainActivity mainActivity;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_leftslidingmenu, container,false);
		
		btn_home = (Button)view.findViewById(R.id.btn_home);
		btn_mine = (Button)view.findViewById(R.id.btn_mine);
		btn_home.setOnClickListener(this);
		btn_mine.setOnClickListener(this);
		
		mainActivity = (MainActivity) getActivity();
		
		
		
		return view;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_home:
			mainActivity.switchContentToHome();
			break;
		case R.id.btn_mine:
			mainActivity.switchContentToMine();
			break;
		default:
			break;
		}
	}
}
