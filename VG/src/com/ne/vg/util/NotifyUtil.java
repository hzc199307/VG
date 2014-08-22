package com.ne.vg.util;




import com.ne.vg.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

/**
 * 
 * @ClassName: NotifyUtil 
 * @author 潘杉
 * @Description: 是一个显示通知栏的简单工具类
 * @date 2014-8-22 下午3:05:27
 */
public class NotifyUtil {


	/** 获取状态栏通知管理*/
	public NotificationManager mNotification;
	/** Notification构造器 */
	NotificationCompat.Builder mBuilder; 
	/** Notification的实例 */
	Notification notify;
	/** Notification的ID */
	int notifyId = 100;

	/**
	 * 
	 * @param context
	 *  调用的activity
	 * @param title
	 * @param text
	 * @param c
	 * 	ticker
	 * @param cls
	 * 	要跳转的activity,是一个类，例如：xxx.class
	 */
	public NotifyUtil(Context context,String title,String text,String c,Class<?> cls){
		/** 获取状态栏通知管理*/
		mNotification = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		/**实例化通知栏构造器 */
		mBuilder = new Builder(context);
		mBuilder.setAutoCancel(true)//点击后让通知将消失
				.setContentTitle(title)
				.setContentText(text)
//				.setNumber(number)//显示数量
				.setTicker(c)//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setSmallIcon(R.drawable.ic_launcher);
		//着两个参数代表着跳转之前的页面，跟跳转之后的页面
		Intent resultIntent = null;
		if(cls !=null)
			resultIntent = new Intent(context,cls);
		//给Intent设置属性
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//设置pendingIntent为开启一个服务，并更新pendingIntent
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		Log.v("Notification", "123456");


		
	}
	/** 显示通知栏*/
	public void showNotify(){
		mNotification.notify(notifyId, mBuilder.build());
		Log.v("Notification", "success");
	} 
}
