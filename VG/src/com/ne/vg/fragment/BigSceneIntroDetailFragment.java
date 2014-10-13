package com.ne.vg.fragment;

import com.ne.vg.R;

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
	private TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_big_scene_intro_detail, container, false);
		textView = (TextView)rootView.findViewById(R.id.bsid_website);
		textView.setText(Html.fromHtml("<a href='http://www.google.cn'>google</a>"));
		textView.setMovementMethod(LinkMovementMethod.getInstance()); 
		return rootView;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
}
