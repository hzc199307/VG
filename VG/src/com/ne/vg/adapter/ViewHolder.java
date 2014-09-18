package com.ne.vg.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*ViewHolder的优化*/
public class ViewHolder
{
	//SparseView 和Map类似，但是效率比Map高
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context mContext;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
		int position)
	{
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
		//setTag
		mConvertView.setTag(this);
		mPosition = position;
		mContext = context;
	}
	/**
	*拿到一个ViewHolder对象
	*/
	public static ViewHolder get(Context context,View convertView,
	 ViewGroup parent, int layoutId,int position)
	{
		if(convertView==null){
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder)convertView.getTag();
	}
	/**
	*通过控件的Id获取对于的控件，如果没有则加入views中
	*/
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	/* 为TextView设置字符串*/
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	public ViewHolder setImageResource(int viewId, String  drawableId){
		int resId = mContext.getResources().getIdentifier(drawableId, "drawable","com.ne.vg");
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}


	public View getConvertView(){
		return mConvertView;
	}

	public int getPosition()
	{
		return mPosition;
	}
}
