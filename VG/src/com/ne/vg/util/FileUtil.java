package com.ne.vg.util;


import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
  
import android.os.Environment;  
/**
 *   
 * @ClassName: FileUtil 
 * @author ��ɼ
 * @Description: �Զ����һ���ļ������࣬��Ҫ����д���ļ��Ĺ��ܡ� 
 * @date 2014-8-22 ����4:39:55
 */
public class FileUtil {  
    private String SDPATH;  
      
    private int FILESIZE = 4 * 1024;   
      
    public String getSDPATH(){  
        return SDPATH;  
    }  
      
    public FileUtil(){  
        //�õ���ǰ�ⲿ�洢�豸��Ŀ¼( /SDCARD )  
        SDPATH = getSD() + "/";  
    }  
      
    /**  
     * ��SD���ϴ����ļ� ����������ɾ�� 
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
     * ��SD���ϴ���Ŀ¼  
     * @param dirName  
     * @return  
     */  
    public File createSDDir(String dirName){  
        File dir = new File(SDPATH + dirName); 
        if(isFileExist(dirName))
        	dir.mkdir();  
        return dir;  
    }  
      
    /**  
     * �ж�SD���ϵ��ļ����Ƿ����  
     * @param fileName  
     * @return  
     */  
    public boolean isFileExist(String fileName){  
        File file = new File(SDPATH + fileName);  
        return file.exists();  
    }  
      
    /**  
     * ��һ��InputStream���������д�뵽SD����  
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
            	//TODO ���������ر�,�����п��ܻᱨ��
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
	 * @Description: ��ȡsd��λ�ã������������ȡ��Ŀ¼λ��
	 * @param @return
	 * @return String 
	 * @throws
	 */
	public static String getSD(){ 
	       File sdDir = null; 
	       boolean sdCardExist = Environment.getExternalStorageState()   
	                           .equals(android.os.Environment.MEDIA_MOUNTED);   //�ж�sd���Ƿ���� 
	       if   (sdCardExist)   
	       {                               
	         sdDir = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼ 
	      }else{
	    	  sdDir = Environment.getRootDirectory();
	      }
	       return sdDir.getPath(); 
	       
	}
  
}  
