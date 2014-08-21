package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.R;
import com.ne.vg.adapter.BigSceneListAdapter;
import com.ne.vg.bean.CreateData;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 
 * @ClassName: BigSceneListFragment 
 * @author ��ɼ
 * @Description: �󾰵��б��ҳ����gridview����ʾ�����Ƽ�activity��
 * @date 2014-8-12 ����3:19:24
 */
public class BigSceneListFragment extends Fragment{
	private final static String TAG="BigSceneListFragment";

	private GridView gridview;
	//�Զ����������
	private BigSceneListAdapter gridAdapter;
	public BigSceneListFragment(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_bigscenelist,
				container, false);
		//Init view
		gridview = (GridView) rootView.findViewById(R.id.bigscene_gridview1);
		InitListener();
		return rootView;

	}
	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: TODO������Ҫ�޸ģ���Ϊ�����û�з�Ӧ
	 * @param 
	 * @return void 
	 * @throws
	 */
	private void InitListener() {
		// TODO Auto-generated method stub
		gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.e("panshan"," " + position);
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(), "pic" + (position+1), Toast.LENGTH_SHORT).show(); 
			}
		});
	}
	@Override
	public void onStart(){
		gridAdapter = new BigSceneListAdapter(getActivity(), CreateData.getBigScenePs());
		gridview.setAdapter(gridAdapter);
		super.onStart();
	}
}
