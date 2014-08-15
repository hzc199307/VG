package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.R.drawable;
import com.ne.vg.R.id;
import com.ne.vg.R.layout;
import com.ne.vg.R.menu;
import com.ne.vg.adapter.RecommendAdapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendActivity extends FragmentActivity {
	
	private String TAG = "RecommendActivity";
	private ViewPager viewPager;//页卡内容
	private RecommendAdapter recommendAdapter;
	private ImageView imageView;// 动画图片
	private TextView textView1,textView2;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recommend);
		InitImageView();
		InitTextView();
		InitViewPager();
	}
	
	/**
	 * 
	 * @Title: InitViewPager 
	 * @Description: 初始化viewpager
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitViewPager() {
		viewPager=(ViewPager) findViewById(R.id.recommend_viewPager); 
		recommendAdapter = new RecommendAdapter(getSupportFragmentManager() );
		viewPager.setAdapter(recommendAdapter);  
	    viewPager.setCurrentItem(0);  
	    viewPager.setOnPageChangeListener(new MyOnPageChangeListener());  
	}
	
	/**
	 * 
	 * @Title: InitTextView 
	 * @Description: 初始化头标
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitTextView() {
		textView1 = (TextView) findViewById(R.id.recommend_top_text1);
		textView2 = (TextView) findViewById(R.id.recommend_top_text2);
		
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
	}

	/**
	 * 
	 * @Title: InitImageView 
	 * @Description: 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitImageView() {
		 imageView= (ImageView) findViewById(R.id.recommend_cursor);  
	     bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.test_main_cursor).getWidth();// 获取图片宽度  
	     DisplayMetrics dm = new DisplayMetrics();  
	     getWindowManager().getDefaultDisplay().getMetrics(dm);  
	     int screenW = dm.widthPixels;// 获取分辨率宽度  
	     offset = (screenW / 3 - bmpW) / 2;// 计算偏移量  
	     Matrix matrix = new Matrix();  
	     matrix.postTranslate(offset, 0);  
	     imageView.setImageMatrix(matrix);// 设置动画初始位置  
	}

	/**
	 * 
	 * @ClassName: MyOnClickListener 
	 * @author 潘杉
	 * @Description: 头标点击监听
	 * @date 2014-8-11 下午4:27:36
	 */
	private class MyOnClickListener implements OnClickListener{

		 private int index=0;
	        public MyOnClickListener(int i){
	        	index=i;
	        }
		
		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
		
	}
	
	 public class MyOnPageChangeListener implements OnPageChangeListener{
		 	
		 //TODO
	    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
			public void onPageScrollStateChanged(int arg0) {
					
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
					
			}

			public void onPageSelected(int arg0) {
				//TODO 主要是imageview的位置问题
				//arg1是动画开始的点离当前View X坐标上的差值，arg2是动画结束的点离当前View X坐标上的差值 
				Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
				currIndex = arg0;
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(300);
				imageView.startAnimation(animation);
				//Toast.makeText(null, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
			}
	    	
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
