package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.TestFragment;
import com.ne.vg.fragment.BigSceneIntroDetailFragment;
import com.ne.vg.fragment.BigSceneIntroFragment;
import com.ne.vg.fragment.HomeFragment;
import com.ne.vg.fragment.MineFragment;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.util.UnitUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * �󾰵���������ҳ
 * @ClassName: BigSceneDetailActivity 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-12 ����3:27:33
 */
public class BigSceneDetailActivity extends FragmentActivity {

	private final static String TAG= "BigSceneDetailActivity";

	private GMapFragment gMapFragment;
	private BigSceneIntroFragment bigSceneIntroFragment;
	private BigSceneIntroDetailFragment bigSceneIntroDetailFragment;

	private int lastMapHeight;
	private FrameLayout gmapFrame;//��ͼ����
	private ScrollView bsdScroll;//��������
	private boolean isMapBig = false;//��¼��ͼ�Ƿ񱻵���Ŵ���
	private ImageButton bsd_btn_up,bsd_btn_location;
	private View big_scene_detail_other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_big_scene_detail);

		gmapFrame = (FrameLayout)findViewById(R.id.big_scene_detail_frame_map);
		bsdScroll = (ScrollView)findViewById(R.id.bsd_scroll);
		bsd_btn_up = (ImageButton)findViewById(R.id.bsd_btn_up);
		bsd_btn_location = (ImageButton)findViewById(R.id.bsd_btn_location);
		big_scene_detail_other = (View)findViewById(R.id.big_scene_detail_other);

		//		if(savedInstanceState == null)
		//		{
		//			if(gMapFragment==null)
		//				gMapFragment = new GMapFragment();
		//			if(bigSceneIntroFragment==null)
		//				bigSceneIntroFragment = new BigSceneIntroFragment();
		//			if(bigSceneIntroDetailFragment==null)
		//				bigSceneIntroDetailFragment = new BigSceneIntroDetailFragment();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.big_scene_detail_frame_map, gMapFragment)
		//			.commit();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.frame_big_scene_intro, bigSceneIntroFragment)
		//			.commit();
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.frame_big_scene_intro_detail, bigSceneIntroDetailFragment)
		//			.commit();
		//		}



	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();

		if(gMapFragment==null)
		{
			gMapFragment = new GMapFragment();
			//�˴����õ�ͼ���������������ͼ��ȫ����ͬʱ�����������ͼ,��ʾ���ϰ�ť
			gMapFragment.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if(!isMapBig)
					{
						changeMapStatus();
					}
				}
			});
		}
		if(bigSceneIntroFragment==null)
			bigSceneIntroFragment = new BigSceneIntroFragment();
		if(bigSceneIntroDetailFragment==null)
			bigSceneIntroDetailFragment = new BigSceneIntroDetailFragment();
		Log.v(TAG, "gMapFragment.isAdded()?" +gMapFragment.isAdded());
		if(!gMapFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.big_scene_detail_frame_map, gMapFragment)
			.commit();
		if(!bigSceneIntroFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro, bigSceneIntroFragment)
			.commit();
		if(!bigSceneIntroDetailFragment.isAdded())
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.frame_big_scene_intro_detail, bigSceneIntroDetailFragment)
			.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	/**
	 * �����ֶ��Ƴ�������
	 */
	public void destroyGMapFragment() {
		// �˴������Ƴ� gMapFragment  ��Ϊ������webview��Ե�� �����ڴ�й¶
		getSupportFragmentManager().beginTransaction().remove(gMapFragment).commit();
		gMapFragment = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		destroyGMapFragment();
		this.finish();
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * �ı��ͼ��״̬
	 */
	public void changeMapStatus()
	{
		if(!isMapBig)
		{
			lastMapHeight = gmapFrame.getHeight();//����֮ǰ�ĸ߶�
			bsd_btn_up.setVisibility(View.VISIBLE);
			bsd_btn_location.setVisibility(View.VISIBLE);
			FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams)gmapFrame.getLayoutParams();
			linearParams.height = bsdScroll.getHeight();
			gmapFrame.setLayoutParams(linearParams);
			linearParams=null;
			big_scene_detail_other.setVisibility(View.GONE);//������ֹ�������Ĺ���
			isMapBig=true;
		}
		else
		{
			bsd_btn_up.setVisibility(View.INVISIBLE);
			bsd_btn_location.setVisibility(View.INVISIBLE);
			FrameLayout.LayoutParams linearParams = (LayoutParams) gmapFrame.getLayoutParams();
			linearParams.height = lastMapHeight;
			gmapFrame.setLayoutParams(linearParams);
			linearParams=null;
			big_scene_detail_other.setVisibility(View.VISIBLE);
			isMapBig=false;
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.big_scene_detail_title_left:
			destroyGMapFragment();
			this.finish();
			break;
		case R.id.bsd_btn_location:
			if(gMapFragment!=null)
				gMapFragment.requestLoc();
			break;	
		case R.id.bsd_btn_up:
			changeMapStatus();
			break;
		case R.id.bsd_enter_scene_btn:
			destroyGMapFragment();
			startActivity(new Intent(this,SceneActivity.class));
			break;
		default:
			break;
		}
	}

}