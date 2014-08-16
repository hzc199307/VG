package com.ne.vg.service;

import java.io.IOException;

import com.ne.vg.R;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
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
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 	创建了mediaPlayer
	 */
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		Toast.makeText(getApplicationContext(), "show media player", Toast.LENGTH_SHORT).show();
		
		if(mediaPlayer == null){
			//TODO 这里需要加入音频文件的id
			mediaPlayer = MediaPlayer.create(this, R.raw.fengwei);
			mediaPlayer.setLooping(false);
	}
		//意图过滤器   
        IntentFilter filter = new IntentFilter();  
          
        //播出电话暂停音乐播放   
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");  
        registerReceiver(new PhoneListener(), filter);  
  
        //创建一个电话服务   
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
        Log.d(TAG, "listen occurs problems!");
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
                Toast.makeText(getApplication(), "call not answer", Toast.LENGTH_LONG).show();      
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
		Toast.makeText(getApplicationContext(), "stop media player", Toast.LENGTH_SHORT).show();
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
				int musicresource = bundle.getInt("musicresource");
				//如果现在存在mediaPlayer则停止。
				if(mediaPlayer != null){
					pause();
				}
				//TODO 这里需要加入音频文件的id
				mediaPlayer = MediaPlayer.create(this, musicresource);
				//这里setLooping有可能不用设置
				mediaPlayer.setLooping(false);
				
				
				int op = bundle.getInt("op");
				Toast.makeText(getApplicationContext(), "on start ,op :"+op, Toast.LENGTH_SHORT).show();
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
			mediaPlayer.start();
		}
	}
	
	/**
	 * 
	 * @Title: pause 
	 * @Description: 若音乐控件存在且在播放，则暂停。
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void pause(){
		if(mediaPlayer !=null&&mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}
	
	/**
	 * 
	 * @Title: stop 
	 * @Description: 暂停音乐
	 * @param 
	 * @return void 
	 * @throws IOException
	 */
	public void stop(){
		if(mediaPlayer != null){
			mediaPlayer.stop();
			try{
				// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数  
				mediaPlayer.prepare();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}


}
