package com.ne.vg.adapter;

import java.util.List;

import com.ne.vg.R;
import com.ne.vg.adapter.RecommendRouteAdapter.Holder;
import com.ne.vg.bean.RecommendRoute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MineCollectionAdapter extends BaseAdapter{

	//LayoutInflater�����ǽ�layout��xml�����ļ�ʵ����ΪView�����
	private LayoutInflater mInflater;
	//�洢����
	List<RecommendRoute> mData;
	private Context mContext;

	public MineCollectionAdapter(Context context, List<RecommendRoute> mData)
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
			convertView = mInflater.inflate(R.layout.item_mine_collection, null);
			holder = new Holder();
			holder.img = (ImageView)convertView.findViewById(R.id.mine_collection_view01);
			//TODO ����ע�͵ĵط���Ҫȡ��
			/** */
			holder.sceneNum = (TextView)convertView.findViewById(R.id.mine_collection_sceneNum);
			holder.routeName = (TextView)convertView.findViewById(R.id.mine_collection_name);
			holder.routeDay = (TextView)convertView.findViewById(R.id.mine_collection_scenenum);

			holder.collectNum = (TextView)convertView.findViewById(R.id.mine_collection_lovenum);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		RecommendRoute mRecommendRoute = mData.get(position);

		//��string���͵���Դ�л�ȡint���͵ı�־id
		int resId = mContext.getResources().getIdentifier(mRecommendRoute.getResource() ,"drawable","com.ne.vg");
		if(resId>0)
			holder.img.setBackgroundResource(resId);

		holder.sceneNum.setText(Integer.toString(mRecommendRoute.getSceneNum()));
		holder.routeName.setText(mRecommendRoute.getRouteName());
		holder.collectNum.setText(Integer.toString(mRecommendRoute.getCollectNum()));
		//routeDay����Ҫ�����������
		holder.routeDay.setText(mRecommendRoute.getRouteDay()+ "��");
		//TODO click����ӦĿǰ�Ȳ�д
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
		private ImageView img;
		private TextView sceneNum;
		private TextView routeName;
		private TextView routeDay;
		private TextView collectNum;
	}

}
