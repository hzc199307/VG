package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.TestFragment;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.SceneContent;
import com.ne.vg.dao.VGDao;
import com.ne.vg.fragment.BigSceneIntroDetailFragment;
import com.ne.vg.fragment.BigSceneIntroFragment;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.main.HomeFragment;
import com.ne.vg.main.MineFragment;
import com.ne.vg.util.LogUtil;
import com.ne.vg.util.UnitUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 大景点详情内容页
 * @ClassName: BigSceneDetailActivity 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:27:33
 */
public class BigSceneDetailActivity extends FragmentActivity {

	private final static String TAG= "BigSceneDetailActivity";

	private GMapFragment gMapFragment;
	private BigSceneIntroFragment bigSceneIntroFragment;
	private BigSceneIntroDetailFragment bigSceneIntroDetailFragment;

	private int lastMapHeight;
	private FrameLayout gmapFrame;//地图布局
	private ScrollView bsdScroll;//滚动布局
	private boolean isMapBig = false;//记录地图是否被点击放大了
	private ImageButton bsd_btn_up,bsd_btn_location;

	private View big_scene_detail_other;
	private Intent mIntent;
	private VGDao mVgDao;
	private String bigSceneName;

	private TextView big_scene_detail_title_name;
	private BigScene bigScene;
	
	private SceneContent sceneContent;

	public SceneContent getSceneContent() {
		return sceneContent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_big_scene_detail);
		gmapFrame = (FrameLayout)findViewById(R.id.big_scene_detail_frame_map);
		bsdScroll = (ScrollView)findViewById(R.id.bsd_scroll);
		bsd_btn_up = (ImageButton)findViewById(R.id.bsd_btn_up);
		bsd_btn_location = (ImageButton)findViewById(R.id.bsd_btn_location);
		big_scene_detail_other = (View)findViewById(R.id.big_scene_detail_other);

		big_scene_detail_title_name = (TextView)findViewById(R.id.big_scene_detail_title_name);

		/** 跟数据传输相关*/
		mVgDao = new VGDao(this);
		mIntent = this.getIntent();

		bigScene = mVgDao.getBigScene(mIntent.getExtras().getInt("bigSceneID"));
		LogUtil.d(TAG, bigScene.getContentID()+"");
		sceneContent = mVgDao.getSceneContent(bigScene.getContentID());
		
		bigSceneName = mVgDao.getBigSceneName(mIntent.getExtras().getInt("bigSceneID"));
		Log.d(TAG, bigSceneName);
		big_scene_detail_title_name.setText(bigSceneName);
		//		if(savedInstanceState == null)
		//		{
		//			if(gMapFragment==null)
		//				gMapFragment = new GMapFragment();
		//			if(bigSceneIntroFragment==null)
		//				bigSceneIntroFragment = new BigSceneIntroFragment();
		//			if(bigSceneIntroDetailFragment==null)
		//				bigSceneIntroDetailFragment = new BigSceneIntroDetailFragment();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.big_scene_detail_frame_map, gMapFragment)
		//			.commit();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.frame_big_scene_intro, bigSceneIntroFragment)
		//			.commit();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.frame_big_scene_intro_detail, bigSceneIntroDetailFragment)
		//			.commit();
		//		}



	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();

		if(gMapFragment==null)
		{
			gMapFragment = new GMapFragment();
			//此处设置地图触摸监听，扩大地图至全屏，同时隐藏下面的视图,显示向上按钮
			gMapFragment.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if(!isMapBig)
					{
						changeMapStatus();
					}
				}
			});
			gMapFragment.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Log.v(TAG, "gMapFragment onTouch "+event.getAction());
					return false;
				}
			});
			if(bigScene!=null)
			{
				gMapFragment.setCenter(bigScene.getLatitude(), bigScene.getLongtitude());
			}


		}
		if(bigSceneIntroFragment==null)
			bigSceneIntroFragment = new BigSceneIntroFragment();
		if(bigSceneIntroDetailFragment==null)
			bigSceneIntroDetailFragment = new BigSceneIntroDetailFragment();
		Log.v(TAG, "gMapFragment.isAdded()?" +gMapFragment.isAdded());
		if(!gMapFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.big_scene_detail_frame_map, gMapFragment)
			.commit();
		if(!bigSceneIntroFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro, bigSceneIntroFragment)
			.commit();
		if(!bigSceneIntroDetailFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro_detail, bigSceneIntroDetailFragment)
			.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	/**
	 * 必须手动移除的内容
	 */
	public void destroyGMapFragment() {
		// 此处必须移除 gMapFragment  因为里面有webview的缘故 否则内存泄露
		getSupportFragmentManager().beginTransaction().remove(gMapFragment).commit();
		gMapFragment = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getRepeatCount() == 0)//没有重复
		{
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				destroyGMapFragment();
				this.finish();
				break;

			default:
				break;
			}
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 改变地图的状态
	 */
	public void changeMapStatus()
	{
		if(!isMapBig)
		{
			//放大地图至全屏
			lastMapHeight = gmapFrame.getHeight();//保存之前的高度
			bsd_btn_up.setVisibility(View.VISIBLE);
			bsd_btn_location.setVisibility(View.VISIBLE);
			FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams)gmapFrame.getLayoutParams();
			linearParams.height = bsdScroll.getHeight();
			gmapFrame.setLayoutParams(linearParams);
			linearParams=null;
			big_scene_detail_other.setVisibility(View.GONE);//这样防止滚动条的滚动
			isMapBig=true;
		}
		else
		{
			//恢复原状
			bsd_btn_up.setVisibility(View.INVISIBLE);
			bsd_btn_location.setVisibility(View.INVISIBLE);
			FrameLayout.LayoutParams linearParams = (LayoutParams) gmapFrame.getLayoutParams();
			linearParams.height = lastMapHeight;
			gmapFrame.setLayoutParams(linearParams);
			linearParams=null;
			big_scene_detail_other.setVisibility(View.VISIBLE);
			isMapBig=false;
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.big_scene_detail_title_left:
			destroyGMapFragment();
			this.finish();
			break;
		case R.id.bsd_btn_location:
			if(gMapFragment!=null)
				gMapFragment.requestLoc();
			break;	
		case R.id.bsd_btn_up:
			changeMapStatus();
			break;
		case R.id.bsd_enter_scene_btn:
			destroyGMapFragment();
			Intent myIntent = new Intent(this,SceneActivity.class); 
			myIntent.putExtra("bigSceneID", mIntent.getExtras().getInt("bigSceneID"));
			startActivity(myIntent);
			break;
		default:
			break;
		}
	}

}