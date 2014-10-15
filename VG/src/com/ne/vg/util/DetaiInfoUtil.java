package com.ne.vg.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import android.util.Log;

public class DetaiInfoUtil {
	
	private String url;
	public DetaiInfoUtil(String url){
		this.url = url;
	}
	
	/**
	 * 
	 * @Title: getBigSceneList 
	 * @Description: TODO
	 * @param @return
	 * @return List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> getBigSceneList(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	String csdnString = http_get(url);
    	org.jsoup.nodes.Document doc = Jsoup.parse(csdnString);
    	Map<String, Object> map = new HashMap<String, Object>();
    	String str = "";
		map.put("bigSceneName", str);
		map.put("latitude", str);
		map.put("longtitude", str);
		map.put("cityID", str);
		map.put("bigSceneID", str);
		map.put("resource", str);
		map.put("contentID", str);
		
		map.put("isDowned", str);
		map.put("loveNum", str);
		map.put("recordNum", str);
    	return result;
	}
	/**
	 * 
	 * @Title: getDetaiInfo 
	 * @Description: 获取大景点详情页的相关信息
	 * @param @return
	 * @return Map<String,Object> 
	 * @throws
	 */
	public Map<String, Object> getDetaiInfo(){
		Map<String, Object> result = new HashMap<String,Object>();
    	String csdnString = http_get(url);
    	org.jsoup.nodes.Document doc = Jsoup.parse(csdnString);
    	Element fenshu = doc.select(".score-info").first().child(0);
    	result.put("recommendLevel", fenshu.text());
    	result.put("content", getInformation(doc, "简介"));
    	result.put("address", getInformation(doc, "地址"));
    	result.put("route", getInformation(doc, "交通"));
    	result.put("workingTime", getInformation(doc, "开放时间"));
    	result.put("website", getInformation(doc, "网址"));
    	result.put("telephone", getInformation(doc, "电话"));
    	Element link7 = doc.select("h3:contains(交通)").first();
    	Element price = link7.nextElementSibling().nextElementSibling().nextElementSibling();//交通的下下个
    	result.put("price", price.text());
    	return result;
	}
	private String getInformation(org.jsoup.nodes.Document doc,String str){
    	Element link = doc.select("h3:contains("+str+")").first();//获取标题标签
    	Element information = link.nextElementSibling();//获取内容标签，即标题标签的下一个标签
    	//Log.d(TAG, str+information.text());
    	return information.text();
    	
    }
	/** 
     * get请求URL，失败时尝试三次 
     * @param url 请求网址 
     * @return 网页内容的字符串 
     * @author Lai Huan 
     * @created 2013-6-20 
     */  
    private String http_get(String url) {  
        final int RETRY_TIME = 3;  
        HttpClient httpClient = null;  
        HttpGet httpGet = null;  
  
        String responseBody = "";  
        int time = 0;  
        do {  
            try {  
                httpClient = getHttpClient();  
                httpGet = new HttpGet(url);  
                HttpResponse response = httpClient.execute(httpGet);  
                if (response.getStatusLine().getStatusCode() == 200) {  
                    //用utf-8编码转化为字符串  
                    byte[] bResult = EntityUtils.toByteArray(response.getEntity());  
                    if (bResult != null) {  
                        responseBody = new String(bResult,"utf-8");  
                    }  
                }  
                break;  
            } catch (IOException e) {  
                time++;  
                if (time < RETRY_TIME) {  
                    try {  
                        Thread.sleep(1000);  
                    } catch (InterruptedException e1) {  
                    }  
                    continue;  
                }  
                e.printStackTrace();  
            } finally {  
                httpClient = null;  
            }  
        } while (time < RETRY_TIME);  
  
        return responseBody;  
    }  
  
    private  HttpClient getHttpClient() {  
        HttpParams httpParams = new BasicHttpParams();  
        //设定连接超时和读取超时时间  
        HttpConnectionParams.setConnectionTimeout(httpParams, 6000);  
        HttpConnectionParams.setSoTimeout(httpParams, 30000);  
        return new DefaultHttpClient(httpParams);  
    }  
}
