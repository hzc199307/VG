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
	private List<Activity> mList = new LinkedList<Activity>();   //用于存放每个Activity的List  
	private static VGApplication instance;    //SysApplication实例     
	
	/** 通知栏按钮广播 */
	public MusicBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	//定义一个全局变量来存储当前播放的smallSceneID
	public int playSceneID;
	
	public MusicNotification mNotification;
	//私有构造器，防止外面实例化该对象，
	public VGApplication(){
		
	}
	
	public synchronized static VGApplication getInstance() {   //通过一个方法给外面提供实例  
        if (null == instance) {     
            instance = new VGApplication();     
        }     
        return instance;     
    }
	
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		SystemUtil.init(this);//SystemUtil的初始化
		onDB();
		//注册通知栏响应的广播
		initButtonReceiver();
		initService();
		super.onCreate();
	}
	
	/**
	 * 
	 * @Title: initService 
	 * @Description: 初始服务
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

	public void exit() {    //遍历List，退出每一个Activity     
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
		Log.d(TAG, "on Terminate()");
		unbindService(this.mConnection);
		clearNotify(100);
		super.onTerminate();
	}
	
	
	public void onDB() {
		// TODO Auto-generated method stub
		DBHelper mDbHelper = new DBHelper(this);
		try{
			//这里false了
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
	 * onLowMemory 当后台程序已经终止资源还匮乏时会调用这个方法。好的应用程序一般会在这个方法里面释放一些不必
	 *	要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。似乎这个函数不用到
	 */
	@Override
	public void onLowMemory(){
		Log.d(TAG, "onLowMemory");
		super.onLowMemory();         
	    System.gc();   //告诉系统回收 
		
	}
	
	public void clearNotify(int notifyId){
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
		
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
	/**
	 * 
	 * @Title: showNotify 
	 * @Description: TODO
	 * @param 
	 * @return 显示及更新通知栏 
	 * @throws
	 */
//	public void showNotify(){
//		mNotification = new MusicNotification(getApplicationContext());
//		mNotification.showButtonNotify();
//	}
	
	
}
