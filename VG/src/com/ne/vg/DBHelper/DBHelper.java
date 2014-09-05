package com.ne.vg.DBHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.ne.vg.util.FileUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "DBHelper";
	//�û����ݿ��ļ��İ汾
	private static final int DB_VERSION = 1;
	 //ʵ����һ��file������
	static FileUtil fileUtil = new FileUtil();
	//���ݿ��ļ����Ŀ�꣬�����sdk��
	private static String DB_PATH = "com.ne.vg/databases/";
	//TODO ������Ҫ�޸�Ϊ���ǵ����� ����������̬�����ֱ���Ŀ���ļ������ƺ���assets�ļ����µ��ļ���
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
	 * @Description: �������ݿ⣬����asset�и������ݵ�ָ��λ��
	 * @param @return
	 * @param @throws IOException
	 * @return boolean 
	 * @throws
	 */
	public boolean createDataBase() throws IOException{
			//����assets�е����ݿ��ļ���DB_PATHĿ¼��
			InputStream myInput = mContext.getAssets().open(ASSETS_NAME);
			//��ָ��Ŀ¼��д������
			File file = fileUtil.write2SDFromInput(DB_PATH, DB_NAME, myInput);
			//�ж��Ƿ��Ƴɹ�
			if(file==null){
				return false;
			}
			return true;
	}
	
	/**
	 * ���ݿⴴ��ʱִ�У��������Ԥ�Ƶ����ݿ⣬��������ЩдһЩ���������ӳ�ʼ�����ݵĲ��� �磺db.execSQL("create table
	 * cookdata (_id integer primary key,cook_name varchar(20),cook_sort
	 * varchar(20))");
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		/**
		 * ���ݿ�����ʱִ�У�ǰ�����Ƕ����DB_VERSION�������ݿ�汾���ڰ汾����ʱִ�� һ����һЩ���ݱ��ݺͻָ��������ݿ�Ĳ�����
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
	 * @Description: ִ�ж����ݿ�Ĳ�ѯ
	 * @param @param sql
	 * @param @param args
	 * @param @return
	 * @return Cursor 
	 * @throws
	 */
	
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase("/sdcard/com.ne.vg/databases/VG_test.db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		Cursor cursor = db.rawQuery(sql, args);
		
		//TODO �ر����ݿ�
		db.close();
		return cursor;
	}

}
