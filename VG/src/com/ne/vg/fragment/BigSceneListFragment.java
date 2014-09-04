package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RouteActivity;
import com.ne.vg.adapter.BigSceneListAdapter;
import com.ne.vg.bean.CreateData;
import com.ne.vg.bean.SceneContent;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.NotifyUtil;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 
 * @ClassName: BigSceneListFragment 
 * @author 潘杉
 * @Description: 大景点列表的页面以gridview来显示，在推荐activity中
 * @date 2014-8-12 下午3:19:24
 */
public class BigSceneListFragment extends Fragment{
	private final static String TAG="BigSceneListFragment";

	private GridView gridview;
	private int cityID; 
	private Intent mIntent;
	private VGDao mVgDao;
	//自定义的适配器
	private BigSceneListAdapter gridAdapter;
	private String cityName;
	public BigSceneListFragment(int cityID){
		Log.d(TAG, cityID+"");
		
		this.cityID = cityID;
		
	}


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mVgDao = new VGDao(getActivity());
		
		this.cityName = mVgDao.getCityName(cityID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_bigscenelist,
				container, false);
		//Init view
		gridview = (GridView) rootView.findViewById(R.id.bigscene_gridview1);
		
		gridAdapter = new BigSceneListAdapter(getActivity(), mVgDao.getBigScene(cityID));
		gridview.setAdapter(gridAdapter);
		InitListener();
		return rootView;

	}
	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: TODO这里需要修改，因为点击后没有反应
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO 现在有反应了
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub				
				Toast.makeText(getActivity().getApplicationContext(), "pic" + (position+1), Toast.LENGTH_SHORT).show();
				mIntent = new Intent(getActivity(),BigSceneDetailActivity.class);
				
				int bigSceneID = mVgDao.getBigScene(cityID).get(position).getBigSceneID();
				mIntent.putExtra("bigSceneID", bigSceneID);
				mIntent.putExtra("cityName", cityName);
				startActivity(mIntent);
			}
		});
	}
	
}
