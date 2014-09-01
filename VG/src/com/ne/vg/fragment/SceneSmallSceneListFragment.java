package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.fragment.PlayMusicFragment.ButtonBroadcastReceiver;

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
	
	private NotificationManager mNotificationManager;
	/** ֪ͨ����ť�㲥 */
	public ButtonBroadcastReceiver bReceiver;
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	/**����״̬�ĳ�ʼ��*/
	private boolean isPlaying = false; 
	/** Notification��ID */
	int notifyId = 100;
	
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	


	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		intent = new Intent("com.ne.vg.service.MusicService");
		mNotificationManager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		 //��ʼ���Զ���㲥
        initButtonReceiver();
	}
	
	
	/**
	 * 
	 * @Title: initButtonReceiver 
	 * @Description: ��ʼ���㲥���գ�����ذ���ACTION_BUTTON�Ĺ㲥
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void initButtonReceiver() {
		// TODO Auto-generated method stub
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getActivity().registerReceiver(bReceiver, intentFilter);
	}


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
				showButtonNotify();
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
	public class ButtonBroadcastReceiver extends BroadcastReceiver{

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
						startSer(1);
					}else{
						play_status = "����ͣ";
						startSer(2);
					}
					//���½��棬��ͼƬ�ı�
					showButtonNotify();
					Log.d(TAG , play_status);
					Toast.makeText(getActivity(), play_status, Toast.LENGTH_SHORT).show();
					
				}
			}
		}
		
	}
	
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1�ǲ��ţ�2��ֹͣ��3����ͣ,0��������
		bundle.putInt("op", op);
		//����ǲ��ŵĸ���
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	public void showButtonNotify() {
		// TODO Auto-generated method stub

		//ʵ����֪ͨ��������NotificationCompat
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
		//Notification���Զ��岼����RemoteViews
		RemoteViews mRemoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.statusbar);
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		//API3.0 ���ϵ�ʱ����ʾ��ť��������ʧ
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "л����");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "ʮ������ζ");
		//����汾�ŵ���3.0����ô����ʾ��ť
		if(android.os.Build.VERSION.SDK_INT<=9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
			
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			if(isPlaying){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_pause2);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.test_btn_play2);				
			}
		}
		
		//������¼�����
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		
		/*����/��ͣ ��ť*/
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(getActivity(), 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
	
		//�ò����������ڶ�����פ
		Intent intent = new Intent(getActivity(),SceneActivity.class);
		PendingIntent pendingIntent= PendingIntent.getActivity(getActivity(), 1, intent, Notification.FLAG_ONGOING_EVENT);
		mBuilder.setContent(mRemoteViews)
				//����֪ͨ�������ͼ
				.setContentIntent(pendingIntent)
				.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setTicker("���ڲ���")
				.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
				.setOngoing(true)
				.setSmallIcon(R.drawable.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;  
		//��һ������Ϊ�Զ����֪ͨΨһ��ʶ������֪ͨ����
        mNotificationManager.notify(notifyId, notify);
	
	}
}
