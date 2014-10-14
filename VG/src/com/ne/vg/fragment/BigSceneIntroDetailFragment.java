package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.bean.SceneContent;
import com.ne.vg.dao.VGDao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * BigSceneDetailActivity ÏÂµÄ ¾°µãÏêÏ¸½éÉÜFragment 
 * @ClassName: BigSceneIntroDetailFragment 
 * @author ºØÖÇ³¬
 * @Description: TODO 
 * @date 2014-8-19 ÏÂÎç3:08:45
 */
public class BigSceneIntroDetailFragment extends Fragment {

	private final static String TAG= "BigSceneIntroDetailFragment";
	private TextView bsid_recommendScene,bsid_address,bsid_route,bsid_workingtime,bsid_website,bsid_telephone,bsid_price;
	private SceneContent sceneContent;
	private int bigSceneID;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro_detail, container, false);
		bigSceneID = getActivity().getIntent().getExtras().getInt("bigSceneID");
		sceneContent = ((BigSceneDetailActivity)getActivity()).getSceneContent();
		bsid_recommendScene = (TextView)rootView.findViewById(R.id.bsid_recommendScene);
		bsid_address = (TextView)rootView.findViewById(R.id.bsid_address);
		bsid_route = (TextView)rootView.findViewById(R.id.bsid_route);
		bsid_workingtime = (TextView)rootView.findViewById(R.id.bsid_workingtime);
		bsid_website = (TextView)rootView.findViewById(R.id.bsid_website);
		bsid_telephone = (TextView)rootView.findViewById(R.id.bsid_telephone);
		bsid_price = (TextView)rootView.findViewById(R.id.bsid_price);
		
		if(sceneContent!=null)
		{
			bsid_recommendScene.setText(sceneContent.getRecommendScene());
			bsid_address.setText(sceneContent.getAddress());
			bsid_route.setText(sceneContent.getRoute());
			bsid_workingtime.setText(sceneContent.getWorkingTime());
			bsid_website.setText(sceneContent.getWebsite());
			bsid_telephone.setText(sceneContent.getTelephone());
			bsid_price.setText(sceneContent.getPrice());
		}
		
//		bsid_website.setText(Html.fromHtml("<a href='http://www.google.cn'>google</a>"));
//		bsid_website.setMovementMethod(LinkMovementMethod.getInstance()); 
		
		return rootView;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
