package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.gmap.GMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class BigSceneDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_scene_detail);
		
		getSupportFragmentManager().beginTransaction()
		.add(R.id.content_frame, new GMapFragment())
		.commit();
	}
}