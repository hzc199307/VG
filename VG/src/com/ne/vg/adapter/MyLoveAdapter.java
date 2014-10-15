package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.adapter.BigSceneListAdapter.Holder;
import com.ne.vg.bean.BigScene;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@Deprecated
public class MyLoveAdapter extends BaseAdapter{

	//LayoutInflater作用是将layout的xml布局文件实例化为View类对象。
	private LayoutInflater mInflater;
	//存储数据
	private List<BigScene> mData;
		
	private Context context;
	
	public MyLoveAdapter(Context context, List<BigScene> mData)
	{
		setListData(mData);
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
		
	private void setListData(List<BigScene> list) {
		// TODO Auto-generated method stub
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
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_mylove, null);
			holder = new Holder();
			holder.img = new ImageView[5];
			holder.sceneName=(TextView)convertView.findViewById(R.id.item_mylove_scenename);
			holder.img[0] = (ImageView)convertView.findViewById(R.id.item_mylove_star1);
			holder.img[1] = (ImageView)convertView.findViewById(R.id.item_mylove_star2);
			holder.img[2] = (ImageView)convertView.findViewById(R.id.item_mylove_star3);
			holder.img[3] = (ImageView)convertView.findViewById(R.id.item_mylove_star4);
			holder.img[4] = (ImageView)convertView.findViewById(R.id.item_mylove_star5);
			convertView.setTag(holder);
			
		}else{
			holder= (Holder)convertView.getTag();
		}
		BigScene mBigScene = mData.get(position);
		holder.sceneName.setText(mBigScene.getBigSceneName());
		
		/** 传过来星级的判断，从而决定界面星星的显示*/
		showstar(mBigScene.getLevel(),holder);
		
		// TODO Auto-generated method stub
		return convertView;
	}
	/**
	 * 
	 * @Title: showstar 
	 * @Description: 显示一定数量的星星,第一个参数是
	 * @param @param level
	 * @param @param holder
	 * @return void 
	 * @throws
	 */
	public void showstar(int level,Holder holder){
		for(int i = 0;i<level;i++){
			//设置显示
			holder.img[i].setVisibility(View.VISIBLE);
		}
		for(int i = level;i<5;i++){
			//设置不显示，但不占空间
			holder.img[i].setVisibility(View.GONE);
		}
	}
	
	class Holder{
		private TextView sceneName;
		private ImageView img[];
	}

}
