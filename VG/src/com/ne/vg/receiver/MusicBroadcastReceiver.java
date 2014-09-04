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

	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	private Intent mIntent = new Intent("com.ne.vg.service.MusicService");
	
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
			//ͨ�����ݹ�����ID�жϰ�ť������Ի���ͨ��getResultCode()�����Ӧ����¼�
			int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
			if(buttonId == BUTTON_PALY_ID){
				String play_status = "";
				app.mBinder.getService().isPlaying = !app.mBinder.getService().isPlaying;
				
				if(app.mBinder.getService().isPlaying){
					play_status = "��ʼ����";
					startSer(1);
					
				}else{
					play_status = "����ͣ";
					startSer(3);
				}
				//���½��棬��ͼƬ�ı�
				//app.showNotify();
				mNotification.showButtonNotify();
				
				Log.d(TAG , play_status);
				
				
			}
		}
	}
	public void startSer(int op){
		
		Bundle bundle = new Bundle();
		//op:1�ǲ��ţ�2��ֹͣ��3����ͣ,0��������
		bundle.putInt("op", op);
		//����ǲ��ŵĸ���
		bundle.putString("musicresource",null);
		mIntent.putExtras(bundle);
		app.startService(mIntent);
	}
	


}
