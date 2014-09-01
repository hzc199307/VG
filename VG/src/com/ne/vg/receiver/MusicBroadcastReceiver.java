package com.ne.vg.receiver;

import com.ne.vg.fragment.SceneSmallSceneListFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MusicBroadcastReceiver extends BroadcastReceiver{
	private final String TAG = "MusicBroadcastReceiver";

	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	/**����״̬�ĳ�ʼ��*/
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
			//ͨ�����ݹ�����ID�жϰ�ť������Ի���ͨ��getResultCode()�����Ӧ����¼�
			int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
			if(buttonId == BUTTON_PALY_ID){
				String play_status = "";
				isPlaying = !isPlaying;
				
				if(isPlaying){
					play_status = "��ʼ����";
					sceneSmallSceneListFragment.startSer(1);
				}else{
					play_status = "����ͣ";
					sceneSmallSceneListFragment.startSer(2);
				}
				//���½��棬��ͼƬ�ı�
				sceneSmallSceneListFragment.showButtonNotify();
				Log.d(TAG , play_status);
				
				
			}
		}
	}
	


}
