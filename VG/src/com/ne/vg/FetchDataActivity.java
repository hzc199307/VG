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
	private static final String BIGSCENELIST = "http://www.mafengwo.cn/jd/10063/gonglve.html";
	private static final String SCENEDETAIL = "http://www.mafengwo.cn/poi/78213.html";
	private List<ContentValues> data;
	private SQLiteDatabase db;
	Handler handler;  
	static FileUtil fileUtil = new FileUtil();
	private static String DB_PATH = "com.ne.vg/voice/����";
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
     * �¿����̴߳����������� 
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
                    msg.what = data.size();
                    for(int i =0;i<data.size();i++){
                    	ContentValues values = data.get(i);
                    	//��������
                        db.insert("bigScene", null, values);
                        //�������ֲ��ŵ�Ŀ¼
                        fileUtil.createSDDir(DB_PATH+values.get("bigSceneName"));
                        //ÿ������ҳ������ݲ������ݿ���
                        detail_data = mDetaiInfoUtil.getDetaiInfo(weblist.get(i));
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
     * ���������������ʾ��listview 
     * @return 
     * @author Lai Huan 
     * @created 2013-6-20 
     */  
    private Handler getHandler() {  
        return new Handler(){  
            public void handleMessage(Message msg) {  
                if (msg.what < 0) {  
                    //Toast.makeText(FetchDataActivity.class, "���ݻ�ȡʧ��", Toast.LENGTH_SHORT).show();  
                    Log.d(TAG, "There is no data");
                }else {  
                    //initListview();  
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
		//�ر����ݿ�
		db.close();
	}
}
