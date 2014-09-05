package com.ne.vg.gmap;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.bean.SmallScene;
import com.ne.vg.bmap.LatLng;
import com.ne.vg.gmap.GMapWebView.MarkerItem;
import com.ne.vg.gmap.GMapWebView.MyMarkerItem;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * google地图
 * @ClassName: GMapFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-12 下午3:22:44
 */
public class GMapFragment extends Fragment{

	private final String TAG = "GMapFragment";
	private GMapWebView webviewGMap;

	private final String MAP_URL="file:///android_asset/map.html"; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		//Toast.makeText(getActivity(), TAG+ " onCreateView", Toast.LENGTH_SHORT).show();

		View view  = inflater.inflate(R.layout.fragment_gmap, container,false);
		webviewGMap = (GMapWebView)view.findViewById(R.id.gmap_webview);
		//		WebSettings webSettings = webviewGMap.getSettings();
		//        //允许在webview中执行JavaScript代码
		//        webSettings.setJavaScriptEnabled(true);
		//        //设置webview是否支持缩放
		//        webSettings.setSupportZoom(false);
		//        //加载本地html代码，此代码位于assets目录下，通过MAP_URL访问。
		//        webviewGMap.loadUrl(MAP_URL);


		//webviewGMap = (WebView)view.findViewById(R.id.gmap_webview);

		//这行代码是用来干嘛的？？
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//        button = (Button)findViewById(R.id.button);
		//        button.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View arg0) {
		//				// TODO Auto-generated method stub
		//				Log.e("123","button is clicked");
		//				webviewGMap.loadUrl("javascript:showroute()");
		//			}
		//		});
		// 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法  
		//        webviewGMap.addJavascriptInterface(new Contact(), "contact");  


		return view;
	}
	@Override
	public void onResume() 
	{
		super.onResume();
		Log.v(TAG, "onResume");
		if(isSet==false&&webviewGMap!=null&&listMarkerItems!=null)
		{
			isSet = true;
			webviewGMap.setMarkerItems(listMarkerItems);
		}
		if(onClickListener!=null)webviewGMap.setOnClickListener(onClickListener);
		if(latLngCenter!=null)
		{
			Log.v(TAG, "webviewGMap.setCenter");
			webviewGMap.setCenter(latLngCenter.getLatitude(), latLngCenter.getLongitude());
		}
	};

	List<MarkerItem> listMarkerItems;
	boolean isSet = false;
	/**
	 * 向地图设置数据
	 * @param listSmallScenes
	 */
	public void setSmallScenes(List<SmallScene> listSmallScenes) {
		// TODO Auto-generated method stub
		int size = listSmallScenes.size();
		listMarkerItems = new ArrayList<MarkerItem>();
		SmallScene smallScene;
		MarkerItem item ;
		for(int i=0;i<size;i++)
		{
			smallScene = listSmallScenes.get(i);
			//item = new MyMarkerItem(23, 43, "1");
			item = new MyMarkerItem(smallScene.getLatitude(), smallScene.getLongtitude(), (i+1)+"."+smallScene.getSmallSceneName());
			listMarkerItems.add(item);
		}
		if(webviewGMap!=null)
		{
			isSet = true;
			webviewGMap.setMarkerItems(listMarkerItems);
		}

	}


	private LatLng latLngCenter;
	public void setCenter(double lat,double lng) {
		// TODO Auto-generated method stub
		if(webviewGMap!=null)
		{
			webviewGMap.setCenter(lat, lng);
		}
		latLngCenter = new LatLng(lat, lng);
	}
	//	public void setMarkerItems(List<MarkerItem> listMarkerItems) {
	//		// TODO Auto-generated method stub
	//		webviewGMap.setMarkerItems(listMarkerItems);
	//	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		webviewGMap.destroyDrawingCache();
		webviewGMap.clearAnimation();
		webviewGMap.clearCache(false);
		webviewGMap.clearDisappearingChildren();
		webviewGMap.clearFocus();
		webviewGMap.clearFormData();
		webviewGMap.clearHistory();
		webviewGMap.clearMatches();
		webviewGMap.clearSslPreferences();
		webviewGMap.clearView();
		webviewGMap.removeAllViews();
		webviewGMap.destroy();
		webviewGMap = null;

		Log.v(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		//Toast.makeText(getActivity(), TAG+ " onDestroy", Toast.LENGTH_SHORT).show();
	}

	private View.OnClickListener onClickListener;
	public void setOnClickListener(View.OnClickListener onClickListener)
	{
		this.onClickListener = onClickListener;
	}

	/**
	 * 定位 地图插入定位坐标
	 */
	public void requestLoc()
	{
		webviewGMap.requestLoc();
	}
}
