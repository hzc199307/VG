package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.receiver.MusicBroadcastReceiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SceneSmallSceneListFragment extends Fragment {

	private final static String TAG= "SceneSmallSceneListFragment";
	
	private NotificationManager mNotificationManager;
	/** 通知栏按钮广播 */
	public MusicBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/**播放状态的初始化*/
	private boolean isPlaying = false; 
	/** Notification的ID */
	int notifyId = 100;
	
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	


	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		intent = new Intent("com.ne.vg.service.MusicService");
		mNotificationManager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		 //初始化自定义广播
        initButtonReceiver();
	}
	
	
	/**
	 * 
	 * @Title: initButtonReceiver 
	 * @Description: 初始化广播接收，并监控包含ACTION_BUTTON的广播
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void initButtonReceiver() {
		// TODO Auto-generated method stub
		bReceiver = new MusicBroadcastReceiver(this,isPlaying);
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getActivity().registerReceiver(bReceiver, intentFilter);
	}


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_scene_smallscenelist, container, false);
		smallSceneListView= (ListView)rootView.findViewById(R.id.smallSceneListView);
		smallSceneListView.setAdapter(new SceneSmallSceneListAdapter(getActivity()));
		InitListener();
		return rootView;
	}

	private void InitListener() {
		// TODO Auto-generated method stub
		smallSceneListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.d(TAG, " item " + position +" is clicked!");
				
				// TODO 开启服务并更新seekbar
				/**
				 * 这一段只是用来测试，还需要重新写
				 */
				startSer(0);
				
				isPlaying = !isPlaying;
				
				
				//更新界面，及图片改变
				showButtonNotify();
				//调用Activity中的函数,用来更新seekBar
				mListener.onMusicSelected();
				
			}
			
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
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
	
	/**
	 * 
	 * @ClassName: ButtonBroadcastReceiver 
	 * @author 潘杉
	 * @Description:广播监听按钮点击事件 
	 * @date 2014-8-14 下午7:32:33
	 */
//	public class ButtonBroadcastReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			
//			// TODO Auto-generated method stub
//			String action = intent.getAction();
//			if(action.equals(ACTION_BUTTON)){
//				//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
//				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
//				if(buttonId == BUTTON_PALY_ID){
//					String play_status = "";
//					isPlaying = !isPlaying;
//					
//					if(isPlaying){
//						play_status = "开始播放";
//						startSer(1);
//					}else{
//						play_status = "已暂停";
//						startSer(2);
//					}
//					//更新界面，及图片改变
//					showButtonNotify();
//					Log.d(TAG , play_status);
//					Toast.makeText(getActivity(), play_status, Toast.LENGTH_SHORT).show();
//					
//				}
//			}
//		}
//		
//	}
	
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1是播放，2是停止，3是暂停,0代表不处理
		bundle.putInt("op", op);
		//这个是播放的歌曲
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	/**
	 * 后面是有关广播的部分
	 */
	
	public void showButtonNotify() {
		// TODO Auto-generated method stub
		//实例化通知栏构造器NotificationCompat
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
		//Notification的自定义布局是RemoteViews
		RemoteViews mRemoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.statusbar);
		
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		//API3.0 以上的时候显示按钮，否则消失
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "谢霆锋");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "十二道锋味");
		//如果版本号低于3.0，那么不显示按钮
		if(android.os.Build.VERSION.SDK_INT<=9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
			
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			if(isPlaying){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_pause2);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_play2);				
			}
		}
		
		//点击的事件处理
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		
		/*播放/暂停 按钮*/
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(getActivity(), 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
	
		//该参数代表着在顶部常驻
		Intent intent = new Intent(getActivity(),SceneActivity.class);
		PendingIntent pendingIntent= PendingIntent.getActivity(getActivity(), 1, intent, Notification.FLAG_ONGOING_EVENT);
		
		mBuilder.setContent(mRemoteViews)
				//设置通知栏点击意图
				.setContentIntent(pendingIntent)
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("正在播放")
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(true)
				.setSmallIcon(R.drawable.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;  
		//第一个参数为自定义的通知唯一标识，发送通知请求
        mNotificationManager.notify(notifyId, notify);
	
	}
}
