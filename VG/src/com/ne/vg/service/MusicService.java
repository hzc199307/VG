package com.ne.vg.service;

import java.io.IOException;

import com.ne.vg.R;


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
	//���ֲ��ſؼ�
	private MediaPlayer mediaPlayer;
	private MyBinder mBinder = new MyBinder();
	private int oldresource;
	
	public class MyBinder extends Binder{
		public MusicService getService(){
			return MusicService.this;
		}
	}

	/**
	 * ͨ��MyBinder��ʵ�ֽ���
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	/**
	 * 	������mediaPlayer
	 */
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		Toast.makeText(getApplicationContext(), "show media player", Toast.LENGTH_SHORT).show();
		
		if(mediaPlayer == null){
			//TODO ������Ҫ������Ƶ�ļ���id
			oldresource = R.raw.fengwei;
			mediaPlayer = MediaPlayer.create(this, oldresource);
			mediaPlayer.setLooping(false);
	}
		//��ͼ������   
        IntentFilter filter = new IntentFilter();  
          
        //�����绰��ͣ���ֲ���   
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");  
        registerReceiver(new PhoneListener(), filter);  
  
        //����һ���绰����   
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
        
        //�����绰״̬���ӵ绰ʱֹͣ���� 
        /**
         * ���������bug�����������һ�ε��play��ť�޷�����,�ѽ����
         */
        manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);  
		
	}
	
	/* 
     * �����绰״̬ 
     */  
    private final class MyPhoneStateListener extends PhoneStateListener {  
        public void onCallStateChanged(int state, String incomingNumber) {
        	switch(state){
        	//���У�û���κλ��new ��ʱ���ת�����״̬��
        	case TelephonyManager.CALL_STATE_IDLE:      
                Toast.makeText(getApplication(), "call not answer", Toast.LENGTH_LONG).show();      
                break;      
            //����          
            case TelephonyManager.CALL_STATE_RINGING:      
                Toast.makeText(getApplication(), "incoming", Toast.LENGTH_LONG).show();      
                pause();
                break;      
            //ժ��״̬��
            case TelephonyManager.CALL_STATE_OFFHOOK:      
                Toast.makeText(getApplication(), "in a call", Toast.LENGTH_LONG).show();      
                pause();
                break;
        	}
        }  
    }  

    /* 
     * �յ��㲥ʱ��ͣ 
     */  
    private final class PhoneListener extends BroadcastReceiver {  
        public void onReceive(Context context, Intent intent) {  
            pause();  
        }  
    }  
    
    
	@Override
	/**
	 * onDestroy���������serviceʱ���á�
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
				//��bundle��ȡ������������Ƶ�ļ�
				int newResource = bundle.getInt("musicresource");
				//������������item����ֹͣ������
				if(newResource != oldresource)
				{
					pause();
					//TODO ������Ҫ������Ƶ�ļ���id
					mediaPlayer = MediaPlayer.create(this, newResource);
					//����setLooping�п��ܲ�������
					mediaPlayer.setLooping(false);
					play();
				}
				else{
					if(mediaPlayer.isPlaying())
						stop();
					else{
						play();
					}
				}
				oldresource = newResource;
				
				
				
				int op = bundle.getInt("op");
				Toast.makeText(getApplicationContext(), "on start ,op :"+op, Toast.LENGTH_SHORT).show();
				//TODO ���op��Ϊ0
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
		
	}
	/**
	 * 
	 * @Title: play 
	 * @Description: ���û�в��ţ��򲥷�����
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
	 * @Description: �����ֿؼ��������ڲ��ţ�����ͣ��
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
	 * @Description: ��ͣ����
	 * @param 
	 * @return void 
	 * @throws IOException
	 */
	public void stop(){
		if(mediaPlayer != null){
			mediaPlayer.stop();
			try{
				// �ڵ���stop�������Ҫ�ٴ�ͨ��start���в���,��Ҫ֮ǰ����prepare����  
				mediaPlayer.prepare();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public MediaPlayer getPlayer(){
	
		return mediaPlayer;
	}
	
	

}
