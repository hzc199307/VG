package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.adapter.CityListAdapter;
import com.ne.vg.bean.City;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	private final static String TAG = "HomeFragment";
	
	private ListView lv_city;
	private CityListAdapter cityListAdapter ;
	private List<City> listCities;
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
		return view;
	}
	
	public void createListCities()
	{
		listCities = new ArrayList<City>();
		City city1,city2,city;
		city1 = new City();
		city1.setCityName("罗马");
		listCities.add(city1);
		city2 = new City();
		city2.setCityName("威尼斯");
		listCities.add(city2);
		for(int i=0;i<10;i++)
		{
			city = new City();
			city.setCityName("意大利城市");
			listCities.add(city);
		}
	}
}
