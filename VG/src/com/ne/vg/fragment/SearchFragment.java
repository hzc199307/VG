package com.ne.vg.fragment;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.PlayMusicActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.activity.RouteActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * “搜索”详情页（暂时用来测试用）
 * @ClassName: SearchFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:21:19
 */
public class SearchFragment extends Fragment implements View.OnClickListener{

	
	
private final static String TAG = "SearchFragment";
	

private View search_title_all;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_search, container,false);

		search_title_all = (View)view.findViewById(R.id.search_title_all);
		
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		default:
			break;
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}
	
	/**
	 * 开始标题栏动画
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		if(search_title_all!=null)
			search_title_all.startAnimation(animation);
	}

	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		if(search_title_all!=null&&search_title_all.getAnimation()!=null)
			search_title_all.clearAnimation();
	}
}
