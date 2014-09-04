package com.ne.vg.bmap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;

/**
 * ����ʱ�ѷ��򴫸����Ĺص��ˣ�
 * �ٶȵ�ͼ ��λ ������ ������baiduMap SDK V2.4.1  ��baidu loc sdk V4.1��
 * @ClassName: BMapLocationUtil 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014��7��25�� ����2:45:19
 */
public class BMapLocationUtil {

	private final static String TAG = "BMapLocationUtil";
	private Context mContext;
	private LocationClient mLocClient;
	private LocationData locData = null;
	public BDLocationListener myListener;
	private LocationClientOption option = new LocationClientOption();
//	private MapView mMapView;
	
	public BMapLocationUtil(Context mContext,BDLocationListener mListener)
	{
		this.mContext = mContext;
		//��λ��ʼ��
		mLocClient = new LocationClient(mContext);
		locData = new LocationData();
		myListener = mListener;
		mLocClient.registerLocationListener(myListener);
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		option.setOpenGps(true);//��gps
		option.setCoorType("bd09ll");     //������������
		option.setScanSpan(3000);
		option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);	
	}

//	private MyLocationOverlay myLocationOverlay = null;
//	public BMapLocationUtil(Context mContext,BDLocationListener mListener,MapView mMapView)
//	{
//		this.mContext = mContext;
//		this.mMapView = mMapView;
//		initOrientation();
//		//��λ��ʼ��
//		mLocClient = new LocationClient(mContext);
//		locData = new LocationData();
//		myListener = mListener;
//		mLocClient.registerLocationListener(myListener);
//		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
//		option.setOpenGps(true);//��gps
//		option.setCoorType("bd09ll");     //������������
//		option.setScanSpan(2000);
//		option.setNeedDeviceDirect(true);
//		mLocClient.setLocOption(option);
//		//��λͼ���ʼ��
//		myLocationOverlay = new MyLocationOverlay(mMapView);
//		//��Ӷ�λͼ��
//		mMapView.getOverlays().add(myLocationOverlay);
//		myLocationOverlay.enableCompass();
//		//�޸Ķ�λ���ݺ�ˢ��ͼ����Ч
//		mMapView.refresh();
//	}
	
	/**
	 * ���ö�λ���ʱ�䣨ms��  0Ϊֻ��λһ��
	 * @param ms_time
	 */
	public void setScanSpan(int ms_time)
	{
		option.setScanSpan(ms_time);
		mLocClient.setLocOption(option);
	}

	/**
	 * ֻ��λһ��
	 */
	public void requestLocation()
	{
		setScanSpan(0);
		if(mLocClient.isStarted())
			mLocClient.requestLocation();
		else
		{
			mLocClient.start();
			registerOrientation();
		}
	}
	
	/**
	 * ��λÿ2��һ��
	 */
	public void requestLocationEvery2() {
		setScanSpan(3000);
		mLocClient.start();
		registerOrientation();
	}
	
	/**
	 * �����Ƿ�λ����ing
	 * @return
	 */
	public boolean isStarted()
	{
		return mLocClient.isStarted();
	}
	
	/**
	 * ��ʼ��λ
	 */
	public void start() {
		mLocClient.start();
		registerOrientation();
	}
	
	/**
	 * ��ͣ��λ
	 */
	public void stop() {
		mLocClient.stop();
		unRegisterOrientation();
	}
	
	/**
	 * ���ٶ�λ
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		mLocClient.unRegisterLocationListener(myListener);
		mLocClient.stop();
		mLocClient = null;
		unRegisterOrientation();
	}
	
	/**
	 * ���µ�λ������
	 * @param mBDLocation
	 */
	public void updateLocationData(BDLocation mBDLocation)
	{
		locData.latitude = mBDLocation.getLatitude();
		locData.longitude = mBDLocation.getLongitude();
		//�������ʾ��λ����Ȧ����accuracy��ֵΪ0����
		locData.accuracy = mBDLocation.getRadius();
		// �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
		//locData.direction = location.getDirection();
	}
	
//	/**
//	 * �ö�λ�����ڵ�ͼ������ʾ
//	 */
//	public void setData()
//	{
//		myLocationOverlay.setData(locData);
//		mMapView.refresh();
//	}
	
	//�������
	private SensorManager sensorManager;
	private MySensorEventListener mySensorEventListener;
	/**
	 * ���򴫸���
	 */
	private void initOrientation()
	{
		sensorManager= (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mySensorEventListener = new MySensorEventListener();
	}
	
	/**
	 * ע�᷽�򴫸���
	 */
	@SuppressWarnings({ "deprecation" })
	private void registerOrientation()
	{
//		if(sensorManager!=null&&mySensorEventListener!=null)
//		{
//			Sensor sensor_orientation=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//		    sensorManager.registerListener(mySensorEventListener,sensor_orientation, SensorManager.SENSOR_DELAY_UI);
//		}
	}
	
	/**
	 * ע�����򴫸���
	 */
	private void unRegisterOrientation()
	{
//		if(sensorManager!=null&&mySensorEventListener!=null)
//			sensorManager.unregisterListener(mySensorEventListener);
	}

	/**
	 * ���򴫸���������
	 * @ClassName: MySensorEventListener 
	 */
	private class MySensorEventListener implements  SensorEventListener{

		@SuppressWarnings("deprecation")
		@Override
		//���Եõ�������ʵʱ���������ı仯ֵ
		public void onSensorChanged(SensorEvent event) {
			//���򴫸���
			if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
				//x��ʾ�ֻ�ָ��ķ�λ��0��ʾ��,90��ʾ����180��ʾ�ϣ�270��ʾ��
				float x = event.values[SensorManager.DATA_X];
				locData.direction = x;//�Ż��ٶȵ�ͼ����׼������
				Log.v(TAG, "����"+x);
			}
		}
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
	}

}
