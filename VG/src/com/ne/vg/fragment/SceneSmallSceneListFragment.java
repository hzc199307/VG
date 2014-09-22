package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.SmallScene;
import com.ne.vg.dao.VGDao;
import com.ne.vg.receiver.MusicBroadcastReceiver;
import com.ne.vg.util.MusicNotification;
import com.ne.vg.util.MusicPlayerUtil;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
	/** Notification��ID */
	int notifyId = 100;
	private VGApplication app;
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	
	public MusicNotification mNotification;
	public ImageView animationIV;
	public AnimationDrawable animationDrawable;
	public View currentView;
	public View divider;
	public View divider2;
	private ImageView button;
	private String cityName;
	private String bigSceneName;
	private String smallSceneName;
	private int smallSceneId;
	private int bigSceneID;
	public VGDao mDao;
	private CommonAdapter<SmallScene> mAdapter;
	private smallBroadcastReceiver sReceiver;
	private Handler mHandler;
	private int mPosition;
	private int oldValue;
	private int newValue;
	
	
	public SceneSmallSceneListFragment(String cityName,String bigSceneName,int bigSceneID)
	{
		this.cityName = cityName;
		this.bigSceneName = bigSceneName;
		this.bigSceneID = bigSceneID;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		app = (VGApplication)this.getActivity().getApplication();
		intent = new Intent("com.ne.vg.service.MusicService");
		mDao = new VGDao(getActivity());
		
		 //��ʼ���Զ���֪ͨ��
		mNotification = new MusicNotification(getActivity().getApplicationContext());
		initSmallReceiver();
		
	}
	
	
	private void initSmallReceiver() {
		// TODO Auto-generated method stub
		sReceiver = new smallBroadcastReceiver();
		
		IntentFilter  intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getActivity().registerReceiver(sReceiver, intentFilter);
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
		//mAdapter = new SceneSmallSceneListAdapter(getActivity(),mDao.getSmallScene(bigSceneID));
		mAdapter = new CommonAdapter<SmallScene>(getActivity(), mDao.getSmallScene(bigSceneID), R.layout.scene_item_small_scene) {

			@Override
			public void convert(ViewHolder helper, SmallScene item){
				// TODO Auto-generated method stub
				helper.setText(R.id.scene_item_smallscene_name, item.getSmallSceneName());
				//������ڲ���
				if(app.playSceneID == item.getSmallSceneID() && app.mBinder.getService().isPlaying==true){
					helper.setImageResourceByInt(R.id.scene_item_smallscene_button, R.drawable.scene_music_pause_icon);
					//���Ŷ���
					helper.setAnimation(R.id.animationIV,R.drawable.scene_music_isplaying_animation,1);
					helper.setView(R.id.scene_item_smallscene_divider1,8);
					helper.setView(R.id.scene_item_smallscene_divider2,0);
				}else{
					helper.setImageResourceByInt(R.id.scene_item_smallscene_button, R.drawable.scene_music_play_icon);
					//��ͣ����
					helper.setAnimation(R.id.animationIV,R.drawable.scene_music_isplaying_animation,0);
					helper.setView(R.id.scene_item_smallscene_divider1,0);
					helper.setView(R.id.scene_item_smallscene_divider2,8);
				}	
			}	
		};
		smallSceneListView.setAdapter(mAdapter);
		InitListener();
		return rootView;
	}

	private void InitListener() {
		// TODO Auto-generated method stub
		smallSceneListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Log.d(TAG, " item " + position +" is clicked!");
				currentView = view;
				
				smallSceneName = mDao.getSmallScene(bigSceneID).get(position).getSmallSceneName();
				smallSceneId = mDao.getSmallScene(bigSceneID).get(position).getSmallSceneID();
				startSer(0);
				//���½���
				mAdapter.notifyDataSetChanged();
				
				
				/**
				 * �������ص㣬����startSer���ں�̨�����ģ�������Ҫ��ʱִ�к���Ĵ��롣
				 */
				mHandler = new Handler();
				Runnable runnable = new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						app.playSceneID = smallSceneId;
						
						// TODO �������񲢸���seekbar
						/**
						 * ��һ��ֻ���������ԣ�����Ҫ����д
						 */
						if(app.mBinder.getService().isPlayStatusChanged()==true)
							//���½���
							mAdapter.notifyDataSetChanged();
						
						mNotification.showName(smallSceneName);
						mNotification.showButtonNotify();
						//����Activity�еĺ���,��������seekBar
						mListener.onMusicSelected();
						//TODO ����������б�
						//mAdapter.notifyDataSetChanged();
						mHandler.postDelayed(this, 300);// �򿪶�ʱ����ִ�в��� 
					}      
		        };  
		        //TODO ������ӳٸ��û������������
		        mHandler.post(runnable);// �򿪶�ʱ����ִ�в��� 
		    	app.mBinder.getService().startSuccess = false;
		    	
			}
			
		
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(sReceiver);
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
	
	public void startSer(int op){
		Bundle bundle = new Bundle();
		//op:1�ǲ��ţ�2��ֹͣ��3����ͣ,0��������
		bundle.putInt("op", op);
		//����ǲ��ŵĸ���
		Log.d(TAG, "bigSceneName="+bigSceneName);
		bundle.putString("musicresource",MusicPlayerUtil.getVoicePath(cityName, bigSceneName, smallSceneName));
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	/**
	 * 
	 * @ClassName: smallBroadcastReceiver 
	 * @author ��ɼ
	 * @Description: ����Ĺ㲥��Ϊ��ʵ��item��notification��ͬ��
	 * @date 2014-9-4 ����10:22:31
	 */
	public class smallBroadcastReceiver extends BroadcastReceiver{
		
		/** ֪ͨ����ť����¼���Ӧ��ACTION */
		public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.d(TAG, "smallBroadcastReceiver");
			String action = intent.getAction();
			if(action.equals(ACTION_BUTTON)){
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				if(buttonId == BUTTON_PALY_ID){
					
					animationIV = (ImageView) currentView.findViewById(R.id.animationIV);
					animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
					animationDrawable = (AnimationDrawable) animationIV
							.getDrawable();
					button = (ImageView)currentView.findViewById(R.id.scene_item_smallscene_button);
					// ������ڲ��ţ�������item
					if(app.mBinder.getService().isPlaying == true){
						animationDrawable.start();
						button.setImageResource(R.drawable.scene_music_pause_icon);
					}
					else{
						animationDrawable.stop();
						button.setImageResource(R.drawable.scene_music_play_icon);
					}
				}
			}	
		}	
	}
}
