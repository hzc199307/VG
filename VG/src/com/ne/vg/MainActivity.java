package com.ne.vg;

import com.ne.vg.fragment.HomeFragment;
import com.ne.vg.fragment.LeftSlidingMenuFragment;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.fragment.RouteFragment;
import com.ne.vg.fragment.SearchFragment;
import com.ne.vg.fragment.SettingFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends SlidingFragmentActivity {

	private final static double behindWidth = 0.75;
	private final static String TAG = "MainActivity";
	
	protected SlidingMenu mSlidingMenu;
	private ImageButton ivTitleBtnLeft;
	
	private FragmentManager fragmentManager;//fragment管理器

	private LeftSlidingMenuFragment leftSlidingMenuFragment;//侧边Fragment
	private HomeFragment homeFragment;//主Fragment
	private MineFragment mineFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;
	
	DisplayMetrics dm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_main);

		fragmentManager = getSupportFragmentManager();
		
		leftSlidingMenuFragment = new LeftSlidingMenuFragment();
		homeFragment = new HomeFragment();
		
		
		if (savedInstanceState == null) {
			fragmentManager.beginTransaction()
                    .add(R.id.content_frame, homeFragment)
                    .commit();
			nowFragment = homeFragment;
        }

		
		dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
                //获得手机的宽度和高度像素单位为px  
        String strPM = "手机屏幕分辨率为:" + dm.widthPixels+"* "+dm.heightPixels;  
		
        Toast.makeText(getApplicationContext(), strPM, Toast.LENGTH_SHORT).show();
        
		initSlidingMenu();

//		ivTitleBtnLeft = (ImageButton)this.findViewById(R.id.ivTitleBtnLeft);
//		ivTitleBtnLeft.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) { 
//				// TODO Auto-generated method stub
//				mSlidingMenu.showMenu(true);
//			}
//		});
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume");
	}

	/**
	 * 初始化左侧菜单
	 */
	private void initSlidingMenu() {
		setBehindContentView(R.layout.main_left_layout);//设置左边的菜单布局

		FragmentTransaction mFragementTransaction = fragmentManager
				.beginTransaction();
		mFragementTransaction.replace(R.id.main_left_fragment, leftSlidingMenuFragment).commit();
		
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
	    
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//设置手势模式
		mSlidingMenu.setShadowDrawable(R.drawable.left_shadow);// 设置左菜单阴影图片
		mSlidingMenu.setShadowWidthRes(R.dimen.left_shadow_width);
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setFadeDegree(0.5f);// 设置淡入淡出的比例
		mSlidingMenu.setBehindScrollScale(0.0f);// 设置滑动时拖拽效果
		mSlidingMenu.setBehindWidth((int)(dm.widthPixels*behindWidth));//侧边栏的宽度 按照黄金比例
		//mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置剩余宽度
	}
	
	Fragment nowFragment;
	
	/**
	 * 从一个Fragment跳转到另外一个Fragment
	 * @param from
	 * @param to
	 */
	public void switchContent(Fragment from, Fragment to) {
        if (nowFragment != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            nowFragment = to;
        }
    }
	
	/**
	 * 跳转到另外一个Fragment
	 * @param toFragment
	 */
	private void switchContent(Fragment toFragment) {
		if (nowFragment != toFragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.hide(nowFragment);// 隐藏当前的fragment
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                transaction.add(R.id.content_frame, toFragment).commit(); // add下一个到Activity中
            } else {
                transaction.show(toFragment).commit(); // 显示下一个
            }
            nowFragment = toFragment;
        }
		mSlidingMenu.showContent();
		
	}
	
	/**
	 * 跳转到HomeFragment
	 */
	public void switchContentToHome()
	{
		
		switchContent(homeFragment);
	}
	
	/**
	 * 跳转到MineFragment
	 */
	public void switchContentToMine()
	{
		if(mineFragment == null)
			mineFragment = new MineFragment();
		switchContent(mineFragment);
	}
	/**
	 * 跳转到SearchFragment
	 */
	public void switchContentToSearch()
	{
		if(searchFragment == null)
			searchFragment = new SearchFragment();
		switchContent(searchFragment);
	}
	/**
	 * 跳转到SettingFragment
	 */
	public void switchContentToSetting()
	{
		if(settingFragment == null)
			settingFragment = new SettingFragment();
		switchContent(settingFragment);
	}
	
	/**
	 * 设置侧边栏是否有用
	 * @param enabled
	 */
	public void setSlidingEnabled(boolean enabled)
	{
		mSlidingMenu.setSlidingEnabled(enabled);
	}
	
	public void showMenu(View view)
	{
		mSlidingMenu.showMenu();
	}

}
