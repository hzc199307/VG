package com.ne.vg.DBHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.ne.vg.util.FileUtil;
import com.ne.vg.util.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{

	private static final String TAG = "DBHelper";
	//用户数据库文件的版本
	private static final int DB_VERSION = 1;
	//实例化一个file工具类
	static FileUtil fileUtil = new FileUtil();
	//数据库文件存放目标，存放在sdk上
	private static String DB_PATH = "com.ne.vg/databases/";
	//TODO 这里需要修改为我们的数据 下面两个静态变量分别是目标文件的名称和在assets文件夹下的文件名
	private static String DB_NAME = "VG_test.db";
	private static String ASSETS_NAME = "VG_test.db";
	public final Context mContext;
	private SQLiteDatabase myDatabase = null;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
		this.mContext = context;
		Log.v(TAG, "onCreate");
	}

	public DBHelper(Context context, String name, int version){
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, DB_VERSION);
	}

	public DBHelper(Context context){
		this(context,DB_PATH+DB_NAME);
	}
	/**
	 * 
	 * @Title: createDataBase 
	 * @Description: 创建数据库，并从asset中复制数据到指定位置
	 * @param @return
	 * @param @throws IOException
	 * @return boolean 
	 * @throws
	 */
	public boolean createDataBase() throws IOException{
		LogUtil.d(TAG, "file of old exit: "+fileUtil.isFileExist(DB_PATH+DB_NAME));
		File fileAsset = new File("file:///android_asset/"+ASSETS_NAME);
		LogUtil.d(TAG, "file of Asset lastModified: "+fileAsset.lastModified()+"");
		//复制assets中的数据库文件到DB_PATH目录下
		InputStream myInput = mContext.getAssets().open(ASSETS_NAME);
		//往指定目录中写入数据
		File file = null;
		if(fileUtil.isFileExist(DB_PATH+DB_NAME)==false)//TODO 当没文件的时候才复制数据库文件
		{
			file = fileUtil.write2SDFromInput(DB_PATH, DB_NAME, myInput);
			Calendar cal=Calendar.getInstance();  
			cal.setTimeInMillis(file.lastModified());  
			LogUtil.d(TAG, "file of copied lastModified: "+file.lastModified()+" "+cal.getTime().toLocaleString());
		}

		//判断是否复制成功
		if(file==null){
			return false;
		}
		return true;
	}

	/**
	 * 数据库创建时执行，如果不是预制的数据库，可以在这些写一些创建表和添加初始化数据的操作 如：db.execSQL("create table
	 * cookdata (_id integer primary key,cook_name varchar(20),cook_sort
	 * varchar(20))");
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		/**
		 * 数据库升级时执行，前面我们定义的DB_VERSION就是数据库版本，在版本升高时执行 一般做一些数据备份和恢复到新数据库的操作。
		 */

	}


	@Override
	public synchronized void close() {
		if (myDatabase != null) {
			myDatabase.close();
		}
		super.close();
	}
	/**
	 * 
	 * @Title: query 
	 * @Description: 执行对数据库的查询
	 * @param @param sql
	 * @param @param args
	 * @param @return
	 * @return Cursor 
	 * @throws
	 */

	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase("/sdcard/com.ne.vg/databases/VG_test.db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		Cursor cursor = db.rawQuery(sql, args);

		//TODO 关闭数据库
		db.close();
		return cursor;
	}

}
