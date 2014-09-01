package com.ne.vg.util;

import android.content.Context;

public class ResourcesUtil {

	/**
	 * ��ȡdrawable��ͼƬ��ԴID
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static int getDrawableID(Context context,String fileName) {
		return context.getResources().getIdentifier(fileName,"drawable",context.getPackageName());
	}
}