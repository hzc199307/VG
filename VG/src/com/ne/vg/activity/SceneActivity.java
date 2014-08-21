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
	private MyViewPager viewPager;//ҳ������
	private SceneAdapter sceneAdapter;
	private View view;// ����ͼƬ
	private View scene_map_layout,scene_list_layout;
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
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
	 * �����ִ��˳��Ҫ����ı�
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
	 * @Description: ��ʼ���������������ҳ������ʱ������ĺ���Ҳ������Ч������������Ҫ����һЩ����
	 */
	private void initTabAnim() {
		view= (View) findViewById(R.id.scene_cursor);   
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��  
		offset = screenW / 2;// ����ƫ����  
	}

	public class MyOnPageChangeListener implements OnPageChangeListener{

		//TODO

		Animation animation ;
		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			//arg1�Ƕ�����ʼ�ĵ��뵱ǰView X�����ϵĲ�ֵ��arg2�Ƕ��������ĵ��뵱ǰView X�����ϵĲ�ֵ 
			Log.v(TAG, "onPageSelected "+arg0);
			animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
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
			//��ʼ��fragment
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
			viewPager.setCurrentItem(MAP_STATUS);  //���Զ����� myOnPageChangeListener.onPageSelected()
			break;
		case R.id.scene_list_layout:
			viewPager.setCurrentItem(LIST_STATUS);
			break;
		default:
			break;
		}
	}

	/**
	 * �����ֶ��Ƴ�������
	 */
	public void destroyGMapFragment() {
		// �˴������Ƴ� gMapFragment  ��Ϊ������webview��Ե�� �����ڴ�й¶
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
