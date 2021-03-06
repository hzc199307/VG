package com.ne.vg.fragment;

import java.util.ArrayList;

import com.ne.vg.R;
import com.ne.vg.adapter.MyFragmentStatePagerAdapter2;
import com.ne.vg.bean.RecommendRoute;
import com.ne.vg.dao.VGDao;
import com.ne.vg.gmap.GMapFragment;
import com.ne.vg.util.LogUtil;

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
import android.widget.TextView;
import android.widget.Toast;

/**
 * 对应 推荐路线的按日期的详情页
 * @ClassName: RouteFragment 
 * @author 贺智超
 * @date 2014-8-12 上午10:14:19
 */
public class RouteFragment extends Fragment{

	private static final String TAG = "RouteFragment";
	private static final boolean DEBUG = true;

	private View rootView;
	private ViewPager route_viewpager;
	private PagerTabStrip route_pagertab;
	private MyFragmentStatePagerAdapter myPagerAdapter;
	private GMapFragment gMapFragment ;
	
	private int routeID;
	private RecommendRoute route;
	private VGDao mVgDao;
	private TextView routeTitleNameTv;
	
	public RouteFragment(int routeID){
		Log.d(TAG, routeID + "");
		this.routeID = routeID;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(DEBUG)Log.v(TAG, "onCreateView");
		mVgDao = VGDao.getInstance(getActivity());
		if(route == null)
			route = mVgDao.getRecommendRoute(routeID);
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		rootView = inflater.inflate(R.layout.fragment_route, container,false);
		routeTitleNameTv = (TextView)rootView.findViewById(R.id.route_title_name);
		routeTitleNameTv.setText(route.getRouteName());
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
	 * 初始化Map
	 */
	private void initMap() {
		
		if(gMapFragment==null)
			gMapFragment = new GMapFragment();
		//TODO 这里需要穿进去latitude和longtitude
		if(!gMapFragment.isAdded())
			getFragmentManager().beginTransaction()
			.add(R.id.route_frame_map, gMapFragment)
			.commit();
	}

	/**
	 * 初始化ViewPager
	 */
	@SuppressLint("ResourceAsColor") 
	private void initViewPager() {
		// TODO Auto-generated method stub
		route_viewpager = (ViewPager)rootView.findViewById(R.id.route_viewpager);
		route_pagertab = (PagerTabStrip)rootView.findViewById(R.id.route_pagertab);

		route_pagertab.setTextColor(getResources().getColor(R.color.route_pagertab_text));
		//设置字体大小好像不太对route_pagertab.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.route_pagertab_textsize));
		route_pagertab.setTabIndicatorColor(getResources().getColor(R.color.white)); 
		route_pagertab.setDrawFullUnderline(false);
		route_pagertab.setBackgroundColor(getResources().getColor(R.color.route_pagertab_bg));
		route_pagertab.setTextSpacing(50);
		
		Log.d(TAG, "routeDay = " + route.getRouteDay());
		myPagerAdapter = new MyFragmentStatePagerAdapter(getFragmentManager(),route.getRouteDay());
		route_viewpager.setAdapter(myPagerAdapter);
		route_viewpager.setCurrentItem(0);
	}


	/**
	 *  而FragmentStatePagerAdapter只会保存3个(左+自己+右) ,FragmentPagerAdapter会保存所有的Fragment对象(此适应于fragment少的情况)    
	 * 
	 * 不在(左+自己+右)位置的时候 执行了onDestroyView onDestroy
	 * @ClassName: MyFragmentPagerAdapter 
	 */
	public class MyFragmentStatePagerAdapter extends MyFragmentStatePagerAdapter2{

		private int NUM_ITEMS = 3;
		private int routeContentID;
		
		public MyFragmentStatePagerAdapter(FragmentManager fm ,int numOfFragment) {  
			super(fm);  
			NUM_ITEMS = numOfFragment;
			routeContentID = route.getRouteContentID();
		}

		@Override  
		public int getCount() {  
			return NUM_ITEMS;  
		}  

		/**
		 * 只会执行一次
		 */
		@Override  
		public Fragment getItem(int position) {  
			Log.d(TAG, "position="+position);
			if(DEBUG)Log.v(TAG, "MyFragmentPagerAdapter getItem");
			//Toast.makeText(getActivity(), TAG+ " getItem", Toast.LENGTH_SHORT).show();
			// 返回相应的  fragment  
			return new RouteDayFragment(position, routeContentID);
		}

		/**
		 * 向fragment动态传输数据应该放在这里
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			if(DEBUG)Log.v(TAG, "MyFragmentPagerAdapter instantiateItem");
			
			//Toast.makeText(getActivity(), TAG+ " instantiateItem", Toast.LENGTH_SHORT).show();
			return super.instantiateItem(container, position);
		}

		/**
		 * 页面的标题名
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if(DEBUG)Log.v(TAG, "getPageTitle"+ position);
			return "第"+(position+1)+"天";//return "Day "+(position+1);
		}

		/**
		 * fragment没有被销毁remove() 只是detach()了
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
	 * 必须手动移除GMapFragment
	 */
	public void destroyGMapFragment() {
		// 此处必须移除 gMapFragment  因为里面有webview的缘故 否则内存泄露
		getFragmentManager().beginTransaction().remove(gMapFragment).commit();
		gMapFragment = null;
	}
}
