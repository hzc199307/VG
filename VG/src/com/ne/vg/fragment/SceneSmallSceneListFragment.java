package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.SmallScene;
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
import android.os.AsyncTask;
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
	private int smallSceneId;
	private int bigSceneID;
	public VGDao mDao;
	private CommonAdapter<SmallScene> mAdapter;
	private smallBroadcastReceiver sReceiver;
	private Handler mHandler;
	private Runnable runnable;
	private int mPosition;
	private int oldValue;
	private int newValue;
	
	
	public SceneSmallSceneListFragment(String cityName,String bigSceneName,int bigSceneID)
	{
		this.cityName = cityName;
		this.bigSceneName = bigSceneName;
		this.bigSceneID = bigSceneID;
		Log.d(TAG, "bigSceneName=" + bigSceneName);
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		app = (VGApplication)this.getActivity().getApplication();
		//判断该fragment存在
		app.setSmallSceneFragmentExisted(true);
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
		//mAdapter = new SceneSmallSceneListAdapter(getActivity(),mDao.getSmallScene(bigSceneID));
		mAdapter = new CommonAdapter<SmallScene>(getActivity(), mDao.getSmallSceneList(bigSceneID), R.layout.scene_item_small_scene) {

			@Override
			public void convert(ViewHolder helper, SmallScene item){
				
				// TODO Auto-generated method stub
				helper.setText(R.id.scene_item_smallscene_name, item.getSmallSceneName());
				Log.d(TAG, "playSceneID="+app.playSceneID);
				Log.d(TAG, "页面刷新 ");
				//如果正在播放
				if(app.playSceneID == item.getSmallSceneID() && app.mBinder.getService().isPlaying==true){
					
					helper.setImageResourceByInt(R.id.scene_item_smallscene_button, R.drawable.scene_music_pause_icon);
					//播放动画
					helper.setAnimation(R.id.animationIV,R.drawable.scene_music_isplaying_animation,1);
					helper.setView(R.id.scene_item_smallscene_divider1,8);
					helper.setView(R.id.scene_item_smallscene_divider2,0);
					
				}else{
					helper.setImageResourceByInt(R.id.scene_item_smallscene_button, R.drawable.scene_music_play_icon);
					//暂停动画
					helper.setAnimation(R.id.animationIV,R.drawable.scene_music_isplaying_animation,0);
					helper.setView(R.id.scene_item_smallscene_divider1,0);
					helper.setView(R.id.scene_item_smallscene_divider2,8);
				}	
			}	
		};
		
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
				
				smallSceneName = mDao.getSmallSceneList(bigSceneID).get(position).getSmallSceneName();
				smallSceneId = mDao.getSmallSceneList(bigSceneID).get(position).getSmallSceneID();
				//设置playSceneID的值为当前选中的值。
				app.playSceneID = smallSceneId;
				Log.d(TAG, "play scene id is :" + app.playSceneID);
				Log.d(TAG, "smallSceneName is :" + smallSceneName);
//				startSer(0);
//				//更新界面
//				mAdapter.notifyDataSetChanged();
				ServiceTask sTask = new ServiceTask();
				mHandler = new Handler();
				
				
				
				
				/**
				 * 这里是重点，由于startSer是在后台开启的，所以需要延时执行后面的代码。
				 */
				
				runnable = new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						
						// TODO 开启服务并更新seekbar
						/**
						 * 这一段只是用来测试，还需要重新写
						 */
						if(app.mBinder.getService().isPlayStatusChanged()==true)
						{
							//更新界面
							mAdapter.notifyDataSetChanged();
							mNotification.showName(mDao.getSmallSceneName(app.playSceneID));
							Log.d(TAG, "Notify1 is start");
							Log.d(TAG, "app.playID="+ app.playSceneID);
							Log.d(TAG, "smallSceneName =" + smallSceneName + "smallSceneId ="+smallSceneId);
							mNotification.showButtonNotify();
						}
							
						//调用Activity中的函数,用来更新seekBar
						mListener.onMusicSelected();
						//TODO 最后来更新列表
						if(mHandler!=null)
							mHandler.postDelayed(this, 600);// 打开定时器，执行操作 
					}      
		        };  
		        //TODO 这里的延迟跟用户体验密切相关
		       
		        sTask.execute(0);
		    	app.mBinder.getService().startSuccess = false;
		    	
			}
			
		
		});
	}
	
	class ServiceTask extends AsyncTask<Integer, Integer, String>{
		/**
		 * 该方法将在执行实际的后台操作前被UI 线程调用。
		 */
		@Override  
	    protected void onPreExecute() {  
	        //第一个执行方法  
	        super.onPreExecute();  
	    } 
		/**
		 * 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。
		 * 这里将主要负责执行那些很耗时的后台处理工作。
		 */
		@Override
		protected String doInBackground(Integer... params) {
			// TODO 先开启服务
			startSer(0);
			return null;
		}
		/**
		 * 在publishProgress方法被调用后，
		 * UI 线程将调用这个方法从而在界面上展示任务的进展情况，例如通过一个进度条进行展示。
		 */
		@Override
		protected void onProgressUpdate(Integer... params){
			super.onProgressUpdate(params);
		}
		
		@Override  
        protected void onPostExecute(String result) {  
            //doInBackground返回时触发，换句话说，就是doInBackground执行完后触发  
            //这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕" 
			//服务完成后，刷新页面
			mAdapter.notifyDataSetChanged(); 
//			mNotification.showName(smallSceneName);
//			
//			mNotification.showButtonNotify();
//			Log.d(TAG, "Notify2 is start");
			mHandler.post(runnable);// 打开定时器，执行操作 
            super.onPostExecute(result);  
        }  
		
		
	}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		Log.v(TAG, "onCreateView");
//		View rootView = inflater.inflate(R.layout.fragment_scene_smallscenelist, container, false);
//		smallSceneListView= (ListView)rootView.findViewById(R.id.smallSceneListView);
//		mAdapter = new SceneSmallSceneListAdapter(getActivity(),mDao.getSmallScene(bigSceneID));
//		smallSceneListView.setAdapter(mAdapter);
//		
//		InitListener();
//		return rootView;
//	}
//	private void InitListener() {
//		// TODO Auto-generated method stub
//		smallSceneListView.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				Log.d(TAG, " item " + position +" is clicked!");
//				currentView = view;
//				
//				smallSceneName = mDao.getSmallScene(bigSceneID).get(position).getSmallSceneName();
//				startSer(0);
//				mPosition = position;
//				/**
//				 * 这里是重点，由于startSer是在后台开启的，所以需要延时执行后面的代码。
//				 */
//				
////				//一直到onStart执行完了才能tiaochuxunhuan
////				while(app.mBinder.getService().startSuccess ==false){
////					
////				}
//
//				
//				mHandler = new Handler();
//				Runnable runnable = new Runnable(){
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						app.playSceneID = (int)mAdapter.getItemId(mPosition);
//						
//						if(animationDrawable!=null){
//							animationDrawable.stop();
//							button.setImageResource(R.drawable.scene_music_play_icon);
//						}
//						if(divider2!=null){
//							//8代表为gone,0代表可见
//							divider.setVisibility(0);
//							divider2.setVisibility(8);
//						}
//						animationIV = (ImageView) currentView.findViewById(R.id.animationIV);
//						animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
//						animationDrawable = (AnimationDrawable) animationIV
//								.getDrawable();
//						button = (ImageView)currentView.findViewById(R.id.scene_item_smallscene_button);
//						//如果正在播放则播动画
//						Log.d(TAG, "isPlaying =" + app.mBinder.getService().isPlaying);
//						if(app.mBinder.getService().isPlaying)
//						{
//							animationDrawable.start();
//							button.setImageResource(R.drawable.scene_music_pause_icon);
//						}else
//						{
//							button.setImageResource(R.drawable.scene_music_play_icon);
//						}
//						divider = (View)currentView.findViewById(R.id.scene_item_smallscene_divider1);
//						divider2 = (View)currentView.findViewById(R.id.scene_item_smallscene_divider2);
//						//8代表为gone
//						divider.setVisibility(8);
//						//0代表visible
//						divider2.setVisibility(0);
//						// TODO 开启服务并更新seekbar
//						/**
//						 * 这一段只是用来测试，还需要重新写
//						 */
//						
//						//更新界面，及图片改变
////						app.showNotify();
//						mNotification.showName(smallSceneName);
//						mNotification.showButtonNotify();
//						//调用Activity中的函数,用来更新seekBar
//						mListener.onMusicSelected();
//						
//					}      
//		        };  
//		        mHandler.postDelayed(runnable, 600);// 打开定时器，执行操作 
//		    	app.mBinder.getService().startSuccess = false;
//			}
//			
//		
//		});
//	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(sReceiver);
		Log.d(TAG,"runnable is null?" + (runnable==null));
		//停止计时器
		if(runnable!=null){
			mHandler.removeCallbacks(runnable);
		}
		mHandler= null;
		
		//判断该fragment不存在
		app.setSmallSceneFragmentExisted(false);
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
