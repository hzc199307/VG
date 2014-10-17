package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.bean.SceneContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * BigSceneDetailActivity ÏÂµÄ ¾°µã½éÉÜFragment 
 * @ClassName: BigSceneIntroFragment 
 * @author ºØÖÇ³¬
 * @Description: TODO 
 * @date 2014-8-19 ÏÂÎç3:08:45
 */
public class BigSceneIntroFragment extends Fragment {

	private final static String TAG= "BigSceneIntroFragment";
	private SceneContent sceneContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro, container, false);
		sceneContent = ((BigSceneDetailActivity)getActivity()).getSceneContent();
		if(sceneContent!=null)
		{
			TextView bsi_content = (TextView)rootView.findViewById(R.id.bsi_content);
			bsi_content.setText(sceneContent.getContent());
			
			View star[] = new View[5];
			star[0] = rootView.findViewById(R.id.bsi_star_1);
			star[1] = rootView.findViewById(R.id.bsi_star_2);
			star[2] = rootView.findViewById(R.id.bsi_star_3);
			star[3] = rootView.findViewById(R.id.bsi_star_4);
			star[4] = rootView.findViewById(R.id.bsi_star_5);
			for(int i=0;i<sceneContent.getRecommendLevel();i++)
			{
				star[i].setVisibility(View.VISIBLE);
			}
			for(int i=sceneContent.getRecommendLevel();i<5;i++)
			{
				star[i].setVisibility(View.GONE);
			}
		}
		
		return rootView;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
