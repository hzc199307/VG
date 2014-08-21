package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.adapter.RouteDay_BigSceneListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ·�ߵ�һ��Ĵ󾰵��б�����ҳ
 * @ClassName: RouteDayFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-12 ����3:19:08
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
		Log.v(TAG, "onAttach");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onAttach", Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * �й����ݵķ�������
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub  ������ͼ�Ķ���������Ū
		Log.v(TAG, "onCreateView");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onCreateView", Toast.LENGTH_SHORT).show();

		if(bigSceneListAdapter == null)
			bigSceneListAdapter = new RouteDay_BigSceneListAdapter(getActivity());
		rootView = inflater.inflate(R.layout.fragment_route_day, container,false);
//		tv= (TextView)rootView.findViewById(R.id.tv);
//		tv.setText(""+i);

		listView_route_day_bigscenes = (ListView)rootView.findViewById(R.id.listView_route_day_bigscenes);
		listView_route_day_bigscenes.setAdapter(bigSceneListAdapter);
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
