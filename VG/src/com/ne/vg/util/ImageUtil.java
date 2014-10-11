package com.ne.vg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {

	/**
	 * ���ر���ͼƬ
	 * @param url ����sd���µ�·��,������"/sdcard/"
	 * @return �����ڵ��ļ��᷵��null
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			File temp = new File("/sdcard/"+url);
			if(!temp.exists())
				return null;
			FileInputStream fis = new FileInputStream("/sdcard/"+url);
			return BitmapFactory.decodeStream(fis);  ///����ת��ΪBitmapͼƬ        

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
