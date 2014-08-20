package com.ne.vg.bean;
/**
 * 
 * @ClassName: RecommendRoute 
 * @author 潘杉
 * @Description: 推荐路线
 * @date 2014-8-19 下午5:55:57
 */
public class RecommendRoute {
	private String routeName;
	private int routeDay,loveNum,sceneNum,resource;
	
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public int getResource() {
		return resource;
	}
	public void setResource(int resource) {
		this.resource = resource;
	}
	public int getRouteDay() {
		return routeDay;
	}
	public void setRouteDay(int routeDay) {
		this.routeDay = routeDay;
	}
	public int getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(int loveNum) {
		this.loveNum = loveNum;
	}
	public int getSceneNum() {
		return sceneNum;
	}
	public void setSceneNum(int sceneNum) {
		this.sceneNum = sceneNum;
	}
	
}
