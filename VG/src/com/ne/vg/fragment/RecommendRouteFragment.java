package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ne.vg.R;
import com.ne.vg.adapter.RecommendRouteAdapter;

public class RecommendRouteFragment extends Fragment{
	//ListView�ؼ�
	private ListView listview;
	//�Զ����������
	private RecommendRouteAdapter gridAdapter;
	//�������ݵ�����
	private List<Map<String, Object>> mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_recommendroute,
				container, false);
		//Init view
		listview = (ListView) rootView.findViewById(R.id.recommendroute_listview);
		InitListener();
		mData = getData();
		gridAdapter = new RecommendRouteAdapter(getActivity(), mData);
		listview.setAdapter(gridAdapter);
		super.onStart();
		return rootView;
		
	}
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: ��ʼ��������Listener,��������List�е�Item
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(), "pic" + (position+1), Toast.LENGTH_SHORT).show(); 
			}

		});
	}
	/**
	 * 
	 * @Title: getData 
	 * @Description: ��ȡ���ݵ�List��
	 * @param @return
	 * @return List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map;
		for(int i=0;i<10;i++){
			map = new HashMap<String, Object>();
			map.put("img",com.ne.vg.R.drawable.haimian);
			map.put("sceneNum", "9����");
			map.put("routeName","�׶�����֮�� 3��");
			map.put("collectNum","34562�ղ�");
			list.add(map);
		}

		return list;
	}
}
