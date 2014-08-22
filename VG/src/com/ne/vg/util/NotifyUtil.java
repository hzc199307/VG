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
 * @author ��ɼ
 * @Description: ��һ����ʾ֪ͨ���ļ򵥹�����
 * @date 2014-8-22 ����3:05:27
 */
public class NotifyUtil {


	/** ��ȡ״̬��֪ͨ����*/
	public NotificationManager mNotification;
	/** Notification������ */
	NotificationCompat.Builder mBuilder; 
	/** Notification��ʵ�� */
	Notification notify;
	/** Notification��ID */
	int notifyId = 100;

	/**
	 * 
	 * @param context
	 *  ���õ�activity
	 * @param title
	 * @param text
	 * @param c
	 * 	ticker
	 * @param cls
	 * 	Ҫ��ת��activity,��һ���࣬���磺xxx.class
	 */
	public NotifyUtil(Context context,String title,String text,String c,Class<?> cls){
		/** ��ȡ״̬��֪ͨ����*/
		mNotification = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		/**ʵ����֪ͨ�������� */
		mBuilder = new Builder(context);
		mBuilder.setAutoCancel(true)//�������֪ͨ����ʧ
				.setContentTitle(title)
				.setContentText(text)
//				.setNumber(number)//��ʾ����
				.setTicker(c)//֪ͨ�״γ�����֪ͨ��������������Ч����
				.setWhen(System.currentTimeMillis())//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setPriority(Notification.PRIORITY_DEFAULT)//���ø�֪ͨ���ȼ�
				.setSmallIcon(R.drawable.ic_launcher);
		//������������������ת֮ǰ��ҳ�棬����ת֮���ҳ��
		Intent resultIntent = null;
		if(cls !=null)
			resultIntent = new Intent(context,cls);
		//��Intent��������
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//����pendingIntentΪ����һ�����񣬲�����pendingIntent
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		Log.v("Notification", "123456");


		
	}
	/** ��ʾ֪ͨ��*/
	public void showNotify(){
		mNotification.notify(notifyId, mBuilder.build());
		Log.v("Notification", "success");
	} 
}
