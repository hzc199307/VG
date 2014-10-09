package com.ne.vg.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ne.vg.R;
import com.ne.vg.bean.City;
import com.ne.vg.view.RoundProgressBar;

@Deprecated
public class MineDownloadCityAdapter extends BaseAdapter {

	//LayoutInflater作用是将layout的xml布局文件实例化为View类对象。
	private LayoutInflater mInflater;
	//存储数据
	List<City> mData;
	private Context mContext;

	public MineDownloadCityAdapter(Context context, List<City> mData)
	{
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
		mContext = context;

	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public City getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.mine_download_item_city, null);
			holder = new Holder();
			holder.mine_download_item_city_bg = (ImageView)convertView.findViewById(R.id.mine_collection_view01);
			holder.mine_download_item_city_name = (TextView)convertView.findViewById(R.id.mine_download_item_city_name);
			holder.mine_download_item_city_pinyin = (TextView)convertView.findViewById(R.id.mine_download_item_city_pinyin);
			holder.mine_download_item_city_update_roundProgressBar = (RoundProgressBar)convertView.findViewById(R.id.mine_download_item_city_update_roundProgressBar);
			holder.mine_download_item_city_update_btn = (View)convertView.findViewById(R.id.mine_download_item_city_update_btn);

			holder.mine_download_item_city_delete_btn = (View)convertView.findViewById(R.id.mine_download_item_city_delete_btn);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}



		return convertView;
	}
	//用来存储对应数据类型，对照着XML文件
	final class Holder
	{
		private ImageView mine_download_item_city_bg;
		private TextView mine_download_item_city_name,mine_download_item_city_pinyin;
		private RoundProgressBar mine_download_item_city_update_roundProgressBar;
		private View mine_download_item_city_delete_btn;
		private View mine_download_item_city_update_btn;
	}
}
