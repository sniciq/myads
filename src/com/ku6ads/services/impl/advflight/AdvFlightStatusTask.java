package com.ku6ads.services.impl.advflight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.iface.advflight.AdvFlightDao;

public class AdvFlightStatusTask extends QuartzJobBean{

	/**
	 * LiuJunshi
	 */
	
	private AdvFlightDao advFlightDao;
	
	public static final int STATUS_FIGHTG_WILL = 0;
	public static final int STATUS_FIGHTING = 1;
	public static final int STATUS_FIGHT_PAUSE = 2;
	public static final int STATUS_FIGHT_COM = 3;
	public static final int STATUS_FIGHT_STOP = 4;
	
	private Logger logger = Logger.getLogger(AdvFlightStatusTask.class);
	
	public void flightComplete(){
		AdvFlight advFlight = new AdvFlight();
		try{
			logger.error("开始更新执行投放状态完成，时间 ： "+new Date());
			getYesterdayTime(advFlight);
			advFlight.setStatus(STATUS_FIGHTING);
			advFlightDao.flightComplete(advFlight);
		}catch(Exception e){
			logger.error("定时更新投放完成状态失败， 时间：" +advFlight.getEndTime() ,e);
		}
	}
	
	
	/**
	 * 获得昨天时间
	 * 00:00:00 ~23:59:59
	 */
	public AdvFlight getYesterdayTime(AdvFlight advFlight){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatterHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		String dateTimeString = formatter.format(c.getTime());
		//System.out.println("A : "+dateTimeString);
		try {
			Date endTime = formatterHMS.parse(dateTimeString+" 00:00:00");
			Date endTimeEnd = formatterHMS.parse(dateTimeString+" 23:59:59");
			
			//String endTimeString1 = formatterHMS.format(endTimeEnd); 
			//String startTimeString = formatterHMS.format(endTime); 
			//System.out.println("B : "+startTimeString);
			//System.out.println("c : "+endTimeString1);
			
			advFlight.setEndTime(endTime);
			advFlight.setEndTimeEnd(endTimeEnd);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}

		return advFlight;
	}
	
	
	public static void main(String []args){

	}
	
	/**
	 * 定时任务开始入口
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
	
		flightComplete();
		
	}


	public AdvFlightDao getAdvFlightDao() {
		return advFlightDao;
	}


	public void setAdvFlightDao(AdvFlightDao advFlightDao) {
		this.advFlightDao = advFlightDao;
	}
	
	
}
