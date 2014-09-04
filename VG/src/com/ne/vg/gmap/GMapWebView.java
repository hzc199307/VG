package com.ne.vg.gmap;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.ne.vg.VGApplication;
import com.ne.vg.bmap.BMapLocationUtil;
import com.ne.vg.bmap.BMapOtherUtil;
import com.ne.vg.bmap.LatLng;
import com.ne.vg.util.LocationUtil;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * google��ͼ WebView 
 * @ClassName: GMapWebView 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-12 ����3:23:12
 */
public class GMapWebView extends WebView {

	private final String TAG = "GMapWebView"; 
	private final String CHINA_URL = "file:///android_asset/map.html";
	private final String FOREIGN_URL = "";

	private Context context;
	public GMapWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		this.context = context;
		init();

		//view = LayoutInflater.from(context).inflate(R.layout.location_button, this);// ���ò����ļ�
		//		location_button = new ImageButton(context);
		//		location_button.setImageResource(R.drawable.location_button_loc);
		//		location_button.setBackgroundColor(0xffffffff);

		//		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0));
		//		addView(view);
		//		location_button.setVisibility(View.GONE);
		//		ViewGroup.MarginLayoutParams lp = new MarginLayoutParams(200, 200);
		//		lp.setMargins(100, 0, 0, 10);
		//		location_button.setLayoutParams(lp);
		//		
		//		ViewGroup.MarginLayoutParams mlp = new MarginLayoutParams(50, 50);
		//		mlp.topMargin = 200;
		//		mlp.setMargins(200, 200, 200, 200);
		//		Toast.makeText(getContext(), ""+this.getLayoutParams().height, Toast.LENGTH_SHORT);//���ʱ���޷���ȡ
		//		WebView.LayoutParams lp = new LayoutParams(50, 50, 200, 200);
		//		location_button.setLayoutParams(mlp);
		//		addView(location_button);

		//		new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		//		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		//		progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
		//        addView(progressBar);

	}
	//	View view;
	//	ImageButton location_button;
	//	private ProgressBar progressBar;

	public void init()
	{
		setupWebView();

//		listMarkerItems = new ArrayList<MarkerItem>();
//		MarkerItem item1,item2,item3;
//		item1 = new MyMarkerItem(23.0, 113, "1", "");
//		item2 = new MyMarkerItem(23.500000, 113.500000, "2", "marker1.png");
//		item3 = new MyMarkerItem(23.100000, 113.100000, "3", "marker1.png");
//		listMarkerItems.add(item1);
//		listMarkerItems.add(item2);
//		listMarkerItems.add(item3);
//
//		setMarkerData(listMarkerItems, null, true, null);
//		setRouteData(listMarkerItems);
//		showMarkerSpan();

	}
	
	public void setMarkerItems(List<MarkerItem> listMarkerItems) {
		// TODO Auto-generated method stub
		this.listMarkerItems = listMarkerItems;
		if(listMarkerItems!=null)
		{
			setMarkerData(listMarkerItems, null, true, null);
//			setRouteData(listMarkerItems);
			showMarkerSpan();
		}
	}

	private void setupWebView()
	{
		/*
		 * webSettings ������WebView�е�״̬��Ϣ��
		 * ��WebView��һ�α�����ʱ��webSetting�д洢�Ķ�ΪĬ��ֵ��
		 * WebSetting��WebViewһһ�󶨵ġ�
		 * ���webView�������ˣ���ô�����ٴε���webSetting�еķ���ʱ�����׳��쳣��
		 */
		WebSettings webSettings = this.getSettings();
		//����+����
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//������webview��ִ��JavaScript����
		webSettings.setJavaScriptEnabled(true);
		//����webview�Ƿ�֧������
		webSettings.setSupportZoom(false);
		//���ر���html���룬�˴���λ��assetsĿ¼�£�ͨ��MAP_URL���ʡ�
		this.loadUrl("file:///android_asset/map.html");

		/** Allows JavaScript calls to access application resources **/  
		/*
		 * Ϊjs�ṩһ��������ע��÷���һ�㲻д��UI�߳���
		 * addJavascriptInterface(Object obj, String interfaceName)
		 * obj����һ��java������������һ���ʵ��һ���Լ����࣬�������ṩ����Ҫ�ṩ��javascript���ʵķ���
		 * interfaceName���Ƿ���������obj�������ķ���ʱ�����õ���js���󣬵��÷���Ϊwindow.interfaceName.������()
		 */
		this.addJavascriptInterface(new JsInterfaceObject(),"demo");


		//		btn.setOnClickListener(new View.OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				Log.d(TAG, "Android������js����");
		//				/*
		//				 * ͨ��webView.loadUrl("javascript:xxx")��ʽ�Ϳ��Ե��õ�ǰ��ҳ�е�����
		//				 * Ϊxxx��javascript����
		//				 */
		//				webView.loadUrl("javascript:rfInfo("+jsonStr+")");
		//			}
		//		});
		//		//webView.addJavascriptInterface(new Object(), "demo"); 
		//		Log.v(TAG, "addJavascriptInterface");
		//	}
		this.setBackgroundColor(0X00000000);//TODO ��������δ����ʱ��ı���ɫ
	}


	/**
	 * ���ó��й��ĵ�ͼ����
	 */
	public void setChinaType()
	{
		this.loadUrl(CHINA_URL);
	}

	/**
	 * ���ó�����ĵ�ͼ����
	 */
	public void setForeignType()
	{
		this.loadUrl(FOREIGN_URL);
	}


	////////////////////////////////////////GMapMarkerUtil/////////////////////////////////////////////

	private List<MarkerItem> listMarkerItems;
	private int dataSize;
	/**
	 * GMap�ı��item
	 * @ClassName: MarkerItem 
	 * @author ���ǳ�
	 * @Description: TODO 
	 */
	public interface MarkerItem
	{
		String getTitle();
		double getLat();
		double getLng();
		String getIcon();

	}
	public static class MyMarkerItem implements MarkerItem,Latlng
	{
		String title,icon;
		double lat,lng;
		
		public MyMarkerItem(double lat,double lng,String title) {
			// TODO Auto-generated constructor stub
			this.lat = lat;
			this.lng = lng;
			this.title = title;
			this.icon = "marker1.png";
		}

		public MyMarkerItem(double lat,double lng,String title,String icon) {
			// TODO Auto-generated constructor stub
			this.lat = lat;
			this.lng = lng;
			this.title = title;
			this.icon = icon;
		}
		public String getTitle()
		{
			return title;
		}
		public double getLat()
		{
			return lat;
		}
		public double getLng()
		{
			return lng;
		}
		public String getIcon()
		{
			return icon;
		}

	}
	/**
	 * ��ע�ı�������Listener
	 * @ClassName: OnMarkerClickListener 
	 */
	public interface OnMarkerClickListener
	{
		public abstract boolean onMarkerClick(MarkerItem markerItem);//���뱻�����MarkerItem
	}
	/**
	 * InfoWindow��������Listener
	 * @ClassName: PopupClickListener 
	 */
	public interface InfoWindowClickListener {

		public abstract void onClickedInfoWindow(MarkerItem markerItem);
	}

	private OnMarkerClickListener onMarkerClickListener = null;
	private InfoWindowClickListener infoWindowClickListener = null;
	private boolean infoWindowStatus = false;
	/**
	 * �����й�Marker������
	 * @param listMarkerItems  Marker�б�
	 * @param onMarkerClickListener  Marker����¼�
	 * @param infoWindowStatus     �Ƿ��е����ĵ��� (Ĭ����)
	 * @param infoWindowClickListener    ��������¼�
	 */
	public void setMarkerData(List<MarkerItem> listMarkerItems,OnMarkerClickListener onMarkerClickListener,boolean infoWindowStatus,InfoWindowClickListener infoWindowClickListener)
	{
		this.listMarkerItems = listMarkerItems;
		dataSize = listMarkerItems.size();
		Toast.makeText(getContext(), "������"+dataSize+"��Marker", Toast.LENGTH_SHORT).show();
		this.onMarkerClickListener = onMarkerClickListener;
		this.infoWindowClickListener = infoWindowClickListener;
		this.infoWindowStatus = infoWindowStatus;
	}

	private boolean isPageFinished = false;//ҳ���Ƿ�������
	/**
	 * չʾ���е�Marker��
	 * ���ҳ������꣬��ֱ��ִ�д��룻  ���û�����꣬�ȼ��������ִ�С�
	 */
	public void showMarkerSpan()
	{

		if(isPageFinished == true)
		{
			Toast.makeText(getContext(), "showMarker,isPageFinished "+isPageFinished, Toast.LENGTH_SHORT).show();
			MarkerItem markerItem;
			for(int i=0;i<dataSize;i++)
			{
				markerItem = listMarkerItems.get(i);
				//this.loadUrl("javascript:addMarker(23.0, 113.0, 'marker1.png', 'A')");
				loadUrl("javascript:addMarker("+markerItem.getLat()+","+ markerItem.getLng()+",'"+ markerItem.getIcon()+"','"+ markerItem.getTitle()+"')");
			}
			showSpan();
			loadUrl("javascript:setInfoWindowStatus("+this.infoWindowStatus+")");
		}
		else
		{
			this.setWebViewClient(new WebViewClient()
			{
				@Override
				public void onPageFinished(WebView view, String url) 
				{
					// TODO Auto-generated method stub
					super.onPageFinished(view, url);
					isPageFinished = true;
					MarkerItem markerItem;
					for(int i=0;i<dataSize;i++)
					{
						markerItem = listMarkerItems.get(i);
						//this.loadUrl("javascript:addMarker(23.0, 113.0, 'marker1.png', 'A')");
						loadUrl("javascript:addMarker("+markerItem.getLat()+","+ markerItem.getLng()+",'"+ markerItem.getIcon()+"','"+ markerItem.getTitle()+"')");
					}
					//showRoute();
					showSpan();
					loadUrl("javascript:setInfoWindowStatus("+infoWindowStatus+")");

				}
			});
		}
	}

	/**
	 * �Ƴ����е�Marker
	 * ��Ҫ�� PageFinished ֮����ܳɹ�����
	 */
	public void removeMarker() 
	{
		this.loadUrl("javascript:showAllMarker(false)");
	}

	/**
	 * �պ���ʾ���е� ���
	 * ��Ҫ�� PageFinished ֮����ܳɹ�����
	 */
	public void showSpan()
	{
		this.loadUrl("javascript:showSpan()");
	}

	/**
	 * �պ���ʾ���е� ���   ������)
	 * ��Ҫ�� PageFinished ֮����ܳɹ�����
	 */
	public void showSpanWithAnimation()
	{
		this.loadUrl("javascript:showSpanWithAnimation()");
	}


	/**
	 * ά�� ���� ������
	 * @ClassName: Latlng 
	 */
	public interface Latlng
	{
		public double getLat();
		public double getLng();
	}

	private List<Latlng> listLatlngs;
	/**
	 * ����·������
	 * @param listLatlngs  ����㵽�յ��·������
	 */
	public void setRouteData(List<?> listLatlngs)
	{
		this.listLatlngs = (List<Latlng>) listLatlngs;	
	}
	/**
	 * չʾ·��
	 */
	public void showRoute()
	{
		int size = listLatlngs.size();
		Latlng latlng;
		for(int i=0;i<size;i++)
		{
			latlng = (Latlng) listLatlngs.get(i);
			this.loadUrl("javascript:addPoint("+latlng.getLat()+","+latlng.getLng()+")");
		}
		if(size>1)
			this.loadUrl("javascript:showRoute(true)");
	}
	/**
	 * �Ƴ�·��
	 */
	public void removeRoute()
	{
		this.loadUrl("javascript:showRoute(false)");
	}


	private LocationUtil locationUtil;
	private BMapLocationUtil bMapLocationUtil;
	/**
	 * JS map����android����Ľӿں���
	 * ע��:android 4.2 ֮��汾�ṩ��js���õĺ����������ע�����@JavascriptInterface
	 * @ClassName: JsInterfaceObject 
	 * @author ���ǳ�
	 */
	public class JsInterfaceObject extends Object
	{
		Handler handler = new Handler();

		/**
		 * ��Ϊ���Դ�����
		 */
		@JavascriptInterface 
		public void testMap()
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js������Android���� testMap", Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js������Android���� testMap");
				}
			});

		}

		/**
		 * �Ƴ�·��
		 */
		@JavascriptInterface
		public void removeRoute()
		{
			handler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadUrl("javascript:showRoute(false)");

				}
			});
		}

		/**
		 * marker������¼�
		 */
		@JavascriptInterface 
		public void onMarkerClick(final int index)
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js������Android����onMarkerClick "+index, Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js������Android����onMarkerClick "+index);
					if(onMarkerClickListener!=null)
						onMarkerClickListener.onMarkerClick(listMarkerItems.get(index));
				}
			});

		}

		/**
		 * infoWindow������¼�
		 */
		@JavascriptInterface 
		public void onClickedInfoWindow(final int index)
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js������Android����onClickedInfoWindow "+index, Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js������Android����onClickedInfoWindow "+index);
					if(infoWindowClickListener!=null)
						infoWindowClickListener.onClickedInfoWindow(listMarkerItems.get(index));
				}
			});

		}

		/**
		 * ��ͼ������¼�
		 */
		@JavascriptInterface 
		public void onClickedMap()
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js������Android����onClickedMap ", Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js������Android����onClickedMap ");
					if(onMapClickListener!=null)
						onMapClickListener.onClick(getRootView());
				}
			});

		}

	}

	private View.OnClickListener onMapClickListener;
	@Override
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onMapClickListener = onClickListener;
	}
	
	public void setCenter(double lat,double lng) {
		// TODO Auto-generated method stub
		loadUrl("javascript:setCenter("+lat+","+lng+")");
	}


	/**
	 * ��λ ��ͼ���붨λ����
	 */
	public void requestLoc() {
		// TODO Auto-generated method stub
		if(locationUtil ==null)
			locationUtil = new LocationUtil(getContext(), new LocationListener() {
				

				@Override
				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChanged(Location arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), ""+arg0.getLatitude()+" "+arg0.getLongitude()+" "+arg0.getBearing(), Toast.LENGTH_SHORT).show();
					//loadUrl("javascript:setCenter("+arg0.getLatitude()+","+arg0.getLongitude()+")");
					loadUrl("javascript:setLocMarker("+arg0.getLatitude()+","+arg0.getLongitude()+","+100+")");
					locationUtil.stopLoc();
				}
			});
//		locationUtil.requestGPSLoc();
//		locationUtil.requestNetLoc();
		locationUtil.requestLoc();
		/*
				if(bMapLocationUtil==null)
					bMapLocationUtil = new BMapLocationUtil(getContext(), new BDLocationListener() {

						@Override
						public void onReceivePoi(BDLocation arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onReceiveLocation(BDLocation arg0) {
							// TODO Auto-generated method stub
							LatLng latLng = BMapUtil.fromBaiduToGoogle(arg0);
							Toast.makeText(getContext(), ""+latLng.getLatitude()+" "+latLng.getLongitude(), Toast.LENGTH_SHORT).show();
							loadUrl("javascript:setCenter("+latLng.getLatitude()+","+arg0.getLongitude()+")");
						}
					});
				bMapLocationUtil.requestLocation();
		 */

	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onDetachedFromWindow");
		super.onDetachedFromWindow();

	}


}
