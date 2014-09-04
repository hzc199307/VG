package com.ne.vg.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ne.vg.R;
import com.ne.vg.VGApplication;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.CreateData;
import com.ne.vg.bean.SmallScene;
import com.ne.vg.fragment.SceneSmallSceneListFragment;


public class SceneSmallSceneListAdapter extends BaseAdapter{
	 
	private final String TAG = "RouteDay_BigSceneListAdapter";
	private List<SmallScene> listData = null;
	private LayoutInflater inflater = null;
	private VGApplication app;
	int sizeOfBigScene = 0;
	public AnimationDrawable animationDrawable;

	public SceneSmallSceneListAdapter(Context context) {
		//setListData(CreateData.getBigSceneList());
		inflater = LayoutInflater.from(context);
		
	}

	public SceneSmallSceneListAdapter(Context context,List<SmallScene> list) {
		setListData(list);
		inflater = LayoutInflater.from(context);
		app = (VGApplication)((SceneActivity)context).getApplication();
	}
	
	public void destroy() {
		listData.clear();
		listData=null;
	}

	public void setListData(List<SmallScene> list){
		if (list != null) {
			listData = list;
		}else {
			listData = new ArrayList<SmallScene>();
		}
		sizeOfBigScene = listData.size();
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public SmallScene getItem(int position) {
		if(listData == null) return null;
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		if(listData == null)
			return 0;
		return listData.get(position).getSmallSceneID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (convertView == null) {// ����������ʹviewѭ�����ã��������ж��ٸ�item�Ͳ������ٸ�view
			//Log.v(TAG, "convertView �ǿյ�");
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.scene_item_small_scene, null);// ���ò����ļ�
			viewHolder.scene_item_smallscene_name = (TextView)convertView.findViewById(R.id.scene_item_smallscene_name);	
			viewHolder.button = (ImageView)convertView.findViewById(R.id.scene_item_smallscene_button);
			viewHolder.animationIV = (ImageView) convertView.findViewById(R.id.animationIV);
			viewHolder.divider2 = (View)convertView.findViewById(R.id.scene_item_smallscene_divider2);
			convertView.setTag(viewHolder);// ������²�����view��������tag
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();// �����ʹ���Ѿ����ڵ�view�����tag�л�ȡ�Ϳ�����
		}
		SmallScene mySmallScene = listData.get(position);
		//int id = mContext.getResources().getIdentifier("city_"+myCity.getCityPinyin() ,"drawable","com.ne.voiceguider");
		viewHolder.scene_item_smallscene_name.setText(mySmallScene.getSmallSceneName());
		
		/**
		 * ��δ������ж��Ƿ������ڲ��ŵ�item��ͬʱ�趨��״̬
		 */
		if(app.playSceneID == mySmallScene.getSmallSceneID() && app.mBinder.getService().isPlaying==true)
		{
			viewHolder.button.setImageResource(R.drawable.scene_music_pause_icon);
			viewHolder.animationIV.setImageResource(R.drawable.scene_music_isplaying_animation);
			animationDrawable = (AnimationDrawable) viewHolder.animationIV
					.getDrawable();
			animationDrawable.start();
		}
		if(app.playSceneID == mySmallScene.getSmallSceneID()){
			viewHolder.divider2.setVisibility(0);
		}
		
		return convertView;
	}

	class ViewHolder {
		public TextView scene_item_smallscene_name;
		public ImageView button;
		public ImageView animationIV;
		public View divider2;
	}


	/**
	 * ����б���
	 * @param item
	 */
	public void addItem(SmallScene item) {
		this.listData.add(item);
	}
}