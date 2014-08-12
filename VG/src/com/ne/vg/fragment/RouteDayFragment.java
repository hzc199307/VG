package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.adapter.RouteDay_BigSceneListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	public RouteDayFragment(int i) {
		// TODO Auto-generated constructor stub
		this.i = i;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Toast.makeText(getActivity(), TAG+ " "+i+" onAttach", Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * 有关数据的放在这里
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bigSceneListAdapter = new RouteDay_BigSceneListAdapter(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub  关于视图的都放在这里弄
		Toast.makeText(getActivity(), TAG+ " "+i+" onCreateView", Toast.LENGTH_SHORT).show();

		rootView = inflater.inflate(R.layout.fragment_route_day, container,false);
		tv= (TextView)rootView.findViewById(R.id.tv);
		tv.setText(""+i);

		listView_route_day_bigscenes = (ListView)rootView.findViewById(R.id.listView_route_day_bigscenes);
		listView_route_day_bigscenes.setAdapter(bigSceneListAdapter);
		return rootView;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Toast.makeText(getActivity(), TAG+ " "+i+" onDestroyView", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		bigSceneListAdapter.destroy();
	}
}
