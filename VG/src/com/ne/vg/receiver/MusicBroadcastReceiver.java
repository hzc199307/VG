package com.ne.vg.receiver;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.fragment.SceneSmallSceneListFragment;
import com.ne.vg.util.MusicNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MusicBroadcastReceiver extends BroadcastReceiver{
	private final String TAG = "MusicBroadcastReceiver";

	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	private Intent intent = new Intent("com.ne.vg.service.MusicService");
	
	private VGApplication app;
	
	public MusicNotification mNotification ;
	
	public MusicBroadcastReceiver(VGApplication app)
	{
		this.app = app;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		 mNotification= new MusicNotification(app.getApplicationContext());
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if(action.equals(ACTION_BUTTON)){
			//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
			int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
			if(buttonId == BUTTON_PALY_ID){
				String play_status = "";
				app.isPlaying = !app.isPlaying;
				
				if(app.isPlaying){
					play_status = "开始播放";
					startSer(1);
					
				}else{
					play_status = "已暂停";
					startSer(2);
				}
				//更新界面，及图片改变
				//app.showNotify();
				mNotification.showButtonNotify();
				
				Log.d(TAG , play_status);
				
				
			}
		}
	}
	public void startSer(int op){
		
		Bundle bundle = new Bundle();
		//op:1是播放，2是停止，3是暂停,0代表不处理
		bundle.putInt("op", op);
		//这个是播放的歌曲
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		app.startService(intent);
	}
	


}
