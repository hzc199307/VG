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
	
	private FragmentManager fragmentManager;//fragment������

	private LeftSlidingMenuFragment leftSlidingMenuFragment;//���Fragment
	private HomeFragment homeFragment;//��Fragment
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
                //����ֻ��Ŀ�Ⱥ͸߶����ص�λΪpx  
        String strPM = "�ֻ���Ļ�ֱ���Ϊ:" + dm.widthPixels+"* "+dm.heightPixels;  
		
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
	 * ��ʼ�����˵�
	 */
	private void initSlidingMenu() {
		setBehindContentView(R.layout.main_left_layout);//������ߵĲ˵�����

		FragmentTransaction mFragementTransaction = fragmentManager
				.beginTransaction();
		mFragementTransaction.replace(R.id.main_left_fragment, leftSlidingMenuFragment).commit();
		
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// �������󻬻����һ����������Ҷ����Ի���������ֻ������
	    
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//��������ģʽ
		mSlidingMenu.setShadowDrawable(R.drawable.left_shadow);// ������˵���ӰͼƬ
		mSlidingMenu.setShadowWidthRes(R.dimen.left_shadow_width);
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setFadeDegree(0.5f);// ���õ��뵭���ı���
		mSlidingMenu.setBehindScrollScale(0.0f);// ���û���ʱ��קЧ��
		mSlidingMenu.setBehindWidth((int)(dm.widthPixels*behindWidth));//������Ŀ�� ���ջƽ����
		//mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// ����ʣ����
	}
	
	Fragment nowFragment;
	
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
                transaction.hide(from).add(R.id.content_frame, to).commit(); // ���ص�ǰ��fragment��add��һ����Activity��
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
		if (nowFragment != toFragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.hide(nowFragment);// ���ص�ǰ��fragment
            if (!toFragment.isAdded()) {    // ���ж��Ƿ�add��
                transaction.add(R.id.content_frame, toFragment).commit(); // add��һ����Activity��
            } else {
                transaction.show(toFragment).commit(); // ��ʾ��һ��
            }
            nowFragment = toFragment;
        }
		mSlidingMenu.showContent();
		
	}
	
	/**
	 * ��ת��HomeFragment
	 */
	public void switchContentToHome()
	{
		
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
		mSlidingMenu.showMenu();
	}

}
