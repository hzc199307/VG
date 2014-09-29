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
 * @author ��ɼ
 * @Description: �Ƽ�activity�����õ����Զ�����������������������ѡ��ʹ���ĸ�fragment
 * @date 2014-8-12 ����3:21:37
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
		//��ʼ��fragment
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
