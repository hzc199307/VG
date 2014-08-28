package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.fragment.RouteFragment;
import com.ne.vg.gmap.GMapFragment;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RouteActivity extends FragmentActivity {

	private final String TAG= "RouteActivity";
	private RouteFragment routeFragment;
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_route);
		mIntent = this.getIntent();
		
		routeFragment = new RouteFragment(mIntent.getExtras().getInt("routeID"));
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
	
	/**
	 * 必须手动移除GMapFragment
	 */
	public void destroyGMapFragment() {
		// 此处必须移除 gMapFragment  因为里面有webview的缘故 否则内存泄露
		if(routeFragment!=null)
			routeFragment.destroyGMapFragment();
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.route_title_left:
			this.finish();
			break;
		default:
			break;
		}
	}

}
