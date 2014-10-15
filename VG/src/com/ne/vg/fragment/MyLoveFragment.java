package com.ne.vg.fragment;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.adapter.BigSceneListAdapter;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.MyLoveAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;
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
import android.widget.Switch;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyLoveFragment extends Fragment{
	private final static String TAG="MyLoveFragment";

	private GridView gridview;
	//自定义的适配器
	private MyLoveAdapter gridAdapter;
	private List<BigScene> bigScenes;
	
	public MyLoveFragment(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_mylove,
				container, false);
		gridview = (GridView) rootView.findViewById(R.id.mylove_gridview1);
		initGridview();
		return rootView;

	}

	/**
	 * 
	 * @Title: initGridview 
	 * @Description: TODO这里需要修改，因为点击后没有反应
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void initGridview() {
		// TODO Auto-generated method stub
		bigScenes = VGDao.getInstance(getActivity()).getCollectedBigSceneList();
		gridview.setAdapter(new CommonAdapter<BigScene>(getActivity(), bigScenes, R.layout.item_mylove) {

			@Override
			public void convert(ViewHolder helper, BigScene item, int position) {
				// TODO Auto-generated method stub
				Log.d(TAG, item.getCityID()+" "+VGDao.getInstance(getActivity()).getCityName(item.getCityID())+"/"+item.getResource());
				helper.setImageBitmap(R.id.item_mylove_bg, ImageUtil.getBigSceneResourceStr(VGDao.getInstance(getActivity()).getCityName(item.getCityID())+"/"+item.getResource()));
				helper.setText(R.id.item_mylove_scenename, item.getBigSceneName());
				helper.setView(R.id.item_mylove_star1,View.GONE);
				helper.setView(R.id.item_mylove_star2,View.GONE);
				helper.setView(R.id.item_mylove_star3,View.GONE);
				helper.setView(R.id.item_mylove_star4,View.GONE);
				helper.setView(R.id.item_mylove_star5,View.GONE);
				switch (item.getLevel()) {
				case 5:
					helper.setView(R.id.item_mylove_star5, View.VISIBLE);
				case 4:
					helper.setView(R.id.item_mylove_star4, View.VISIBLE);
				case 3:
					helper.setView(R.id.item_mylove_star3, View.VISIBLE);
				case 2:
					helper.setView(R.id.item_mylove_star2, View.VISIBLE);
				case 1:
					helper.setView(R.id.item_mylove_star1, View.VISIBLE);
				default:
					break;
				}
				
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),BigSceneDetailActivity.class); 
				myIntent.putExtra("bigSceneID", bigScenes.get(position).getBigSceneID());
				startActivity(myIntent);
			}
		});
	}

}
