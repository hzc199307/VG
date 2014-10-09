package com.ne.vg.activity;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.bean.SmallScene;
import com.ne.vg.dao.VGDao;
import com.ne.vg.fragment.BigSceneListFragment;
import com.ne.vg.fragment.SceneSmallSceneListFragment;
import com.ne.vg.fragment.SceneSmallSceneListFragment.OnMusicSelectedListener;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.gmap.GMapWebView.MarkerItem;
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
import android.graphics.drawable.Drawable;
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
	private MyViewPager viewPager;//页卡内容
	private SceneAdapter sceneAdapter;
	private View view;// 动画图片
	private View scene_map_layout,scene_list_layout;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private SeekBar mSeekBar;// 初始化seekBar
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
	//从service获取的播放状态。
	private boolean isPlaying;
	//当拖动seekbar时用来保存播放状态。
	private boolean playing;
	//当seekbar拖动时的标志位。
	
	private boolean isSeekbarChanged = false;
	
	private List<SmallScene> listSmallScenes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//初始化VGApplication
		// TODO Auto-generated method stub
		
		app = (VGApplication)this.getApplication();
		mVgDao = new VGDao(this);
		mIntent = this.getIntent();
		
		bigSceneID = mIntent.getExtras().getInt("bigSceneID");
		bigSceneName = mVgDao.getBigScene(bigSceneID).getBigSceneName();
		cityName = mVgDao.getCityName(mVgDao.getBigScene(bigSceneID).getCityID());
		listSmallScenes = mVgDao.getSmallSceneList(bigSceneID);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene);
		initTab();
		initTabAnim();
		initViewPager();
		initSeekBar();
		//启动handler
		handler.post(updateThread);
	}
	
	/**
	 * 该线程用来实时更新seekbar
	 */
	Handler handler = new Handler();
	Runnable updateThread = new Runnable()
	{
		public void run() 
		{
			mSeekBar = (SeekBar)findViewById(R.id.scene_music_seekbar);
	      //获得歌曲现在播放位置并设置成播放进度条的值
			mSeekBar.setProgress(app.mBinder.getService().getPlayer().getCurrentPosition());
			scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(app.mBinder.getService().getPlayer().getCurrentPosition()));
	      //每次延迟100毫秒再启动线程
			handler.postDelayed(updateThread, 100);
	    }
	};
	
	/**
	 * 
	 * @Title: initSeekBar 
	 * @Description: 初始化seekBar
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void initSeekBar() {
		MediaPlayer player = app.mBinder.getService().getPlayer();
		
		mSeekBar = (SeekBar)findViewById(R.id.scene_music_seekbar);
		//获得歌曲的长度并设置成播放进度条的最大值  
		if(player!=null){
			//设置seekbar与播放状态同步
			isPlaying = app.mBinder.getService().isPlaying;
			//不是处于seekBar拖动状态，则更新seekbar图片
			if(isSeekbarChanged == false){
				if(isPlaying == true)
				{
					Drawable mplay = getResources().getDrawable(R.drawable.scene_music_playing_icon);
					mSeekBar.setThumb(mplay);
				}else{
					Drawable mpause = getResources().getDrawable(R.drawable.scene_music_pausing_icon);
					mSeekBar.setThumb(mpause);
				}
			}
			
			scene_music_time_total.setText(MusicPlayerUtil.milliSecondsToTimer(player.getDuration()));
			scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(player.getCurrentPosition()));
			
			mSeekBar.setMax(player.getDuration());
			
			
		}
			
		else{
			mSeekBar.setMax(0);
			Log.d(TAG, "player is null!");
		}
		//mSeekBar.setMax(player.getDuration());  
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			int oldProgress;
			int newProgress;
			//滑动停止时调用的
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				isSeekbarChanged = false;
				//停止滑动则播放音乐
				Log.d(TAG, "onStop playing=" + playing);
				newProgress = arg0.getProgress();
				Log.d(TAG,"newProgress = " + newProgress);
				
				if((Math.abs(newProgress-oldProgress)*1.0 / arg0.getMax()) < 0.1){
					app.mBinder.getService().isPlaying = (playing==true?false:true);
					
				}else{
					app.mBinder.getService().isPlaying = playing;
				}
				Log.d(TAG,"FINAL playing = " + app.mBinder.getService().isPlaying);
				if(app.mBinder.getService().isPlaying==true){
					app.mBinder.getService().play();
				}
				else{
					app.mBinder.getService().pause();
				}
				scene_music_time_now.setText(MusicPlayerUtil.milliSecondsToTimer(app.mBinder.getService().getPlayer().getCurrentPosition()));
			}
			
			//滑动开始时调用的
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				isSeekbarChanged = true;
				playing = app.mBinder.getService().isPlaying;
				Log.d(TAG,"ONstart playing is " + playing);
				oldProgress = arg0.getProgress();
				Log.d(TAG,"oldProgress = " + oldProgress);
			}
			
			//滑块滑动时调用的
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				 // fromUser判断是用户改变的滑块的值  
                if(fromUser==true){
                	
                	//先暂停音乐
                	app.mBinder.getService().pause();
                	//player.seekTo(progress); 
                	app.mBinder.getService().getPlayer().seekTo(progress);
                }  
                Log.d(TAG,"ONchanged playing is " + playing);
			}
		});
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
//		viewPager.setCurrentItem(MAP_STATUS);  
//		myOnPageChangeListener.onPageSelected(MAP_STATUS);
		viewPager.setCurrentItem(LIST_STATUS);  
		myOnPageChangeListener.onPageSelected(LIST_STATUS);
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
				gMapFragment.setSmallScenes(listSmallScenes);
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


	@Override
	public void onMusicSelected() {
		// TODO Auto-generated method stub
		//更新seekBar
		initSeekBar();
	}

}
