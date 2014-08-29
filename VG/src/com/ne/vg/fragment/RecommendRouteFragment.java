package com.ne.vg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.ne.vg.R;
import com.ne.vg.activity.RouteActivity;
import com.ne.vg.adapter.RecommendRouteAdapter;
import com.ne.vg.bean.CreateData;
import com.ne.vg.dao.VGDao;
/**
 * 
 * @ClassName: RecommendRouteFragment 
 * @author 潘杉
 * @Description: 推荐路线的页面以listview来显示，在推荐activity中
 * @date 2014-8-12 下午3:44:46
 */
public class RecommendRouteFragment extends Fragment{
	private static final String TAG = "RecommendRouteFragment";
	//ListView控件
	private ListView listview;
	//自定义的适配器
	private RecommendRouteAdapter gridAdapter;
	private int cityID;
	private VGDao mVgDao;
	private Intent mIntent;
	
	public RecommendRouteFragment(int cityID){
		this.cityID = cityID;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_recommendroute,
				container, false);
		//Init view
		listview = (ListView) rootView.findViewById(R.id.recommendroute_listview);
		
		
		
		//TODO 这里的数据需要转换，即第二个参数改为mVgDao.getRecommendRoute(cityID)那个
		mVgDao = new VGDao(getActivity());
		gridAdapter = new RecommendRouteAdapter(getActivity(), mVgDao.getRecommendRoute(cityID));
		listview.setAdapter(gridAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//TODO 下面是我自己修改的部分，带参数的startActivity
				mIntent = new Intent(getActivity(),RouteActivity.class);
				int routeID = mVgDao.getRecommendRoute(cityID).get(position).getRouteID();
				mIntent.putExtra("routeID", routeID);
				startActivity(mIntent);
			}
		});
		super.onStart();
		return rootView;
		
	}
	
	
}
