package com.ne.vg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {

	/**
	 * 加载本地图片
	 * @param url 输入sd卡下的路径,不包括"/sdcard/"
	 * @return 不存在的文件会返回null
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			File temp = new File("/sdcard/"+url);
			if(!temp.exists())
				return null;
			FileInputStream fis = new FileInputStream("/sdcard/"+url);
			return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
