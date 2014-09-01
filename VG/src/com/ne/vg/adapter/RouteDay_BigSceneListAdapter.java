package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;
import com.ne.vg.dao.VGDao;

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
	private String[] bigSceneIDs;
	int sizeOfBigScene = 0;
	private VGDao mVgDao;

	public RouteDay_BigSceneListAdapter(Context context,String[] bigSceneIDs) {
		//setListData(CreateData.getBigSceneList());
		mVgDao = new VGDao(context);
		inflater = LayoutInflater.from(context);
		this.bigSceneIDs = bigSceneIDs;
		Log.d(TAG,"bigScene size is " + bigSceneIDs.length);
	}

//	public RouteDay_BigSceneListAdapter(Context context,List<BigScene> list) {
//		setListData(list);
//		inflater = LayoutInflater.from(context);
//	}
	
	public void destroy() {
		mVgDao.closeDatabase();
		bigSceneIDs=null;
	}

//	public void setListData(List<BigScene> list){
//		if (list != null) {
//			listData = list;
//		}else {
//			listData = new ArrayList<BigScene>();
//		}
//		sizeOfBigScene = listData.size();
//	}
	
	@Override
	public int getCount() {
		return bigSceneIDs.length;
	}

	@Override
	public BigScene getItem(int position) {
		if(bigSceneIDs.length == 0) return null;
		return mVgDao.getRecommendSceneContent(Integer.parseInt(bigSceneIDs[position]));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * ��������ݴ���Ҫ��bigSceneIDs[]�й�
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (convertView == null) {// ����������ʹviewѭ�����ã��������ж��ٸ�item�Ͳ������ٸ�view
			//Log.v(TAG, "convertView �ǿյ�");
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.route_item_big_scene, null);// ���ò����ļ�
			viewHolder.bigSceneName = (TextView)convertView.findViewById(R.id.bigSceneName);
			viewHolder.loveNum = (TextView)convertView.findViewById(R.id.num1);
			viewHolder.recordNum = (TextView)convertView.findViewById(R.id.num2);
			convertView.setTag(viewHolder);// ������²�����view��������tag
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
		}
		//��ȡBigScene.ֻ������������
		Log.d(TAG, "postion="+ position);
		Log.d(TAG, "bigSceneId=" + Integer.parseInt(bigSceneIDs[position]));
		BigScene myBigScene = mVgDao.getRecommendSceneContent(Integer.parseInt(bigSceneIDs[position]));
		//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
		viewHolder.bigSceneName.setText(myBigScene.getBigSceneName());
		viewHolder.loveNum.setText(""+myBigScene.getLoveNum());
		viewHolder.recordNum.setText(""+myBigScene.getRecordNum());
		return convertView;
	}

	class ViewHolder {
		public TextView bigSceneName;
		public TextView loveNum;
		public TextView recordNum;
	}


	/**
	 * ����б���
	 * @param item
	 */
	public void addItem(BigScene item) {
		this.listData.add(item);
	}
}
