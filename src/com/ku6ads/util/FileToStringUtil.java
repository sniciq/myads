package com.ku6ads.util;

import java.io.*;  

import org.apache.log4j.Logger;
/**
 * 读取文件，返回String
 * @author liujunshi
 *
 */
public class FileToStringUtil {  
	

	 static Logger logger = Logger.getLogger(FileToStringUtil.class);
     /**
      * 读取文件中的内容 返回String
      * @param fileName
      * @return
      */
	 public static String readFile(File file) throws Exception{  
        String output = "";   
  
        if(file.exists()){  
            if(file.isFile()){  
                try{  
                    BufferedReader input = new BufferedReader (new FileReader(file));  
                    StringBuffer buffer = new StringBuffer();  
                    String text;  
                         
                    while((text = input.readLine()) != null)  
                        buffer.append(text +"\n");  
                         
                    output = buffer.toString();                      
                }  
                catch(IOException ioException){  
                    logger.error("read File Error!", ioException);
                    throw ioException;
                }  
            }  
            else if(file.isDirectory()){  
                String[] dir = file.list();  
                output += "Directory contents:\n";  
                  
                for(int i=0; i<dir.length; i++){  
                    output += dir[i] +"\n";  
                }  
            }  
        }  
        else{   
            logger.error("Does not exist!");
        }  
        return output;  
     }  
	 /**
	  * readFile的多态
	  * @param fileName
	  * @return
	  * @throws Exception
	  */
	 public static String readFile(String fileName) throws Exception{ 
		 File file = new File(fileName); 
		 return readFile(file);
	 }
	 
	 
	 
     public static void main (String args[]){  
         String str = null;
		try {
			str = readFile("C:/1.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           
         System.out.print(str);  
     }  
}  