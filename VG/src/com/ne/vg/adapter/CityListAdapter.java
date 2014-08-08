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

public class CityListAdapter extends BaseAdapter{
	private final String TAG = "CityListAdapter";
	private List<City> listData = null;
	private LayoutInflater inflater = null;
	private Context mContext;
	int size = 0;

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

	public void setListData(List<City> list){
		if (list != null) {
			listData = list;
		}else {
			listData = new ArrayList<City>();
		}
		size = getCount();
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public City getItem(int position) {
		if(listData == null) return null;
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//TODO
		Holder holder = null;

		if (convertView == null) {// ����������ʹviewѭ�����ã��������ж��ٸ�item�Ͳ������ٸ�view
			holder = new Holder();
			convertView = inflater.inflate(R.layout.item_city, null);// ���ò����ļ�
			holder.cityName = (TextView)convertView.findViewById(R.id.cityName);	
			convertView.setTag(holder);// ������²�����view��������tag
		} 
		else
		{
			holder = (Holder) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
		}
		City myCity = listData.get(position);
		//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
		holder.cityName.setText(myCity.getCityName());

		return convertView;
	}

	final class Holder {
		public TextView cityName;
	}

	/**
	 * ����б���
	 * @param item
	 */
	public void addItem(City item) {
		this.listData.add(item);
	}

	public City getNearestCity(double lat,double longt)
	{
		double min=-1;
		int minIndex = 0;
		double distance = 0;
		City mCity ; 
		for(int i=0;i<size;i++)
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