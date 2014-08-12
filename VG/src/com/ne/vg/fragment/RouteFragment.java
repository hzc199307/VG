package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.gmap.GMapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
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

	private final static String TAG = "RouteFragment";
	private View rootView;
	private ViewPager route_viewpager;
	private PagerTabStrip route_pagertab;
	private MyFragmentPagerAdapter myFragmentPagerAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		rootView = inflater.inflate(R.layout.fragment_route, container,false);
		initMap();
		initViewPager();
		return rootView;
	}
	 
	/**
	 * ��ʼ��Map
	 */
	private void initMap() {
		// TODO Auto-generated method stub
		getFragmentManager().beginTransaction()
		.add(R.id.route_frame_map, new GMapFragment())
		.commit();
	}
	/**
	 * ��ʼ��ViewPager
	 */
	private void initViewPager() {
		// TODO Auto-generated method stub
		route_viewpager = (ViewPager)rootView.findViewById(R.id.route_viewpager);
		route_pagertab = (PagerTabStrip)rootView.findViewById(R.id.route_pagertab);
		
		route_pagertab.setTabIndicatorColor(getResources().getColor(R.color.black)); 
		route_pagertab.setDrawFullUnderline(false);
		route_pagertab.setBackgroundColor(getResources().getColor(R.color.white));
		route_pagertab.setTextSpacing(50);
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(),3);
		route_viewpager.setAdapter(myFragmentPagerAdapter);
		route_viewpager.setCurrentItem(0);
	}
	
	
	/**
	 * ���������ᱣ�����е�Fragment����     ��FragmentStatePagerAdapterֻ�ᱣ��3��(��+�Լ�+��)
	 * ����Ӧ��fragment�ٵ����
	 * ����(��+�Լ�+��)λ�õ�ʱ�� ִ����onDestroyView�����³��ֵ�ʱ�� ִ����onCreateView
	 * @ClassName: MyFragmentPagerAdapter 
	 */
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
		
		private int NUM_ITEMS = 3;
		
		public MyFragmentPagerAdapter(FragmentManager fm ,int numOfFragment) {  
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
			Toast.makeText(getActivity(), TAG+ " getItem", Toast.LENGTH_SHORT).show();
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
			Fragment fragment = (Fragment) super.instantiateItem(container, position);
			return fragment;
		}
		
		/**
		 * ҳ��ı�����
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
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
}
