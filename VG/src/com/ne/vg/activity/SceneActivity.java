package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.BigSceneListFragment;
import com.ne.vg.fragment.SceneSmallSceneListFragment;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.view.MyViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SceneActivity extends FragmentActivity implements View.OnClickListener{

	private final static String TAG = "SceneActivity";

	private final static int MAP_STATUS = 0,LIST_STATUS = 1;
	private MyViewPager viewPager;//页卡内容
	private SceneAdapter sceneAdapter;
	private View view;// 动画图片
	private View scene_map_layout,scene_list_layout;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private MyOnPageChangeListener myOnPageChangeListener;

	private GMapFragment gMapFragment ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene);
		initTab();
		initTabAnim();
		initViewPager();
	}

	/**
	 * initViewPager 
	 * 里面的执行顺序不要随意改变
	 */
	private void initViewPager() {
		viewPager=(MyViewPager) findViewById(R.id.scene_viewpager); 
		sceneAdapter = new SceneAdapter(getSupportFragmentManager());
		viewPager.setAdapter(sceneAdapter);
		myOnPageChangeListener = new MyOnPageChangeListener();
		viewPager.setOnPageChangeListener(myOnPageChangeListener);  
		viewPager.setCurrentItem(MAP_STATUS);  
		myOnPageChangeListener.onPageSelected(MAP_STATUS);
	}

	/**
	 * initTab
	 */
	private void initTab() {
		scene_map_layout = (View)findViewById(R.id.scene_map_layout);
		scene_list_layout = (View)findViewById(R.id.scene_list_layout);
		scene_map_layout.setOnClickListener(this);
		scene_list_layout.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initImageView 
	 * @Description: 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void initTabAnim() {
		view= (View) findViewById(R.id.scene_cursor);   
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		int screenW = dm.widthPixels;// 获取分辨率宽度  
		offset = screenW / 2;// 计算偏移量  
	}

	public class MyOnPageChangeListener implements OnPageChangeListener{

		//TODO

		Animation animation ;
		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			//arg1是动画开始的点离当前View X坐标上的差值，arg2是动画结束的点离当前View X坐标上的差值 
			Log.v(TAG, "onPageSelected "+arg0);
			animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			view.startAnimation(animation);
			switch (arg0) {
			case MAP_STATUS:
				scene_map_layout.setSelected(true);
				scene_list_layout.setSelected(false);
				viewPager.setCanScroll(false);
				break;
			case LIST_STATUS:
				scene_map_layout.setSelected(false);
				scene_list_layout.setSelected(true);
				viewPager.setCanScroll(true);
				break;
			default:
				break;
			}
		}
	}

	public class SceneAdapter extends FragmentPagerAdapter{

		int NUM_ITEM = 2;
		public SceneAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			//初始化fragment
			case 0:
				Log.v(TAG, "new GMapFragment()");
				gMapFragment = new GMapFragment();
				return gMapFragment;
			case 1:
				return new SceneSmallSceneListFragment();
			default:return null;
			}
		}

		@Override
		public int getCount() {
			return NUM_ITEM;
		}

		@Override  
		public void destroyItem(ViewGroup container, int position, Object object) {  
			super.destroyItem(container, position, object);  
		} 

	}

	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.scene_title_left:
			destroyGMapFragment();
			this.finish();
		case R.id.scene_map_layout:
			viewPager.setCurrentItem(MAP_STATUS);  //会自动调用 myOnPageChangeListener.onPageSelected()
			break;
		case R.id.scene_list_layout:
			viewPager.setCurrentItem(LIST_STATUS);
			break;
		default:
			break;
		}
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
		destroyGMapFragment();
		this.finish();
		return super.onKeyDown(keyCode, event);
	}

}
