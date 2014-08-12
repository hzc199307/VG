package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.gmap.GMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 大景点详情内容页
 * @ClassName: BigSceneDetailActivity 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:27:33
 */
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