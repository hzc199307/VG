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
 * android�Դ���λ����
 * @ClassName: LocationUtil 
 * @author ���ǳ�
 * @Description: 
 * @date 2014-9-1 ����2:29:48
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
	 * GPS��λ
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
	 * Net��λ
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
	 * ���ܶ�λ ����Ҫʱʹ��baidu��λ
	 */
	public void requestLoc()
	{
		if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			// ͨ��GPS���Ƕ�λ����λ������Ծ�ȷ���֣�ͨ��24�����Ƕ�λ��������Ϳտ��ĵط���λ׼ȷ���ٶȿ죩 
			if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				requestGPSLoc();
			// ͨ��WLAN���ƶ�����(3G/2G)ȷ����λ�ã�Ҳ����AGPS������GPS��λ����Ҫ���������ڻ��ڸ������Ⱥ��ï�ܵ����ֵȣ��ܼ��ĵط���λ�� 
			if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
				requestNetLoc();
		}
		//�ٶȵ�ͼ�Ķ�λ�������ڶ�λ����δ�����������ʹ��
		else if(SystemUtil.getGPRSStatus()||SystemUtil.getWifiStatus())
		{
			if(DEBUG)Log.v(TAG, "requestBMapLoc");
			bMapLocationUtil = new BMapLocationUtil(mContext, new BDLocationListener() {

				@Override
				public void onReceivePoi(BDLocation bdLocation) {	
				}

				@Override
				public void onReceiveLocation(BDLocation bdLocation) {
					mLocationListener.onLocationChanged(LocationUtil.fromBDLocationToLocation(bdLocation));
				}
			});
			bMapLocationUtil.start();
		}
		else
		{
			if(DEBUG)Log.v(TAG, "no loc");
		}
	}

	public void stopLoc()
	{
		manager.removeUpdates(mLocationListener);
		if(bMapLocationUtil!=null)
			bMapLocationUtil.stop();
	}

	/**
	 * BDLocationת����Location
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

	@Override
	protected void finalize() throws Throwable {
		stopLoc();
		if(bMapLocationUtil!=null)
			bMapLocationUtil.destroy();
		super.finalize();
	}
}
