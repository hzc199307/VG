package com.ne.vg.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.jsoup.select.Elements;

import android.content.ContentValues;
import android.util.Log;

public class DetaiInfoUtil {
	
	
	public DetaiInfoUtil(){
		
	}
	
	/**
	 * 
	 * @Title: getBigSceneList 
	 * @Description: 从指定网址获取大景点列表的信息
	 * @param @return
	 * @return List<Map<String,Object>> 
	 * @throws
	 */
	public List<ContentValues> getBigSceneList(String url){
		String my_url = url;
		List<ContentValues> result = new ArrayList<ContentValues>();
    	Matcher m = getMatcher("alt=\".*?-(.*?)\"",my_url);//(景点名称)
    	Matcher m2 = getMatcher(">No\\.(.*?)<",my_url); //获取bigSceneID
    	while(m.find()&&m2.find()){
    		MatchResult mr = m.toMatchResult();
    		MatchResult mr2 = m2.toMatchResult();
    		ContentValues map = new ContentValues();
    		map.put("bigSceneName", mr.group(1));
    		//map.put("latitude", null);
    		//map.put("longtitude", null);
    		map.put("cityID", 1);
    		map.put("bigSceneID", Integer.parseInt(mr2.group(1)));
    		//map.put("resource", null);
    		map.put("contentID", Integer.parseInt(mr2.group(1)));
    		
    		map.put("IsDowned", 1);
    		//map.put("loveNum", 123);
    		map.put("recordNum", 456);
    		result.add(map);
    	}
		return result;
	}
	/**
	 * 
	 * @Title: getWebSite 
	 * @Description: 获取列表页的每个item的详细地址
	 * @param @param url
	 * @param @return
	 * @return List<String> 
	 * @throws
	 */
	public List<String> getWebSite(String url){
		
		List<String> myList = new ArrayList<String>();
		String my_url = http_get(url);
		org.jsoup.nodes.Document doc = Jsoup.parse(my_url);
    	Elements links = doc.select(".title h3 a");
    	for(Element link:links){
    		String linkhref = link.attr("href");
    		myList.add("http://www.mafengwo.cn" + linkhref);//获取了网站的详情页面website
    	}
		return myList;
	}
	public Matcher getMatcher(String str,String url){
    	String csdnString = http_get(url);
    	Pattern p = Pattern.compile(str);
    	return p.matcher(csdnString);
    }
	/**
	 * 
	 * @Title: getDetaiInfo 
	 * @Description: 获取大景点详情页的相关信息
	 * @param @return
	 * @return Map<String,Object> 
	 * @throws
	 */
	public ContentValues getDetaiInfo(String url, String contentID){
		
		ContentValues result = new ContentValues();
		result.put("contentID", Integer.parseInt(contentID));
		Log.d("website",url);
//		String csdnString2 = http_get(CSDNURL2);
//    	org.jsoup.nodes.Document doc2 = Jsoup.parse(csdnString2);
    	String my_url = http_get(url);
    	org.jsoup.nodes.Document doc = Jsoup.parse(my_url);
    	if(getInformation(doc, "地址")!=null){
    		result.put("address", getInformation(doc, "地址"));
    		Log.d("地址",getInformation(doc, "地址"));
    	}else{
    		Log.d("dizhi","为空");
    	}
//    	Element link10 = doc2.select(".score-info").first();
//    	Element fenshu = link10;
//    	Log.d(TAG, "fenshu=" + fenshu.text());
    	//Element fenshu = doc.select(".score span em").first().child(0);
    	Element fenshu = doc.select(".score-info em").first();
    	
    	result.put("recommendLevel", fenshu.text());
    	
    	
    	result.put("content", getInformation(doc, "简介"));
    	
    	
    	if(getInformation(doc, "交通")!=null){
    		result.put("route", getInformation(doc, "交通"));
    	}
    	if(getInformation(doc, "开放时间")!=null){
    		result.put("workingTime", getInformation(doc, "开放时间"));
    	}
    	if(getInformation(doc, "网址")!=null){
    		result.put("website", getInformation(doc, "网址"));
    	}
    	if(getInformation(doc, "电话")!=null){
    		result.put("telephone", getInformation(doc, "电话"));
    	}
    	Element link7 = doc.select("h3:contains(交通)").first();
    	if(link7!=null){
    		Element price = link7.nextElementSibling().nextElementSibling().nextElementSibling();//交通的下下个
        	
        	result.put("price", price.text());
    	}
    	
    	return result;
	}
	private String getInformation(org.jsoup.nodes.Document doc,String str){
    	Element link = doc.select("h3:contains("+str+")").first();//获取标题标签
    	if(link==null){
    		Log.d("error", "error");
    		return null;
    		
    	}
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
