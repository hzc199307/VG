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

		if (convertView == null) {// 这样做可以使view循环利用，而不会有多少个item就产生多少个view
			holder = new Holder();
			convertView = inflater.inflate(R.layout.item_city, null);// 引用布局文件
			holder.cityName = (TextView)convertView.findViewById(R.id.cityName);	
			convertView.setTag(holder);// 如果是新产生的view，则设置tag
		} 
		else
		{
			holder = (Holder) convertView.getTag();// 如果是使用已经存在的view，则从tag中获取就可以了
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
	 * 添加列表项
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