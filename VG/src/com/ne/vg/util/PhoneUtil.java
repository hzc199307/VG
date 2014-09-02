package com.ne.vg.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * ���ض��ֻ���صĹ���
 * @ClassName: PhoneUtil 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014-9-2 ����8:26:58
 */
public class PhoneUtil {
	private static DisplayMetrics dm;
	public static int getPhoneWidthPx(Context context) {
		// TODO Auto-generated method stub

		if(dm==null)
		{
			dm = new DisplayMetrics();  
			((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);  
		}
		return dm.widthPixels;
	}
	
	public static int getPhoneHeightPx(Context context) {
		if(dm==null)
		{
			dm = new DisplayMetrics();  
			((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);  
		}
		return dm.heightPixels;
	}

}
