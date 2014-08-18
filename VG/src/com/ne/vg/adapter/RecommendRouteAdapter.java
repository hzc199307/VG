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
 * @author ��ɼ
 * @Description: �Ƽ�·�ߣ��Ƽ�activity�������õ����Զ�����������������������
 * @date 2014-8-12 ����3:21:05
 */
public class RecommendRouteAdapter extends BaseAdapter{

	//LayoutInflater�����ǽ�layout��xml�����ļ�ʵ����ΪView�����
	private LayoutInflater mInflater;
	//�洢����
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
			//TODO ����ע�͵ĵط���Ҫȡ��
			/** */
//			holder.sceneNum = (TextView)convertView.findViewById(R.id.recommendroute_tv1);
//			holder.routeName = (TextView)convertView.findViewById(R.id.recommendroute_tv2);
//			holder.collectNum = (TextView)convertView.findViewById(R.id.recommendroute_tv3);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
		holder.sceneNum.setText((String)mData.get(position).get("sceneNum"));
		holder.routeName.setText((String)mData.get(position).get("routeName"));
		holder.collectNum.setText((String)mData.get(position).get("collectNum"));
		holder.collectNum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
				//mContext.showInfo();					
			}
		});
		
		
		return convertView;
	}
	//�����洢��Ӧ�������ͣ�������XML�ļ�
	final class Holder
	{
		public ImageView img;
		public TextView sceneNum;
		public TextView routeName;
		public TextView collectNum;
	}


}
