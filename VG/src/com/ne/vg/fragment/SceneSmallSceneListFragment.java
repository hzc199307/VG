package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.receiver.MusicBroadcastReceiver;
import com.ne.vg.util.MusicNotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SceneSmallSceneListFragment extends Fragment {

	private final static String TAG= "SceneSmallSceneListFragment";
	/** ֪ͨ����ť�㲥 */
	public MusicBroadcastReceiver bReceiver;
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	/**����״̬�ĳ�ʼ��*/
	public boolean isPlaying; 
	/** Notification��ID */
	int notifyId = 100;
	private VGApplication app;
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	
	public MusicNotification mNotification;
	


	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		app = (VGApplication)this.getActivity().getApplication();
		intent = new Intent("com.ne.vg.service.MusicService");
		isPlaying = app.isPlaying;
		
		 //��ʼ���Զ���֪ͨ��
		mNotification = new MusicNotification(getActivity().getApplicationContext());
        
	}
	
	
	/**
	 * 
	 * @Title: initButtonReceiver 
	 * @Description: ��ʼ���㲥���գ�����ذ���ACTION_BUTTON�Ĺ㲥
	 * @param 
	 * @return void 
	 * @throws
	 */
	


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_scene_smallscenelist, container, false);
		smallSceneListView= (ListView)rootView.findViewById(R.id.smallSceneListView);
		smallSceneListView.setAdapter(new SceneSmallSceneListAdapter(getActivity()));
		InitListener();
		return rootView;
	}

	private void InitListener() {
		// TODO Auto-generated method stub
		smallSceneListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.d(TAG, " item " + position +" is clicked!");
				
				// TODO �������񲢸���seekbar
				/**
				 * ��һ��ֻ���������ԣ�����Ҫ����д
				 */
				startSer(0);
				
				isPlaying = !isPlaying;
				
				
				//���½��棬��ͼƬ�ı�
//				app.showNotify();
				
				mNotification.showButtonNotify();
				//����Activity�еĺ���,��������seekBar
				mListener.onMusicSelected();
				
			}
			
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
	
	/**
	 * 
	 * @ClassName: OnMusicSelectedListener 
	 * @author ��ɼ
	 * @Description: ����һ���ص��ӿڣ�Ҫ������activityʵ������ 
	 * @date 2014-9-1 ����10:56:36
	 */
	public interface OnMusicSelectedListener{
		public void onMusicSelected();
	}
	
	/**
	 * ���������fragment �����뵽activity��ʱ��ϵͳ����
	 * ���activityû��ʵ������ӿڣ�fragment�����׳�ClassCastException�쳣��
	 * ����ɹ��ˣ�mListener������activityʵ��OnArticleSelectedListener�ӿڵ�һ�����ã�
	 * ����ͨ������OnArticleSelectedListener�ӿڵķ�����fragment A���Ժ�activity�����¼���
	 */
	@Override
	public void onAttach(Activity activity){
		
		super.onAttach(activity);
		try{
			mListener = (OnMusicSelectedListener)activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString()+ "must " +
					"implement onMusicSelectedListener");
		}
	}
	
	/**
	 * 
	 * @ClassName: ButtonBroadcastReceiver 
	 * @author ��ɼ
	 * @Description:�㲥������ť����¼� 
	 * @date 2014-8-14 ����7:32:33
	 */
//	public class ButtonBroadcastReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			
//			// TODO Auto-generated method stub
//			String action = intent.getAction();
//			if(action.equals(ACTION_BUTTON)){
//				//ͨ�����ݹ�����ID�жϰ�ť������Ի���ͨ��getResultCode()�����Ӧ����¼�
//				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
//				if(buttonId == BUTTON_PALY_ID){
//					String play_status = "";
//					isPlaying = !isPlaying;
//					
//					if(isPlaying){
//						play_status = "��ʼ����";
//						startSer(1);
//					}else{
//						play_status = "����ͣ";
//						startSer(2);
//					}
//					//���½��棬��ͼƬ�ı�
//					showButtonNotify();
//					Log.d(TAG , play_status);
//					Toast.makeText(getActivity(), play_status, Toast.LENGTH_SHORT).show();
//					
//				}
//			}
//		}
//		
//	}
	
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1�ǲ��ţ�2��ֹͣ��3����ͣ,0��������
		bundle.putInt("op", op);
		//����ǲ��ŵĸ���
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	
}
