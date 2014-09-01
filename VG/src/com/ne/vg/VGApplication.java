package com.ne.vg;

import java.io.IOException;

import com.ne.vg.DBHelper.DBHelper;
import com.ne.vg.util.SystemUtil;
import com.ne.vg.service.MusicService;

import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class VGApplication extends Application{
	
	private final String TAG = "VGApplication";
	//NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	@Override
	public void onCreate(){
		Log.v(TAG, "onCreate");
		SystemUtil.init(this);//SystemUtil�ĳ�ʼ��
		onDB();
		
		super.onCreate();
	}

	public void onDB() {
		// TODO Auto-generated method stub
		DBHelper mDbHelper = new DBHelper(this);
		try{
			//����false��
			if(mDbHelper.createDataBase())
				Log.v(TAG, "success create table");
				;
		}catch(IOException e){
			e.printStackTrace();
		}
		Cursor mCursor = mDbHelper
				.query("select * from city  ",
						null);
	//	Log.v(TAG, "mCursor: "+mCursor.getCount());
	}
	
	public MusicService.MyBinder mBinder;
	public  ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("TAG", "<-----onServiceDisconnected---->");
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("TAG", "onServiceConnected---->");
			mBinder = (MusicService.MyBinder) service;
		}
	};
	
	
	
	/**
	 * onLowMemory ����̨�����Ѿ���ֹ��Դ���ѷ�ʱ���������������õ�Ӧ�ó���һ�����������������ͷ�һЩ����
	 *	Ҫ����Դ��Ӧ������̨�����Ѿ���ֹ��ǰ̨Ӧ�ó����ڴ滹����ʱ��������ƺ�����������õ�
	 */
	/*@Override
	public void onLowMemory(){
		clearNotify(100);
	}
	
	public void clearNotify(int notifyId){
		mNotificationManager.cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}*/
}
