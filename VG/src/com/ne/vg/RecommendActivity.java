package com.ne.vg;

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
	private ViewPager viewPager;//ҳ������
	private RecommendAdapter recommendAdapter;
	private ImageView imageView;// ����ͼƬ
	private TextView textView1,textView2;
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	
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
	 * @Description: ��ʼ��viewpager
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
	 * @Description: ��ʼ��ͷ��
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
	 * @Description: ��ʼ���������������ҳ������ʱ������ĺ���Ҳ������Ч������������Ҫ����һЩ����
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitImageView() {
		 imageView= (ImageView) findViewById(R.id.recommend_cursor);  
	     bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_cursor).getWidth();// ��ȡͼƬ���  
	     DisplayMetrics dm = new DisplayMetrics();  
	     getWindowManager().getDefaultDisplay().getMetrics(dm);  
	     int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��  
	     offset = (screenW / 3 - bmpW) / 2;// ����ƫ����  
	     Matrix matrix = new Matrix();  
	     matrix.postTranslate(offset, 0);  
	     imageView.setImageMatrix(matrix);// ���ö�����ʼλ��  
	}

	/**
	 * 
	 * @ClassName: MyOnClickListener 
	 * @author ��ɼ
	 * @Description: ͷ��������
	 * @date 2014-8-11 ����4:27:36
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
	    	int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
			public void onPageScrollStateChanged(int arg0) {
					
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
					
			}

			public void onPageSelected(int arg0) {
				//TODO ��Ҫ��imageview��λ������
				//arg1�Ƕ�����ʼ�ĵ��뵱ǰView X�����ϵĲ�ֵ��arg2�Ƕ��������ĵ��뵱ǰView X�����ϵĲ�ֵ 
				Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
				currIndex = arg0;
				animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
				animation.setDuration(300);
				imageView.startAnimation(animation);
				//Toast.makeText(null, "��ѡ����"+ viewPager.getCurrentItem()+"ҳ��", Toast.LENGTH_SHORT).show();
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
