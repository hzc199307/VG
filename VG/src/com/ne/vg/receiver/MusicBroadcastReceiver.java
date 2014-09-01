package com.ne.vg.receiver;

import com.ne.vg.fragment.SceneSmallSceneListFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MusicBroadcastReceiver extends BroadcastReceiver{
	private final String TAG = "MusicBroadcastReceiver";

	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/**播放状态的初始化*/
	private boolean isPlaying;
	private SceneSmallSceneListFragment sceneSmallSceneListFragment;
	
	public MusicBroadcastReceiver(SceneSmallSceneListFragment sceneSmallSceneListFragment,boolean b){
		this.isPlaying = b;
		this.sceneSmallSceneListFragment = sceneSmallSceneListFragment;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if(action.equals(ACTION_BUTTON)){
			//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
			int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
			if(buttonId == BUTTON_PALY_ID){
				String play_status = "";
				isPlaying = !isPlaying;
				
				if(isPlaying){
					play_status = "开始播放";
					sceneSmallSceneListFragment.startSer(1);
				}else{
					play_status = "已暂停";
					sceneSmallSceneListFragment.startSer(2);
				}
				//更新界面，及图片改变
				sceneSmallSceneListFragment.showButtonNotify();
				Log.d(TAG , play_status);
				
				
			}
		}
	}
	


}
