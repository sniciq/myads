package com.ku6ads.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ku6ads.struts.sysconfig.SysConfig;

/**
 * 
 * @author liujunshi
 *
 */
public class PushMemoryUtil {

	private static SysConfig config;
	private static Logger logger = Logger.getLogger(PushMemoryUtil.class);
	public static final String HTTP_LEAD = "http://";
	/**
	 * 调用RUL 推入内存
	 * @param RUL
	 * @param id
	 * @param operatrion
	 * @author liujunshi
	 */
	public static boolean PushMemory(String URLString,int id,String sign ,String operation)throws Exception{
		//logger.info("into PushMemoryUtil PushMemory method.");
		boolean falg = true;
		StringBuffer sbURL = new StringBuffer();
		sbURL.append(URLString).append(id).append(sign).append(operation);
		URL url;
		try {
			url = new URL(sbURL.toString());
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			// 判断是否成功
			if (responseCode == HttpURLConnection.HTTP_OK) {
				 InputStream urlStream = httpConnection.getInputStream();  
	             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));  
	             String sCurrentLine = "";  
	             String sTotalString = "";  
	             while ((sCurrentLine = bufferedReader.readLine()) != null) {  
	                 sTotalString += sCurrentLine;  
	             }  
	         	//{"data":63,"status":1}
				String [] para = sTotalString.split(",");
				if(para[para.length-1].contains("1")){
					logger.error("成功 , responseCode: "+ responseCode + " ,URL地址为: "+ sbURL.toString()+" ,返回值： "+ sTotalString);
				}else{
					falg = false;
					logger.error("失败,CALL缓存接口 返回值失败 , responseCode: "+ responseCode + " ,URL地址为: "+ sbURL.toString()+" ,返回值： "+ sTotalString);
				}
			
			} else {
				falg = false;
				logger.error("失败 ,CALL缓存接口,responseCode :" + responseCode+" ,URL地址为: "+sbURL.toString());
				throw new RuntimeException("失败 ,CALL缓存接口,responseCode :" + responseCode+" ,URL地址为: "+sbURL.toString());
			}
			
		} catch (MalformedURLException e) {
			logger.error("失败 ,CALL缓存接口,URL地址为: " + sbURL.toString(), e);
			throw e;
		} catch (IOException e) {
			logger.error("失败 ,CALL缓存接口,URL地址为: " + sbURL.toString(), e);
			throw e;
		}catch (Exception e) {
			logger.error("失败 ,CALL缓存接口,URL地址为: " + sbURL.toString(), e);
			throw e;
		}
		
		//System.out.println("调用缓存接口： URL = "+sbURL.toString());
		//logger.error("调用缓存接口： URL = "+sbURL.toString());
		return falg;
	}
	
	public static boolean PushMemoryList(String URLString,int id,String sign ,String operation)throws Exception{
		//logger.info("into PushMemoryUtil PushMemoryList method.");
		boolean flag = false;
		List<String> readList = config.getReadList();
		StringBuffer sb;
		if(readList.size()<1){
			logger.error("Read Server List Is Empty");
			throw new RuntimeException("Read Server List Is Empty");
		}
		try{
			for(String readip:readList){
				sb = new StringBuffer();
				sb.append(HTTP_LEAD).append(readip).append(URLString);	
				flag = PushMemory(sb.toString(), id, sign, operation);
				if(!flag){
					break;
				}
				//logger.error("Push memory url= "+sb.toString()+id+sign+operation);
			}	
		}catch (RuntimeException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}
		return flag;
	}

	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

}
