package com.ne.vg.bean;

import java.util.ArrayList;
import java.util.List;

public class CreateData {

	public static List<BigScene> getBigSceneList()
	{
		List<BigScene> listCities = new ArrayList<BigScene>();
		BigScene bigScene1,bigScene2,bigScene;
		bigScene1 = new BigScene();
		bigScene1.setBigSceneName("���޳�");
		listCities.add(bigScene1);
		bigScene2 = new BigScene();
		bigScene2.setBigSceneName("�Ƕ���");
		listCities.add(bigScene2);
		for(int i=0;i<10;i++)
		{
			bigScene = new BigScene();
			bigScene.setBigSceneName("����ĳ���󾰵�");
			listCities.add(bigScene);
		}
		return listCities;
	}
	
	public static List<City> getCityList()
	{
		List<City> listCities = new ArrayList<City>();
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
		return listCities;
	}
}
