package com.ku6ads.struts.advflight;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.util.PropertiesUtils;


public class TestDataString {
	public static final int CALL_OK = 1;
	public static final int CALL_ERR= 0;
	public static final String PLEACE_PATH="\\%path\\%";
	
	public static void main(String [] args){
		String advBarCode ="<div id=\"barDivId\"height=\"90\"width=\"255\"><div id=\"ggw_id\">[%LINE%]<IMG src=\"%path%\" width=%w% height=%h%></div></div>";
		//advBarCode = advBarCode.replaceFirst(PLEACE_PATH, "http:\\12.22.122");
		String[] tems = advBarCode.split("\\[\\%item1\\%\\]");
		String PosCode = "[%item1%]";
		PosCode = PosCode.replace("[%item1%]", "aaaa");
		System.out.println(PosCode.startsWith("[%item1%]"));
	}
	
	
	
	public static int callTransCode(String sid,String path) {
		// TODO Auto-generated method stub
		int TransCode_Ok = CALL_OK;
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String TransCodeURL=PropertiesUtils.getValue("TRANSCODEURL");
		String TransCodeFrom=PropertiesUtils.getValue("TRANSCODEFROM");
		String TransCodePath=PropertiesUtils.getValue("TRANSCODEPATH");
	     String urlStr = TransCodeURL+sid+TransCodeFrom+TransCodePath+path;  
	     urlStr = "http://127.0.0.1:8080/ku6ads/HTML/advfight/IsTransCodeAction.action?id=160&isSuccess=2&&domain=3&&path=4";
         URL url;
         try {
             url = new URL(urlStr);
             URLConnection URLconnection = url.openConnection();  
             HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;  
             int responseCode = httpConnection.getResponseCode();  
             if (responseCode == HttpURLConnection.HTTP_OK) {  
                 System.err.println("CALL转码接口成功");
                 InputStream urlStream = httpConnection.getInputStream();  
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));  
                 String sCurrentLine = "";  
                 String sTotalString = "";  
                 while ((sCurrentLine = bufferedReader.readLine()) != null) {  
                     sTotalString += sCurrentLine;  
                 }  
                 System.err.println(sTotalString);
                 //判断转码接口是否成功
                if(!"ok".equalsIgnoreCase(sTotalString)){
                	TransCode_Ok=CALL_ERR;
                }
                
             }else{
                 System.err.println("CALL转码接口失败");
                
                 TransCode_Ok=CALL_ERR;
              }
         } catch (Exception e) {
             // TODO Auto-generated catch blockeb
             e.printStackTrace();
           
             TransCode_Ok=CALL_ERR;

         }  
         return  TransCode_Ok;
	}
}
