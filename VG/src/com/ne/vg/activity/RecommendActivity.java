package com.ne.vg.activity;

import com.ne.vg.MyLoveActivity;
import com.ne.vg.R;
import com.ne.vg.R.drawable;
import com.ne.vg.R.id;
import com.ne.vg.R.layout;
import com.ne.vg.R.menu;
import com.ne.vg.adapter.RecommendAdapter;
import com.ne.vg.util.NotifyUtil;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendActivity extends FragmentActivity {
	
	private String TAG = "RecommendActivity";
	private ViewPager viewPager;//ҳ������
	private RecommendAdapter recommendAdapter;
	private View view;// ����ͼƬ
	private TextView textView1,textView2;
	private ImageView imageView1,imageView2;
	private LinearLayout linearLayout;
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	//��������
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = this.getIntent();
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
		//�ڶ���������cityID
		Log.d(TAG, "cityID=" + intent.getExtras().getInt("cityID"));
		recommendAdapter = new RecommendAdapter(getSupportFragmentManager(),intent.getExtras().getInt("cityID") );
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
		imageView1 = (ImageView)findViewById(R.id.recommend_routelist_icon);
		imageView2 = (ImageView)findViewById(R.id.recommend_scenelist_icon);
		linearLayout = (LinearLayout)findViewById(R.id.recommend_title_name);
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		linearLayout.setOnClickListener(new MyOnClickListener(-1));
		
		/** ����С���ԣ��й���notifyutil���*/
		NotifyUtil noti = new NotifyUtil(this, "panshan", "�ú�Ŭ������������", "����", MyLoveActivity.class);
		noti.showNotify();
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
		view= (View) findViewById(R.id.recommend_cursor);  
	     bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.test_main_cursor).getWidth();// ��ȡͼƬ���  
	     DisplayMetrics dm = new DisplayMetrics();  
	     getWindowManager().getDefaultDisplay().getMetrics(dm);  
	     int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��  
	     offset = screenW / 2;// ����ƫ����  
	     Matrix matrix = new Matrix();  
	     matrix.postTranslate(offset, 0);  
//	     imageView.setImageMatrix(matrix);// ���ö�����ʼλ��  
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
			if(index == -1){
				//�����ؼ��򷵻��ϸ�activity
				finish();
			}else{
				viewPager.setCurrentItem(index);
				/** Tabʵ��ѡ�б�ɫ��*/
				if(index==1){
					textView1.setTextColor(getResources().getColor(R.color.black));
					imageView1.setImageResource(R.drawable.recommend_routelist_icon);
					textView2.setTextColor(getResources().getColor(R.color.special_red));
					imageView2.setImageResource(R.drawable.recommend_scenelist_icon_s);
					
					Toast.makeText(getApplicationContext(), intent.getExtras().getString("panshan"), Toast.LENGTH_SHORT).show();
					
				}else{
					textView1.setTextColor(getResources().getColor(R.color.special_red));
					imageView1.setImageResource(R.drawable.recommend_routelist_icon_se);
					textView2.setTextColor(getResources().getColor(R.color.black));
					imageView2.setImageResource(R.drawable.recommend_scenelist_icon);
				}
			}
			
			
		}
		
	}
	
	 public class MyOnPageChangeListener implements OnPageChangeListener{
		 	
		 //TODO
	    	
			public void onPageScrollStateChanged(int arg0) {
					
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
					
			}

			public void onPageSelected(int arg0) {
				//TODO ��Ҫ��imageview��λ������
				//arg1�Ƕ�����ʼ�ĵ��뵱ǰView X�����ϵĲ�ֵ��arg2�Ƕ��������ĵ��뵱ǰView X�����ϵĲ�ֵ 
				Animation animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
				currIndex = arg0;
				animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
				animation.setDuration(300);
				view.startAnimation(animation);
				
				//Toast.makeText(null, "��ѡ����"+ viewPager.getCurrentItem()+"ҳ��", Toast.LENGTH_SHORT).show();
				/** Tabʵ��ѡ�б�ɫ��*/
				if(arg0==1){
					textView1.setTextColor(getResources().getColor(R.color.black));
					imageView1.setImageResource(R.drawable.recommend_routelist_icon);
					textView2.setTextColor(getResources().getColor(R.color.special_red));
					imageView2.setImageResource(R.drawable.recommend_scenelist_icon_s);
				}
				if(arg0==0){
					textView1.setTextColor(getResources().getColor(R.color.special_red));
					imageView1.setImageResource(R.drawable.recommend_routelist_icon_se);
					textView2.setTextColor(getResources().getColor(R.color.black));
					imageView2.setImageResource(R.drawable.recommend_scenelist_icon);
				}
			
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
