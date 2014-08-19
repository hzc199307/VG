package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import com.ne.vg.bean.BigScene;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
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
	private List<BigScene> mData;
	
	private Context context;

	/**
	 * 
	 * @param context
	 * @param mData
	 */
	public BigSceneListAdapter(Context context, List<BigScene> mData)
	{
		setListData(mData);
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	public void setListData(List<BigScene> list){
		if(list!=null){
			mData = list;
		}else{
			mData = new ArrayList<BigScene>();
		}
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
			holder.download = (FrameLayout)convertView.findViewById(R.id.bigscenelist_download);
			holder.sceneName = (TextView)convertView.findViewById(R.id.bigscenelist_item_name);
			holder.loveNum = (TextView)convertView.findViewById(R.id.bigscenelist_loveNum);
			holder.recordNum = (TextView)convertView.findViewById(R.id.bigscenelist_musicNum);
			holder.shadow=(ImageView)convertView.findViewById(R.id.bigscenelist_item_shadow);
			convertView.setTag(holder);
		
		}
		else{
			holder = (Holder)convertView.getTag();
		}
		BigScene mBigScene = mData.get(position);
		holder.img.setImageResource(mBigScene.getSrc());
		//holder.download.setText((String)mData.get(position).get("download"));
		holder.sceneName.setText(mBigScene.getBigSceneName());
		holder.loveNum.setText(Integer.toString(mBigScene.getLoveNum()));
		holder.recordNum.setText(Integer.toString(mBigScene.getRecordNum()));
		
		/** 这段函数是阴影的变化部分，那个参数应该为isdowned*/
		//第一个参数是宽，第二个是高。
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		//1代表已下载，0代表没下载
		if(mBigScene.isDowned()==false){
			holder.shadow.setLayoutParams( params);
		}
		
		
		holder.download.setOnClickListener(new View.OnClickListener() {
			
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
		private ImageView img;
		private FrameLayout download;
		private TextView sceneName;
		private TextView loveNum;
		private TextView recordNum;
		//这个是用来判断阴影的变量
		private ImageView shadow;
	}

}
