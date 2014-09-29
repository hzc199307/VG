package com.ne.vg.receiver;

import com.ne.vg.main.MainActivity;
import com.ne.vg.service.PushService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ���������� BroadcastReceiver
 * @ClassName: BootCompletedReceiver 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-8-22 ����9:46:57
 */
public class BootCompletedReceiver extends BroadcastReceiver { 
	@Override 
	public void onReceive(Context context, Intent intent) { 
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) 
		{ 
			Toast.makeText(context, "BootCompletedReceiver", Toast.LENGTH_LONG).show();
			
//			Intent newIntent1 = new Intent(context, MainActivity.class); 
//			//Ĭ�ϵ���ת����,��Activity�ŵ�һ���µ�Task��
//			newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //ע�⣬������������ǣ�����������ʧ�� 
//			context.startActivity(newIntent1);

			Intent newIntent2 = new Intent(context, PushService.class); 
			context.startService(newIntent2);
		}
		
	} 
} 