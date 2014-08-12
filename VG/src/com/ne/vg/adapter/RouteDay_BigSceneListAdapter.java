package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteDay_BigSceneListAdapter extends BaseAdapter{
 
	private final String TAG = "RouteDay_BigSceneListAdapter";
	private List<BigScene> listData = null;
	private LayoutInflater inflater = null;

	int sizeOfBigScene = 0;

	public RouteDay_BigSceneListAdapter(Context context) {
		setListData(CreateData.getBigSceneList());
		inflater = LayoutInflater.from(context);
	}

	public RouteDay_BigSceneListAdapter(Context context,List<BigScene> list) {
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

		if (convertView == null) {// ����������ʹviewѭ�����ã��������ж��ٸ�item�Ͳ������ٸ�view
			Log.v(TAG, "convertView �ǿյ�");
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_bigsene_routeday, null);// ���ò����ļ�
			viewHolder.bigSceneName = (TextView)convertView.findViewById(R.id.bigSceneName);	
			convertView.setTag(viewHolder);// ������²�����view��������tag
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
		}
		BigScene myBigScene = listData.get(position);
		//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
		viewHolder.bigSceneName.setText(myBigScene.getBigSceneName());
		return convertView;
	}

	class ViewHolder {
		public TextView bigSceneName;
	}


	/**
	 * ����б���
	 * @param item
	 */
	public void addItem(BigScene item) {
		this.listData.add(item);
	}
}
