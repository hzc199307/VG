package com.ne.vg.util;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * android自带定位工具
 * @ClassName: LocationUtil 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-1 下午2:29:48
 */
public class LocationUtil {

	LocationManager manager;

	LocationListener mLocationListener;
	Context mContext;
	public LocationUtil(Context mContext,LocationListener mLocationListener) {

		this.mContext = mContext;
		manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		this.mLocationListener = mLocationListener; 
	}

	public void requestGPSLoc()
	{
		if(mLocationListener==null)
			return;
		Toast.makeText(mContext, "requestGPSLoc", Toast.LENGTH_SHORT).show();
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, mLocationListener);
	}

	public void requestNetLoc()
	{
		if(mLocationListener==null)
			return;
		Toast.makeText(mContext, "requestNetLoc", Toast.LENGTH_SHORT).show();
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				1000, 0, mLocationListener);
	}

	public void stopLoc()
	{
		manager.removeUpdates(mLocationListener);
	}

}
