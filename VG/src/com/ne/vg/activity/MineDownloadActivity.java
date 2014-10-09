package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.MineDownloadFragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

public class MineDownloadActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_download);
		
		if (savedInstanceState == null) {
			//TODO ���ﻹ��Ҫ�޸�R.id.xxx
			getSupportFragmentManager().beginTransaction()
			.add(R.id.mine_download_frame, new MineDownloadFragment()).commit();
		}

	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mine_download_title_left://��������ť
			this.finish();
		default:
			break;
		}
	}

}
