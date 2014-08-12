package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.bean.City;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ��ҳ��city�б������� 
 * ��2�ֲ�����ʾ
 * @ClassName: CityListAdapter 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-11 ����3:53:34
 */
public class CityListAdapter extends BaseAdapter{
	
	private static final int TYPE_HEAD = 0,TYPE_DATA = 1;  
	private final String TAG = "CityListAdapter";
	private List<City> listData = null;
	private LayoutInflater inflater = null;
	private Context mContext;
	int sizeOfCity = 0;

	//	public CityListAdapter(Context context) {
	//		mContext = context;
	//		CitySceneDao mCitySceneDao = new CitySceneDao(context);
	//		setListData(mCitySceneDao.getCityBeans());
	//		inflater = LayoutInflater.from(context);
	//		Log.v(TAG, listData.size()+"");
	//	}

	public CityListAdapter(Context context,List<City> list) {
		setListData(list);
		inflater = LayoutInflater.from(context);
	}
	
	public void destroy() {
		listData.clear();
		listData=null;
	}

	public void setListData(List<City> list){
		if (list != null) {
			listData = list;
		}else {
			listData = new ArrayList<City>();
		}
		sizeOfCity = listData.size();
	}
	@Override
	public int getCount() {
		return listData.size()+1;
	}

	@Override
	public City getItem(int position) {
		if(listData == null) return null;
		return listData.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * ��������Դ��position������Ҫ��ʾ�ĵ�layout��type
	 * 
	 * type��ֵ�����0��ʼ
	 * 
	 * */
	@Override
	public int getItemViewType(int position) {

		if(position ==0)
			return TYPE_HEAD;
		else
			return TYPE_DATA;
	}

	/**
	 * �������е�layout������
	 * 
	 * */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int type = getItemViewType(position);
		//TODO
		ViewHolderData viewHolderData = null;
		ViewHolderHead viewHolderHead = null;

		if (convertView == null) {// ����������ʹviewѭ�����ã��������ж��ٸ�item�Ͳ������ٸ�view
			Log.v(TAG, "convertView �ǿյ� ��type = "+type);
			switch(type)
			{
			case TYPE_DATA:
				viewHolderData = new ViewHolderData();
				convertView = inflater.inflate(R.layout.item_city, null);// ���ò����ļ�
				viewHolderData.cityName = (TextView)convertView.findViewById(R.id.cityName);	
				convertView.setTag(viewHolderData);// ������²�����view��������tag
				break;
			case TYPE_HEAD:
				viewHolderHead = new ViewHolderHead();
				convertView = inflater.inflate(R.layout.item_city_head, null);// ���ò����ļ�
				viewHolderHead.tv_locationEnter = (TextView)convertView.findViewById(R.id.tv_locationEnter);	
				viewHolderHead.tv_randomEnter = (TextView)convertView.findViewById(R.id.tv_randomEnter);
				convertView.setTag(viewHolderHead);// ������²�����view��������tag
				break;
			}
		} 
		else
		{
			switch(type)
			{
			case TYPE_DATA:
				viewHolderData = (ViewHolderData) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
				break;
			case TYPE_HEAD:
				viewHolderHead = (ViewHolderHead) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
				break;
			}
			
		}
		
		switch(type)
		{
		case TYPE_DATA:
			City myCity = listData.get(position-1);
			viewHolderData.cityName.setText(myCity.getCityName());
			//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
			break;
		case TYPE_HEAD:
			viewHolderHead = (ViewHolderHead) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
			break;
		}
		return convertView;
	}

	class ViewHolderData {
		public TextView cityName;
	}
	class ViewHolderHead {
		public TextView tv_locationEnter,tv_randomEnter;
	}

	/**
	 * ����б���
	 * @param item
	 */
	public void addItem(City item) {
		this.listData.add(item);
	}

	/**
	 * ��ȡ����ĳ���
	 * @param lat
	 * @param longt
	 * @return
	 */
	public City getNearestCity(double lat,double longt)
	{
		double min=-1;
		int minIndex = 0;
		double distance = 0;
		City mCity ; 
		for(int i=0;i<sizeOfCity;i++)
		{
			mCity = listData.get(i);
			distance = Math.sqrt((lat-mCity.getLatitude())*(lat-mCity.getLatitude())
					+(longt-mCity.getLongtitude())*(longt-mCity.getLongtitude()));
			if(distance<min)
			{
				minIndex = i;
				min = distance;
			}
		}
		return listData.get(minIndex);
	}


}