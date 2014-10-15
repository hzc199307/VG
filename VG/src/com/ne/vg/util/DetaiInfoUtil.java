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
	 * @Description: TODO
	 * @param @return
	 * @return List<Map<String,Object>> 
	 * @throws
	 */
	public List<ContentValues> getBigSceneList(String url){
		String my_url = url;
		List<ContentValues> result = new ArrayList<ContentValues>();
    	Matcher m = getMatcher("alt=\".*?-(.*?)\\(",my_url);//(��������)
    	Matcher m2 = getMatcher(">No\\.(.*?)<",my_url); //��ȡbigSceneID
    	while(m.find()&&m2.find()){
    		MatchResult mr = m.toMatchResult();
    		MatchResult mr2 = m2.toMatchResult();
    		ContentValues map = new ContentValues();
    		map.put("bigSceneName", mr.group(1));
    		//map.put("latitude", null);
    		//map.put("longtitude", null);
    		map.put("cityID", 1);
    		map.put("bigSceneID", mr2.group(1));
    		//map.put("resource", null);
    		map.put("contentID", mr2.group(1));
    		
    		map.put("isDowned", 1);
    		//map.put("loveNum", 123);
    		//map.put("recordNum", 456);
    		result.add(map);
    	}
		return result;
	}
	/**
	 * 
	 * @Title: getWebSite 
	 * @Description: ��ȡ�б�ҳ��ÿ��item����ϸ��ַ
	 * @param @param url
	 * @param @return
	 * @return List<String> 
	 * @throws
	 */
	public List<String> getWebSite(String url){
		String my_url = url;
		List<String> myList = new ArrayList<String>();
		org.jsoup.nodes.Document doc = Jsoup.parse(my_url);
    	Elements links = doc.select(".title h3 a");
    	for(Element link:links){
    		String linkhref = link.attr("href");
    		myList.add(linkhref);//��ȡ����վ������ҳ��website
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
	 * @Description: ��ȡ�󾰵�����ҳ�������Ϣ
	 * @param @return
	 * @return Map<String,Object> 
	 * @throws
	 */
	public ContentValues getDetaiInfo(String url){
		ContentValues result = new ContentValues();
    	String csdnString = http_get(url);
    	org.jsoup.nodes.Document doc = Jsoup.parse(csdnString);
    	Element fenshu = doc.select(".score-info").first().child(0);
    	result.put("recommendLevel", fenshu.text());
    	result.put("content", getInformation(doc, "���"));
    	result.put("address", getInformation(doc, "��ַ"));
    	result.put("route", getInformation(doc, "��ͨ"));
    	result.put("workingTime", getInformation(doc, "����ʱ��"));
    	result.put("website", getInformation(doc, "��ַ"));
    	result.put("telephone", getInformation(doc, "�绰"));
    	Element link7 = doc.select("h3:contains(��ͨ)").first();
    	Element price = link7.nextElementSibling().nextElementSibling().nextElementSibling();//��ͨ�����¸�
    	result.put("price", price.text());
    	return result;
	}
	private String getInformation(org.jsoup.nodes.Document doc,String str){
    	Element link = doc.select("h3:contains("+str+")").first();//��ȡ�����ǩ
    	Element information = link.nextElementSibling();//��ȡ���ݱ�ǩ���������ǩ����һ����ǩ
    	//Log.d(TAG, str+information.text());
    	return information.text();
    	
    }
	/** 
     * get����URL��ʧ��ʱ�������� 
     * @param url ������ַ 
     * @return ��ҳ���ݵ��ַ��� 
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
                    //��utf-8����ת��Ϊ�ַ���  
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
        //�趨���ӳ�ʱ�Ͷ�ȡ��ʱʱ��  
        HttpConnectionParams.setConnectionTimeout(httpParams, 6000);  
        HttpConnectionParams.setSoTimeout(httpParams, 30000);  
        return new DefaultHttpClient(httpParams);  
    }  
}
