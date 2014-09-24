package com.ne.vg.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*�Զ����ViewHolder*/
public class ViewHolder
{
	//SparseView ��Map���ƣ�����Ч�ʱ�Map��
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
	*�õ�һ��ViewHolder����
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
	*ͨ���ؼ���Id��ȡ���ڵĿؼ������û�������views��
	*/
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	/* ΪTextView�����ַ���*/
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}
	/**
	 * 
	 * @Title: setImageResource 
	 * @Description: ͨ��String������ͼƬ��Դ
	 * @param @param viewId
	 * @param @param drawableId
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	public ViewHolder setImageResource(int viewId, String  drawableId){
		int resId = mContext.getResources().getIdentifier(drawableId, "drawable","com.ne.vg");
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	/**
	 * 
	 * @Title: setImageResourceByInt 
	 * @Description: ͨ��Int������ImageView����Դ
	 * @param @param viewId
	 * @param @param drawableId
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	public ViewHolder setImageResourceByInt(int viewId, int drawableId){
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}
	/**
	 * 
	 * @Title: setView 
	 * @Description: ��������View�Ŀɼ��ԣ�0����visible,4����invisible��8����gone
	 * @param @param viewId
	 * @param @param i
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	public ViewHolder setView(int viewId, int i){
		View view = getView(viewId);
		view.setVisibility(i);
		return this;
	}
	/**
	 * 
	 * @Title: setAnimation 
	 * @Description: 1����ʼ���Ŷ�����0����ֹͣ����
	 * @param @param viewId
	 * @param @param drawableId
	 * @param @param isStart
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	public ViewHolder setAnimation(int viewId, int drawableId,int isStart){
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		AnimationDrawable animationDrawable;
		animationDrawable = (AnimationDrawable) view
				.getDrawable();
		if(isStart == 1){
			animationDrawable.start();
		}
		if(isStart == 0)
			animationDrawable.stop();
		
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
