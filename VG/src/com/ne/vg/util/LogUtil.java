package com.ne.vg.util;

/**
 * 封装一层Log 能够有效控制Log的打印信息
 * @ClassName: LogUtil 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-29 上午11:25:41
 */
public class LogUtil {

	private static final boolean DEBUG = true; 

	public static void v(String tag, String msg) { 
		if(DEBUG) { 
			android.util.Log.v(tag, msg); 
		} 
	} 
	public static void v(String tag, String msg, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.v(tag, msg, tr); 
		} 
	} 
	public static void d(String tag, String msg) { 
		if(DEBUG) { 
			android.util.Log.d(tag, msg); 
		} 
	} 
	public static void d(String tag, String msg, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.d(tag, msg, tr); 
		} 
	} 
	public static void i(String tag, String msg) { 
		if(DEBUG) { 
			android.util.Log.i(tag, msg); 
		} 
	} 
	public static void i(String tag, String msg, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.i(tag, msg, tr); 
		} 
	} 
	public static void w(String tag, String msg) { 
		if(DEBUG) { 
			android.util.Log.w(tag, msg); 
		} 
	} 
	public static void w(String tag, String msg, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.w(tag, msg, tr); 
		} 
	} 
	public static void w(String tag, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.w(tag, tr); 
		} 
	} 
	public static void e(String tag, String msg) { 
		if(DEBUG) { 
			android.util.Log.e(tag, msg); 
		} 
	} 
	public static void e(String tag, String msg, Throwable tr) { 
		if(DEBUG) { 
			android.util.Log.e(tag, msg, tr); 
		} 
	} 
}
