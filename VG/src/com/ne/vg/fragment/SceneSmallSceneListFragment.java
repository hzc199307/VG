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
	/**播放状态的初始化*/
	public boolean isPlaying; 
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

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		app = (VGApplication)this.getActivity().getApplication();
		intent = new Intent("com.ne.vg.service.MusicService");
		isPlaying = app.isPlaying;
		
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
		smallSceneListView.setAdapter(new SceneSmallSceneListAdapter(getActivity()));
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
				if(!isPlaying)
				{
					animationDrawable.start();
				}
				divider = (View)view.findViewById(R.id.scene_item_smallscene_divider2);
				//0代表visible
				divider.setVisibility(0);
				// TODO 开启服务并更新seekbar
				/**
				 * 这一段只是用来测试，还需要重新写
				 */
				startSer(0);
				
				isPlaying = !isPlaying;
				
				
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
	
	/**
	 * 
	 * @ClassName: ButtonBroadcastReceiver 
	 * @author 潘杉
	 * @Description:广播监听按钮点击事件 
	 * @date 2014-8-14 下午7:32:33
	 */
//	public class ButtonBroadcastReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			
//			// TODO Auto-generated method stub
//			String action = intent.getAction();
//			if(action.equals(ACTION_BUTTON)){
//				//通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
//				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
//				if(buttonId == BUTTON_PALY_ID){
//					String play_status = "";
//					isPlaying = !isPlaying;
//					
//					if(isPlaying){
//						play_status = "开始播放";
//						startSer(1);
//					}else{
//						play_status = "已暂停";
//						startSer(2);
//					}
//					//更新界面，及图片改变
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
		//op:1是播放，2是停止，3是暂停,0代表不处理
		bundle.putInt("op", op);
		//这个是播放的歌曲
		bundle.putInt("musicresource",R.raw.fengwei);
		intent.putExtras(bundle);
		getActivity().startService(intent);
	}
	
	
}
