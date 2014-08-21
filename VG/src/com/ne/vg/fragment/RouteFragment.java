package com.ne.vg.fragment;

import java.util.ArrayList;

import com.ne.vg.R;
import com.ne.vg.adapter.MyFragmentStatePagerAdapter2;
import com.ne.vg.gmap.GMapFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * ��Ӧ �Ƽ�·�ߵİ����ڵ�����ҳ
 * @ClassName: RouteFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-12 ����10:14:19
 */
public class RouteFragment extends Fragment{

	private static final String TAG = "RouteFragment";
	private static final boolean DEBUG = true;

	private View rootView;
	private ViewPager route_viewpager;
	private PagerTabStrip route_pagertab;
	private MyFragmentStatePagerAdapter myPagerAdapter;
	private GMapFragment gMapFragment ;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(DEBUG)Log.v(TAG, "onCreateView");
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		rootView = inflater.inflate(R.layout.fragment_route, container,false);
		initMap();
		initViewPager();
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initMap();
	}

	/**
	 * ��ʼ��Map
	 */
	private void initMap() {
		// TODO Auto-generated method stub
		if(gMapFragment==null)
			gMapFragment = new GMapFragment();
		if(!gMapFragment.isAdded())
			getFragmentManager().beginTransaction()
			.add(R.id.route_frame_map, gMapFragment)
			.commit();
	}

	/**
	 * ��ʼ��ViewPager
	 */
	@SuppressLint("ResourceAsColor") 
	private void initViewPager() {
		// TODO Auto-generated method stub
		route_viewpager = (ViewPager)rootView.findViewById(R.id.route_viewpager);
		route_pagertab = (PagerTabStrip)rootView.findViewById(R.id.route_pagertab);

		route_pagertab.setTextColor(getResources().getColor(R.color.route_pagertab_text));
		//���������С����̫��route_pagertab.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.route_pagertab_textsize));
		route_pagertab.setTabIndicatorColor(getResources().getColor(R.color.none)); 
		route_pagertab.setDrawFullUnderline(false);
		route_pagertab.setBackgroundColor(getResources().getColor(R.color.route_pagertab_bg));
		route_pagertab.setTextSpacing(50);
		myPagerAdapter = new MyFragmentStatePagerAdapter(getFragmentManager(),3);
		route_viewpager.setAdapter(myPagerAdapter);
		route_viewpager.setCurrentItem(0);
	}


	/**
	 *  ��FragmentStatePagerAdapterֻ�ᱣ��3��(��+�Լ�+��) ,FragmentPagerAdapter�ᱣ�����е�Fragment����(����Ӧ��fragment�ٵ����)    
	 * 
	 * ����(��+�Լ�+��)λ�õ�ʱ�� ִ����onDestroyView onDestroy
	 * @ClassName: MyFragmentPagerAdapter 
	 */
	public class MyFragmentStatePagerAdapter extends MyFragmentStatePagerAdapter2{

		private int NUM_ITEMS = 3;

		public MyFragmentStatePagerAdapter(FragmentManager fm ,int numOfFragment) {  
			super(fm);  
			NUM_ITEMS = numOfFragment;
		}

		@Override  
		public int getCount() {  
			return NUM_ITEMS;  
		}  

		/**
		 * ֻ��ִ��һ��
		 */
		@Override  
		public Fragment getItem(int position) {  
			if(DEBUG)Log.v(TAG, "MyFragmentPagerAdapter getItem");
			//Toast.makeText(getActivity(), TAG+ " getItem", Toast.LENGTH_SHORT).show();
			// ������Ӧ��  fragment  
			switch(position)
			{
			case 0:return new RouteDayFragment(0);
			case 1:return new RouteDayFragment(1);
			case 2:return new RouteDayFragment(2);
			default:return null;
			}
		}

		/**
		 * ��fragment��̬��������Ӧ�÷�������
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			if(DEBUG)Log.v(TAG, "MyFragmentPagerAdapter instantiateItem");
			//Toast.makeText(getActivity(), TAG+ " instantiateItem", Toast.LENGTH_SHORT).show();
			return super.instantiateItem(container, position);
		}

		/**
		 * ҳ��ı�����
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if(DEBUG)Log.v(TAG, "getPageTitle"+ position);
			return "Day "+position;
		}

		/**
		 * fragmentû�б�����remove() ֻ��detach()��
		 */
		@Override  
		public void destroyItem(ViewGroup container, int position, Object object) {  
			super.destroyItem(container, position, object); 
		} 
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		gMapFragment =null;
		myPagerAdapter.destroy();
		myPagerAdapter= null;
		super.onDestroyView();
		if(DEBUG)Log.v(TAG, "onDestroyView");
		//Toast.makeText(getActivity(), TAG+ " onDestroyView", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(DEBUG)Log.v(TAG, "onDestroy");
		//Toast.makeText(getActivity(), TAG+ " onDestroy", Toast.LENGTH_SHORT).show();

	}

	/**
	 * �����ֶ��Ƴ�GMapFragment
	 */
	public void destroyGMapFragment() {
		// �˴������Ƴ� gMapFragment  ��Ϊ������webview��Ե�� �����ڴ�й¶
		getFragmentManager().beginTransaction().remove(gMapFragment).commit();
		gMapFragment = null;
	}
}
