package com.ne.vg.fragment;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.MineCollectionActivity;
import com.ne.vg.activity.MineDownloadActivity;
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
import android.widget.Toast;

/**
 * “我的”
 * @ClassName: MineFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-2 上午11:30:02
 */
public class MineFragment extends Fragment implements View.OnClickListener{

	private final static String TAG = "MineFragment";

	private View mine_item_download,mine_item_love,mine_item_collection;
	private View mine_title_left;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_mine, container,false);
		mine_title_left = (View)view.findViewById(R.id.mine_title_left);
		mine_item_download = (View)view.findViewById(R.id.mine_item_download);
		mine_item_love = (View)view.findViewById(R.id.mine_item_love);
		mine_item_collection = (View)view.findViewById(R.id.mine_item_collection);
		mine_item_download.setOnClickListener(this);
		mine_item_love.setOnClickListener(this);
		mine_item_collection.setOnClickListener(this);
		return view;
	}

	/**
	 * 开始标题栏动画
	 */
	public void startAnimation(TranslateAnimation animation) {
		animation.setDuration(400);
		animation.setFillAfter(true);
		if(mine_title_left!=null)
			mine_title_left.startAnimation(animation);
	} 

	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		if(mine_title_left!=null&&mine_title_left.getAnimation()!=null)
			mine_title_left.clearAnimation();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

		case R.id.mine_item_download:
			//TODO 跳转到我喜爱的页面
			startActivity(new Intent(getActivity(),MineDownloadActivity.class));
			break;
		case R.id.mine_item_love:
			//TODO 跳转到我喜爱的页面
			startActivity(new Intent(getActivity(),MyLoveActivity.class));
			break;
		case R.id.mine_item_collection:
			//TODO 跳转到我喜爱的页面
			startActivity(new Intent(getActivity(),MineCollectionActivity.class));
			break;
		default:
			break;
		}
	}
}
