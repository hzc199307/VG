package com.ne.vg.adapter;

import java.util.List;
import java.util.Map;

import com.ne.vg.R;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @ClassName: BigSceneListAdapter 
 * @author 潘杉
 * @Description: 大景点列表（推荐activity）中所用到的自定义适配器，用来传输数据
 * @date 2014-8-12 下午3:20:00
 */
public class BigSceneListAdapter extends BaseAdapter{

	//LayoutInflater作用是将layout的xml布局文件实例化为View类对象。
	private LayoutInflater mInflater;
	//存储数据
	List<Map<String, Object>> mData;
	
	private Context context;

	/**
	 * 
	 * @param context
	 * @param mData
	 */
	public BigSceneListAdapter(Context context, List<Map<String, Object>> mData)
	{
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null)
		{
			//TODO 需要在XML文件中写一个item来表达list或者说grid中的每一项
			convertView = mInflater.inflate(R.layout.item_bigscenelist, null);
			holder = new Holder();			
			holder.img = (ImageView)convertView.findViewById(R.id.bigscenelist_view01);
			holder.viewButton = (Button)convertView.findViewById(R.id.bigscenelist_bu1);
			holder.sceneName = (TextView)convertView.findViewById(R.id.bigscenelist_tv1);
			holder.loveNum = (TextView)convertView.findViewById(R.id.bigscenelist_tv2);
			holder.recordNum = (TextView)convertView.findViewById(R.id.bigscenelist_tv3);
			convertView.setTag(holder);
		
		}
		else{
			holder = (Holder)convertView.getTag();
		}
		holder.img.setImageResource((Integer)mData.get(position).get("img"));
		holder.viewButton.setText((String)mData.get(position).get("download"));
		holder.sceneName.setText((String)mData.get(position).get("sceneName"));
		holder.loveNum.setText((String)mData.get(position).get("loveNum"));
		holder.recordNum.setText((String)mData.get(position).get("recordNum"));
		
		holder.viewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO	点击下载之后的时间响应		
				Log.v("ppp", "download is clicked");
				//Toast.makeText(context.getApplicationContext(), "xiazai is clicked", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	final class Holder
	{
		public ImageView img;
		public Button viewButton;
		public TextView sceneName;
		public TextView loveNum;
		public TextView recordNum;
	}

}
