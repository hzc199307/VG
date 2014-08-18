package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.adapter.CityListAdapter;
import com.ne.vg.bean.City;
import com.ne.vg.bean.CreateData;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 主页详情页
 * @ClassName: HomeFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:21:59
 */
public class HomeFragment extends Fragment implements OnClickListener{

	private final static String TAG = "HomeFragment";

	private ListView lv_city;
	private CityListAdapter cityListAdapter ;
	private View home_title_search_btn,home_title_left;


	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_home, container,false);
		lv_city = (ListView)view.findViewById(R.id.lv_city);
		if(cityListAdapter==null)
			cityListAdapter = new CityListAdapter(getActivity(), CreateData.getCityList());
		lv_city.setAdapter(cityListAdapter);
		home_title_search_btn = (ImageButton)view.findViewById(R.id.home_title_search_btn);
		home_title_search_btn.setOnClickListener(this);

		home_title_left = (View)view.findViewById(R.id.home_title_left);

		return view;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch(id)
		{
		case R.id.home_title_search_btn:
			//点击标题左边按钮弹出左侧菜单
			startActivity(new Intent(getActivity(),BigSceneDetailActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		cityListAdapter.destroy();
		cityListAdapter = null;
	}
	
	/**
	 * 开始标题栏动画
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		home_title_left.startAnimation(animation);
	}
	
	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		home_title_left.clearAnimation();
	}

}
