package com.ne.vg.bean;

public class RouteContent {
	private int routeContentID;
	private String firstScene,secondScene,thirdScene,fourthScene,
	fifthScene,sixthScene,seventhScene;
	public int getRouteContentID() {
		return routeContentID;
	}
	public void setRouteContentID(int routeContentID) {
		this.routeContentID = routeContentID;
	}
	public String getFirstScene() {
		return firstScene;
	}
	public void setFirstScene(String firstScene) {
		this.firstScene = firstScene;
	}
	public String getSecondScene() {
		return secondScene;
	}
	public void setSecondScene(String secondScene) {
		this.secondScene = secondScene;
	}
	public String getThirdScene() {
		return thirdScene;
	}
	public void setThirdScene(String thirdScene) {
		this.thirdScene = thirdScene;
	}
	public String getFourthScene() {
		return fourthScene;
	}
	public void setFourthScene(String forthScene) {
		this.fourthScene = forthScene;
	}
	public String getFifthScene() {
		return fifthScene;
	}
	public void setFifthScene(String fifthScene) {
		this.fifthScene = fifthScene;
	}
	public String getSixthScene() {
		return sixthScene;
	}
	public void setSixthScene(String sixthScene) {
		this.sixthScene = sixthScene;
	}
	public String getSeventhScene() {
		return seventhScene;
	}
	public void setSeventhScene(String seventhScene) {
		this.seventhScene = seventhScene;
	}

	public String getSceneOfIndex(int index) {
		switch (index) {
		case 1:
			return getFirstScene();
		case 2:
			return getSecondScene();
		case 3:
			return getThirdScene();
		case 4:
			return getFourthScene();
		case 5:
			return getFifthScene();
		case 6:
			return getSixthScene();
		case 7:
			return getSeventhScene();
		default:
			return null;
		}

	}

}
