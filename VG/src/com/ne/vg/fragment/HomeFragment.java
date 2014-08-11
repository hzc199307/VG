package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.adapter.CityListAdapter;
import com.ne.vg.bean.City;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener{

	private final static String TAG = "HomeFragment";

	private ListView lv_city;
	private CityListAdapter cityListAdapter ;
	private List<City> listCities;
	private ImageButton home_title_left_btn;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();

		createListCities();

		View view  = inflater.inflate(R.layout.fragment_home, container,false);
		lv_city = (ListView)view.findViewById(R.id.lv_city);
		cityListAdapter = new CityListAdapter(getActivity(), listCities);
		lv_city.setAdapter(cityListAdapter);
		home_title_left_btn = (ImageButton)view.findViewById(R.id.home_title_left_btn);
		home_title_left_btn.setOnClickListener(this);
		return view;
	}

	public void createListCities()
	{
		listCities = new ArrayList<City>();
		City city1,city2,city;
		city1 = new City();
		city1.setCityName("����");
		listCities.add(city1);
		city2 = new City();
		city2.setCityName("����˹");
		listCities.add(city2);
		for(int i=0;i<10;i++)
		{
			city = new City();
			city.setCityName("���������");
			listCities.add(city);
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch(id)
		{
		case R.id.home_title_left_btn:
			//���������߰�ť�������˵�
			((SlidingFragmentActivity)getActivity()).getSlidingMenu().showMenu(true);
			break;
		default:
			break;
		}
	}
}
