package com.ne.vg.adapter;

import com.ne.vg.fragment.BigSceneListFragment;
import com.ne.vg.fragment.RecommendRouteFragment;
import com.ne.vg.main.HomeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @ClassName: RecommendAdapter 
 * @author 潘杉
 * @Description: 推荐activity中所用到的自定义适配器，用来传输数据选择使用哪个fragment
 * @date 2014-8-12 下午3:21:37
 */
public class RecommendAdapter extends FragmentPagerAdapter{

	int NUM_ITEM = 2;
	int cityID;
	public RecommendAdapter(FragmentManager fm,int cityID) {
		super(fm);
		this.cityID = cityID;
	}

	@Override
	public Fragment getItem(int position) {
		switch(position){
		//初始化fragment
		case 0:return new RecommendRouteFragment(cityID);
		case 1:return new BigSceneListFragment(cityID);
		default:return null;
		}
	}

	@Override
	public int getCount() {
		return NUM_ITEM;
	}

	@Override  
	public void destroyItem(ViewGroup container, int position, Object object) {  
		super.destroyItem(container, position, object);  
	} 


}
