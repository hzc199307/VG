package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.MineDownloadSubFragment;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;


public class MineDownloadSubActivity extends FragmentActivity {

	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_download_sub);
		textView = (TextView) findViewById(R.id.mine_download_sub_title_cityname);
		textView.setText(getIntent().getExtras().getString("cityName"));
		if (savedInstanceState == null) {
			//TODO ���ﻹ��Ҫ�޸�R.id.xxx
			getSupportFragmentManager().beginTransaction()
			.add(R.id.mine_download_sub_frame, new MineDownloadSubFragment()).commit();
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mine_download_sub_title_left://��������ť
			this.finish();
		default:
			break;
		}
	}
}
