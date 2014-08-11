package com.ne.vg.gmap;

import com.ne.vg.R;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GMapFragment extends Fragment{

	//private WebView webviewGMap;

	private static final String MAP_URL="file:///android_asset/map.html"; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View view  = inflater.inflate(R.layout.fragment_gmap, container,false);
//		webviewGMap = (WebView)view.findViewById(R.id.webview_gmap);
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
}
