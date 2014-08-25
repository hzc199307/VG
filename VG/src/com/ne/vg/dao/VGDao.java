package com.ne.vg.dao;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.DBHelper.TableColumns.BigSceneColumns;
import com.ne.vg.DBHelper.TableColumns.CityColumns;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.City;
import com.ne.vg.bean.RecommendRoute;

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
		Cursor cr = db.query("bigScene", null, "cityID=?", args, null, null, null);
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

	public List<RecommendRoute> getRecommendRoute(int cityID){
		List<RecommendRoute> listRecommendRoutes = new ArrayList<RecommendRoute>();
		
		return listRecommendRoutes;
	}
}
