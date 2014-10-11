package com.ne.vg.adapter;

import com.ne.vg.util.ImageUtil;
import com.ne.vg.util.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*自定义的ViewHolder*/
public class ViewHolder
{
	
	private static final String TAG = "ViewHolder";
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
	/**
	 * 
	 * @Title: setImageResource 
	 * @Description: 通过String来设置图片资源
	 * @param @param viewId
	 * @param @param drawableId
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	public ViewHolder setImageResource(int viewId, String drawableId){
		int resId = mContext.getResources().getIdentifier(drawableId, "drawable","com.ne.vg");
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	/**
	 * 
	 * @Title: setImageResourceByInt 
	 * @Description: 通过Int来设置ImageView的资源
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
	 * @Title: setImageBitmap 
	 * @Description: 通过String来设置存储在SD卡的图片资源,前面的"com.ne.vg/image/"部分不用包含在内
	 * @param viewId
	 * @param imageUrl
	 * @param @return
	 * @return ViewHolder 
	 * @throws
	 */
	@SuppressLint("NewApi") public ViewHolder setImageBitmap(int viewId, String imageUrl){
		Bitmap temp = ImageUtil.getLoacalBitmap("com.ne.vg/image/"+imageUrl+".png");
		if(temp==null)
			temp = ImageUtil.getLoacalBitmap("com.ne.vg/image/"+imageUrl+".jpg");
		//temp.setDensity(mContext.getResources().getDisplayMetrics().densityDpi);
		ImageView view = getView(viewId);
		
//		double hScale = view.getHeight()/temp.getHeight();
//		double wScale = view.getWidth()/temp.getWidth();
//		double scale = hScale>wScale?hScale:wScale;
//		LogUtil.d(TAG, view.getHeight()+" "+temp.getHeight()+" "+scale);
//		temp.setHeight((int) (temp.getHeight()*scale));
//		temp.setWidth((int) (temp.getHeight()*scale));
		view.setImageBitmap(temp); 
		temp=null;
		return this;
	}
	
	
	/**
	 * 
	 * @Title: setView 
	 * @Description: 用来设置View的可见性，0代表visible,4代表invisible，8代表gone
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
	 * @Description: 1代表开始播放动画，0代表停止动画
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
