package com.ne.vg;

import java.io.IOException;

import com.ne.vg.DBHelper.DBHelper;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

public class VGApplication extends Application{
	
	private final String TAG = "VGApplication";
	
	public void onCreate(){
		Log.v(TAG, "onCreate");
		onDB();
		
		super.onCreate();
	}

	public void onDB() {
		// TODO Auto-generated method stub
		DBHelper mDbHelper = new DBHelper(this);
		try{
			//’‚¿Ôfalse¡À
			if(mDbHelper.createDataBase())
				Log.v(TAG, "success create table");
				;
		}catch(IOException e){
			e.printStackTrace();
		}
		Cursor mCursor = mDbHelper
				.query("select * from city  ",
						null);
		Log.v(TAG, "mCursor: "+mCursor.getCount());
	}
}
