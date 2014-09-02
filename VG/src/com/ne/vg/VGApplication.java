package com.ne.vg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.ne.vg.DBHelper.DBHelper;
import com.ne.vg.util.SystemUtil;

import com.ne.vg.service.MusicService;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class VGApplication extends Application{
	
	private final String TAG = "VGApplication";
	private List<Activity> mList = new LinkedList<Activity>();   //用于存放每个Activity的List  
	private static VGApplication instance;    //SysApplication实例     
	
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
		Log.v(TAG, "onCreate");
		SystemUtil.init(this);//SystemUtil的初始化
		onDB();
		
		super.onCreate();
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
        	clearNotify(100);
            System.exit(0);     
        }     
    }
	@Override
	public void onTerminate(){
		Log.d(TAG, "is on terminate!");
		
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
	 * onLowMemory 当后台程序已经终止资源还匮乏时会调用这个方法。好的应用程序一般会在这个方法里面释放一些不必
	 *	要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。似乎这个函数不用到
	 */
	@Override
	public void onLowMemory(){
		super.onLowMemory();         
	    System.gc();   //告诉系统回收 
		
	}
	
	public void clearNotify(int notifyId){
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
		
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
}
