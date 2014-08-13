package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.fragment.RouteFragment;
import com.ne.vg.gmap.GMapFragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class RouteActivity extends FragmentActivity {

	private final String TAG= "RouteActivity";
	private RouteFragment routeFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_route);
		routeFragment = new RouteFragment();
		getSupportFragmentManager().beginTransaction()
		.add(R.id.route_frame, routeFragment)
		.commit();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		routeFragment = null;
		Log.v(TAG, "onDestroy");
	}

}
