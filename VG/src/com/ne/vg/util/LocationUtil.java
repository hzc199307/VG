package com.ne.vg.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.ne.vg.bmap.BMapLocationUtil;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * android自带定位工具
 * @ClassName: LocationUtil 
 * @author 贺智超
 * @Description: 
 * @date 2014-9-1 下午2:29:48
 */
public class LocationUtil {

	private final static String TAG = "LocationUtil";
	private final static boolean DEBUG = true;

	LocationManager manager;
	LocationListener mLocationListener;
	Context mContext;
	public LocationUtil(Context mContext,LocationListener mLocationListener) {

		this.mContext = mContext;
		manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		this.mLocationListener = mLocationListener; 
	}

	/**
	 * GPS定位
	 */
	public void requestGPSLoc()
	{
		if(mLocationListener==null)
			return;
		if(DEBUG)Log.v(TAG, "requestGPSLoc");
		Toast.makeText(mContext, "requestGPSLoc", Toast.LENGTH_SHORT).show();
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, mLocationListener);
	}

	/**
	 * Net定位
	 */
	public void requestNetLoc()
	{
		if(mLocationListener==null)
			return;
		if(DEBUG)Log.v(TAG, "requestNetLoc");
		Toast.makeText(mContext, "requestNetLoc", Toast.LENGTH_SHORT).show();
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				1000, 0, mLocationListener);
	}

	private BMapLocationUtil bMapLocationUtil = null;
	/**
	 * 智能定位 在需要时使用baidu定位
	 */
	public void requestLoc()
	{
		if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快） 
			if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				requestGPSLoc();
			// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位） 
			if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
				requestNetLoc();
		}
		//百度地图的定位，可以在定位服务未开启的情况下使用
		else if(SystemUtil.getGPRSStatus()||SystemUtil.getWifiStatus())
		{
			if(DEBUG)Log.v(TAG, "requestBMapLoc");
			if(bMapLocationUtil==null)
				bMapLocationUtil = new BMapLocationUtil(mContext, bdLocationListener);
			bMapLocationUtil.requestLocation();//bMapLocationUtil.start();
		}
		else
		{
			if(DEBUG)Log.v(TAG, "no loc");
		}
	}
	
	private BDLocationListener bdLocationListener = new BDLocationListener() {
		
		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "onReceiveLocation from baidu", Toast.LENGTH_SHORT).show();
			Log.v(TAG, "BMapLocationUtil BDLocationListener onReceiveLocation");
			mLocationListener.onLocationChanged(LocationUtil.fromBDLocationToLocation(bdLocation));
		}
	};

	public void stopLoc()
	{
		manager.removeUpdates(mLocationListener);
		if(bMapLocationUtil!=null)
			bMapLocationUtil.stop();
	}

	/**
	 * BDLocation转换成Location
	 * @param bdLocation
	 * @return Location
	 */
	public static Location fromBDLocationToLocation(BDLocation bdLocation)
	{
		Location location = new Location("");
		location.setLatitude(bdLocation.getLatitude());
		location.setLongitude(bdLocation.getLongitude());
		return location;
	}

//	@Override
//	protected void finalize() throws Throwable {
//		stopLoc();
//		if(bMapLocationUtil!=null)
//			bMapLocationUtil.destroy();
//		super.finalize();
//	}
}
