package com.ne.vg.bean;

/*
 * ´ó¾°µã
 */
public class BigScene {

	private String bigSceneName,resource;
	private int cityID,bigSceneID,contentID,level,loveNum,recordNum,isCollected;
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	private double latitude,longtitude;
	private boolean isDowned;
	
	public String getBigSceneName() {
		return bigSceneName;
	}
	public void setBigSceneName(String bigSceneName) {
		this.bigSceneName = bigSceneName;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public int getBigSceneID() {
		return bigSceneID;
	}
	public void setBigSceneID(int bigSceneID) {
		this.bigSceneID = bigSceneID;
	}
	public int getContentID() {
		return contentID;
	}
	public void setContentID(int contentID) {
		this.contentID = contentID;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
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
	public boolean isDowned() {
		return isDowned;
	}
	public void setDowned(boolean isDowned) {
		this.isDowned = isDowned;
	}
	public int getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(int loveNum) {
		this.loveNum = loveNum;
	}
	public int getIsCollected() {
		return isCollected;
	}
	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}
	
	
	
	
}
