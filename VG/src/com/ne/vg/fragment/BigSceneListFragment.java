package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import com.ne.vg.adapter.BigSceneListAdapter;



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

public class BigSceneListFragment extends Fragment{
	
	private GridView gridview;
	//�Զ����������
	private BigSceneListAdapter gridAdapter;
	//�������ݵ�����
	private List<Map<String, Object>> mData;
	
	
	public BigSceneListFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		View rootView = inflater.inflate(R.layout.fragment_bigscenelist,
				container, false);
		//Init view
		gridview = (GridView) rootView.findViewById(R.id.bigscene_gridview1);
		InitListener();
		return rootView;
		
	}
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: TODO������Ҫ�޸ģ���Ϊ�����û�з�Ӧ
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO Auto-generated method stub
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(), "pic" + (position+1), Toast.LENGTH_SHORT).show(); 
			}

		});
	}
	
	@Override
	public void onStart(){
		mData = getData();
		gridAdapter = new BigSceneListAdapter(getActivity(), mData);
		gridview.setAdapter(gridAdapter);
		super.onStart();
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
			map.put("img",R.drawable.haimian);
			map.put("download", "����");
			map.put("sceneName","������");
			map.put("loveNum","12035");
			map.put("recordNum","18");
			list.add(map);
		}

		return list;
	}
	
	


}