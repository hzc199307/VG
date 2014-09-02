package com.ne.vg.activity;

import com.ne.vg.R;
import com.ne.vg.fragment.MineCollectionFragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


public class MineCollectionActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_collection);

		if (savedInstanceState == null) {
			//TODO ���ﻹ��Ҫ�޸�R.id.xxx
			getSupportFragmentManager().beginTransaction()
			.add(R.id.mine_collection_frame, new MineCollectionFragment(1)).commit();
		}

	}

}
