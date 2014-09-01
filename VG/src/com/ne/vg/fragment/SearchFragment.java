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
	
	private Button btn_panshan,btn_zhichao,btn_bofang,btn_mylove;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		View view  = inflater.inflate(R.layout.fragment_search, container,false);
		btn_panshan = (Button)view.findViewById(R.id.btn_panshan);
		btn_panshan.setOnClickListener(this);
		btn_zhichao = (Button)view.findViewById(R.id.btn_zhichao);
		btn_zhichao.setOnClickListener(this); 
		btn_bofang = (Button)view.findViewById(R.id.btn_bofangyieyue);
		btn_bofang.setOnClickListener(this);
		btn_mylove = (Button)view.findViewById(R.id.btn_mylove);
		btn_mylove.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_panshan:
			//TODO 潘杉 你要执行的跳转
			startActivity(new Intent(getActivity(),RecommendActivity.class));
			break;
		case R.id.btn_zhichao:
			//TODO 
			//getFragmentManager().beginTransaction().hide(this).add(R.id.content_frame, new RouteFragment()).addToBackStack(null).commit();
			startActivity(new Intent(getActivity(),RouteActivity.class));
			//startActivity(new Intent(getActivity(),BigSceneDetailActivity.class));
			break;
		case R.id.btn_bofangyieyue:
			//TODO 播放音乐跳转的地方
			startActivity(new Intent(getActivity(),PlayMusicActivity.class));
			break;
		case R.id.btn_mylove:
			//TODO 跳转到我喜爱的页面
			startActivity(new Intent(getActivity(),MyLoveActivity.class));
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroy");
	}
}
