package com.ne.vg.main;

import com.ne.vg.R;
import com.ne.vg.activity.MineCollectionActivity;
import com.ne.vg.activity.MineDownloadActivity;
import com.ne.vg.activity.MyLoveActivity;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ���ҵġ�
 * @ClassName: MineFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-9-2 ����11:30:02
 */
public class MineFragment extends AnimationFragment implements View.OnClickListener{

	private final static String TAG = "MineFragment";

	private View mine_item_download,mine_item_love,mine_item_collection;
	private View mine_title_left;
	
	private TextView mine_colleced_route_num,mine_love_bigscene_num,mine_download_voice_num;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();
		LogUtil.d(TAG, "onCreateView");
		View view  = inflater.inflate(R.layout.fragment_mine, container,false);
		mine_title_left = (View)view.findViewById(R.id.mine_title_left);
		super.setAnimView(mine_title_left);
		mine_item_download = (View)view.findViewById(R.id.mine_item_download);
		mine_item_love = (View)view.findViewById(R.id.mine_item_love);
		mine_item_collection = (View)view.findViewById(R.id.mine_item_collection);
		mine_item_download.setOnClickListener(this);
		mine_item_love.setOnClickListener(this);
		mine_item_collection.setOnClickListener(this);
		
		mine_download_voice_num = (TextView)view.findViewById(R.id.mine_download_voice_num);
		mine_download_voice_num.setText(VGDao.getInstance(getActivity()).getSmallSceneNum()+"");
		mine_love_bigscene_num = (TextView)view.findViewById(R.id.mine_love_bigscene_num);
		mine_love_bigscene_num.setText(VGDao.getInstance(getActivity()).getCollectedBigSceneNum()+"");
		mine_colleced_route_num = (TextView)view.findViewById(R.id.mine_colleced_route_num);
		mine_colleced_route_num.setText(VGDao.getInstance(getActivity()).getCollectedRecommendRouteNum()+"");
		
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		LogUtil.d(TAG, "onDestroyView");
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {

		case R.id.mine_item_download:
			//TODO ��ת����ϲ����ҳ��
			startActivity(new Intent(getActivity(),MineDownloadActivity.class));
			break;
		case R.id.mine_item_love:
			//TODO ��ת����ϲ����ҳ��
			startActivity(new Intent(getActivity(),MyLoveActivity.class));
			break;
		case R.id.mine_item_collection:
			//TODO ��ת����ϲ����ҳ��
			startActivity(new Intent(getActivity(),MineCollectionActivity.class));
			break;
		default:
			break;
		}
	}
}
