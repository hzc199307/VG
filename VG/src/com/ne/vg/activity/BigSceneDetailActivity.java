package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.TestFragment;
import com.ne.vg.fragment.BigSceneIntroDetailFragment;
import com.ne.vg.fragment.BigSceneIntroFragment;
import com.ne.vg.fragment.HomeFragment;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.gmap.GMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/**
 * 大景点详情内容页
 * @ClassName: BigSceneDetailActivity 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:27:33
 */
public class BigSceneDetailActivity extends FragmentActivity {

	private final String TAG= "BigSceneDetailActivity";
	private GMapFragment gMapFragment;
	private BigSceneIntroFragment bigSceneIntroFragment;
	private BigSceneIntroDetailFragment bigSceneIntroDetailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_big_scene_detail);
		
		if(savedInstanceState == null)
		{
			gMapFragment = new GMapFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.big_scene_detail_frame_map, gMapFragment)
			.commit();
			bigSceneIntroFragment = new BigSceneIntroFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro, bigSceneIntroFragment)
			.commit();
			bigSceneIntroDetailFragment = new BigSceneIntroDetailFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro_detail, bigSceneIntroDetailFragment)
			.commit();
		}
		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		getSupportFragmentManager().beginTransaction()
		.remove(gMapFragment).commit();
		this.finish();
		return super.onKeyDown(keyCode, event);
		
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.big_scene_detail_title_left:
			this.finish();
			break;

		default:
			break;
		}
	}

}