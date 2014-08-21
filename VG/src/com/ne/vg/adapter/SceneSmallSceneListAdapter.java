package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ne.vg.R;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;


public class SceneSmallSceneListAdapter extends BaseAdapter{
	 
	private final String TAG = "RouteDay_BigSceneListAdapter";
	private List<BigScene> listData = null;
	private LayoutInflater inflater = null;

	int sizeOfBigScene = 0;

	public SceneSmallSceneListAdapter(Context context) {
		setListData(CreateData.getBigSceneList());
		inflater = LayoutInflater.from(context);
	}

	public SceneSmallSceneListAdapter(Context context,List<BigScene> list) {
		setListData(list);
		inflater = LayoutInflater.from(context);
	}
	
	public void destroy() {
		listData.clear();
		listData=null;
	}

	public void setListData(List<BigScene> list){
		if (list != null) {
			listData = list;
		}else {
			listData = new ArrayList<BigScene>();
		}
		sizeOfBigScene = listData.size();
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public BigScene getItem(int position) {
		if(listData == null) return null;
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (convertView == null) {// 这样做可以使view循环利用，而不会有多少个item就产生多少个view
			//Log.v(TAG, "convertView 是空的");
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.scene_item_small_scene, null);// 引用布局文件
			viewHolder.scene_item_smallscene_name = (TextView)convertView.findViewById(R.id.scene_item_smallscene_name);	
			convertView.setTag(viewHolder);// 如果是新产生的view，则设置tag
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();// 如果是使用已经存在的view，则从tag中获取就可以了
		}
		BigScene myBigScene = listData.get(position);
		//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
		viewHolder.scene_item_smallscene_name.setText(myBigScene.getBigSceneName());
		return convertView;
	}

	class ViewHolder {
		public TextView scene_item_smallscene_name;
	}


	/**
	 * 添加列表项
	 * @param item
	 */
	public void addItem(BigScene item) {
		this.listData.add(item);
	}
}