package com.ne.vg.bean;

public class SceneContent {
	private int ContentID,recommendLevel;
	private String content,recommendScene,address,route,
			workingTime,website,telephone,price,relativeScene;
	public int getContentID() {
		return ContentID;
	}
	public void setContentID(int contentID) {
		ContentID = contentID;
	}
	public int getRecommendLevel() {
		return recommendLevel;
	}
	public void setRecommendLevel(int recommendLevel) {
		this.recommendLevel = recommendLevel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRecommendScene() {
		return recommendScene;
	}
	public void setRecommendScene(String recommendScene) {
		this.recommendScene = recommendScene;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRelativeScene() {
		return relativeScene;
	}
	public void setRelativeScene(String relativeScene) {
		this.relativeScene = relativeScene;
	}
	
}
