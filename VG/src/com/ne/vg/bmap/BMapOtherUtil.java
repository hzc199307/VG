package com.ne.vg.bmap;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class BMapOtherUtil {

	public BMapOtherUtil() {
		// TODO Auto-generated constructor stub
	}

	public static LatLng fromBaiduToGoogle(GeoPoint baiduPoint)
	{
		GeoPoint temp = CoordinateConvert.fromGcjToBaidu(baiduPoint);
		GeoPoint googlePoint = new GeoPoint(baiduPoint.getLatitudeE6()*2-temp.getLatitudeE6(), baiduPoint.getLongitudeE6()*2-temp.getLongitudeE6());
		return new LatLng(googlePoint);
	}

	public static LatLng fromBaiduToGoogle(BDLocation bdLocation)
	{
		CoordinateConvert convert = new CoordinateConvert();
		GeoPoint baiduPoint = new GeoPoint((int)(bdLocation.getLatitude()*1E6), (int)(bdLocation.getLongitude()*1E6));
		
		GeoPoint temp = null;
		if(baiduPoint!=null)
			temp = convert.fromGcjToBaidu(baiduPoint);
		GeoPoint googlePoint = new GeoPoint(baiduPoint.getLatitudeE6()*2-temp.getLatitudeE6(), baiduPoint.getLongitudeE6()*2-temp.getLongitudeE6());
		return new LatLng(googlePoint);
	}
}
