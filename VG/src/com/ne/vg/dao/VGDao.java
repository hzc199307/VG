package com.ne.vg.dao;

import java.util.ArrayList;
import java.util.List;

import com.baidu.platform.comapi.map.base.m;
import com.ne.vg.DBHelper.TableColumns.BigSceneColumns;
import com.ne.vg.DBHelper.TableColumns.CityColumns;
import com.ne.vg.DBHelper.TableColumns.RecommendRouteColumns;
import com.ne.vg.DBHelper.TableColumns.RouteContentColumns;
import com.ne.vg.DBHelper.TableColumns.SceneContentColumns;
import com.ne.vg.DBHelper.TableColumns.SmallSceneColumns;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.City;
import com.ne.vg.bean.RecommendRoute;
import com.ne.vg.bean.RouteContent;
import com.ne.vg.bean.SceneContent;
import com.ne.vg.bean.SmallScene;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * 
 * @ClassName: VGDao 
 * @author 潘杉
 * @Description: 用来从数据库中获取数据
 * @date 2014-8-25 下午5:02:48
 */
public class VGDao {
	
	private final String TAG = "VGDao";
	
	private SQLiteDatabase db;
	//Context.MODE_PRIVATE代表该数据库是私有的只能被应用内访问
	public VGDao(Context context){
		db = context.openOrCreateDatabase("", Context.MODE_PRIVATE, null);
	}
	
	/**
	 * 
	 * @Title: getCity 
	 * @Description: 获取city列表的数据
	 * @param @return
	 * @return List<City> 
	 * @throws
	 */
	public List<City> getCity(){
		List<City> listCities = new ArrayList<City>();
		Cursor cr = db.query("City", null, null, null, null, null, null);
		int count = cr.getCount();
		Log.v(TAG,"start getCity() count = " + count);
		if(cr!=null){
			cr.moveToFirst();
			for(int i = 0; i < count; i++){
				City cb = new City();
				cb.setCityID(cr.getInt(cr.getColumnIndex(CityColumns.cityID)));
				cb.setCityName(cr.getString(cr.getColumnIndex(CityColumns.cityName)));
				cb.setCityPinyin(cr.getString(cr.getColumnIndex(CityColumns.cityPinyin)));
				cb.setLatitude(cr.getDouble(cr.getColumnIndex(CityColumns.latitude)));
				cb.setLongtitude(cr.getDouble(cr.getColumnIndex(CityColumns.longtitude)));
				cb.setResource(cr.getString(cr.getColumnIndex(CityColumns.resource)));
				cb.setRouteNum(cr.getInt(cr.getColumnIndex(CityColumns.routeNum)));
				cb.setSceneNum(cr.getInt(cr.getColumnIndex(CityColumns.sceneNum)));
				listCities.add(cb);
				cr.moveToNext();
			}
		}
		Log.v(TAG,"getCity is finished!");
		return  listCities;
		
	}

	/**
	 * 
	 * @Title: getBigScene 
	 * @Description: 根据城市id获取大景点列表
	 * @param @param cityID
	 * @param @return
	 * @return List<BigScene> 
	 * @throws
	 */
	public List<BigScene> getBigScene(int cityID){
		List<BigScene> listBigScenes = new ArrayList<BigScene>();
		String[] args = {cityID+""};
		Cursor cr = db.query("BigScene", null, "cityID=?", args, null, null, null);
		int count = cr.getCount();
		Log.v(TAG,"start getCity() count = " + count);
		if(cr!=null){
			cr.moveToFirst();
			for(int i = 0; i < count; i++){
				BigScene mBigScene = new BigScene();
				mBigScene.setBigSceneID(cr.getInt(cr.getColumnIndex(BigSceneColumns.bigCityID)));
				mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
				mBigScene.setContentID(cr.getInt(cr.getColumnIndex(BigSceneColumns.contentID)));
				//mBigScene.setDowned(cr.get(cr.getColumnIndex(BigSceneColumns.bigCityID)));
				int l = cr.getInt(cr.getColumnIndex(BigSceneColumns.bigCityID));
				if(l == 1){
					mBigScene.setDowned(true);
				}else{
					mBigScene.setDowned(false);
				}
				
				mBigScene.setLatitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.latitude)));
				mBigScene.setLevel(cr.getInt(cr.getColumnIndex(BigSceneColumns.level)));
				mBigScene.setLongtitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.longtitude)));
				mBigScene.setLoveNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.loveNum)));
				mBigScene.setRecordNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.recordNum)));
				mBigScene.setResource(cr.getString(cr.getColumnIndex(BigSceneColumns.resource)));
				listBigScenes.add(mBigScene);
				
				cr.moveToNext();
			}
		}
		Log.v(TAG,"getBigScene is finished!");
		return listBigScenes;
	}
	
	/**
	 * 
	 * @Title: getRecommendRoute 
	 * @Description: 根据城市ID获取推荐路线列表
	 * @param @param cityID
	 * @param @return
	 * @return List<RecommendRoute> 
	 * @throws
	 */
	public List<RecommendRoute> getRecommendRoute(int cityID){
		List<RecommendRoute> listRecommendRoutes = new ArrayList<RecommendRoute>();
		String[] args = {cityID+""};
		Cursor cr = db.query("RecommendRoute", null, "cityID=?", args, null, null, null);
		int count = cr.getCount();
		Log.v(TAG,"start getRecommendRoute() count = " + count);
		if(cr!=null){
			cr.moveToFirst();
			for(int i =0; i < count; i++){
				RecommendRoute mRecommendRoute = new RecommendRoute();
				mRecommendRoute.setCollectNum(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.collectNum)));
				mRecommendRoute.setResource(cr.getString(cr.getColumnIndex(RecommendRouteColumns.resource)));
				mRecommendRoute.setRouteContentID(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeContentID)));
				mRecommendRoute.setRouteDay(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeDay)));
				mRecommendRoute.setRouteID(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeID)));
				mRecommendRoute.setRouteName(cr.getString(cr.getColumnIndex(RecommendRouteColumns.routeName)));
				mRecommendRoute.setSceneNum(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.sceneNum)));
				
				listRecommendRoutes.add(mRecommendRoute);
				cr.moveToNext();
			}
		}
		return listRecommendRoutes;
	}
	
	/**
	 * 
	 * @Title: getSceneContent 
	 * @Description: 通过大景点ID获取大景点列表的内容
	 * @param @param contentID
	 * @param @return
	 * @return SceneContent 
	 * @throws
	 */
	public SceneContent getSceneContent(int contentID){
		SceneContent mSceneContent = new SceneContent();
		String[] args = {contentID + ""};
		Cursor cr = db.query("SceneContent", null, "contentID=?", args, null, null, null);
		if(cr!=null){
			mSceneContent.setAddress(cr.getString(cr.getColumnIndex(SceneContentColumns.address)));
			
			mSceneContent.setContent(cr.getString(cr.getColumnIndex(SceneContentColumns.content)));
			mSceneContent.setPrice(cr.getString(cr.getColumnIndex(SceneContentColumns.price)));
			mSceneContent.setRecommendLevel(cr.getInt(cr.getColumnIndex(SceneContentColumns.recommendLevel)));
			mSceneContent.setRecommendScene(cr.getString(cr.getColumnIndex(SceneContentColumns.recommendScnene)));
			mSceneContent.setRelativeScene(cr.getString(cr.getColumnIndex(SceneContentColumns.relativeScene)));
			mSceneContent.setRoute(cr.getString(cr.getColumnIndex(SceneContentColumns.route)));
			mSceneContent.setTelephone(cr.getString(cr.getColumnIndex(SceneContentColumns.telephone)));
			mSceneContent.setWebsite(cr.getString(cr.getColumnIndex(SceneContentColumns.website)));
			mSceneContent.setWorkingTime(cr.getString(cr.getColumnIndex(SceneContentColumns.workingTime)));
		}
		
		
		return mSceneContent;
	}
	
	/**
	 * 
	 * @Title: getSmallScene 
	 * @Description: TODO
	 * @param @param bigSceneID
	 * @param @return
	 * @return List<SmallScene> 
	 * @throws
	 */
	public List<SmallScene> getSmallScene(int bigSceneID){
		List<SmallScene> listSmallScenes = new ArrayList<SmallScene>();
		String[] args = {bigSceneID +""};
		Cursor cr = db.query("SmallScne", null, "bigSceneID=?", args, null, null, null);
		int count = cr.getCount();
		if(cr!=null){
			cr.moveToFirst();
			for(int i = 0; i <count; i++){
				SmallScene mSmallScene = new SmallScene();
				mSmallScene.setContent(cr.getString(cr.getColumnIndex(SmallSceneColumns.content)));
				mSmallScene.setLatitude(cr.getDouble(cr.getColumnIndex(SmallSceneColumns.latitude)));
				mSmallScene.setLongtitude(cr.getDouble(cr.getColumnIndex(SmallSceneColumns.longtitude)));
				mSmallScene.setResource(cr.getString(cr.getColumnIndex(SmallSceneColumns.resource)));
				mSmallScene.setSmallSceneID(cr.getInt(cr.getColumnIndex(SmallSceneColumns.smallSceneID)));
				mSmallScene.setSmallSceneName(cr.getString(cr.getColumnIndex(SmallSceneColumns.smallSceneName)));
				
				listSmallScenes.add(mSmallScene);
				cr.moveToNext();
			}
		}
		return listSmallScenes;
	}
	
	/**
	 * 
	 * 这个有可能得修改下。。。
	 * @Title: getRouteContent 
	 * @Description: TODO
	 * @param @param contentID
	 * @param @return
	 * @return List<RouteContent> 
	 * @throws
	 */
	public List<RouteContent> getRouteContent(int routeContentID){
		List<RouteContent> listRouteContents  = new ArrayList<RouteContent>();
		String[] args = {routeContentID +""};
		Cursor cr = db.query("RouteContent", null, "crouteContentID=?", args, null, null, null);
		int count = cr.getCount();
		if(cr!=null)
		{
			cr.moveToFirst();
			for(int i = 0; i< count; i++){
				RouteContent mRouteContent = new RouteContent();
				mRouteContent.setFirstScene(cr.getString(cr.getColumnIndex(RouteContentColumns.firstScene)));
				mRouteContent.setSecondScene(cr.getString(cr.getColumnIndex(RouteContentColumns.secondScene)));
				mRouteContent.setThirdScene(cr.getString(cr.getColumnIndex(RouteContentColumns.thirdScene)));
				mRouteContent.setForthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.forthScene)));
				mRouteContent.setFifthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.fifthScene)));
				mRouteContent.setSixthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.sixthScene)));
				mRouteContent.setSeventhScene(cr.getString(cr.getColumnIndex(RouteContentColumns.seventhScene)));
				listRouteContents.add(mRouteContent);
				
				cr.moveToNext();
			
			}
			
			 
		}
		return listRouteContents;
	}

}
