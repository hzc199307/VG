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
 * @author 潘杉
 * @Description: 用来从数据库中获取数据
 * @date 2014-8-25 下午5:02:48
 */
public class VGDao {

	private final String TAG = "VGDao";

	private SQLiteDatabase db;
	//Context.MODE_PRIVATE代表该数据库是私有的只能被应用内访问
	public VGDao(Context context){
		//读取外部sd卡上的文件就用最后那个属性
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
	 * @Description: 获取city列表的数据
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
	 * 通过城市ID获取城市对象
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
	 * @Description: 通过城市ID获取城市名称
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
	 * @Description: 根据城市id获取大景点列表
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
	 * 获取被收藏的大景点
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
	 * 获取被收藏的大景点个数
	 * @return
	 */
	public int getCollectedBigSceneNum(){
		String[] args = {1+""};
		Cursor cr = db.query("bigScene", null, "isCollected=?", args, null, null, null);
		return cr.getCount();
	}
	
	/**
	 * * 
	 * 改变BigScene的isCollected值
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
	 * 获取BigScene的isCollected值
	 * @param bigSceneID
	 * @return
	 */
	public boolean getBigSceneCollected(int bigSceneID) {
		// TODO Auto-generated method stub
		String[] args = {bigSceneID+""};
		String[] columns = {BigSceneColumns.isCollected+""};
		boolean ans = false;
		Cursor cr = db.query("bigScene", columns, "bigSceneID=?", args, null, null, null);
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
			mBigScene.setBigSceneID(cr.getInt(cr.getColumnIndex(BigSceneColumns.bigSceneID)));
			mBigScene.setBigSceneName(cr.getString(cr.getColumnIndex(BigSceneColumns.bigSceneName)));
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
	 * @Title: getRecommendRoute 
	 * @Description: 主要用于获取路线的天数跟路线信息ID
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
	 * @Title: getRecommendRouteList 
	 * @Description: 根据城市ID获取推荐路线列表
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
	 * 获取RecommendRoute的isCollected值
	 * @param routeID
	 * @return
	 */
	public boolean getRecommendRouteCollected(int routeID) {
		// TODO Auto-generated method stub
		String[] args = {routeID+""};
		String[] columns = {RecommendRouteColumns.isCollected+""};
		boolean ans = false;
		Cursor cr = db.query("recommendRoute", columns, "routeID=?", args, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToFirst();
			ans = cr.getInt(cr.getColumnIndex(RecommendRouteColumns.isCollected))==1?true:false;
		}
		return ans;
	}
	
	/**
	 * * 
	 * 改变RecommendRoute的isCollected值
	 * @param routeID
	 * @param status
	 * @return
	 */
	public boolean setRecommendRouteCollected(int routeID,int status) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("isCollected", status+"");
		String[] whereArgs = {routeID+""};
		if(db.update("recommendRoute", cv, "routeID=?", whereArgs)>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 获取被收藏的路线
	 * @return
	 */
	public List<RecommendRoute> getCollectedRecommendRouteList()
	{
		List<RecommendRoute> listRecommendRoutes = new ArrayList<RecommendRoute>();
		String[] args = {1+""};
		Cursor cr = db.query("recommendRoute", null, "isCollected=?", args, null, null, null);
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
	 * 获取收藏的推荐路线数量
	 * @return
	 */
	public int getCollectedRecommendRouteNum() {
		String[] args = {1+""};
		Cursor cr = db.query("recommendRoute", null, "isCollected=?", args, null, null, null);
		return cr.getCount();
	}

	/**
	 * 
	 * @Title: getSceneContent 
	 * @Description: 通过大景点ID获取大景点列表的详细内容
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
	 * @Description: 通过大景点ID来获取小景点列表
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
	 * 获取小景点的数目
	 * @return
	 */
	public int getSmallSceneNum() {
		Cursor cr = db.query("smallScene", null, null, null, null, null, null);
		return cr.getCount();
	}

	/**
	 * 
	 * @Title: getBigSceneID 
	 * @Description: 通过smallSceneName获取bigSceneID
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
	 * 随机获取一个大景点id 没有就返回-1
	 * @return
	 */
	public int getRandomBigSceneID() {
		int id = -1;
		Cursor cr = db.query("smallScene", null, null, null, null, null, null);
		if(cr!=null&&cr.getCount()>0){
			cr.moveToPosition((int) (System.currentTimeMillis()%cr.getCount()));
			id = cr.getInt(cr.getColumnIndex(SmallSceneColumns.bigSceneID));
		}
		return id;
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
	 * @Description: 用于获取推荐路线每一天具体景点的情况
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
	 * 关闭数据库连接
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
