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
import android.widget.Toast;
@Deprecated
/**
 * 首页的city列表适配器 
 * 有1种布局显示
 * @ClassName: CityListAdapter 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-11 下午3:53:34
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
		mContext = context;
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

		Log.v(TAG, "getView");
		//TODO
		ViewHolderData viewHolderData = null;

		if (convertView == null) {// 这样做可以使view循环利用，而不会有多少个item就产生多少个view
			
			viewHolderData = new ViewHolderData();
			convertView = inflater.inflate(R.layout.item_city, null);// 引用布局文件
			viewHolderData.cityName = (TextView)convertView.findViewById(R.id.cityName);
			viewHolderData.cityPinyin = (TextView)convertView.findViewById(R.id.cityPinyin);
			viewHolderData.item_city_bg = (ImageView)convertView.findViewById(R.id.item_city_bg);
			convertView.setTag(viewHolderData);// 如果是新产生的view，则设置tag
		} 
		else
		{
			viewHolderData = (ViewHolderData) convertView.getTag();// 如果是使用已经存在的view，则从tag中获取就可以了
		}

		City myCity = listData.get(position);
		viewHolderData.cityName.setText(myCity.getCityName());
		
		
		
		Log.d(TAG, "cityName=" + myCity.getCityName());
		Log.d(TAG, "cityID:"+myCity.getCityID());
		
		viewHolderData.cityPinyin.setText(myCity.getCityPinyin());
		int resId = mContext.getResources().getIdentifier(myCity.getResource() ,"drawable","com.ne.vg");
		if(resId>0)
			viewHolderData.item_city_bg.setImageResource(resId);

		return convertView;
	}

	class ViewHolderData {
		public TextView cityName,cityPinyin;
		public ImageView item_city_bg;
	}

	/**
	 * 添加列表项
	 * @param item
	 */
	public void addItem(City item) {
		this.listData.add(item);
	}

	/**
	 * 获取最近的城市
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