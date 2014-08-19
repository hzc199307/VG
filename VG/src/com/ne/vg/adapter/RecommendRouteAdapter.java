package com.ne.vg.adapter;

import java.util.List;
import java.util.Map;

import com.ne.vg.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @ClassName: RecommendRouteAdapter 
 * @author 潘杉
 * @Description: 推荐路线（推荐activity）中所用到的自定义适配器，用来传输数据
 * @date 2014-8-12 下午3:21:05
 */
public class RecommendRouteAdapter extends BaseAdapter{

	//LayoutInflater作用是将layout的xml布局文件实例化为View类对象。
	private LayoutInflater mInflater;
	//存储数据
	List<Map<String, Object>> mData;
	
	public RecommendRouteAdapter(Context context, List<Map<String, Object>> mData)
	{
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
	
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_recommendroute, null);
			holder = new Holder();
			holder.img = (ImageView)convertView.findViewById(R.id.recommendroute_view01);
			//TODO 这里注释的地方需要取消
			/** */
			holder.sceneNum = (TextView)convertView.findViewById(R.id.recommendroute_sceneNum);
			holder.routeName = (TextView)convertView.findViewById(R.id.recommendroute_name);
			holder.routeDay = (TextView)convertView.findViewById(R.id.recommendroute_scenenum);
			
			holder.collectNum = (TextView)convertView.findViewById(R.id.recommendroute_lovenum);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
		holder.sceneNum.setText((String)mData.get(position).get("sceneNum"));
		holder.routeName.setText((String)mData.get(position).get("routeName"));
		holder.collectNum.setText((String)mData.get(position).get("collectNum"));
		//routeDay还需要加入天这个字
		holder.routeDay.setText((String)mData.get(position).get("routeDay")+ "天");
		//TODO click的响应目前先不写
		holder.collectNum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
				//mContext.showInfo();					
			}
		});
		
		
		return convertView;
	}
	//用来存储对应数据类型，对照着XML文件
	final class Holder
	{
		private ImageView img;
		private TextView sceneNum;
		private TextView routeName;
		private TextView routeDay;
		private TextView collectNum;
	}


}
