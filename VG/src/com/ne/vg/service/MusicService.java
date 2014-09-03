package com.ne.vg.service;

import java.io.IOException;

import com.ne.vg.R;
import com.ne.vg.VGApplication;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service{

	private static final String TAG= "MusicService";
	//音乐播放控件
	private MediaPlayer mediaPlayer;
	private MyBinder mBinder = new MyBinder();
	private String oldresource;
	public boolean isPlaying;

	public class MyBinder extends Binder{
		public MusicService getService(){
			return MusicService.this;
		}
	}

	/**
	 * 通过MyBinder来实现交互
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	/**
	 * 	创建了mediaPlayer
	 */
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		//初始时设置为没有播放
		isPlaying = false;
		Toast.makeText(getApplicationContext(), "show media player", Toast.LENGTH_SHORT).show();

		if(mediaPlayer == null){
			//TODO 这里需要加入音频文件的id
			oldresource = null;
			mediaPlayer = new MediaPlayer();
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mediaPlayer.setLooping(false);
		}
		//意图过滤器   
		IntentFilter filter = new IntentFilter();  

		//播出电话暂停音乐播放   
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");  
		registerReceiver(new PhoneListener(), filter);  

		//创建一个电话服务   
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  

		//监听电话状态，接电话时停止播放 
		/**
		 * 这里出现了bug，若监听则第一次点击play按钮无法播放,已解决。
		 */
		manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);  

	}

	/* 
	 * 监听电话状态 
	 */  
	private final class MyPhoneStateListener extends PhoneStateListener {  
		public void onCallStateChanged(int state, String incomingNumber) {
			switch(state){
			//空闲，没有任何活动。new 的时候会转到这个状态。
			case TelephonyManager.CALL_STATE_IDLE:      

				break;      
				//来电          
			case TelephonyManager.CALL_STATE_RINGING:      
				Toast.makeText(getApplication(), "incoming", Toast.LENGTH_LONG).show();      
				pause();
				break;      
				//摘机状态。
			case TelephonyManager.CALL_STATE_OFFHOOK:      
				Toast.makeText(getApplication(), "in a call", Toast.LENGTH_LONG).show();      
				pause();
				break;
			}
		}  
	}  

	/* 
	 * 收到广播时暂停 
	 */  
	private final class PhoneListener extends BroadcastReceiver {  
		public void onReceive(Context context, Intent intent) {  
			pause();  
		}  
	}  


	@Override
	/**
	 * onDestroy是最后销毁service时调用。
	 */
	public void onDestroy(){
		Log.d(TAG, "onDestroy");
		Toast.makeText(getApplicationContext(), "stop media player", Toast.LENGTH_LONG).show();
		if(mediaPlayer!=null){
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	/**
	 * 
	 */
	@Override
	public void onStart(Intent intent, int startId){
		Log.d(TAG,"onStart");

		if(intent !=null){

			Bundle bundle = intent.getExtras();
			if(bundle!=null){
				//从bundle中取出传过来的音频文件
				String newResource = bundle.getString("musicresource");
				//这个代表是notification所触发的广播调用的service传过来的参数
				if(newResource == null){
					newResource = oldresource;
				}
				Log.d(TAG, newResource+"");
				//如果点击了其他item项则停止现有项
				if(!newResource.equals(oldresource))
				{
					stop();
					//TODO 这里需要加入音频文件的id
					mediaPlayer = new MediaPlayer();
					try {
						mediaPlayer.setDataSource(newResource);
						mediaPlayer.prepare();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//这里setLooping有可能不用设置
					mediaPlayer.setLooping(false);
					play();
					
				}
				else{
					if(mediaPlayer.isPlaying())
					{
						pause();
						
					}
					else{
						play();
						
					}
					
				}
				oldresource = null;
				oldresource = newResource;

				int op = bundle.getInt("op");
				Toast.makeText(getApplicationContext(), "on start ,op :"+op, Toast.LENGTH_SHORT).show();
				//TODO 如果op不为0
				if(op!=0){
					switch(op){
					case 1:
						play();
						
						break;
					case 2:
						stop();
						
						break;
					case 3:
						pause();
						break;


					}
				}
			}

		}
		Log.d(TAG, "isPlaying="+ isPlaying);

	}
	/**
	 * 
	 * @Title: play 
	 * @Description: 如果没有播放，则播放音乐
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void play(){
		if(!mediaPlayer.isPlaying()){
			Toast.makeText(getApplicationContext(), "is not play", Toast.LENGTH_SHORT).show();
			isPlaying = true;
			mediaPlayer.start();
		}
	}

	/**
	 * 
	 * @Title: pause 
	 * @Description: 暂停
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void pause(){
		if(mediaPlayer !=null&&mediaPlayer.isPlaying()){
			mediaPlayer.pause();
			isPlaying = false;
		}
	}

	/**
	 * 
	 * @Title: stop 
	 * @Description: 停止
	 * @param 
	 * @return void 
	 * @throws IOException
	 */
	public void stop(){
		if(mediaPlayer != null){
			isPlaying = false;
			mediaPlayer.pause();
			mediaPlayer.stop();
			// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数  
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public MediaPlayer getPlayer(){

		return mediaPlayer;
	}
}
