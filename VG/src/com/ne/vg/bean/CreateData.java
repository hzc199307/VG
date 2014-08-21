package com.ne.vg.bean;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;


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
	
	/**
	 * 
	 * @Title: getRecommendRoute 
	 * @Description: 获取推荐路线
	 * @param @return
	 * @return List<RecommendRoute> 
	 * @throws
	 */
	public static List<RecommendRoute> getRecommendRoute()
	{
		List<RecommendRoute> listRoutes = new ArrayList<RecommendRoute>();
		RecommendRoute route1,route2,route3;
		
		route1 = new RecommendRoute();
		route1.setRouteName("五渔村必游之地");
		route1.setRouteDay(3);
		route1.setSceneNum(9);
		route1.setLoveNum(2325);
		route1.setResource(R.drawable.recommend_item_iv1);
		listRoutes.add(route1);
		
		route2 = new RecommendRoute();
		route2.setRouteName("五渔村必游之地");
		route2.setRouteDay(3);
		route2.setSceneNum(9);
		route2.setLoveNum(2325);
		route2.setResource(R.drawable.recommend_item_iv1);
		listRoutes.add(route2);
		
		route3 = new RecommendRoute();
		route3.setRouteName("五渔村必游之地");
		route3.setRouteDay(3);
		route3.setSceneNum(9);
		route3.setLoveNum(2325);
		route3.setResource(R.drawable.recommend_item_iv1);
		listRoutes.add(route3);
		
		return listRoutes;
	}

	public static List<BigScene> getBigScenePs(){
		List<BigScene> scenelist = new ArrayList<BigScene>();
		BigScene sc,sc1,sc2,sc3;
		
		for(int i = 0;i<10;i++){
			sc = new BigScene();
			
			sc.setBigSceneName("景福宫景福宫");
			sc.setDowned(false);
			sc.setRecordNum(18);
			sc.setLoveNum(3252);
			sc.setSrc(R.drawable.recommend_item_iv1);
			scenelist.add(sc);
		}
		sc1 = new BigScene();
		sc1.setBigSceneName("景福宫景福宫");
		sc1.setDowned(true);
		sc1.setRecordNum(18);
		sc1.setLoveNum(3252);
		sc1.setSrc(R.drawable.recommend_item_iv1);
		scenelist.add(sc1);

		sc2 = new BigScene();
		sc2.setBigSceneName("景福宫景福宫");
		sc2.setDowned(true);
		sc2.setRecordNum(18);
		sc2.setLoveNum(3252);
		sc2.setSrc(R.drawable.recommend_item_iv1);
		scenelist.add(sc2);

		sc3 = new BigScene();
		sc3.setBigSceneName("景福宫景福宫");
		sc3.setDowned(false);
		sc3.setRecordNum(18);
		sc3.setLoveNum(3252);
		sc3.setSrc(R.drawable.recommend_item_iv1);
		scenelist.add(sc3);
		
		return scenelist;
	}



	public static List<BigScene> getMyLove(){
		List<BigScene> mylovelist = new ArrayList<BigScene>();
		BigScene love1,love2,love3,love;
		
		love1 = new BigScene();
		love1.setBigSceneName("景福宫");
		love1.setLevel(4);
		mylovelist.add(love1);
		
		love2 = new BigScene();
		love2.setBigSceneName("大明宫");
		love2.setLevel(4);
		mylovelist.add(love2);
		
		love3 = new BigScene();
		love3.setBigSceneName("景福宫景福宫");
		love3.setLevel(5);
		mylovelist.add(love3);
		for(int i=0 ;i<10 ;i++){
			love = new BigScene();
			love.setBigSceneName("景福宫");
			love.setLevel(5);
			mylovelist.add(love);
		}
		
		return mylovelist;
	}
}