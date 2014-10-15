package com.ne.vg.dao;

import java.util.ArrayList;
import java.util.List;

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
import com.ne.vg.util.LogUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * 
 * @ClassName: VGDao 
 * @author ��ɼ
 * @Description: ���������ݿ��л�ȡ����
 * @date 2014-8-25 ����5:02:48
 */
public class VGDao {

	private final String TAG = "VGDao";

	private SQLiteDatabase db;
	//Context.MODE_PRIVATE��������ݿ���˽�е�ֻ�ܱ�Ӧ���ڷ���
	public VGDao(Context context){
		//��ȡ�ⲿsd���ϵ��ļ���������Ǹ�����
		//db = SQLiteDatabase.openDatabase("/sdcard/com.ne.vg/databases/VG_text.db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		db = context.openOrCreateDatabase
				("/sdcard/com.ne.vg/databases/VG_test.db", Context.MODE_PRIVATE, null);
	}

	private static VGDao instance;
	public synchronized static VGDao getInstance(Context context) {  
		if (instance == null) {  
			instance = new VGDao(context);  
		}   
		return instance;  
	}  

	/**
	 * 
	 * @Title: getCityList 
	 * @Description: ��ȡcity�б������
	 * @param
	 * @return List<City> 
	 * @throws
	 */
	public List<City> getCityList(){
		List<City> listCities = new ArrayList<City>();
		Cursor cr = db.query("city", null, null, null, null, null, null);
		int count = cr.getCount();
		LogUtil.d(TAG,"start getCity() count = " + count);
		if(cr!=null&&cr.getCount()>0){
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
		LogUtil.d(TAG,"getCity is finished!");
		return  listCities;
	}

	/**
	 * ͨ������ID��ȡ���ж���
	 * @param cityID
	 * @return
	 */
	public City getCity(int cityID) {
		City cb = null;
		String[] args = {cityID+""};
		Cursor cr = db.query("city", null, "cityID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			cb = new City();
			cb.setCityID(cr.getInt(cr.getColumnIndex(CityColumns.cityID)));
			cb.setCityName(cr.getString(cr.getColumnIndex(CityColumns.cityName)));
			cb.setCityPinyin(cr.getString(cr.getColumnIndex(CityColumns.cityPinyin)));
			cb.setLatitude(cr.getDouble(cr.getColumnIndex(CityColumns.latitude)));
			cb.setLongtitude(cr.getDouble(cr.getColumnIndex(CityColumns.longtitude)));
			cb.setResource(cr.getString(cr.getColumnIndex(CityColumns.resource)));
			cb.setRouteNum(cr.getInt(cr.getColumnIndex(CityColumns.routeNum)));
			cb.setSceneNum(cr.getInt(cr.getColumnIndex(CityColumns.sceneNum)));
		}
		return cb;
	}

	/**
	 * 
	 * @Title: getCityName 
	 * @Description: ͨ������ID��ȡ��������
	 * @param @param cityID
	 * @param @return
	 * @return String 
	 * @throws
	 */
	public String getCityName(int cityID)
	{
		String name = "";
		String[] args = {cityID+""};
		Cursor cr = db.query("city", null, "cityID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			name = cr.getString(cr.getColumnIndex(CityColumns.cityName));
		}
		return name;
	}

	/**
	 * 
	 * @Title: getBigSceneList 
	 * @Description: ���ݳ���id��ȡ�󾰵��б�
	 * @param @param cityID
	 * @param @return
	 * @return List<BigScene> 
	 * @throws
	 */
	public List<BigScene> getBigSceneList(int cityID){
		List<BigScene> listBigScenes = new ArrayList<BigScene>();
		String[] args = {cityID+""};
		Cursor cr = db.query("bigScene", null, "cityID=?", args, null, null, null);
		int count = cr.getCount();
		Log.d(TAG,"start getBigSceneList() count = " + count);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			for(int i = 0; i < count; i++){
				BigScene mBigScene = new BigScene();
				mBigScene.setBigSceneID(cr.getInt(cr.getColumnIndex(BigSceneColumns.bigSceneID)));
				mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
				mBigScene.setContentID(cr.getInt(cr.getColumnIndex(BigSceneColumns.contentID)));
				//mBigScene.setDowned(cr.get(cr.getColumnIndex(BigSceneColumns.bigCityID)));
				int l = cr.getInt(cr.getColumnIndex(BigSceneColumns.bigSceneID));
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
				mBigScene.setIsCollected(cr.getInt(cr.getColumnIndex(BigSceneColumns.isCollected)));
				mBigScene.setResource(cr.getString(cr.getColumnIndex(BigSceneColumns.resource)));
				listBigScenes.add(mBigScene);

				cr.moveToNext();
			}
		}
		LogUtil.d(TAG,"getBigScene is finished!");
		return listBigScenes;
	}
	
	/**
	 * ��ȡ���ղصĴ󾰵�
	 * @param cityID
	 * @return
	 */
	public List<BigScene> getCollectedBigSceneList(){
		List<BigScene> listBigScenes = new ArrayList<BigScene>();
		String[] args = {1+""};
		Cursor cr = db.query("bigScene", null, "isCollected=?", args, null, null, null);
		int count = cr.getCount();
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			for(int i = 0; i < count; i++){
				BigScene mBigScene = new BigScene();
				mBigScene.setBigSceneID(cr.getInt(cr.getColumnIndex(BigSceneColumns.bigSceneID)));
				mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
				mBigScene.setContentID(cr.getInt(cr.getColumnIndex(BigSceneColumns.contentID)));
				//mBigScene.setDowned(cr.get(cr.getColumnIndex(BigSceneColumns.bigCityID)));
				int l = cr.getInt(cr.getColumnIndex(BigSceneColumns.bigSceneID));
				if(l == 1){
					mBigScene.setDowned(true);
				}else{
					mBigScene.setDowned(false);
				}
				mBigScene.setCityID(cr.getInt(cr.getColumnIndex(BigSceneColumns.cityID)));
				mBigScene.setLatitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.latitude)));
				mBigScene.setLevel(cr.getInt(cr.getColumnIndex(BigSceneColumns.level)));
				mBigScene.setLongtitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.longtitude)));
				mBigScene.setLoveNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.loveNum)));
				mBigScene.setRecordNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.recordNum)));
				mBigScene.setIsCollected(cr.getInt(cr.getColumnIndex(BigSceneColumns.isCollected)));
				mBigScene.setResource(cr.getString(cr.getColumnIndex(BigSceneColumns.resource)));
				listBigScenes.add(mBigScene);

				cr.moveToNext();
			}
		}
		LogUtil.d(TAG,"getBigScene is finished!");
		return listBigScenes;
	}
	
	/**
	 * * 
	 * �ı�BigScene��isCollectedֵ
	 * @param bigSceneID
	 * @param status
	 * @return
	 */
	public boolean setBigSceneCollected(int bigSceneID,int status) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("isCollected", status+"");
		String[] whereArgs = {bigSceneID+""};
		if(db.update("bigScene", cv, "bigSceneID=?", whereArgs)>0)
			return true;
		else
			return false;
	}
	
	/**
	 * ��ȡBigScene��isCollectedֵ
	 * @param bigSceneID
	 * @return
	 */
	public boolean getBigSceneCollected(int bigSceneID) {
		// TODO Auto-generated method stub
		String[] args = {bigSceneID+""};
		String[] columns = {BigSceneColumns.isCollected+""};
		boolean ans = false;
		Cursor cr = db.query("bigScene", null, "bigSceneID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			ans = cr.getInt(cr.getColumnIndex(BigSceneColumns.isCollected))==1?true:false;
		}
		return ans;
	}

	public BigScene getBigScene(int BigSceneID){
		BigScene mBigScene = null;
		String[] arg = {BigSceneID+""};
		Cursor cr = db.query("bigScene", null, "bigSceneID=?", arg, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			mBigScene = new BigScene();
			cr.moveToFirst();
			mBigScene.setContentID(cr.getInt(cr.getColumnIndex(BigSceneColumns.contentID)));
			mBigScene.setCityID(cr.getInt(cr.getColumnIndex(BigSceneColumns.cityID)));
			mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
			mBigScene.setLatitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.latitude)));
			mBigScene.setLevel(cr.getInt(cr.getColumnIndex(BigSceneColumns.level)));
			mBigScene.setLongtitude(cr.getDouble(cr.getColumnIndex(BigSceneColumns.longtitude)));
			mBigScene.setLoveNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.loveNum)));
			mBigScene.setRecordNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.recordNum)));
			mBigScene.setResource(cr.getString(cr.getColumnIndex(BigSceneColumns.resource)));
			mBigScene.setIsCollected(cr.getInt(cr.getColumnIndex(BigSceneColumns.isCollected)));
		}
		return mBigScene;
	}

	public String getBigSceneName(int BigSceneID){
		String name = "";
		String[] arg = {BigSceneID+""};
		Cursor cr = db.query("bigScene", null, "bigSceneID=?", arg, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			name = cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName));
		}
		return name;
	}

	/**
	 * 
	 * @Title: getRecommendRouteList 
	 * @Description: ���ݳ���ID��ȡ�Ƽ�·���б�
	 * @param @param cityID
	 * @param @return
	 * @return List<RecommendRoute> 
	 * @throws
	 */
	public List<RecommendRoute> getRecommendRouteList(int cityID){
		List<RecommendRoute> listRecommendRoutes = new ArrayList<RecommendRoute>();
		String[] args = {cityID+""};
		Log.d(TAG, "cityID=" + cityID);
		Cursor cr = db.query("recommendRoute", null, "cityID=?", args, null, null, null);
		int count = cr.getCount();
		LogUtil.d(TAG,"start getRecommendRoute() count = " + count);
		if(cr!=null&&cr.getCount()>0){
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
	 * @Description: ͨ���󾰵�ID��ȡ�󾰵��б����ϸ����
	 * @param @param contentID
	 * @param @return
	 * @return SceneContent 
	 * @throws
	 */
	public SceneContent getSceneContent(int contentID){
		SceneContent mSceneContent = null;
		String[] args = {contentID + ""};
		Cursor cr = db.query("sceneContent", null, "contentID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			mSceneContent = new SceneContent();
			cr.moveToFirst();
			Log.d(TAG,cr.getString(cr.getColumnIndex(SceneContentColumns.address)));
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
			return mSceneContent;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 
	 * @Title: getSmallSceneList 
	 * @Description: ͨ���󾰵�ID����ȡС�����б�
	 * @param @param bigSceneID
	 * @param @return
	 * @return List<SmallScene> 
	 * @throws
	 */
	public List<SmallScene> getSmallSceneList(int bigSceneID){
		List<SmallScene> listSmallScenes = new ArrayList<SmallScene>();
		String[] args = {bigSceneID +""};
		Cursor cr = db.query("smallScene", null, "bigSceneID=?", args, null, null, null);
		int count = cr.getCount();
		if(cr!=null&&cr.getCount()>0){
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
	 * @Title: getBigSceneID 
	 * @Description: ͨ��smallSceneName��ȡbigSceneID
	 * @param @param smallSceneID
	 * @param @return
	 * @return int 
	 * @throws
	 */
	public int getBigSceneID(String smallSceneName){
		SmallScene mScene = null;
		String[] args = {smallSceneName};
		Cursor cr = db.query("smallScene", null, "smallSceneName=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			mScene = new SmallScene();
			cr.moveToFirst();
			mScene.setBigSceneID(cr.getInt(cr.getColumnIndex(SmallSceneColumns.bigSceneID)));
		}
		return mScene.getBigSceneID();	
	}

	/**
	 * 
	 * @Title: getRoute 
	 * @Description: ��Ҫ���ڻ�ȡ·�ߵ�������·����ϢID
	 * @param @param RouteID
	 * @param @return
	 * @return RecommendRoute 
	 * @throws
	 */
	public RecommendRoute getRecommendRoute(int RouteID){
		RecommendRoute mRecommendRoute = null;
		String[] args = {RouteID+ ""};
		Cursor cr = db.query("recommendRoute", null, "RouteID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			mRecommendRoute = new RecommendRoute();
			cr.moveToFirst();
			mRecommendRoute.setCollectNum(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.collectNum)));
			mRecommendRoute.setResource(cr.getString(cr.getColumnIndex(RecommendRouteColumns.resource)));
			mRecommendRoute.setRouteContentID(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeContentID)));
			mRecommendRoute.setRouteDay(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeDay)));
			mRecommendRoute.setRouteID(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.routeID)));
			mRecommendRoute.setRouteName(cr.getString(cr.getColumnIndex(RecommendRouteColumns.routeName)));
			mRecommendRoute.setSceneNum(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.sceneNum)));
			mRecommendRoute.setIsCollected(cr.getInt(cr.getColumnIndex(RecommendRouteColumns.isCollected)));
		}
		return mRecommendRoute;
	}

	/**
	 * 
	 * ����п��ܵ��޸��¡�����
	 * @Title: getRouteContent 
	 * @Description: TODO
	 * @param @param contentID
	 * @param @return
	 * @return List<RouteContent> 
	 * @throws
	 */
	public RouteContent getRouteContent(int routeContentID){
		RouteContent mRouteContent = null;
		String[] args = {routeContentID +""};
		Cursor cr = db.query("routeContent", null, "routeContentID=?", args, null, null, null);
		int count = cr.getCount();
		if(cr!=null&&cr.getCount()>0){
			mRouteContent = new RouteContent();
			cr.moveToFirst();
			mRouteContent.setFirstScene(cr.getString(cr.getColumnIndex(RouteContentColumns.firstScene)));
			mRouteContent.setSecondScene(cr.getString(cr.getColumnIndex(RouteContentColumns.secondScene)));
			mRouteContent.setThirdScene(cr.getString(cr.getColumnIndex(RouteContentColumns.thirdScene)));
			mRouteContent.setFourthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.fourthScene)));
			mRouteContent.setFifthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.fifthScene)));
			mRouteContent.setSixthScene(cr.getString(cr.getColumnIndex(RouteContentColumns.sixthScene)));
			mRouteContent.setSeventhScene(cr.getString(cr.getColumnIndex(RouteContentColumns.seventhScene)));
		}
		return mRouteContent;
	}

	/**
	 * 
	 * @Title: getRecommendSceneContent 
	 * @Description: ���ڻ�ȡ�Ƽ�·��ÿһ����徰������
	 * @param @param bigSceneID
	 * @param @return
	 * @return BigScene 
	 * @throws
	 */
	public BigScene getRecommendSceneContent(int bigSceneID){
		BigScene mBigScene = new BigScene();
		String[] args = {bigSceneID +""};
		Cursor cr = db.query("bigScene", null, "bigSceneID= ?", args, null, null, null);

		if(cr!=null){
			cr.moveToFirst();
			mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
			mBigScene.setLoveNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.loveNum)));
			mBigScene.setRecordNum(cr.getInt(cr.getColumnIndex(BigSceneColumns.recordNum)));
		}
		return mBigScene;
	}

	public String getSmallSceneName(int smallSceneID){
		SmallScene mScene = null;
		String[] args = {smallSceneID+""};
		Cursor cr = db.query("smallScene", null, "smallSceneID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			mScene = new SmallScene();
			cr.moveToFirst();
			mScene.setSmallSceneName(cr.getString(cr.getColumnIndex(SmallSceneColumns.smallSceneName)));
		}
		return mScene.getSmallSceneName();	
	}
	/**
	 * �ر����ݿ�����
	 * @Title: closeDatabase 
	 * @Description: TODO 
	 * @param 
	 * @return void 
	 * @throws
	 */
	public void closeDatabase(){
		db.close();
	}

}
