package com.ne.vg;

import com.ne.vg.fragment.MyLoveFragment;
import com.ne.vg.fragment.PlayMusicFragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MyLoveActivity extends FragmentActivity {

	private LinearLayout mlinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_love);
		if (savedInstanceState == null) {
			//TODO 这里还需要修改R.id.xxx
			getSupportFragmentManager().beginTransaction()
					.add(R.id.mylove_frame, new MyLoveFragment()).commit();
		}
		initListenter();
		
	}
	/** 设置返回按钮监听*/
	private void initListenter() {
		// TODO Auto-generated method stub
		mlinearLayout = (LinearLayout)findViewById(R.id.mylove_back);
		mlinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
