package com.ne.vg.gmap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * google地图 WebView 
 * @ClassName: GMapWebView 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:23:12
 */
public class GMapWebView extends WebView {

	private final String TAG = "GMapWebView"; 
	private final String CHINA_URL = "file:///android_asset/map.html";
	private final String FOREIGN_URL = "";

	public GMapWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		init();

		//view = LayoutInflater.from(context).inflate(R.layout.location_button, this);// 引用布局文件
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
		//		Toast.makeText(getContext(), ""+this.getLayoutParams().height, Toast.LENGTH_SHORT);//这个时候还无法获取
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

		listMarkerItems = new ArrayList<MarkerItem>();
		MarkerItem item1,item2,item3;
		item1 = new MyMarkerItem(23.0, 113, "1", "");
		item2 = new MyMarkerItem(23.500000, 113.500000, "2", "marker1.png");
		item3 = new MyMarkerItem(23.100000, 113.100000, "3", "marker1.png");
		listMarkerItems.add(item1);
		listMarkerItems.add(item2);
		listMarkerItems.add(item3);

		setMarkerData(listMarkerItems, null, true, null);
		setRouteData(listMarkerItems);
		showMarkerSpan();

	}

	private void setupWebView()
	{
		/*
		 * webSettings 保存着WebView中的状态信息。
		 * 当WebView第一次被创建时，webSetting中存储的都为默认值。
		 * WebSetting和WebView一一绑定的。
		 * 如果webView被销毁了，那么我们再次调用webSetting中的方法时，会抛出异常。
		 */
		WebSettings webSettings = this.getSettings();
		//缓存+网络
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//允许在webview中执行JavaScript代码
		webSettings.setJavaScriptEnabled(true);
		//设置webview是否支持缩放
		webSettings.setSupportZoom(false);
		//加载本地html代码，此代码位于assets目录下，通过MAP_URL访问。
		this.loadUrl("file:///android_asset/map.html");

		/** Allows JavaScript calls to access application resources **/  
		/*
		 * 为js提供一个方法，注意该方法一般不写在UI线程中
		 * addJavascriptInterface(Object obj, String interfaceName)
		 * obj代表一个java对象，这里我们一般会实现一个自己的类，类里面提供我们要提供给javascript访问的方法
		 * interfaceName则是访问我们在obj中声明的方法时候所用到的js对象，调用方法为window.interfaceName.方法名()
		 */
		this.addJavascriptInterface(new JsInterfaceObject(),"demo");


		//		btn.setOnClickListener(new View.OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				Log.d(TAG, "Android调用了js方法");
		//				/*
		//				 * 通过webView.loadUrl("javascript:xxx")方式就可以调用当前网页中的名称
		//				 * 为xxx的javascript方法
		//				 */
		//				webView.loadUrl("javascript:rfInfo("+jsonStr+")");
		//			}
		//		});
		//		//webView.addJavascriptInterface(new Object(), "demo"); 
		//		Log.v(TAG, "addJavascriptInterface");
		//	}
	}


	/**
	 * 设置成中国的地图链接
	 */
	public void setChinaType()
	{
		this.loadUrl(CHINA_URL);
	}

	/**
	 * 设置成外国的地图链接
	 */
	public void setForeignType()
	{
		this.loadUrl(FOREIGN_URL);
	}


	////////////////////////////////////////GMapMarkerUtil/////////////////////////////////////////////

	private List<MarkerItem> listMarkerItems;
	private int dataSize;
	/**
	 * GMap的标记item
	 * @ClassName: MarkerItem 
	 * @author 贺智超
	 * @Description: TODO 
	 */
	public interface MarkerItem
	{
		String getTitle();
		double getLat();
		double getLng();
		String getIcon();

	}
	public class MyMarkerItem implements MarkerItem,Latlng
	{
		String title,icon;
		double lat,lng;

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
	 * 标注的被触摸的Listener
	 * @ClassName: OnMarkerClickListener 
	 */
	public interface OnMarkerClickListener
	{
		public abstract boolean onMarkerClick(MarkerItem markerItem);//传入被点击的MarkerItem
	}
	/**
	 * InfoWindow被触摸的Listener
	 * @ClassName: PopupClickListener 
	 */
	public interface InfoWindowClickListener {

		public abstract void onClickedInfoWindow(MarkerItem markerItem);
	}

	private OnMarkerClickListener onMarkerClickListener = null;
	private InfoWindowClickListener infoWindowClickListener = null;
	private boolean infoWindowStatus = false;
	/**
	 * 设置有关Marker的内容
	 * @param listMarkerItems  Marker列表
	 * @param onMarkerClickListener  Marker点击事件
	 * @param infoWindowStatus     是否有点击后的弹窗 (默认无)
	 * @param infoWindowClickListener    弹窗点击事件
	 */
	public void setMarkerData(List<MarkerItem> listMarkerItems,OnMarkerClickListener onMarkerClickListener,boolean infoWindowStatus,InfoWindowClickListener infoWindowClickListener)
	{
		this.listMarkerItems = listMarkerItems;
		dataSize = listMarkerItems.size();
		Toast.makeText(getContext(), "设置了"+dataSize+"个Marker", Toast.LENGTH_SHORT).show();
		this.onMarkerClickListener = onMarkerClickListener;
		this.infoWindowClickListener = infoWindowClickListener;
		this.infoWindowStatus = infoWindowStatus;
	}

	private boolean isPageFinished = false;//页面是否加载完成
	/**
	 * 展示所有的Marker。
	 * 如果页面加载完，就直接执行代码；  如果没加载完，等加载完成再执行。
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
					showRoute();
					showSpan();
					loadUrl("javascript:setInfoWindowStatus("+infoWindowStatus+")");

				}
			});
		}
	}

	/**
	 * 移除所有的Marker
	 * 需要在 PageFinished 之后才能成功调用
	 */
	public void removeMarker() 
	{
		this.loadUrl("javascript:showAllMarker(false)");
	}

	/**
	 * 刚好显示所有的 标记
	 * 需要在 PageFinished 之后才能成功调用
	 */
	public void showSpan()
	{
		this.loadUrl("javascript:showSpan()");
	}

	/**
	 * 刚好显示所有的 标记   （动画)
	 * 需要在 PageFinished 之后才能成功调用
	 */
	public void showSpanWithAnimation()
	{
		this.loadUrl("javascript:showSpanWithAnimation()");
	}


	/**
	 * 维度 经度 坐标类
	 * @ClassName: Latlng 
	 */
	public interface Latlng
	{
		public double getLat();
		public double getLng();
	}

	private List<Latlng> listLatlngs;
	/**
	 * 设置路线数据
	 * @param listLatlngs  从起点到终点的路线数据
	 */
	public void setRouteData(List<?> listLatlngs)
	{
		this.listLatlngs = (List<Latlng>) listLatlngs;	
	}
	/**
	 * 展示路线
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
		this.loadUrl("javascript:showRoute(true)");
	}
	/**
	 * 移除路线
	 */
	public void removeRoute()
	{
		this.loadUrl("javascript:showRoute(false)");
	}


	/**
	 * JS map调用android代码的接口函数
	 * 注意:android 4.2 之后版本提供给js调用的函数必须带有注释语句@JavascriptInterface
	 * @ClassName: JsInterfaceObject 
	 * @author 贺智超
	 */
	public class JsInterfaceObject extends Object
	{
		Handler handler = new Handler();

		/**
		 * 作为测试代码用
		 */
		@JavascriptInterface 
		public void testMap()
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js调用了Android方法 testMap", Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js调用了Android方法 testMap");
				}
			});

		}

		/**
		 * 移除路线
		 */
		@JavascriptInterface
		public void removeRoute()
		{
			loadUrl("javascript:showRoute(false)");
		}



		/**
		 * marker被点击事件
		 */
		@JavascriptInterface 
		public void onMarkerClick(final int index)
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js调用了Android方法onMarkerClick "+index, Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js调用了Android方法onMarkerClick "+index);
					if(onMarkerClickListener!=null)
						onMarkerClickListener.onMarkerClick(listMarkerItems.get(index));
				}
			});

		}

		/**
		 * infoWindow被点击事件
		 */
		@JavascriptInterface 
		public void onClickedInfoWindow(final int index)
		{
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getContext(), "js调用了Android方法onClickedInfoWindow "+index, Toast.LENGTH_SHORT).show();
					Log.v(TAG, "js调用了Android方法onClickedInfoWindow "+index);
					if(infoWindowClickListener!=null)
						infoWindowClickListener.onClickedInfoWindow(listMarkerItems.get(index));
				}
			});

		}

	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onDetachedFromWindow");
		super.onDetachedFromWindow();
		
	}


}
