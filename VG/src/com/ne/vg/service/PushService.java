package com.ne.vg.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class PushService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "PushService", Toast.LENGTH_LONG).show();
		return null;
	}

}
