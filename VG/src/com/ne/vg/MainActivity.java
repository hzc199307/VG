package com.ne.vg;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.ne.vg.DBHelper.DBHelper;
import com.ne.vg.fragment.HomeFragment;
import com.ne.vg.fragment.LeftSlidingMenuFragment;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.fragment.RouteFragment;
import com.ne.vg.fragment.SearchFragment;
import com.ne.vg.fragment.SettingFragment;
import com.ne.vg.service.MusicService;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends SlidingFragmentActivity {

	private final static double behindWidth = 0.75;
	private final static String TAG = "MainActivity";

	protected SlidingMenu mSlidingMenu;
	private ImageButton ivTitleBtnLeft;

	private FragmentManager fragmentManager;//fragment������

	private LeftSlidingMenuFragment leftSlidingMenuFragment;//���Fragment
	private HomeFragment homeFragment;//��Fragment
	private MineFragment mineFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;

	private ImageView home_iv_cover;

	private TranslateAnimation animation1,animation2;

	DisplayMetrics dm;
	DBHelper mDbHelper;

	private VGApplication app;
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
			.add(R.id.main_content_frame, homeFragment)
			.commit();
			nowFragment = homeFragment;
		}


		dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		//����ֻ��Ŀ�Ⱥ͸߶����ص�λΪpx  
		String strPM = "�ֻ���Ļ�ֱ���Ϊ:" + dm.widthPixels+"* "+dm.heightPixels;  

		Toast.makeText(getApplicationContext(), strPM, Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), getResources().getDisplayMetrics().densityDpi+"", Toast.LENGTH_SHORT).show(); 

		animation1 = new TranslateAnimation(0, (int)(dm.widthPixels*(1-behindWidth-0.05)), 0, 0);
		animation2 = new TranslateAnimation(-(int)(dm.widthPixels*behindWidth), 0, 0, 0);

		initSlidingMenu();
		initService();
	}
	/**
	 * 
	 * @Title: initService 
	 * @Description: ��ʼ����
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void initService()
	{
		app = (VGApplication) this.getApplication();
		Intent serviceIntent = new Intent(this,MusicService.class);
		bindService(serviceIntent, app.mConnection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume");

	}

	@Override
	protected void onResumeFragments() {
		Log.v(TAG, "onResumeFragments");
		super.onResumeFragments();
		// TODO ��nowFragmentû������ ���߱����� ����������
		if(nowFragment == null||nowFragment.isHidden())
			switchContentToHome();

	}

	/**
	 * ��ʼ�����˵�
	 */
	private void initSlidingMenu() {

		home_iv_cover = (ImageView)findViewById(R.id.home_iv_cover);

		setBehindContentView(R.layout.main_left_layout);//������ߵĲ˵�����

		FragmentTransaction mFragementTransaction = fragmentManager
				.beginTransaction();
		mFragementTransaction.replace(R.id.main_left_fragment, leftSlidingMenuFragment).commit();

		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// �������󻬻����һ����������Ҷ����Ի���������ֻ������

		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//��������ģʽ
		//		mSlidingMenu.setShadowDrawable(R.drawable.left_shadow);// ������˵���ӰͼƬ
		//		mSlidingMenu.setShadowWidthRes(R.dimen.left_shadow_width);
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setFadeDegree(0.5f);// ���õ��뵭���ı���
		mSlidingMenu.setBehindScrollScale(0.1f);// ���û���ʱ��קЧ��
		mSlidingMenu.setBehindWidth((int)(dm.widthPixels*behindWidth));//������Ŀ�� ���ջƽ����
		//mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ����ʣ����
		mSlidingMenu.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
				setCoverIvVisibility(View.VISIBLE);
				if(nowFragment == homeFragment)
					homeFragment.startAnimation(animation1);
				else if(nowFragment == mineFragment)
					mineFragment.startAnimation(animation1);
				//				leftSlidingMenuFragment.startAnimation(animation2);
			}
		});
		mSlidingMenu.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				setCoverIvVisibility(View.GONE);
				if(nowFragment == homeFragment)
					homeFragment.clearAnimation();
				else if(nowFragment == mineFragment)
					mineFragment.clearAnimation();
				//				leftSlidingMenuFragment.clearAnimation();
			}
		});
	}

	private Fragment nowFragment;//Ŀǰ��fragment

	/**
	 * ��һ��Fragment��ת������һ��Fragment
	 * @param from
	 * @param to
	 */
	public void switchContent(Fragment from, Fragment to) {
		if (nowFragment != to) {
			FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
					android.R.anim.fade_in, android.R.anim.fade_out);
			if (!to.isAdded()) {    // ���ж��Ƿ�add��
				transaction.hide(from).add(R.id.main_content_frame, to).commit(); // ���ص�ǰ��fragment��add��һ����Activity��
			} else {
				transaction.hide(from).show(to).commit(); // ���ص�ǰ��fragment����ʾ��һ��
			}
			nowFragment = to;
		}
	}

	/**
	 * ��ת������һ��Fragment
	 * @param toFragment
	 */
	private void switchContent(Fragment toFragment) {
		if(toFragment==null)//Ϊ�ղ���ת
			return;
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (nowFragment != toFragment) {
			//.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
			if(nowFragment!=null)transaction.hide(nowFragment);// ���ص�ǰ��fragment
			nowFragment = toFragment;
		}
		if (!toFragment.isAdded()) { //�ж��Ƿ�add��
			transaction.add(R.id.main_content_frame, toFragment).commit(); // add��һ����Activity��
		} 
		if(toFragment.isHidden()){   //�ж��Ƿ����ع�
			transaction.show(toFragment).commit(); // ��ʾ��һ��
		}
		mSlidingMenu.showContent();
	}

	/**
	 * ��ת��HomeFragment
	 */
	public void switchContentToHome()
	{
		if(homeFragment == null)
			homeFragment = new HomeFragment();
		switchContent(homeFragment);
	}

	/**
	 * ��ת��MineFragment
	 */
	public void switchContentToMine()
	{
		if(mineFragment == null)
			mineFragment = new MineFragment();
		switchContent(mineFragment);
	}
	/**
	 * ��ת��SearchFragment
	 */
	public void switchContentToSearch()
	{
		if(searchFragment == null)
			searchFragment = new SearchFragment();
		switchContent(searchFragment);
	}
	/**
	 * ��ת��SettingFragment
	 */
	public void switchContentToSetting()
	{
		if(settingFragment == null)
			settingFragment = new SettingFragment();
		switchContent(settingFragment);
	}

	/**
	 * ���ò�����Ƿ�����
	 * @param enabled
	 */
	public void setSlidingEnabled(boolean enabled)
	{
		mSlidingMenu.setSlidingEnabled(enabled);
	}

	public void showMenu(View view)
	{
		mSlidingMenu.showMenu(true);
	}

	/**
	 * ���ø���ͼ���Ƿ���ʾ
	 * @param visibility
	 */
	public void setCoverIvVisibility(int visibility)
	{
		home_iv_cover.setVisibility(visibility);
	}



	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onSaveInstanceState");
		//super.onSaveInstanceState(outState);

	}

	@Override
	protected void onStop() {
		// TODO ��Activity���ں�̨��ʱ�� �ص���Щ���õ�Fragment
		destroyAllFragmentWithoutNow();
		super.onStop();
		Log.v(TAG, "onStop");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		Log.v(TAG, "onDestroy");
		//		destroyFragment(leftSlidingMenuFragment);
		//		destroyFragment(homeFragment);
		//		destroyFragment(mineFragment);
		//		destroyFragment(searchFragment);
		//		destroyFragment(settingFragment);
		nowFragment = null;
		//�������İ�
		unbindService(app.mConnection);
		super.onDestroy();
	}

	/**
	 * removeĳһ��fragment
	 * @param fragment
	 */
	public void destroyFragment(Fragment fragment)
	{
		if(fragmentManager==null)
			fragmentManager = getSupportFragmentManager();
		if(fragment!=null)
		{
			if(nowFragment==fragment)//���remove����������ʾ��fragment
				nowFragment = null;
			fragmentManager.beginTransaction()
			.remove(fragment).commit();
			fragment = null;
		}
	}

	/**
	 * removeĳһ����nowragment��fragment 
	 * @param fragment
	 */
	public void destroyFragmentNotNow(Fragment fragment)
	{
		if(nowFragment==fragment)
			return;
		destroyFragment(fragment);
	}

	/**
	 * remove����fragment ������leftSlidingMenuFragment��
	 * @param fragment
	 */
	public void destroyAllFragmentWithoutNow()
	{
		destroyFragmentNotNow(homeFragment);
		destroyFragmentNotNow(mineFragment);
		destroyFragmentNotNow(searchFragment);
		destroyFragmentNotNow(settingFragment);
	}
	
	private static Boolean isExit = false;
	/**
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			//����˫���˳�����
			exitBy2Click();
		}
		return false;
	}
	private void exitBy2Click() {
		// TODO Auto-generated method stub
		Timer tExit = null;
		if(isExit == false){
			//׼���˳�
			isExit = true;
			Toast.makeText(getApplication(), "�ٰ�һ���˳�����", Toast.LENGTH_LONG).show();
			tExit = new Timer();
			//���2s��û�а��·��ؼ�����������ʱ��ȡ�����ղŵĲ���
			tExit.schedule(new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					isExit = false;
				}
				
			}, 2000);
		}else{
			//�˳���������,��һ���Ҳ�֪���Ӳ��ӡ���
			unbindService(app.mConnection);
			app.exit();
		}
	}
}
