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
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	private VGDao mVgDao;
	VGApplication app;
	/** Notification��ID */
	int notifyId = 100;
	public MusicNotification(Context context){
		mNotificationManager =(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		this.context = context;
		app = (VGApplication)context;
		mVgDao = new VGDao(context.getApplicationContext());
	}
	public void showButtonNotify() {
		
		// TODO Auto-generated method stub
		//ʵ����֪ͨ��������NotificationCompat
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		//Notification���Զ��岼����RemoteViews
		mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.statusbar);
		
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		//API3.0 ���ϵ�ʱ����ʾ��ť��������ʧ
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, musicName);
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, author);
		//����汾�ŵ���3.0����ô����ʾ��ť
		if(android.os.Build.VERSION.SDK_INT<=9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
			
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//������ڲ��ţ�������Ϊ����״̬����������Ϊ��ͣ״̬��
			if(!app.mBinder.getService().isPlaying){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_play2);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_pause2);				
			}
		}
		
		//������¼�����
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		
		/*����/��ͣ ��ť*/
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(context, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
	
		//�ò����������ڶ�����פ
		Intent intent = new Intent(context,SceneActivity.class);
		//TODO ����music_scenename��smallScene��ͬ�ʿ�����һ�·���
		//�Ժ󴫹����������ͬ����������Ҫ�Ľ���
		intent.putExtra("bigSceneID", mVgDao.getBigSceneID(musicName));
		Log.d("musicNotification", "musicName=" + musicName);
		Log.d("musicNotification", "bigSceneID="+mVgDao.getBigSceneID(musicName));
		
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, intent, Notification.FLAG_ONGOING_EVENT);
		
		mBuilder.setContent(mRemoteViews)
				//����֪ͨ�������ͼ
				//.setContentIntent(pendingIntent)
				.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setTicker("���ڲ���")
				.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
				.setOngoing(true)
				.setSmallIcon(R.drawable.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;  
		//��һ������Ϊ�Զ����֪ͨΨһ��ʶ������֪ͨ����
        mNotificationManager.notify(notifyId, notify);
	
	}
	public void showName(String musicName){
		this.musicName = musicName;
		this.author = "���С����";
	}
}
