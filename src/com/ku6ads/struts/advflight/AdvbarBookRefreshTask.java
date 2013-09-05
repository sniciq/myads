package com.ku6ads.struts.advflight;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ku6ads.services.impl.advflight.BookCommon;

public class AdvbarBookRefreshTask extends QuartzJobBean {
	private Logger logger = Logger.getLogger(AdvbarBookRefreshTask.class);
	
	protected void executeInternal(JobExecutionContext job) throws JobExecutionException {
		logger.info("定时更新点位选择信息: " + new Date());
		//定时更新排期信息表，以免在保存时出现数据丢失情况
		BookCommon.reGenrateBookedPreBookData();
	}
}
