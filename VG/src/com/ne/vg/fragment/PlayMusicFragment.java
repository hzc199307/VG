package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.adapter.PlayMusicAdapter;
import com.ne.vg.adapter.RecommendRouteAdapter;

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
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PlayMusicFragment extends Fragment{
	
	private Intent intent;
	private static final String TAG = "PlayMusicFragment";
	
	private NotificationManager mNotificationManager;
	//ListView�ؼ�
	private ListView listview;
	//�Զ����������
	private PlayMusicAdapter musicAdapter;
	//�������ݵ�����
	private List<Map<String, Object>> mData;
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	/**����״̬�ĳ�ʼ��*/
	private boolean isPlaying = false; 
	
	/** ֪ͨ����ť�㲥 */
	public ButtonBroadcastReceiver bReceiver;
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	/** Notification��ID */
	int notifyId = 100;
	
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mNotificationManager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		 //��ʼ���Զ���㲥
        initButtonReceiver();
	}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState){
		Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragement_playmusic,
				container, false);
		//Init view
		listview = (ListView) rootView.findViewById(R.id.playmusic_lvSongs);
		InitListener();
		mData = getData();
		musicAdapter = new PlayMusicAdapter(getActivity(), mData);
		listview.setAdapter(musicAdapter);
		return rootView;
	}

	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: ��ʼ��������Listener,��������List�е�Item
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO ������д��ת��������ת������ҳ��
				// ������service��ע���ļ��� �� intent-filter��action name
				intent = new Intent("com.ne.vg.service.MusicService");
				Log.v(TAG, "item is clicked!");
				Toast.makeText(getActivity().getApplicationContext(), "music" + (position+1), Toast.LENGTH_SHORT).show(); 
				if(isPlaying == false){
					//��������
					startSer(1);
				}else{
					//��ͣ����
					startSer(2);
				}
				//���½��棬��ͼƬ�ı�
				showButtonNotify();
				isPlaying = !isPlaying;
			}

		});
	}
	
	
	
	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map;
		for(int i=0;i<10;i++){
			map = new HashMap<String, Object>();
			map.put("img",R.drawable.test_btn_play);
			map.put("musicName", "ʮ������ζ");
			list.add(map);
		}

		return list;
	}
		
	/**
	 * 
	 * @Title: startSer 
	 * @Description: �����������װ��һ������
	 * @param @param op
	 * @return void 
	 * @throws
	 */
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1�ǲ��ţ�2��ֹͣ��3����ͣ
		bundle.putInt("op", op);
		//����ǲ��ŵĸ���
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		
		getActivity().startService(intent);
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
	/**
	 * �ڴ治��ʱ���ö�����finish��ʱ�����
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		Toast.makeText(getActivity(), "activity is detroyed", Toast.LENGTH_SHORT).show();
		mNotificationManager.cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
		if(intent!=null){
			getActivity().stopService(intent);
		}
	}




	public void showButtonNotify() {
		// TODO Auto-generated method stub

		//ʵ����֪ͨ��������NotificationCompat
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
		//Notification���Զ��岼����RemoteViews
		RemoteViews mRemoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.statusbar);
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		//API3.0 ���ϵ�ʱ����ʾ��ť��������ʧ
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "�ܽ���");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "������");
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
		Intent intent = new Intent(getActivity(),PlayMusicActivity.class);
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
