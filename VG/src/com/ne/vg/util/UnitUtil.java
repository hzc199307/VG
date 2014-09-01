package com.ne.vg.util;

import android.content.Context;

/**
 * dp px 等单位换算
 * @ClassName: UnitUtil 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-9-1 下午2:30:38
 */
public class UnitUtil {
	public static int dip2px(Context context, float dipValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(dipValue * scale + 0.5f); 
	}
	
	public static int px2dip(Context context, float pxValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(pxValue / scale + 0.5f); 
	}
	
	public static int getRemainderHeightPxFromDp(Context context, float dipValue) {
		int px = dip2px(context,dipValue);
		//return context.getResources().getDisplayMetrics().heightPixels-px;
		return dip2px(context,430);
	}
	
	public static int getRemainderHeightPxFromPx(Context context, int pxValue) {
		return context.getResources().getDisplayMetrics().heightPixels-pxValue;
	}
}
