package com.ne.vg.fragment;

import com.ne.vg.R;
import com.ne.vg.adapter.BigSceneListAdapter;
import com.ne.vg.adapter.MyLoveAdapter;
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

public class MyLoveFragment extends Fragment{
	private final static String TAG="MyLoveFragment";
	
	private GridView gridview;
	//自定义的适配器
	private MyLoveAdapter gridAdapter;
	
	public MyLoveFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
	{
		//TODO
		//Toast.makeText(getActivity(), TAG + " onCreateView", Toast.LENGTH_SHORT).show();
		View rootView = inflater.inflate(R.layout.fragment_mylove,
				container, false);
		//Init view
		gridview = (GridView) rootView.findViewById(R.id.mylove_gridview1);
		InitListener();
		return rootView;
		
	}
	
	/**
	 * 
	 * @Title: InitListener 
	 * @Description: TODO这里需要修改，因为点击后没有反应
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
		gridAdapter = new MyLoveAdapter(getActivity(), CreateData.getMyLove());
		gridview.setAdapter(gridAdapter);
		super.onStart();
	}
}
