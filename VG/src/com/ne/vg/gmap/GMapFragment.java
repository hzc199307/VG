package com.ne.vg.gmap;

import com.ne.vg.R;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * google��ͼ
 * @ClassName: GMapFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-12 ����3:22:44
 */
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
//        //������webview��ִ��JavaScript����
//        webSettings.setJavaScriptEnabled(true);
//        //����webview�Ƿ�֧������
//        webSettings.setSupportZoom(false);
//        //���ر���html���룬�˴���λ��assetsĿ¼�£�ͨ��MAP_URL���ʡ�
//        webviewGMap.loadUrl(MAP_URL);
		
		
		//webviewGMap = (WebView)view.findViewById(R.id.gmap_webview);
        
        //���д�������������ģ���
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
        // ���һ������, ��JS���Է��ʸö���ķ���, �ö����п��Ե���JS�еķ���  
//        webviewGMap.addJavascriptInterface(new Contact(), "contact");  
		return view;
	}
}
