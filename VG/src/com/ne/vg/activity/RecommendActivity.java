package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.adapter.RecommendAdapter;
import com.ne.vg.dao.VGDao;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class RecommendActivity extends FragmentActivity implements View.OnClickListener{

	private String TAG = "RecommendActivity";
	private ViewPager viewPager;//页卡内容
	private RecommendAdapter recommendAdapter;
	private View view;// 动画图片
	private TextView recommend_cityname;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private VGDao mVgDao;

	private final static int ROUTELIST_STATUS = 0,SCENELIST_STATUS = 1;
	private View recommend_routelist_layout,recommend_scenelist_layout;
	private MyOnPageChangeListener myOnPageChangeListener;

	//测试数据
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = this.getIntent();
		setContentView(R.layout.activity_recommend);
		mVgDao = new VGDao(this);
		initTab();
		initTabAnim();
		initViewPager();
		//		InitImageView();
		//		InitTextView();
		//		InitViewPager();

	}

	/**
	 * initTab
	 */
	private void initTab() {
		recommend_routelist_layout = (View)findViewById(R.id.recommend_routelist_layout);
		recommend_scenelist_layout = (View)findViewById(R.id.recommend_scenelist_layout);
		recommend_routelist_layout.setOnClickListener(this);
		recommend_scenelist_layout.setOnClickListener(this);
	}

	/**
	 * 
	 * @Title: initImageView 
	 * @Description: 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void initTabAnim() {
		view= (View) findViewById(R.id.recommend_cursor);
		recommend_cityname = (TextView)findViewById(R.id.recommend_cityname);
		recommend_cityname.setText(mVgDao.getCityName(intent.getExtras().getInt("cityID")));
		DisplayMetrics dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		int screenW = dm.widthPixels;// 获取分辨率宽度  
		offset = screenW / 2;// 计算偏移量  
	}

	/**
	 * 
	 * @Title: initViewPager 
	 * @Description: 初始化viewpager
	 */
	private void initViewPager() {
		viewPager=(ViewPager) findViewById(R.id.recommend_viewpager); 
		//第二个参数是cityID
		Log.d(TAG, "cityID=" + intent.getExtras().getInt("cityID"));
		recommendAdapter = new RecommendAdapter(getSupportFragmentManager(),intent.getExtras().getInt("cityID") );
		viewPager.setAdapter(recommendAdapter);  
		//viewPager.setCurrentItem(ROUTELIST_STATUS);  TODO
		viewPager.setCurrentItem(SCENELIST_STATUS);
		myOnPageChangeListener = new MyOnPageChangeListener();
		viewPager.setOnPageChangeListener(myOnPageChangeListener);  
		//myOnPageChangeListener.onPageSelected(ROUTELIST_STATUS); TODO
		myOnPageChangeListener.onPageSelected(SCENELIST_STATUS);
	}

	//	/**
	//	 * 
	//	 * @Title: InitViewPager 
	//	 * @Description: 初始化viewpager
	//	 * @param 
	//	 * @return void 
	//	 * @throws
	//	 */
	//	private void InitViewPager() {
	//		viewPager=(ViewPager) findViewById(R.id.recommend_viewPager); 
	//		//第二个参数是cityID
	//		Log.d(TAG, "cityID=" + intent.getExtras().getInt("cityID"));
	//		recommendAdapter = new RecommendAdapter(getSupportFragmentManager(),intent.getExtras().getInt("cityID") );
	//		viewPager.setAdapter(recommendAdapter);  
	//	    viewPager.setCurrentItem(0);  
	//	    viewPager.setOnPageChangeListener(new MyOnPageChangeListener());  
	//	}

	//	/**
	//	 * 
	//	 * @Title: InitTextView 
	//	 * @Description: 初始化头标
	//	 * @param 
	//	 * @return void 
	//	 * @throws
	//	 */
	//	private void InitTextView() {
	//		textView1 = (TextView) findViewById(R.id.recommend_top_text1);
	//		textView2 = (TextView) findViewById(R.id.recommend_top_text2);
	//		imageView1 = (ImageView)findViewById(R.id.recommend_routelist_icon);
	//		imageView2 = (ImageView)findViewById(R.id.recommend_scenelist_icon);
	//		linearLayout = (LinearLayout)findViewById(R.id.recommend_title_name);
	//		textView1.setOnClickListener(new MyOnClickListener(0));
	//		textView2.setOnClickListener(new MyOnClickListener(1));
	//		linearLayout.setOnClickListener(new MyOnClickListener(-1));
	//		
	//		/** 做个小测试，有关于notifyutil类的*/
	//		NotifyUtil noti = new NotifyUtil(this, "panshan", "好好努力，天天向上", "加油", MyLoveActivity.class);
	//		noti.showNotify();
	//	}
	//
	//	/**
	//	 * 
	//	 * @Title: InitImageView 
	//	 * @Description: 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	//	 * @param 
	//	 * @return void 
	//	 * @throws
	//	 */
	//	private void InitImageView() {
	//		view= (View) findViewById(R.id.recommend_cursor);  
	//	     bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.test_main_cursor).getWidth();// 获取图片宽度  
	//	     DisplayMetrics dm = new DisplayMetrics();  
	//	     getWindowManager().getDefaultDisplay().getMetrics(dm);  
	//	     int screenW = dm.widthPixels;// 获取分辨率宽度  
	//	     offset = screenW / 2;// 计算偏移量  
	//	     Matrix matrix = new Matrix();  
	//	     matrix.postTranslate(offset, 0);  
	////	     imageView.setImageMatrix(matrix);// 设置动画初始位置  
	//	}

	//	/**
	//	 * 
	//	 * @ClassName: MyOnClickListener 
	//	 * @author 潘杉
	//	 * @Description: 头标点击监听
	//	 * @date 2014-8-11 下午4:27:36
	//	 */
	//	private class MyOnClickListener implements OnClickListener{
	//
	//		private int index=0;
	//		public MyOnClickListener(int i){
	//			index=i;
	//		}
	//
	//		@Override
	//		public void onClick(View v) {
	//			if(index == -1){
	//				//按返回键则返回上个activity
	//				finish();
	//			}else{
	//				viewPager.setCurrentItem(index);
	//				/** Tab实现选中变色　*/
	//				if(index==1){
	//					textView1.setTextColor(getResources().getColor(R.color.black));
	//					imageView1.setImageResource(R.drawable.recommend_routelist_icon);
	//					textView2.setTextColor(getResources().getColor(R.color.special_red));
	//					imageView2.setImageResource(R.drawable.recommend_scenelist_icon_s);
	//
	//					Toast.makeText(getApplicationContext(), intent.getExtras().getString("panshan"), Toast.LENGTH_SHORT).show();
	//
	//				}else{
	//					textView1.setTextColor(getResources().getColor(R.color.special_red));
	//					imageView1.setImageResource(R.drawable.recommend_routelist_icon_se);
	//					textView2.setTextColor(getResources().getColor(R.color.black));
	//					imageView2.setImageResource(R.drawable.recommend_scenelist_icon);
	//				}
	//			}
	//
	//
	//		}
	//
	//	}

	public class MyOnPageChangeListener implements OnPageChangeListener{

		//TODO

		Animation animation ;
		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			//arg1是动画开始的点离当前View X坐标上的差值，arg2是动画结束的点离当前View X坐标上的差值 
			Log.v(TAG, "onPageSelected "+arg0);
			animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			view.startAnimation(animation);
			switch (arg0) {
			case ROUTELIST_STATUS:
				recommend_routelist_layout.setSelected(true);
				recommend_scenelist_layout.setSelected(false);
				break;
			case SCENELIST_STATUS:
				recommend_routelist_layout.setSelected(false);
				recommend_scenelist_layout.setSelected(true);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.recommend_title_left:
			this.finish();
			break;
		case R.id.recommend_routelist_layout:
			viewPager.setCurrentItem(ROUTELIST_STATUS);  //会自动调用 myOnPageChangeListener.onPageSelected()
			break;
		case R.id.recommend_scenelist_layout:
			viewPager.setCurrentItem(SCENELIST_STATUS);
			break;
		default:
			break;
		}
	}

}
