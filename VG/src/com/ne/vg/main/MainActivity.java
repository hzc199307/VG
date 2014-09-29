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

	private final static double behindWidth = 0.75;//�������������
	private final static String TAG = "MainActivity";

	protected SlidingMenu mSlidingMenu;

	private FragmentManager fragmentManager;//fragment������

	//MainActivity�µĲ����Fragment�Լ��ĸ�ѡ�Fragment
	private LeftSlidingMenuFragment leftSlidingMenuFragment;//���Fragment
	private HomeFragment homeFragment;//��Fragment
	private MineFragment mineFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;
	private Fragment nowFragment;//Ŀǰ��fragment

	private ImageView main_iv_cover;//�����ָ�����Ӱ �ڲ��������ʱ����Ӱ��ʾ

	private TranslateAnimation titleAnimation;//����������

	private DisplayMetrics dm;

	private VGApplication app;

	private ProgressDialog progressDialog;//���ڼ��صĵ�����

	private AlertDialog.Builder builder;//��λ��ɵĵ�����

	private boolean enter2ndF = false;//�Ƿ�����˶���ҳ��
	private Fragment lastFragment = null;//�Ƿ�����˶���ҳ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(TAG, "onCreate " + this + ": " + savedInstanceState);
		setContentView(R.layout.activity_main);

		//��ʼ��app
		app = (VGApplication)this.getApplication();

		progressDialog = new ProgressDialog(this);

		fragmentManager = getSupportFragmentManager();

		//��Ȼ���ز��������ҳ��
		leftSlidingMenuFragment = new LeftSlidingMenuFragment();
		homeFragment = new HomeFragment();

		if (savedInstanceState == null) {
			fragmentManager.beginTransaction()
			.add(R.id.main_content_frame, homeFragment)
			.commit();
			nowFragment = homeFragment;
		}

		/***�й���Ļ���غ�dpi��Ϣ***/
		dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		//����ֻ��Ŀ�Ⱥ͸߶����ص�λΪpx  
		//String strPM = "�ֻ���Ļ�ֱ���Ϊ:" + dm.widthPixels+"* "+dm.heightPixels;  
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
		// ��nowFragmentû������ ���߱�����,������������,ȷ��������ʾ��ȷ
		if(nowFragment == null||nowFragment.isHidden())
			switchContentToHome();

	}

	/**
	 * ��ʼ�����˵�
	 */
	private void initSlidingMenu() {

		main_iv_cover = (ImageView)findViewById(R.id.main_iv_cover);

		setBehindContentView(R.layout.main_left_layout);//������ߵĲ˵�����

		FragmentTransaction mFragementTransaction = fragmentManager
				.beginTransaction();
		mFragementTransaction.replace(R.id.main_left_fragment, leftSlidingMenuFragment).commit();

		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// �������󻬻����һ����������Ҷ����Ի���������ֻ������
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//��������ģʽ
		//mSlidingMenu.setShadowDrawable(R.drawable.shadow);// ������˵���ӰͼƬ  ���������в�����Ӱ
		//mSlidingMenu.setShadowWidth(100);
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setFadeDegree(1.0f);// ���õ��뵭���ı���
		mSlidingMenu.setBehindScrollScale(0.1f);// ���û���ʱ��קЧ��
		mSlidingMenu.setBehindWidth((int)(dm.widthPixels*behindWidth));//������Ŀ�� ����behindWidth�ı���
		//mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ����ʣ����
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
	 * ��ת������һ��Fragment(��Fragment���ڵ�һ��)
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
		enter2ndF = false;
	}

	/**
	 * ��ת������һ��Fragment �ҿ��Է���(��Fragment���ڵڶ���)
	 * @param toFragment
	 */
	private void switchContentCanBack(Fragment toFragment) {
		switchContent(toFragment);
		enter2ndF = true;
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
	 * ��ת��SearchFragment �ҿ��Է��ص�lastFragment
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
	 * ��ת��SettingFragment
	 */
	public void switchContentToSetting()
	{
		if(settingFragment == null)
			settingFragment = new SettingFragment();
		switchContent(settingFragment);
	}

	/**
	 * �˺���Ϊ��xml��������ġ�android:onClick="showMenu"��
	 * @param view
	 */
	public void showMenu(View view)
	{
		mSlidingMenu.showMenu(true);
	}

	/**
	 * ����������ĸ���ͼ���Ƿ���ʾ
	 * @param visibility
	 */
	public void setCoverIvVisibility(int visibility)
	{
		main_iv_cover.setVisibility(visibility);
	}

	/**
	 * ��ʾ���ص�����
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
	 * �رռ��ص�����
	 */
	public void dismissProgressDialog()
	{
		progressDialog.dismiss();
	}

	/**
	 * ��ʾ˫��ť������  �����йض�λ TODO ��ʱδ�ӽ�ȥ
	 */
	public void showBuilder() {

		builder = new Builder(this);
		builder.setMessage("ȷ��������");
		builder.setTitle("����ĳ���  ````");
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//				Intent intent = new Intent(mContext,CityActivity.class); // ��ת�����о�������ҳ�� 
				//				Bundle bundle = new Bundle();                           //����Bundle����   
				//				bundle.putString("cityName", mCityBean.getCityName());     //װ������  
				//				bundle.putInt("cityID", mCityBean.getCityID());
				//				intent.putExtras(bundle);                               //��Bundle����Intent����   
				//				startActivity(intent);                                     //��ʼ�л� 
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {

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
		// TODO ��Activity���ں�̨��ʱ�� �ص���Щ���õ�Fragment,�����ڴ�����
		super.onStop();
		destroyAllFragmentWithoutNow();
		LogUtil.d(TAG, "onStop");

	}

	@Override
	protected void onDestroy() {
		LogUtil.d(TAG, "onDestroy");
		nowFragment = null;

		//ɾ��֪ͨ��
		clearNotify(MusicNotification.notifyId);
		//�������İ�
		getApplication().unbindService(app.mConnection);
		super.onDestroy();
	}

	/**
	 * ���֪ͨ������Ϣ��������֤��APP��һ��Activity������ʱ���ܹ��Ƴ�֪ͨ��
	 * @param notifyId
	 */
	private void clearNotify(int notifyId) {
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
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
			if(fragment.isAdded()==false)//�������Ӳ��Ƴ�
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
				//����˫���˳�����
				exitBy2Click();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);

	}
	/**
	 * ��2�η����˳�
	 */
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
					isExit = false;
				}

			}, 2000);
		}else{
			app.exit();
		}
	}
}
