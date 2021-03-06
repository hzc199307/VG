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
import com.ne.vg.adapter.CommonRRAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.RecommendRoute;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.ImageUtil;
import com.ne.vg.util.LogUtil;
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



		//TODO 这里的数据需要转换，即第二个参数改为mVgDao.getRecommendRoute(cityID)那个
		mVgDao = VGDao.getInstance(getActivity());
		//gridAdapter = new RecommendRouteAdapter(getActivity(), mVgDao.getRecommendRouteList(cityID));
		//		gridAdapter = new CommonAdapter<RecommendRoute>(getActivity(), mVgDao.getRecommendRouteList(cityID),R.layout.item_recommendroute) {
		//
		//			@Override
		//			public void convert(ViewHolder helper, RecommendRoute item, int position) {
		//				helper.setImageResource(R.id.recommendroute_view01, item.getResource());
		//				helper.setText(R.id.recommendroute_sceneNum, Integer.toString(item.getSceneNum()));
		//				helper.setText(R.id.recommendroute_name, item.getRouteName());
		//				helper.setText(R.id.recommendroute_scenenum, item.getRouteDay()+ "天");
		//				helper.setText(R.id.recommendroute_lovenum, Integer.toString(item.getCollectNum()));
		//			}
		//		};

		gridAdapter = new CommonRRAdapter(getActivity(), mVgDao.getRecommendRouteList(cityID), R.layout.item_recommendroute){

			@Override
			public void convert(ViewHolder helper, RecommendRoute item,
					int position) {
				helper.setImageBitmap(R.id.recommendroute_bg, ImageUtil.getRouteResourceStr(mVgDao.getCityName(cityID)+"/"+item.getResource()));
//				helper.setImageResource(R.id.recommendroute_bg, item.getResource());
				helper.setText(R.id.recommendroute_sceneNum, Integer.toString(item.getSceneNum()));
				helper.setText(R.id.recommendroute_name, item.getRouteName());
				helper.setText(R.id.recommendroute_scenenum, item.getRouteDay()+ "天");
				helper.setText(R.id.recommendroute_lovenum, Integer.toString(item.getCollectNum()));
				boolean isCollected = VGDao.getInstance(getContext()).getRecommendRouteCollected(item.getRouteID());
				helper.setImageSelected(R.id.recommendroute_love_iv, isCollected);

			}

			@Override
			public void aboutOnClickListener(ViewHolder helper,
					MyOnClickListener myOnClickListener) {
				// TODO Auto-generated method stub
				helper.setOnClickListener(R.id.recommendroute_love, myOnClickListener);
			}

			@Override
			public void onLoveClick(ViewHolder helper,
					RecommendRoute recommendRoute) {
				// TODO Auto-generated method stub
				boolean isCollected = VGDao.getInstance(getContext()).getRecommendRouteCollected(recommendRoute.getRouteID());
				helper.setImageSelected(R.id.recommendroute_love_iv, (isCollected==false));
				VGDao.getInstance(getContext()).setRecommendRouteCollected(recommendRoute.getRouteID(), (isCollected==false)?1:0);
			}
		};

		listview.setAdapter(gridAdapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//TODO 下面是我自己修改的部分，带参数的startActivity
				mIntent = new Intent(getActivity(),RouteActivity.class);
				int routeID = mVgDao.getRecommendRouteList(cityID).get(position).getRouteID();
				mIntent.putExtra("routeID", routeID);
				startActivity(mIntent);
			}
		});
		super.onStart();
		return rootView;

	}


}
