package com.ne.vg.DBHelper;

public class TableColumns {
	public interface CityColumns{
		public static final String cityName = "cityName";
		public static final String cityPinyin = "cityPinyin";
		public static final String latitude = "latitude";
		public static final String longtitude = "longtitude";
		public static final String cityID = "cityID";
		public static final String routeNum = "routeNum";
		public static final String sceneNum = "sceneNum";
		public static final String resource = "resource";
	}
	public interface RecommendRouteColumns{
		public static final String routeName = "routeName";
		public static final String routeID = "routeID";
		public static final String cityID = "cityID";
		public static final String sceneNum = "sceneNum";
		public static final String routeDay = "routeDay";
		public static final String collectNum = "collectNum";
		public static final String routeContentID = "routeContentID";
		public static final String resource = "resource";
	}
	public interface RouteContentColumns{
		public static final String routeContentID = "routeContentID";
		public static final String firstScene = "firstScene";
		public static final String secondScene = "secondScene";
		public static final String thirdScene = "thirdScene";
		public static final String forthScene = "forthScene";
		public static final String fifthScene = "fifthScene";
		public static final String sixthScene = "sixthScene";
		public static final String seventhScene = "seventhScene";
	}
	public interface BigSceneColumns{
		public static final String bigSceneName = "bigScenename";
		public static final String latitude = "latitude";
		public static final String longtitude = "longtitude";
		public static final String cityID = "cityID";
		public static final String bigCityID = "bigCityID";
		public static final String level = "level";
		public static final String resource = "resource";
		public static final String contentID = "contentID";
		public static final String isDowned = "isDowned";
		public static final String loveNum = "loveNum";
		public static final String recordNum = "recordNum";
		
	}
	public interface SceneContentColumns{
		public static final String contentID = "contentID";
		public static final String recommendLevel = "recommendLevel";
		public static final String content = "content";
		public static final String recommendScnene = "recommendScene";
		public static final String address = "adress";
		public static final String route = "route";
		public static final String workingTime = "workingTime";
		public static final String website = "website";
		public static final String telephone = "telephone";
		public static final String price = "price";
		public static final String relativeScene = "relativeScene";
	}
	public interface SmallSceneColumns{
		public static final String smallSceneName = "smallSceneName";
		public static final String latitude = "latitude";
		public static final String longtitude = "longtitude";
		public static final String bigSceneID = "bigSceneID";
		public static final String smallSceneID = "smallSceneID";
		public static final String content = "content";
		public static final String resource = "resource";
	}
}
