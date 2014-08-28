package com.ne.vg.util;


import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;  
/**
 *   
 * @ClassName: FileUtil 
 * @author 潘杉
 * @Description: 自定义的一个文件工具类，主要用于写入文件的功能。 
 * @date 2014-8-22 下午4:39:55
 */
public class FileUtil {  
    private String SDPATH;  
      
    private int FILESIZE = 4 * 1024;   
      
    public String getSDPATH(){  
        return SDPATH;  
    }  
      
    public FileUtil(){  
        //得到当前外部存储设备的目录( /SDCARD )  
        SDPATH = getSD() + "/";  
    }  
      
    /**  
     * 在SD卡上创建文件 ，若存在则删除 
     * @param fileName  
     * @return  
     * @throws IOException  
     */  
    public File createSDFile(String fileName) throws IOException{  
        File file = new File(SDPATH + fileName);
        if(isFileExist(fileName)){
        	file.delete();
        }
        file.createNewFile();  
        return file;  
    }  
      
    /**  
     * 在SD卡上创建目录  
     * @param dirName  
     * @return  
     */  
    public File createSDDir(String dirName){  
        File dir = new File(SDPATH + dirName); 
        if(!isFileExist(dirName))
        	dir.mkdirs();  
        return dir;  
    }  
      
    /**  
     * 判断SD卡上的文件夹是否存在  
     * @param fileName  
     * @return  
     */  
    public boolean isFileExist(String fileName){  
        File file = new File(SDPATH + fileName);  
        return file.exists();  
    }  
      
    /**  
     * 将一个InputStream里面的数据写入到SD卡中  
     * @param path  
     * @param fileName  
     * @param input  
     * @return  
     */  
    public File write2SDFromInput(String path,String fileName,InputStream input){  
        File file = null;  
        OutputStream output = null;  
        try {  
            createSDDir(path);  
            file = createSDFile(path + fileName);  
            
            //这一行我不知道为什么要？
            SQLiteDatabase.openOrCreateDatabase(file, null);
            
            
            output = new FileOutputStream(file);  
            byte[] buffer = new byte[FILESIZE];  
  
            int length;  
            while((length=(input.read(buffer))) >0){  
            	output.write(buffer,0,length);  
            }  
            output.flush();  
        }   
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try {
            	//TODO 将两个流关闭,这里有可能会报错
                output.close();
                input.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return file;  
    }  
    
    /**
	 * 
	 * @Title: getSDPath 
	 * @Description: 获取sd卡位置，若不存在则获取根目录位置
	 * @param @return
	 * @return String 
	 * @throws
	 */
	public static String getSD(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState()   
	                           .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
	      }else{
	    	  sdDir = Environment.getRootDirectory();
	      }
	       return sdDir.getPath(); 
	       
	}
  
}  
