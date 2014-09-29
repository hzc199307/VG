package com.ne.vg.receiver;

import com.ne.vg.main.MainActivity;
import com.ne.vg.service.PushService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 开机自启动 BroadcastReceiver
 * @ClassName: BootCompletedReceiver 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-8-22 上午9:46:57
 */
public class BootCompletedReceiver extends BroadcastReceiver { 
	@Override 
	public void onReceive(Context context, Intent intent) { 
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) 
		{ 
			Toast.makeText(context, "BootCompletedReceiver", Toast.LENGTH_LONG).show();
			
//			Intent newIntent1 = new Intent(context, MainActivity.class); 
//			//默认的跳转类型,将Activity放到一个新的Task中
//			newIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败 
//			context.startActivity(newIntent1);

			Intent newIntent2 = new Intent(context, PushService.class); 
			context.startService(newIntent2);
		}
		
	} 
} 