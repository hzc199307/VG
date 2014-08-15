package com.ne.vg.gmap;

import com.ne.vg.R;

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
}
