package com.ne.vg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.ne.vg.DBHelper.DBHelper;
import com.ne.vg.util.MusicNotification;
import com.ne.vg.util.SystemUtil;

import com.ne.vg.receiver.MusicBroadcastReceiver;
import com.ne.vg.service.MusicService;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class VGApplication extends Application{
	
	private final String TAG = "VGApplication";
	private List<Activity> mList = new LinkedList<Activity>();   //���ڴ��ÿ��Activity��List  
	private static VGApplication instance;    //SysApplicationʵ��     
	
	public boolean isPlaying=false;
	/** ֪ͨ����ť�㲥 */
	public MusicBroadcastReceiver bReceiver;
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	
	public MusicNotification mNotification;
	//˽�й���������ֹ����ʵ�����ö���
	public VGApplication(){
		
	}
	
	public synchronized static VGApplication getInstance() {   //ͨ��һ�������������ṩʵ��  
        if (null == instance) {     
            instance = new VGApplication();     
        }     
        return instance;     
    }
	
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		SystemUtil.init(this);//SystemUtil�ĳ�ʼ��
		onDB();
		//ע��֪ͨ����Ӧ�Ĺ㲥
		initButtonReceiver();
		initService();
		super.onCreate();
	}
	
	/**
	 * 
	 * @Title: initService 
	 * @Description: ��ʼ����
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void initService()
	{
		Intent serviceIntent = new Intent(this,MusicService.class);
		bindService(serviceIntent, this.mConnection, BIND_AUTO_CREATE);
	}

	private void initButtonReceiver() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		bReceiver = new MusicBroadcastReceiver(this);
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		registerReceiver(bReceiver, intentFilter);
	}

	public void exit() {    //����List���˳�ÿһ��Activity     
        try {     
            for (Activity activity : mList) {     
                if (activity != null)     
                    activity.finish();     
            }     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {
        	unbindService(this.mConnection);
        	clearNotify(100);
            System.exit(0);     
        }     
    }
	@Override
	public void onTerminate(){
		Toast.makeText(getApplicationContext(), "onTerminate", Toast.LENGTH_LONG).show();
		unbindService(this.mConnection);
		clearNotify(100);
		super.onTerminate();
	}
	
	
	public void onDB() {
		// TODO Auto-generated method stub
		DBHelper mDbHelper = new DBHelper(this);
		try{
			//����false��
			if(mDbHelper.createDataBase())
				Log.v(TAG, "success create table");
				;
		}catch(IOException e)
		{
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
	@Override
	public void onLowMemory(){
		Toast.makeText(getApplicationContext(), "onLowMemory", Toast.LENGTH_LONG).show();
		super.onLowMemory();         
	    System.gc();   //����ϵͳ���� 
		
	}
	
	public void clearNotify(int notifyId){
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
		
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
	/**
	 * 
	 * @Title: showNotify 
	 * @Description: TODO
	 * @param 
	 * @return ��ʾ������֪ͨ�� 
	 * @throws
	 */
//	public void showNotify(){
//		mNotification = new MusicNotification(getApplicationContext());
//		mNotification.showButtonNotify();
//	}
	
	
}
