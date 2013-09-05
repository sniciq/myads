package com.ku6ads.services.impl.advflight;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ku6ads.util.PropertiesUtils;

/**
 * 定时任务生为CDN生成路径信息
 * @author liujunshi
 *
 */
public class makeCDNPathTask extends QuartzJobBean {
	
	private Logger logger = Logger.getLogger(makeCDNPathTask.class);
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
			
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String makeMPathURL = PropertiesUtils.getValue("makeMPathURL");
		//System.out.println("执行定时任务生成Mpath.txt" + makeMPathURL);
        URL url;
        try {
            url = new URL(makeMPathURL);
           
            URLConnection URLconnection = url.openConnection();  
            HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;  
            int responseCode = httpConnection.getResponseCode();  
            if (responseCode == HttpURLConnection.HTTP_OK) {  
                //System.err.println("CALL转码接口成功");
            	//logger.info("访问生成路径URL成功, URL = "+makeMPathURL);
            }else{
                // System.err.println("CALL转码接口失败");
            	logger.info("访问生成路径URL失败, URL = "+makeMPathURL);
             }
        } catch (Exception e) {
            // TODO Auto-generated catch blockeb
            //e.printStackTrace();
        	logger.error("访问生成路径URL失败", e);

        }  
	}	
}
