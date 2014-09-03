package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.SceneSmallSceneListAdapter;
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
	/** 通知栏按钮广播 */
	public MusicBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2; 
	/** Notification的ID */
	int notifyId = 100;
	private VGApplication app;
	
	private Intent intent;
	private Bundle bundle;

	private ListView smallSceneListView ;
	private OnMusicSelectedListener mListener;
	
	public MusicNotification mNotification;
	public ImageView animationIV;
	public AnimationDrawable animationDrawable;
	public View divider;
	private ImageView button;
	private String cityName;
	private String bigSceneName;
	private int bigSceneID;
	public VGDao mDao;
	
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
		
		 //初始化自定义通知栏
		mNotification = new MusicNotification(getActivity().getApplicationContext());
        
	}
	
	
	/**
	 * 
	 * @Title: initButtonReceiver 
	 * @Description: 初始化广播接收，并监控包含ACTION_BUTTON的广播
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
		smallSceneListView.setAdapter(new SceneSmallSceneListAdapter(getActivity(),mDao.getSmallScene(bigSceneID)));
		
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
				startSer(0);
				
				
				if(animationDrawable!=null){
					animationDrawable.stop();
				}
				if(divider!=null){
					//8代表为gone
					divider.setVisibility(8);
				}
				animationIV = (ImageView) view.findViewById(R.id.animationIV);
				animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
				animationDrawable = (AnimationDrawable) animationIV
						.getDrawable();
				button = (ImageView)view.findViewById(R.id.scene_item_smallscene_button);
				//如果正在播放则播动画
				if(!app.mBinder.getService().isPlaying)
				{
					animationDrawable.start();
					button.setImageResource(R.drawable.scene_music_pause_icon);
				}else
				{
					button.setImageResource(R.drawable.scene_music_play_icon);
				}
				divider = (View)view.findViewById(R.id.scene_item_smallscene_divider2);
				//0代表visible
				divider.setVisibility(0);
				// TODO 开启服务并更新seekbar
				/**
				 * 这一段只是用来测试，还需要重新写
				 */
				
				
				
				
				//更新界面，及图片改变
//				app.showNotify();
				
				mNotification.showButtonNotify();
				//调用Activity中的函数,用来更新seekBar
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
	 * @author 潘杉
	 * @Description: 定义一个回调接口，要求宿主activity实现它。 
	 * @date 2014-9-1 上午10:56:36
	 */
	public interface OnMusicSelectedListener{
		public void onMusicSelected();
	}
	
	/**
	 * 这个方法在fragment 被加入到activity中时由系统调用
	 * 如果activity没有实现这个接口，fragment将会抛出ClassCastException异常，
	 * 如果成功了，mListener将会是activity实现OnArticleSelectedListener接口的一个引用，
	 * 所以通过调用OnArticleSelectedListener接口的方法，fragment A可以和activity共享事件。
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
		//op:1是播放，2是停止，3是暂停,0代表不处理
		bundle.putInt("op", op);
		//这个是播放的歌曲
		Log.d(TAG, "bigSceneName="+bigSceneName);
		bundle.putString("musicresource",MusicPlayerUtil.getVoicePath(cityName, bigSceneName, "2大教堂"));
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	
}
