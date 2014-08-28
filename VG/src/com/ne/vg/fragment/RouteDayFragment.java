package com.ne.vg.fragment;

import com.ne.vg.MainActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.activity.RouteActivity;
import com.ne.vg.adapter.RouteDay_BigSceneListAdapter;
import com.ne.vg.dao.VGDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 路线的一天的大景点列表详情页
 * @ClassName: RouteDayFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:19:08
 */
public class RouteDayFragment extends Fragment {

	private final static String TAG = "RouteDayFragment";
	private View rootView;
	private TextView tv;
	private ListView listView_route_day_bigscenes;
	private RouteDay_BigSceneListAdapter bigSceneListAdapter;

	int i =-1;
	int routeContentID;
	private String[] bigSceneIDs;
	private VGDao mVgDao;
	private Intent mIntent;
	
	public RouteDayFragment(int i, int routeContentID) {
		// TODO Auto-generated constructor stub
		this.i = i;
		this.routeContentID = routeContentID;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, "onAttach");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onAttach", Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * 有关数据的放在这里
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO TMD 这要有的写了。。。。。
		/** 主要是获取对应每天的景点列表所获取的数据，bigSceneIDs是以逗号隔开的bigSceneID的集合分开的数组*/
		mVgDao = new VGDao(getActivity());
		switch(i){
		case 0:
			if(mVgDao.getRouteContent(routeContentID).getFirstScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getFirstScene().split(",");
			}
			
			break;
		case 1:
			if(mVgDao.getRouteContent(routeContentID).getSecondScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getSecondScene().split(",");
			}
		
			break;
		case 2:
			if(mVgDao.getRouteContent(routeContentID).getThirdScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getThirdScene().split(",");
			}
			break;
		case 3:
			if(mVgDao.getRouteContent(routeContentID).getFourthScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getFourthScene().split(",");
			}
			break;
		case 4:
			if(mVgDao.getRouteContent(routeContentID).getFifthScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getFifthScene().split(",");
			}
			break;
		case 5:
			if(mVgDao.getRouteContent(routeContentID).getFifthScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getFifthScene().split(",");
			}
			break;
		case 6:
			if(mVgDao.getRouteContent(routeContentID).getSeventhScene()==null){
				bigSceneIDs = null;
			}else{
				bigSceneIDs = mVgDao.getRouteContent(routeContentID).getSeventhScene().split(",");
			}
			break;
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub  关于视图的都放在这里弄
		Log.v(TAG, "onCreateView");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onCreateView", Toast.LENGTH_SHORT).show();

		if(bigSceneListAdapter == null)
			bigSceneListAdapter = new RouteDay_BigSceneListAdapter(getActivity(),bigSceneIDs);
		rootView = inflater.inflate(R.layout.fragment_route_day, container,false);
//		tv= (TextView)rootView.findViewById(R.id.tv);
//		tv.setText(""+i);

		listView_route_day_bigscenes = (ListView)rootView.findViewById(R.id.listView_route_day_bigscenes);
		listView_route_day_bigscenes.setAdapter(bigSceneListAdapter);
		listView_route_day_bigscenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				mIntent = new Intent(getActivity(),BigSceneDetailActivity.class);
				//这里的position是不是从0开始算的？
				mIntent.putExtra("bigSceneID",bigSceneIDs[position]);
				startActivity(mIntent);
				((RouteActivity)getActivity()).destroyGMapFragment();
			}
		});
		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onDestroyView");
		super.onDestroyView();
		//Toast.makeText(getActivity(), TAG+ " "+i+" onDestroyView", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onDestroy", Toast.LENGTH_SHORT).show();
		bigSceneListAdapter.destroy();
		bigSceneListAdapter = null;
	}
}
