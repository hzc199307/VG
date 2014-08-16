package com.ne.vg.adapter;

import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayMusicAdapter extends BaseAdapter{

	private Intent intent;
	//LayoutInflater�����ǽ�layout��xml�����ļ�ʵ����ΪView�����
	private LayoutInflater mInflater;
	private Context context;
	
	Holder holder;
	//�洢����
	List<Map<String, Object>> mData;	
	
	private boolean isPlaying = false; 
	public PlayMusicAdapter(Context context, List<Map<String, Object>> mData){
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
	
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		//Holder holder;
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_playmusiclist, null);
			holder = new Holder();
			holder.img = (ImageView)convertView.findViewById(R.id.playmusic_pic);
			holder.musicName = (TextView)convertView.findViewById(R.id.playmusic_name);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder)convertView.getTag();
		}
		
		holder.img.setImageResource((Integer)mData.get(position).get("img"));
		holder.musicName.setText((String)mData.get(position).get("musicName"));
		
		holder.img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO ������Ҫ�ú�д�����Ű�ť����ͣ��ť��ת��
				if(isPlaying == false){
					//��������
					holder.img.setImageResource(R.drawable.test_btn_pause);
					//context.
					//startSer(1);
				}else{
					//��ͣ����
					holder.img.setImageResource( R.drawable.test_btn_play);
					//startSer(2);
				}
				isPlaying = !isPlaying;
			}
		});
		return convertView;
	}
	
	/**
	 * 
	 * @Title: startSer 
	 * @Description: �����������װ��һ������
	 * @param @param op
	 * @return void 
	 * @throws
	 */
	public void startSer(int op){
		Bundle bundle = new Bundle();
		bundle.putInt("op", op);
		intent.putExtras(bundle);
		//����ǲ��ŵĸ���
		bundle.putInt("musicresource",R.raw.fengwei);
		context.startService(intent);
	}
	
	
	final class Holder
	{
		public ImageView img;
		public TextView musicName;
	}

}
