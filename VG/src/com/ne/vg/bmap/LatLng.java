package com.ne.vg.bmap;

import com.baidu.platform.comapi.basestruct.GeoPoint;

public class LatLng {

	private double lat;
	private double lng;

	public LatLng(double lat,double lng) {
		// TODO Auto-generated constructor stub
		this.lat = lat;
		this.lng = lng;
	}

	public LatLng(GeoPoint geoPointE6) {
		// TODO Auto-generated constructor stub
		this.lat = (double)geoPointE6.getLatitudeE6()/1000000;
		this.lng = (double)geoPointE6.getLongitudeE6()/1000000;
	}


	public double getLatitude() {
		return lat;
	}
	public void setLatitude(double lat) {
		this.lat = lat;
	}
	public double getLongitude() {
		return lng;
	}
	public void setLongitude(double lng) {
		this.lng = lng;
	}


}
