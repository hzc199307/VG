package com.ne.vg.main;

import java.util.Timer;
import java.util.TimerTask;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.DBHelper.DBHelper;
import com.ne.vg.R.id;
import com.ne.vg.R.layout;
import com.ne.vg.fragment.LeftSlidingMenuFragment;
import com.ne.vg.util.LogUtil;
import com.ne.vg.util.MusicNotification;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends SlidingFragmentActivity {

	private final static double behindWidth = 0.75;//侧边栏比例参数
	private final static String TAG = "MainActivity";

	protected SlidingMenu mSlidingMenu;

	private FragmentManager fragmentManager;//fragment管理器

	//MainActivity下的侧边栏Fragment以及四个选项卡Fragment
	private LeftSlidingMenuFragment leftSlidingMenuFragment;//侧边Fragment
	private HomeFragment homeFragment;//主Fragment
	private MineFragment mineFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;
	private Fragment nowFragment;//目前的fragment

	private ImageView main_iv_cover;//主布局覆盖阴影 在侧边栏出现时该阴影显示

	private TranslateAnimation titleAnimation;//标题栏动画

	private DisplayMetrics dm;

	private VGApplication app;

	private ProgressDialog progressDialog;//正在加载的弹出框

	private AlertDialog.Builder builder;//定位完成的弹出框

	private boolean enter2ndF = false;//是否进入了二层页面
	private Fragment lastFragment = null;//是否进入了二层页面

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(TAG, "onCreate " + this + ": " + savedInstanceState);
		setContentView(R.layout.activity_main);

		//初始化app
		app = (VGApplication)this.getApplication();

		progressDialog = new ProgressDialog(this);

		fragmentManager = getSupportFragmentManager();

		//必然加载侧边栏和主页面
		leftSlidingMenuFragment = new LeftSlidingMenuFragment();
		homeFragment = new HomeFragment();

		if (savedInstanceState == null) {
			fragmentManager.beginTransaction()
			.add(R.id.main_content_frame, homeFragment)
			.commit();
			nowFragment = homeFragment;
		}

		/***有关屏幕像素和dpi信息***/
		dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		//获得手机的宽度和高度像素单位为px  
		//String strPM = "手机屏幕分辨率为:" + dm.widthPixels+"* "+dm.heightPixels;  
		//Toast.makeText(getApplicationContext(), strPM, Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), getResources().getDisplayMetrics().densityDpi+"", Toast.LENGTH_SHORT).show(); 
		titleAnimation = new TranslateAnimation(0, (int)(dm.widthPixels*(1-behindWidth-0.05)), 0, 0);

		initSlidingMenu();

	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.d(TAG, "onResume");

	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		LogUtil.d(TAG, "onResumeFragments");
		// 当nowFragment没有内容 或者被隐藏,就跳往主界面,确保界面显示正确
		if(nowFragment == null||nowFragment.isHidden())
			switchContentToHome();

	}

	/**
	 * 初始化左侧菜单
	 */
	private void initSlidingMenu() {

		main_iv_cover = (ImageView)findViewById(R.id.main_iv_cover);

		setBehindContentView(R.layout.main_left_layout);//设置左边的菜单布局

		FragmentTransaction mFragementTransaction = fragmentManager
				.beginTransaction();
		mFragementTransaction.replace(R.id.main_left_fragment, leftSlidingMenuFragment).commit();

		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//设置手势模式
		//mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片  这里我自行采用阴影
		//mSlidingMenu.setShadowWidth(100);
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setFadeDegree(1.0f);// 设置淡入淡出的比例
		mSlidingMenu.setBehindScrollScale(0.1f);// 设置滑动时拖拽效果
		mSlidingMenu.setBehindWidth((int)(dm.widthPixels*behindWidth));//侧边栏的宽度 按照behindWidth的比例
		//mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置剩余宽度
		mSlidingMenu.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
				setCoverIvVisibility(View.VISIBLE);
				if(nowFragment instanceof AnimationFragment)
					((AnimationFragment)nowFragment).startAnimation(titleAnimation);
			}
		});
		mSlidingMenu.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				setCoverIvVisibility(View.GONE);
				if(nowFragment instanceof AnimationFragment)
					((AnimationFragment)nowFragment).clearAnimation();
			}
		});
	}

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
				transaction.hide(from).add(R.id.main_content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
			nowFragment = to;
		}
	}

	/**
	 * 跳转到另外一个Fragment(此Fragment处于第一层)
	 * @param toFragment
	 */
	private void switchContent(Fragment toFragment) {
		if(toFragment==null)//为空不跳转
			return;
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (nowFragment != toFragment) {
			//.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
			if(nowFragment!=null)transaction.hide(nowFragment);// 隐藏当前的fragment
			nowFragment = toFragment;
		}
		if (!toFragment.isAdded()) { //判断是否被add过
			transaction.add(R.id.main_content_frame, toFragment).commit(); // add下一个到Activity中
		} 
		if(toFragment.isHidden()){   //判断是否被隐藏过 
			transaction.show(toFragment).commit(); // 显示下一个
		}
		mSlidingMenu.showContent();
		enter2ndF = false;
	}

	/**
	 * 跳转到另外一个Fragment 且可以返回(此Fragment处于第二层)
	 * @param toFragment
	 */
	private void switchContentCanBack(Fragment toFragment) {
		switchContent(toFragment);
		enter2ndF = true;
	}

	/**
	 * 跳转到HomeFragment
	 */
	public void switchContentToHome()
	{
		if(homeFragment == null)
			homeFragment = new HomeFragment();
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
	 * 跳转到SearchFragment 且可以返回到lastFragment
	 */
	public void switchContentCanBackToSearchFrom(Fragment lastFragment)
	{
		this.lastFragment = lastFragment;
		if(searchFragment == null)
			searchFragment = new SearchFragment();
		switchContentCanBack(searchFragment);
		searchFragment.clearAnimation();
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
	 * 此函数为了xml布局里面的“android:onClick="showMenu"”
	 * @param view
	 */
	public void showMenu(View view)
	{
		mSlidingMenu.showMenu(true);
	}

	/**
	 * 设置主界面的覆盖图层是否显示
	 * @param visibility
	 */
	public void setCoverIvVisibility(int visibility)
	{
		main_iv_cover.setVisibility(visibility);
	}

	/**
	 * 显示加载弹出框
	 * @param stringID
	 */
	public void showProgressDialog(int stringID)
	{
		progressDialog.setMessage(getString(stringID));
		progressDialog.setCancelable(true);
		progressDialog.show();
		progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {

			}
		});
	}

	/**
	 * 关闭加载弹出框
	 */
	public void dismissProgressDialog()
	{
		progressDialog.dismiss();
	}

	/**
	 * 显示双按钮弹出框  处理有关定位 TODO 暂时未加进去
	 */
	public void showBuilder() {

		builder = new Builder(this);
		builder.setMessage("确定进入吗？");
		builder.setTitle("最近的城市  ````");
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//				Intent intent = new Intent(mContext,CityActivity.class); // 跳转到城市景点详情页面 
				//				Bundle bundle = new Bundle();                           //创建Bundle对象   
				//				bundle.putString("cityName", mCityBean.getCityName());     //装入数据  
				//				bundle.putInt("cityID", mCityBean.getCityID());
				//				intent.putExtras(bundle);                               //把Bundle塞入Intent里面   
				//				startActivity(intent);                                     //开始切换 
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		LogUtil.d(TAG, "onRestoreInstanceState");
		//		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		LogUtil.d(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		// TODO 在Activity处于后台的时候 关掉那些不用的Fragment,减少内存消耗
		super.onStop();
		destroyAllFragmentWithoutNow();
		LogUtil.d(TAG, "onStop");

	}

	@Override
	protected void onDestroy() {
		LogUtil.d(TAG, "onDestroy");
		nowFragment = null;

		//删除通知栏
		clearNotify(MusicNotification.notifyId);
		//解除服务的绑定
		getApplication().unbindService(app.mConnection);
		super.onDestroy();
	}

	/**
	 * 清除通知栏的消息，这样保证在APP第一个Activity被销毁时，能够移除通知栏
	 * @param notifyId
	 */
	private void clearNotify(int notifyId) {
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
	}

	/**
	 * remove某一个fragment
	 * @param fragment
	 */
	public void destroyFragment(Fragment fragment)
	{
		if(fragmentManager==null)
			fragmentManager = getSupportFragmentManager();
		if(fragment!=null)
		{
			if(nowFragment==fragment)//如果remove的是现在显示的fragment
				nowFragment = null;
			if(fragment.isAdded()==false)//如果被添加才移除
				fragmentManager.beginTransaction()
				.remove(fragment).commit();
			fragment = null;
		}
	}

	/**
	 * remove某一个非nowragment的fragment 
	 * @param fragment
	 */
	public void destroyFragmentNotNow(Fragment fragment)
	{
		if(nowFragment==fragment)
			return;
		destroyFragment(fragment);
	}

	/**
	 * remove所有fragment （除了leftSlidingMenuFragment）
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		LogUtil.d(TAG, "onKeyDown");
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			LogUtil.d(TAG, "back");
			if(enter2ndF == true)
			{
				switchContent(lastFragment);
			}
			else
			{
				//调用双击退出函数
				exitBy2Click();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);

	}
	/**
	 * 按2次返回退出
	 */
	private void exitBy2Click() {
		// TODO Auto-generated method stub
		Timer tExit = null;
		if(isExit == false){
			//准备退出
			isExit = true;
			Toast.makeText(getApplication(), "再按一次退出程序", Toast.LENGTH_LONG).show();
			tExit = new Timer();
			//如果2s内没有按下返回键，则启动定时器取消掉刚才的操作
			tExit.schedule(new TimerTask(){

				@Override
				public void run() {
					isExit = false;
				}

			}, 2000);
		}else{
			app.exit();
		}
	}
}
