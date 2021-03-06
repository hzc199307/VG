package com.ne.vg.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.dao.VGDao;

public class MusicNotification {
	private NotificationManager mNotificationManager;
	private Context context;
	private RemoteViews mRemoteViews;
	private static String musicName;
	private static String author;
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	private VGDao mVgDao;
	VGApplication app;
	/** Notification的ID */
	public static int notifyId = 100;
	public MusicNotification(Context context){
		mNotificationManager =(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		this.context = context;
		app = (VGApplication)context;
		mVgDao = new VGDao(context.getApplicationContext());
	}
	
	public void showButtonNotify() {
		
		// TODO Auto-generated method stub
		//实例化通知栏构造器NotificationCompat
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		//Notification的自定义布局是RemoteViews
		mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.statusbar);
		
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		//API3.0 以上的时候显示按钮，否则消失
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, musicName);
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, author);
		//如果版本号低于3.0，那么不显示按钮
		if(android.os.Build.VERSION.SDK_INT<=9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
			
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//如果正在播放，则设置为播放状态，否则设置为暂停状态。
			if(!app.mBinder.getService().isPlaying){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_play2);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_pause2);				
			}
		}
		
		//点击的事件处理
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		
		/*播放/暂停 按钮*/
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(context, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
	
		//该参数代表着在顶部常驻
		Intent intent = new Intent(context,SceneActivity.class);
		//TODO 由于music_scenename和smallScene相同故可以用一下方法
		//以后传过来的如果不同，则这里需要改进。
		intent.putExtra("bigSceneID", mVgDao.getBigSceneID(musicName));
		Log.d("musicNotification", "musicName=" + musicName);
		Log.d("musicNotification", "bigSceneID="+mVgDao.getBigSceneID(musicName));
		
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, intent, Notification.FLAG_ONGOING_EVENT);
		
		mBuilder.setContent(mRemoteViews)
				//设置通知栏点击意图
				//.setContentIntent(pendingIntent)
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
	
	public void showName(String musicName){
		this.musicName = musicName;
		this.author = "天才小土豆";
	}
}
