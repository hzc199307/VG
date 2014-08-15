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
	//保存数据的链表
	private List<Map<String, Object>> mData;
	
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
	 * @Description: 初始化监听器Listener,用来监听List中的Item
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
				// TODO 这里是写跳转函数，跳转到其他页面。
				Toast.makeText(getActivity().getApplicationContext(), "pic" + (position+1), Toast.LENGTH_SHORT).show(); 
			}

		});
	}
	/**
	 * 
	 * @Title: getData 
	 * @Description: 获取数据到List中
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
			map.put("img",com.ne.vg.R.drawable.test_haimian);
			map.put("sceneNum", "9景点");
			map.put("routeName","首尔必游之地 3天");
			map.put("collectNum","34562收藏");
			list.add(map);
		}

		return list;
	}
}
