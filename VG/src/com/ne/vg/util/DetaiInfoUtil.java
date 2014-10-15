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
		map.put("url", str);
		map.put("score", str);
    	return result;
	}
	/**
	 * 
	 * @Title: getDetaiInfo 
	 * @Description: ��ȡ�󾰵�����ҳ�������Ϣ
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
