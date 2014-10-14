package com.ne.vg.main;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.adapter.CityListAdapter;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.City;
import com.ne.vg.bean.CreateData;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.ImageUtil;
import com.ne.vg.util.LocationUtil;
import com.ne.vg.util.LogUtil;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.R.layout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
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
public class HomeFragment extends AnimationFragment implements OnClickListener{

	private final static String TAG = "HomeFragment";

	private ListView lv_city;
	private CommonAdapter<City> mAdapter;
	private View home_title_search_btn,home_title_left;

	private Intent intent;
	private VGDao mVgDao;

	//	private ProgressDialog progressDialog;
	private View home_tab_locationEnter,home_tab_randomEnter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = new Intent(getActivity(),RecommendActivity.class);
		//		progressDialog = new ProgressDialog(getActivity());
		//		progressDialog.setMessage(getString(R.string.loading));
		//		progressDialog.setCancelable(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "onCreateView");

		if(mVgDao==null)
			mVgDao = new VGDao(getActivity());
		View view  = inflater.inflate(R.layout.fragment_home, container,false);
		lv_city = (ListView)view.findViewById(R.id.lv_city);


		//		if(cityListAdapter==null)
		//			cityListAdapter = new CityListAdapter(getActivity(), mVgDao.getCity());
		//lv_city.setAdapter(cityListAdapter);
		if(mAdapter==null)
			mAdapter = new CommonAdapter<City>(getActivity(), mVgDao.getCityList(), R.layout.item_city) {

			@Override
			public void convert(ViewHolder helper, City item, int position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.cityName, item.getCityName());
				helper.setText(R.id.cityPinyin, item.getCityPinyin());
				helper.setImageBitmap(R.id.item_city_bg, ImageUtil.getCityResourceStr(item.getResource()));
				//helper.setImageResource(R.id.item_city_bg, item.getResource());
				helper.setText(R.id.item_city_route_number_tv, item.getRouteNum()+"");
				helper.setText(R.id.item_city_scene_number_tv, item.getSceneNum()+"");
			}

		};
		lv_city.setAdapter(mAdapter);
		lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				LogUtil.d(TAG, "item" + position + "is clicked");
				City city = mVgDao.getCityList().get(position);
				intent.putExtra("cityID", city.getCityID());
				Log.d(TAG, "cityID:"+city.getCityID());
				startActivity(intent);
				((MainActivity)getActivity()).destroyAllFragmentWithoutNow();
			}
		});

		home_title_left = (View)view.findViewById(R.id.home_title_left);
		super.setAnimView(home_title_left);
		home_title_search_btn = (ImageButton)view.findViewById(R.id.home_title_search_btn);
		home_title_search_btn.setOnClickListener(this);
		home_tab_locationEnter = (View)view.findViewById(R.id.home_tab_locationEnter);
		home_tab_locationEnter.setOnClickListener(this);
		home_tab_randomEnter = (View)view.findViewById(R.id.home_tab_randomEnter);
		home_tab_randomEnter.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch(id)
		{
		case R.id.home_title_search_btn:
			((MainActivity) getActivity()).switchContentCanBackToSearchFrom(this);
			break;
		case R.id.home_tab_locationEnter: 
			MainActivity mainActivity = (MainActivity)getActivity();
			mainActivity.showProgressDialog(R.string.locating);
			//requestLoc();
			//progressDialog.isShowing();
			break;
		case R.id.home_tab_randomEnter:

			break;
		default:
			break;
		}
	}

	private LocationUtil locationUtil;
	/**
	 * 定位 地图插入定位坐标
	 */
	public void requestLoc() {
		// TODO Auto-generated method stub
		if(locationUtil ==null)
			locationUtil = new LocationUtil(getActivity(), new LocationListener() {


				@Override
				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChanged(Location arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), ""+arg0.getLatitude()+" "+arg0.getLongitude()+" "+arg0.getBearing(), Toast.LENGTH_SHORT).show();
					//loadUrl("javascript:setCenter("+arg0.getLatitude()+","+arg0.getLongitude()+")");
					//loadUrl("javascript:setLocMarker("+arg0.getLatitude()+","+arg0.getLongitude()+","+100+")");
					locationUtil.stopLoc();
				}
			});
		//		locationUtil.requestGPSLoc();
		//		locationUtil.requestNetLoc();
		locationUtil.requestLoc();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, "onDestroy");
		mAdapter.destroy();
		mAdapter = null;
	}
}
