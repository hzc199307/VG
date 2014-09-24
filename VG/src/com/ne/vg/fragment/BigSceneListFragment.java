package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.RouteActivity;
import com.ne.vg.adapter.BigSceneListAdapter;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;
import com.ne.vg.bean.SceneContent;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.NotifyUtil;



import android.app.Activity;
import android.content.Intent;
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
import android.widget.FrameLayout.LayoutParams;
/**
 * 
 * @ClassName: BigSceneListFragment 
 * @author ��ɼ
 * @Description: �󾰵��б��ҳ����gridview����ʾ�����Ƽ�activity��
 * @date 2014-8-12 ����3:19:24
 */
public class BigSceneListFragment extends Fragment{
	private final static String TAG="BigSceneListFragment";

	private GridView gridview;
	private int cityID; 
	private Intent mIntent;
	private VGDao mVgDao;
	//�Զ����������
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
		gridAdapter = new CommonAdapter<BigScene>(getActivity(), mVgDao.getBigScene(cityID),R.layout.item_bigscenelist) {

			@Override
			public void convert(ViewHolder helper, BigScene item) {
				// TODO ��Ҫ����download
				helper.setImageResource(R.id.bigscenelist_view01, item.getResource());
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
	 * @Description: TODO������Ҫ�޸ģ���Ϊ�����û�з�Ӧ
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO �����з�Ӧ��
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub				
				
				mIntent = new Intent(getActivity(),BigSceneDetailActivity.class);
				
				int bigSceneID = mVgDao.getBigScene(cityID).get(position).getBigSceneID();
				mIntent.putExtra("bigSceneID", bigSceneID);
				mIntent.putExtra("cityName", cityName);
				startActivity(mIntent);
			}
		});
	}
	
}
