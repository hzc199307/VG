package com.ne.vg.adapter;

import com.ne.vg.fragment.HomeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class RecommendAdapter extends FragmentPagerAdapter{

	int NUM_ITEM = 2;
	public RecommendAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// TODO 这里两个fragment需要被修改
		switch(position){
		case 0:return new HomeFragment();
		case 1:return new HomeFragment();
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
