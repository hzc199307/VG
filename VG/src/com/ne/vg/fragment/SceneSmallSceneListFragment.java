package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.dao.VGDao;
import com.ne.vg.receiver.MusicBroadcastReceiver;
import com.ne.vg.util.MusicNotification;
import com.ne.vg.util.MusicPlayerUtil;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SceneSmallSceneListFragment extends Fragment {

	private final static String TAG= "SceneSmallSceneListFragment";
	/** 通知栏按钮广播 */
	public MusicBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2; 
	/** Notification的ID */
	int notifyId = 100;
	private VGApplication app;
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	
	public MusicNotification mNotification;
	public ImageView animationIV;
	public AnimationDrawable animationDrawable;
	public View currentView;
	public View divider;
	public View divider2;
	private ImageView button;
	private String cityName;
	private String bigSceneName;
	private String smallSceneName;
	private int bigSceneID;
	public VGDao mDao;
	private SceneSmallSceneListAdapter mAdapter;
	private smallBroadcastReceiver sReceiver;
	private Handler mHandler;
	private int mPosition;
	
	
	public SceneSmallSceneListFragment(String cityName,String bigSceneName,int bigSceneID)
	{
		this.cityName = cityName;
		this.bigSceneName = bigSceneName;
		this.bigSceneID = bigSceneID;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		app = (VGApplication)this.getActivity().getApplication();
		intent = new Intent("com.ne.vg.service.MusicService");
		mDao = new VGDao(getActivity());
		
		 //初始化自定义通知栏
		mNotification = new MusicNotification(getActivity().getApplicationContext());
		initSmallReceiver();
	}
	
	
	private void initSmallReceiver() {
		// TODO Auto-generated method stub
		sReceiver = new smallBroadcastReceiver();
		
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getActivity().registerReceiver(sReceiver, intentFilter);
	}
	/**
	 * 
	 * @Title: initButtonReceiver 
	 * @Description: 初始化广播接收，并监控包含ACTION_BUTTON的广播
	 * @param 
	 * @return void 
	 * @throws
	 */
	


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_scene_smallscenelist, container, false);
		smallSceneListView= (ListView)rootView.findViewById(R.id.smallSceneListView);
		mAdapter = new SceneSmallSceneListAdapter(getActivity(),mDao.getSmallScene(bigSceneID));
		smallSceneListView.setAdapter(mAdapter);
		
		InitListener();
		return rootView;
	}

	private void InitListener() {
		// TODO Auto-generated method stub
		smallSceneListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Log.d(TAG, " item " + position +" is clicked!");
				currentView = view;
				
				smallSceneName = mDao.getSmallScene(bigSceneID).get(position).getSmallSceneName();
				startSer(0);
				mPosition = position;
				/**
				 * 这里是重点，由于startSer是在后台开启的，所以需要延时执行后面的代码。
				 */
				
//				//一直到onStart执行完了才能tiaochuxunhuan
//				while(app.mBinder.getService().startSuccess ==false){
//					
//				}

				
				mHandler = new Handler();
				Runnable runnable = new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						app.playSceneID = (int)mAdapter.getItemId(mPosition);
						
						if(animationDrawable!=null){
							animationDrawable.stop();
							button.setImageResource(R.drawable.scene_music_play_icon);
						}
						if(divider2!=null){
							//8代表为gone,0代表可见
							divider.setVisibility(0);
							divider2.setVisibility(8);
						}
						animationIV = (ImageView) currentView.findViewById(R.id.animationIV);
						animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
						animationDrawable = (AnimationDrawable) animationIV
								.getDrawable();
						button = (ImageView)currentView.findViewById(R.id.scene_item_smallscene_button);
						//如果正在播放则播动画
						Log.d(TAG, "isPlaying =" + app.mBinder.getService().isPlaying);
						if(app.mBinder.getService().isPlaying)
						{
							animationDrawable.start();
							button.setImageResource(R.drawable.scene_music_pause_icon);
						}else
						{
							button.setImageResource(R.drawable.scene_music_play_icon);
						}
						divider = (View)currentView.findViewById(R.id.scene_item_smallscene_divider1);
						divider2 = (View)currentView.findViewById(R.id.scene_item_smallscene_divider2);
						//8代表为gone
						divider.setVisibility(8);
						//0代表visible
						divider2.setVisibility(0);
						// TODO 开启服务并更新seekbar
						/**
						 * 这一段只是用来测试，还需要重新写
						 */
						
						//更新界面，及图片改变
//						app.showNotify();
						mNotification.showName(smallSceneName);
						mNotification.showButtonNotify();
						//调用Activity中的函数,用来更新seekBar
						mListener.onMusicSelected();
						
					}      
		        };  
		        mHandler.postDelayed(runnable, 600);// 打开定时器，执行操作 
		    	app.mBinder.getService().startSuccess = false;
			}
			
		
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(sReceiver);
		super.onDestroyView();
		
		Log.v(TAG, "onDestroyView");
	}
	
	/**
	 * 
	 * @ClassName: OnMusicSelectedListener 
	 * @author 潘杉
	 * @Description: 定义一个回调接口，要求宿主activity实现它。 
	 * @date 2014-9-1 上午10:56:36
	 */
	public interface OnMusicSelectedListener{
		public void onMusicSelected();
	}
	
	/**
	 * 这个方法在fragment 被加入到activity中时由系统调用
	 * 如果activity没有实现这个接口，fragment将会抛出ClassCastException异常，
	 * 如果成功了，mListener将会是activity实现OnArticleSelectedListener接口的一个引用，
	 * 所以通过调用OnArticleSelectedListener接口的方法，fragment A可以和activity共享事件。
	 */
	@Override
	public void onAttach(Activity activity){
		
		super.onAttach(activity);
		try{
			mListener = (OnMusicSelectedListener)activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString()+ "must " +
					"implement onMusicSelectedListener");
		}
	}
	
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1是播放，2是停止，3是暂停,0代表不处理
		bundle.putInt("op", op);
		//这个是播放的歌曲
		Log.d(TAG, "bigSceneName="+bigSceneName);
		bundle.putString("musicresource",MusicPlayerUtil.getVoicePath(cityName, bigSceneName, smallSceneName));
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	/**
	 * 
	 * @ClassName: smallBroadcastReceiver 
	 * @author 潘杉
	 * @Description: 这里的广播是为了实现item与notification的同步
	 * @date 2014-9-4 上午10:22:31
	 */
	public class smallBroadcastReceiver extends BroadcastReceiver{
		
		/** 通知栏按钮点击事件对应的ACTION */
		public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.d(TAG, "smallBroadcastReceiver");
			String action = intent.getAction();
			if(action.equals(ACTION_BUTTON)){
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				if(buttonId == BUTTON_PALY_ID){
					
					animationIV = (ImageView) currentView.findViewById(R.id.animationIV);
					animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
					animationDrawable = (AnimationDrawable) animationIV
							.getDrawable();
					button = (ImageView)currentView.findViewById(R.id.scene_item_smallscene_button);
					// 如果正在播放，则设置item
					if(app.mBinder.getService().isPlaying == true){
						animationDrawable.start();
						button.setImageResource(R.drawable.scene_music_pause_icon);
					}
					else{
						animationDrawable.stop();
						button.setImageResource(R.drawable.scene_music_play_icon);
					}
				}
			}	
		}	
	}
}
