package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.R.layout;
import com.ne.vg.fragment.MineCollectionFragment;
import com.ne.vg.fragment.MineDownloadFragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MineDownloadActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_download);
		
		if (savedInstanceState == null) {
			//TODO 这里还需要修改R.id.xxx
			getSupportFragmentManager().beginTransaction()
			.add(R.id.mine_download_frame, new MineDownloadFragment()).commit();
		}

	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mine_download_title_left://标题栏左按钮
			this.finish();
		default:
			break;
		}
	}

}
