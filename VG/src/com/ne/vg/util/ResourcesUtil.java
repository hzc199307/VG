package com.ne.vg.util;

import android.content.Context;

public class ResourcesUtil {

	/**
	 * 获取drawable下图片资源ID
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static int getDrawableID(Context context,String fileName) {
		return context.getResources().getIdentifier(fileName,"drawable",context.getPackageName());
	}
}