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
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.RecommendRoute;
import com.ne.vg.dao.VGDao;
/**
 * 
 * @ClassName: RecommendRouteFragment 
 * @author ��ɼ
 * @Description: �Ƽ�·�ߵ�ҳ����listview����ʾ�����Ƽ�activity��
 * @date 2014-8-12 ����3:44:46
 */
public class RecommendRouteFragment extends Fragment{
	private static final String TAG = "RecommendRouteFragment";
	//ListView�ؼ�
	private ListView listview;
	//�Զ����������
	private CommonAdapter<RecommendRoute> gridAdapter;
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
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_recommendroute,
				container, false);
		//Init view
		listview = (ListView) rootView.findViewById(R.id.recommendroute_listview);
		
		
		
		//TODO �����������Ҫת�������ڶ���������ΪmVgDao.getRecommendRoute(cityID)�Ǹ�
		mVgDao = new VGDao(getActivity());
		//gridAdapter = new RecommendRouteAdapter(getActivity(), mVgDao.getRecommendRoute(cityID));
		gridAdapter = new CommonAdapter<RecommendRoute>(getActivity(), mVgDao.getRecommendRoute(cityID),R.layout.item_recommendroute) {

			@Override
			public void convert(ViewHolder helper, RecommendRoute item) {
				helper.setImageResource(R.id.recommendroute_view01, item.getResource());
				helper.setText(R.id.recommendroute_sceneNum, Integer.toString(item.getSceneNum()));
				helper.setText(R.id.recommendroute_name, item.getRouteName());
				helper.setText(R.id.recommendroute_scenenum, item.getRouteDay()+ "��");
				helper.setText(R.id.recommendroute_lovenum, Integer.toString(item.getCollectNum()));
				//TODO click����ӦĿǰ�Ȳ�д
			}
		};
		
		
		listview.setAdapter(gridAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//TODO ���������Լ��޸ĵĲ��֣���������startActivity
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
