package com.ne.vg.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ne.vg.R;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.RecommendRoute;
import com.ne.vg.dao.VGDao;

/**
 * 用于RecommendRouteFragment和MineCollectionFragment
 * @ClassName: CommonRRAdapter 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-10-16 上午10:26:17
 */
public abstract class CommonRRAdapter extends CommonAdapter<RecommendRoute> {

	public CommonRRAdapter(Context context, List<RecommendRoute> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		
		ViewHolder helper = getViewHolderNow();
		MyOnClickListener myOnClickListener = (MyOnClickListener) helper.getOnClickListener();
		if(myOnClickListener==null)
			myOnClickListener = new MyOnClickListener();
		myOnClickListener.setPosition(position);
		myOnClickListener.setHelper(helper);
		myOnClickListener.setRecommendRoute(mDatas.get(position));
		aboutOnClickListener(helper,myOnClickListener);
		return view;
	}
	
	public abstract void aboutOnClickListener(ViewHolder helper,MyOnClickListener myOnClickListener);
	public abstract void onLoveClick(ViewHolder helper, RecommendRoute recommendRoute);

	public class MyOnClickListener implements View.OnClickListener
	{
		private int position ;
		private ViewHolder helper;
		private RecommendRoute recommendRoute;
		public void setRecommendRoute(RecommendRoute recommendRoute) {
			this.recommendRoute = recommendRoute;
		}
		public void setHelper(ViewHolder helper) {
			this.helper = helper;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onLoveClick(helper, recommendRoute);
//			boolean isCollected = VGDao.getInstance(getContext()).getRecommendRouteCollected(recommendRoute.getRouteID());
//			helper.setImageSelected(R.id.bigscenelist_love_iv, (isCollected==false));
//			VGDao.getInstance(getContext()).setRecommendRouteCollected(recommendRoute.getRouteID(), (isCollected==false)?1:0);
		}
	}
};

