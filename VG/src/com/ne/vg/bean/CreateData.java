package com.ne.vg.bean;

import java.util.ArrayList;
import java.util.List;

public class CreateData {

	public static List<BigScene> getBigSceneList()
	{
		List<BigScene> listCities = new ArrayList<BigScene>();
		BigScene bigScene1,bigScene2,bigScene;
		bigScene1 = new BigScene();
		bigScene1.setBigSceneName("斗兽场");
		listCities.add(bigScene1);
		bigScene2 = new BigScene();
		bigScene2.setBigSceneName("角斗场");
		listCities.add(bigScene2);
		for(int i=0;i<10;i++)
		{
			bigScene = new BigScene();
			bigScene.setBigSceneName("罗马某个大景点");
			listCities.add(bigScene);
		}
		return listCities;
	}
	
	public static List<City> getCityList()
	{
		List<City> listCities = new ArrayList<City>();
		City city1,city2,city;
		city1 = new City();
		city1.setCityName("佛罗伦萨");
		city1.setCityPinyin("Florence");
		city1.setResource("city_florence");
		
		listCities.add(city1);
		city2 = new City();
		city2.setCityName("罗马");
		city2.setCityPinyin("Roman");
		city2.setResource("city_roman");
		listCities.add(city2);
		for(int i=0;i<10;i++)
		{
			city = new City();
			city.setCityName("五渔村");
			city.setCityPinyin("Cinque Terre");
			city.setResource("city_cinqueterre");
			listCities.add(city);
		}
		return listCities;
	}
}
