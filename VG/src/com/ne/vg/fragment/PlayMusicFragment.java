package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.adapter.PlayMusicAdapter;
import com.ne.vg.adapter.RecommendRouteAdapter;

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
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PlayMusicFragment extends Fragment{
	
	private Intent intent;
	private static final String TAG = "PlayMusicFragment";
	
	private NotificationManager mNotificationManager;
	//ListView控件
	private ListView listview;
	//自定义的适配器
	private PlayMusicAdapter musicAdapter;
	//保存数据的链表
	private List<Map<String, Object>> mData;
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/**播放状态的初始化*/
	private boolean isPlaying = false; 
	
	/** 通知栏按钮广播 */
	public ButtonBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	/** Notification的ID */
	int notifyId = 100;
	
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mNotificationManager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		 //初始化自定义广播
        initButtonReceiver();
	}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState){
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragement_playmusic,
				container, false);
		//Init view
		listview = (ListView) rootView.findViewById(R.id.playmusic_lvSongs);
		InitListener();
		mData = getData();
		musicAdapter = new PlayMusicAdapter(getActivity(), mData);
		listview.setAdapter(musicAdapter);
		return rootView;
	}

	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: 初始化监听器Listener,用来监听List中的Item
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO 这里是写跳转函数，跳转到其他页面
				// 参数是service在注册文件中 的 intent-filter的action name
				intent = new Intent("com.ne.vg.service.MusicService");
				Log.v(TAG, "item is clicked!");
				Toast.makeText(getActivity().getApplicationContext(), "music" + (position+1), Toast.LENGTH_SHORT).show(); 
				if(isPlaying == false){
					//播放音乐
					startSer(1);
				}else{
					//暂停音乐
					startSer(3);
				}
				//更新界面，及图片改变
				showButtonNotify();
				isPlaying = !isPlaying;
			}

		});
	}
	
	
	
	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map;
		for(int i=0;i<10;i++){
			map = new HashMap<String, Object>();
			map.put("img",R.drawable.test_btn_play);
			map.put("musicName", "十二道锋味");
			list.add(map);
		}

		return list;
	}
		
	/**
	 * 
	 * @Title: startSer 
	 * @Description: 将开启服务封装成一个函数
	 * @param @param op
	 * @return void 
	 * @throws
	 */
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1是播放，2是停止，3是暂停
		bundle.putInt("op", op);
		//这个是播放的歌曲
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		
		getActivity().startService(intent);
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
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getActivity().registerReceiver(bReceiver, intentFilter);
	}
	
	/**
	 * 
	 * @ClassName: ButtonBroadcastReceiver 
	 * @author 潘杉
	 * @Description:广播监听按钮点击事件 
	 * @date 2014-8-14 下午7:32:33
	 */
	public class ButtonBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(action.equals(ACTION_BUTTON)){
				//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				if(buttonId == BUTTON_PALY_ID){
					String play_status = "";
					isPlaying = !isPlaying;
					
					if(isPlaying){
						play_status = "开始播放";
						startSer(1);
					}else{
						play_status = "已暂停";
						startSer(2);
					}
					//更新界面，及图片改变
					showButtonNotify();
					Log.d(TAG , play_status);
					Toast.makeText(getActivity(), play_status, Toast.LENGTH_SHORT).show();
					
				}
			}
		}
		
	}
	/**
	 * 内存不够时调用而不是finish的时候调用
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		//应该还需要注销广播
		
		Toast.makeText(getActivity(), "activity is detroyed", Toast.LENGTH_SHORT).show();
		mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
		if(intent!=null){
			getActivity().stopService(intent);
		}
	}




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
		Intent intent = new Intent(getActivity(),PlayMusicActivity.class);
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
