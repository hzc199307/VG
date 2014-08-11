package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.RecommendActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MineFragment extends Fragment implements View.OnClickListener{

	private final static String TAG = "MineFragment";
	
	private Button btn_panshan;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_mine, container,false);
		btn_panshan = (Button)view.findViewById(R.id.btn_panshan);
		btn_panshan.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_panshan:
			//TODO 潘杉 你要执行的跳转
			startActivity(new Intent(getActivity(),RecommendActivity.class));
			break;
		default:
			break;
		}
	}
}
