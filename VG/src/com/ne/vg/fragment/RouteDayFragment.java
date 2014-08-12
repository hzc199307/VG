package com.ne.vg.fragment;

import com.ne.vg.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RouteDayFragment extends Fragment {

	private final static String TAG = "RouteDayFragment";
	private View rootView;
	private TextView tv;
	int i =-1;
	public RouteDayFragment(int i) {
		// TODO Auto-generated constructor stub
		this.i = i;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Toast.makeText(getActivity(), TAG+ " "+i+" onAttach", Toast.LENGTH_SHORT).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " "+i+" onCreateView", Toast.LENGTH_SHORT).show();

		rootView = inflater.inflate(R.layout.fragment_route_day, container,false);
		tv= (TextView)rootView.findViewById(R.id.tv);
		tv.setText(""+i);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Toast.makeText(getActivity(), TAG+ " "+i+" onDestroyView", Toast.LENGTH_SHORT).show();
	}
}
