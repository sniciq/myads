package com.ku6ads.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ku6ads.struts.basic.cache.DictionaryFactory;
/*
 * Project :	ku6ads
 * Author:	zhangyan
 * Company: 	ku6
 * Created Date:2010-11-22
 * Copyright @ 2010 ku6– Confidential and Proprietary
 * Description:
 */
public class SystemListener implements ServletContextListener{
	Logger log = Logger.getLogger(SystemListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("系统已经停止...");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		log.info("加载数据字典...");
		DictionaryFactory.getInstance().getDictionary();
		log.info("加载数据字典完成...");
	}
}
