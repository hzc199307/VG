package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.ImageUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
/**
 * 
 * @ClassName: BigSceneListFragment 
 * @author 潘杉
 * @Description: 大景点列表的页面以gridview来显示，在推荐activity中
 * @date 2014-8-12 下午3:19:24
 */
public class BigSceneListFragment extends Fragment{
	private final static String TAG="BigSceneListFragment";

	private GridView gridview;
	private int cityID; 
	private Intent mIntent;
	private VGDao mVgDao;
	//自定义的适配器
	private CommonAdapter<BigScene> gridAdapter;
	private String cityName;
	public BigSceneListFragment(int cityID){
		Log.d(TAG, cityID+"");
		
		this.cityID = cityID;
		
	}


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mVgDao = new VGDao(getActivity());
		
		this.cityName = mVgDao.getCityName(cityID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_bigscenelist,
				container, false);
		//Init view
		gridview = (GridView) rootView.findViewById(R.id.bigscene_gridview1);
		
		//gridAdapter = new BigSceneListAdapter(getActivity(), mVgDao.getBigScene(cityID));\
		gridAdapter = new CommonAdapter<BigScene>(getActivity(), mVgDao.getBigSceneList(cityID),R.layout.item_bigscenelist) {

			@Override
			public void convert(ViewHolder helper, BigScene item, int position) {
				// TODO 还要处理download
				helper.setImageBitmap(R.id.bigscenelist_view01, ImageUtil.getBigSceneResourceStr(cityName+"/"+item.getResource()));
//				helper.setImageResource(R.id.bigscenelist_view01, item.getResource());
				helper.setText(R.id.bigscenelist_item_name, item.getBigSceneName());
				helper.setText(R.id.bigscenelist_loveNum, Integer.toString(item.getLoveNum()));
				helper.setText(R.id.bigscenelist_musicNum, Integer.toString(item.getRecordNum()));
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
				if(item.isDowned()==false){
					helper.getView(R.id.bigscenelist_item_shadow).setLayoutParams( params);
				}
			
			}
		};
		gridview.setAdapter(gridAdapter);
		InitListener();
		return rootView;

	}
	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: TODO这里需要修改，因为点击后没有反应
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO 现在有反应了
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub				
				
				mIntent = new Intent(getActivity(),BigSceneDetailActivity.class);
				
				int bigSceneID = mVgDao.getBigSceneList(cityID).get(position).getBigSceneID();
				mIntent.putExtra("bigSceneID", bigSceneID);
				startActivity(mIntent);
			}
		});
	}
	
}
