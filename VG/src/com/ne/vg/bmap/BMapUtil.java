package com.ne.vg.bmap;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * �ٶȵ�ͼ ���� ������baiduMap SDK V2.4.1��
 * @ClassName: BMapUtil 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014��7��25�� ����2:37:01
 */
public class BMapUtil {

	private final static String TAG = "BMapUtil";
	private static Context mContext;
	private static BMapManager mBMapManager;

	/*
	 * ��ʼ��BMap �����ͼ�޷�ʹ��  �����Ĳ���mContext�봫��getApplicationContext()
	 */
	public static void initBMapManager(Context context) {
		mContext = context;
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(mContext);
			if (!mBMapManager.init(new MyGeneralListener())) {
				Toast.makeText(mContext, 
						"�ٶȵ�ͼ��ʼ������!", Toast.LENGTH_LONG).show();
			}
			mBMapManager.start();
		}
		Log.v(TAG, "initEngineManager");
	}

	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(mContext, "���������������",
						Toast.LENGTH_LONG).show();
			}
			else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(mContext, "������ȷ�ļ���������",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			//����ֵ��ʾkey��֤δͨ��
			if (iError != 0) {
				//��ȨKey����
				Toast.makeText(mContext, 
						"��������ȷ����ȨKey,������������������Ƿ�������error: "+iError, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(mContext, 
						"Key��֤�ɹ�", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * ��Viewת��Bitmap
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;

	}
	
	/**
	 * ��ȡgeoPoints���ĵ�;�γ�ȷ�Χ
	 * @param geoPoints
	 * @return GeoPoint[0] geoPoints���ĵ������     GeoPoint[1] geoPoints�ľ�γ�ȷ�Χ
	 */
	public static GeoPoint[] getCenterAndSpan(GeoPoint[] geoPoints)
	{
		RouteOverlay routeOverlay = new RouteOverlay(null, null);
		MKRoute route = new MKRoute();
		route.customizeRoute(geoPoints[0], geoPoints[geoPoints.length-1], geoPoints);	
		routeOverlay.setData(route);
		GeoPoint[] center_span = new GeoPoint[2];
		center_span[0] = new GeoPoint(routeOverlay.getCenter().getLatitudeE6(), routeOverlay.getCenter().getLongitudeE6());
		center_span[1] = new GeoPoint(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
		return center_span;
	}

}
