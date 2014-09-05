package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.MainActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.adapter.CityListAdapter;
import com.ne.vg.bean.City;
import com.ne.vg.bean.CreateData;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.LocationUtil;
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
public class HomeFragment extends Fragment implements OnClickListener{

	private final static String TAG = "HomeFragment";

	private ListView lv_city;
	private CityListAdapter cityListAdapter ;
	private View home_title_search_btn,home_title_left;

	private Intent intent;
	private VGDao mVgDao;

//	private ProgressDialog progressDialog;
	private View home_tab_locationEnter,home_tab_randomEnter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		progressDialog = new ProgressDialog(getActivity());
//		progressDialog.setMessage(getString(R.string.loading));
//		progressDialog.setCancelable(false);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		Log.v(TAG, "onCreateView");
		mVgDao = new VGDao(getActivity());
		View view  = inflater.inflate(R.layout.fragment_home, container,false);
		lv_city = (ListView)view.findViewById(R.id.lv_city);


		if(cityListAdapter==null)
			cityListAdapter = new CityListAdapter(getActivity(), mVgDao.getCity());


		lv_city.setAdapter(cityListAdapter);

		lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "item" + position + "is clicked");
				intent = new Intent(getActivity(),RecommendActivity.class);
				City city = mVgDao.getCity().get(position);
				intent.putExtra("cityID", city.getCityID());

				Log.d(TAG, "cityID:"+city.getCityID());

				startActivity(intent);
				((MainActivity)getActivity()).destroyAllFragmentWithoutNow();
			}
		});
		home_title_left = (View)view.findViewById(R.id.home_title_left);
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
		if(home_title_left!=null)
			home_title_left.startAnimation(animation);
	}

	/**
	 * 关闭标题栏动画
	 */
	public void clearAnimation() {
		if(home_title_left!=null&&home_title_left.getAnimation()!=null)
			home_title_left.clearAnimation();
	}

}
