package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.dao.VGDao;
import com.ne.vg.fragment.BigSceneListFragment;
import com.ne.vg.fragment.SceneSmallSceneListFragment;
import com.ne.vg.fragment.SceneSmallSceneListFragment.OnMusicSelectedListener;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.util.MusicPlayerUtil;
import com.ne.vg.view.MyViewPager;

import android.R.mipmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.SeekBar;
import android.widget.TextView;

public class SceneActivity extends FragmentActivity implements View.OnClickListener,OnMusicSelectedListener{

	private final static String TAG = "SceneActivity";

	private final static int MAP_STATUS = 0,LIST_STATUS = 1;
	private MyViewPager viewPager;//ҳ������
	private SceneAdapter sceneAdapter;
	private View view;// ����ͼƬ
	private View scene_map_layout,scene_list_layout;
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private SeekBar mSeekBar;// ��ʼ��seekBar
	private MyOnPageChangeListener myOnPageChangeListener;
	
	private TextView scene_music_time_now;
	private TextView scene_music_time_total;
	private GMapFragment gMapFragment ;
	private TextView scene_title_name;
	private VGApplication app;
	private Intent mIntent;
	private VGDao mVgDao;
	private String cityName;
	private String bigSceneName;
	private int bigSceneID;
	private boolean isPlaying;
	
	private boolean isSeekbarChanged;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//��ʼ��VGApplication
		// TODO Auto-generated method stub
		
		app = (VGApplication)this.getApplication();
		mVgDao = new VGDao(this);
		mIntent = this.getIntent();
		cityName = mIntent.getExtras().getString("cityName");
		bigSceneName = mIntent.getExtras().getString("bigSceneName");
		bigSceneID = mIntent.getExtras().getInt("bigSceneID");
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene);
		initTab();
		initTabAnim();
		initViewPager();
		initSeekBar();
		//�����߳�
		handler.post(updateThread);
	}
	
	/**
	 * ���߳�����ʵʱ����seekbar
	 */
	Handler handler = new Handler();
	Runnable updateThread = new Runnable()
	{
		public void run() 
		{
			mSeekBar = (SeekBar)findViewById(R.id.scene_music_seekbar);
	      //��ø������ڲ���λ�ò����óɲ��Ž�������ֵ
			mSeekBar.setProgress(app.mBinder.getService().getPlayer().getCurrentPosition());
			scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(app.mBinder.getService().getPlayer().getCurrentPosition()));
	      //ÿ���ӳ�100�����������߳�
			handler.postDelayed(updateThread, 100);
	    }
	};
	
	/**
	 * 
	 * @Title: initSeekBar 
	 * @Description: ��ʼ��seekBar
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void initSeekBar() {
		MediaPlayer player = app.mBinder.getService().getPlayer();
		
		mSeekBar = (SeekBar)findViewById(R.id.scene_music_seekbar);
		//��ø����ĳ��Ȳ����óɲ��Ž����������ֵ  
		if(player!=null){
			scene_music_time_total.setText(MusicPlayerUtil.milliSecondsToTimer(player.getDuration()));
			scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(player.getCurrentPosition()));
			
			mSeekBar.setMax(player.getDuration());
			Log.d(TAG, "duration is:" + player.getDuration());
		}
			
		else{
			mSeekBar.setMax(0);
			Log.d(TAG, "player is null!");
		}
		//mSeekBar.setMax(player.getDuration());  
		
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			//����ֹͣʱ���õ�
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				//ֹͣ�����򲥷�����
				Log.d(TAG, "isPlaying=" + isPlaying);
				if(isPlaying==true){
					app.mBinder.getService().play();
				}
				else{
					app.mBinder.getService().pause();
				}
				scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(app.mBinder.getService().getPlayer().getCurrentPosition()));
			}
			
			//������ʼʱ���õ�
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
				isPlaying = app.mBinder.getService().isPlaying;
			}
			
			//���黬��ʱ���õ�
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				 // fromUser�ж����û��ı�Ļ����ֵ  
                if(fromUser==true){
                	
                	//����ͣ����
                	app.mBinder.getService().pause();
                	//player.seekTo(progress); 
                	app.mBinder.getService().getPlayer().seekTo(progress);
                }  
			}
		});
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
		scene_title_name = (TextView)findViewById(R.id.scene_title_name);
		scene_title_name.setText(bigSceneName);
		scene_map_layout.setOnClickListener(this);
		scene_list_layout.setOnClickListener(this);
		scene_music_time_now = (TextView)findViewById(R.id.scene_music_time_now);
		scene_music_time_total = (TextView)findViewById(R.id.scene_music_time_total);
		
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
				return new SceneSmallSceneListFragment(cityName,bigSceneName,bigSceneID);
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
		if(event.getRepeatCount() == 0)//û���ظ�
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


	@Override
	public void onMusicSelected() {
		// TODO Auto-generated method stub
		//����seekBar
		initSeekBar();
	}

}
