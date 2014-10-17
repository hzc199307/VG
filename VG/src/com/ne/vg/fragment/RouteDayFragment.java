package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RecommendActivity;
import com.ne.vg.activity.RouteActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.RouteDay_BigSceneListAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.dao.VGDao;
import com.ne.vg.main.MainActivity;
import com.ne.vg.util.LogUtil;

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
	private CommonAdapter<BigScene> bigSceneListAdapter;

	int indexOfDay =-1;
	int routeContentID;
	private String[] bigSceneIDs;
	private List<BigScene> bigScenes;
	private VGDao mVgDao;
	private Intent mIntent;

	/**
	 * @param indexOfDay ������0��ʼ
	 */
	public RouteDayFragment(int indexOfDay, int routeContentID) {
		// TODO Auto-generated constructor stub
		this.indexOfDay = indexOfDay;
		this.routeContentID = routeContentID;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, "onAttach");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onAttach", Toast.LENGTH_SHORT).show();

	}

	/**
	 * �й����ݵķ�������
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO TMD ��Ҫ�е�д�ˡ���������
		/** ��Ҫ�ǻ�ȡ��Ӧÿ��ľ����б�����ȡ�����ݣ�bigSceneIDs���Զ��Ÿ�����bigSceneID�ļ��Ϸֿ�������*/
		mVgDao = VGDao.getInstance(getActivity());
		String sceneOfIndex = mVgDao.getRouteContent(routeContentID).getSceneOfIndex(indexOfDay+1);
		if(sceneOfIndex == null)
			bigSceneIDs = null;
		else
			bigSceneIDs = sceneOfIndex.split(",");
	}

	/**
	 * 
	 * @Title: ArrayToList 
	 * @Description: ������ת��ΪList
	 * @param @param sceneId
	 * @param @return
	 * @return List<BigScene> 
	 * @throws
	 */
	public List<BigScene> ArrayToList(String[] sceneId)
	{
		List<BigScene> scene = new ArrayList<BigScene>();
		for(int i =0; i <sceneId.length ; i++)
		{
			scene.add(mVgDao.getBigScene(Integer.parseInt(sceneId[i])));
		}
		return scene;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub  ������ͼ�Ķ���������Ū
		Log.v(TAG, "onCreateView");
		//Toast.makeText(getActivity(), TAG+ " "+i+" onCreateView", Toast.LENGTH_SHORT).show();


		rootView = inflater.inflate(R.layout.fragment_route_day, container,false);
		//		tv= (TextView)rootView.findViewById(R.id.tv);
		//		tv.setText(""+i);


		listView_route_day_bigscenes = (ListView)rootView.findViewById(R.id.listView_route_day_bigscenes);
		if(bigSceneListAdapter == null)
			//			bigSceneListAdapter = new RouteDay_BigSceneListAdapter(getActivity(),bigSceneIDs);
			bigScenes = ArrayToList(bigSceneIDs);
			bigSceneListAdapter = new CommonAdapter<BigScene>(getActivity(),bigScenes,R.layout.route_item_big_scene) {
			@Override
			public void convert(ViewHolder helper, BigScene myBigScene, int position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.bigSceneName, myBigScene.getBigSceneName());
				helper.setText(R.id.num1, ""+myBigScene.getLoveNum());
				helper.setText(R.id.num2, ""+myBigScene.getRecordNum());
			}
		};
		listView_route_day_bigscenes.setAdapter(bigSceneListAdapter);
		listView_route_day_bigscenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				mIntent = new Intent(getActivity(),BigSceneDetailActivity.class);
				LogUtil.d(TAG, bigScenes.get(position).getBigSceneID()+"notes");
				mIntent.putExtra("bigSceneID",bigScenes.get(position).getBigSceneID());
				startActivity(mIntent);
				//�����position�ǲ��Ǵ�0��ʼ��ģ�
				//				mIntent.putExtra("bigSceneID",bigSceneIDs[position]);
				//				startActivity(mIntent);
				//				((RouteActivity)getActivity()).destroyGMapFragment();
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
