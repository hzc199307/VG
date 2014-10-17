package com.ne.vg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ne.vg.util.DetaiInfoUtil;
import com.ne.vg.util.FileUtil;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FetchDataActivity extends Activity {

	private static final String TAG = "FetchDataActivity";
	private static final String BIGSCENELIST = "http://www.mafengwo.cn/jd/10063/0-0-0-0-0-2.html";
	private List<ContentValues> data;
	private SQLiteDatabase db;
	Handler handler;  
	static FileUtil fileUtil = new FileUtil();
	private static String DB_PATH = "com.ne.vg/voice/罗马/";
	DetaiInfoUtil mDetaiInfoUtil = new DetaiInfoUtil();
	private ContentValues detail_data;
	protected List<String> weblist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fetch_data);
		db = this.openOrCreateDatabase
				("/sdcard/com.ne.vg/databases/VG_test.db", Context.MODE_PRIVATE, null);
		handler = getHandler();
	    ThreadStart();
		
	}

	/** 
     * 新开辟线程处理联网操作 
     * @author Lai Huan 
     * @created 2013-6-20 
     */  
    private void ThreadStart() {  
        new Thread() {  
            public void run() {  
                Message msg = new Message();  
                try {  
                    data = mDetaiInfoUtil.getBigSceneList(BIGSCENELIST);  
                    weblist = mDetaiInfoUtil.getWebSite(BIGSCENELIST);
                    msg.what = weblist.size();
                    
                    Log.d(TAG, "size=" + weblist.size());
                    for(int i =0;i<data.size();i++){
                    	//往bigScene表插入的内容
                    	ContentValues values = data.get(i);
                    	//插入数据
                        long m1= db.insert("bigScene", null, values);
                        //创建音乐播放的目录
                        fileUtil.createSDDir(DB_PATH+values.getAsString("bigSceneName"));
                        Log.d(TAG, "bigSceneName=" + values.getAsString("bigSceneName"));
                        //每个详情页面的数据插入数据库中
                        Log.d(TAG, "website=" + weblist.get(i));
                        Log.d(TAG, "contentID=" + values.getAsString("contentID"));
                        detail_data = mDetaiInfoUtil.getDetaiInfo(weblist.get(i), values.getAsString("contentID"));
                        db.insert("sceneContent", null, detail_data);
                    }
                    
                } catch (Exception e) {  
                    e.printStackTrace();  
                    msg.what = -1;  
                }  
                handler.sendMessage(msg);  
            }  
        }.start();  
    }  

    /** 
     * 处理联网结果，显示在listview 
     * @return 
     * @author Lai Huan 
     * @created 2013-6-20 
     */  
    private Handler getHandler() {  
        return new Handler(){  
            public void handleMessage(Message msg) {  
                if (msg.what < 0) {  
                    //Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();  
                    Log.d(TAG, "There is no data");
                }else {  
                    Log.d(TAG, "Insert is successful"); 
                }  
            }  
        };  
    }  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fetch_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//关闭数据库
		db.close();
	}
}
