package com.ne.vg.bean;

import android.R.string;

/**
 * 
 * @ClassName: RecommendRoute 
 * @author 潘杉
 * @Description: 推荐路线
 * @date 2014-8-19 下午5:55:57
 */
public class RecommendRoute {
	private String routeName,resource;
	private int routeID,routeDay,collectNum,sceneNum,cityID,routeContentID;
	
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public int getRouteContentID() {
		return routeContentID;
	}
	public void setRouteContentID(int routeContentID) {
		this.routeContentID = routeContentID;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getRouteDay() {
		return routeDay;
	}
	public void setRouteDay(int routeDay) {
		this.routeDay = routeDay;
	}
	public int getCollectNum() {
		return collectNum;
	}
	public void setCollectNum(int loveNum) {
		this.collectNum = loveNum;
	}
	public int getSceneNum() {
		return sceneNum;
	}
	public void setSceneNum(int sceneNum) {
		this.sceneNum = sceneNum;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	
}
