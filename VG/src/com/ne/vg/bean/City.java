package com.ne.vg.bean;

public class City {
	private double latitude,longtitude;
	private int cityID,routeNum,sceneNum;
	private String cityName,cityPinyin,resource;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public int getRouteNum() {
		return routeNum;
	}
	public void setRouteNum(int routeNum) {
		this.routeNum = routeNum;
	}
	public int getSceneNum() {
		return sceneNum;
	}
	public void setSceneNum(int sceneNum) {
		this.sceneNum = sceneNum;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityPinyin() {
		return cityPinyin;
	}
	public void setCityPinyin(String cityPinyin) {
		this.cityPinyin = cityPinyin;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
}
 