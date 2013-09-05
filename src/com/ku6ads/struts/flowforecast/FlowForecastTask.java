package com.ku6ads.struts.flowforecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.entity.advflight.FutureFlight;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advflight.FlowForecastDao;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarMonthDao;

/**
 * 流量预测
 * @author yangHanguang
 *
 */
public class FlowForecastTask extends QuartzJobBean {
	
	private FlowForecastDao flowForecastDao;
	private FutureFlightDao futureFlightDao;
	private StatisticsAdvbarMonthDao statisticsAdvbarMonthDao;
	private AdvbarDao advbarDao;
	
	private Logger logger = Logger.getLogger(FlowForecastTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		this.dotask();
	}
	
	public void dotask() {
		try {
			futureFlightDao.deleteToday();
			
			List<ForecastAdvbarEty> advbarList = flowForecastDao.selectAllAdvbar();
			for(ForecastAdvbarEty ety : advbarList) {
				FutureFlight futureFlightEty = new FutureFlight();
				futureFlightEty.setAdvbarId(ety.getId());
				
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				for(int d = 0; d < 7; d++) {
					Date date = cal.getTime();
					ForecastAdvbarEty forecastEty = new ForecastAdvbarEty();
					forecastEty.setId(ety.getId());
					forecastEty.setForecasrDate(df.format(date));
					DayForecast forecast = new DayForecast(forecastEty);
					forecast.setStatisticsAdvbarMonthDao(statisticsAdvbarMonthDao);
					forecast.setAdvbarDao(advbarDao);
					int forecastNum = forecast.getForecastNum();
					
					switch (d) {
						case 0:
							futureFlightEty.setNum1(forecastNum);
							break;
						case 1:
							futureFlightEty.setNum2(forecastNum);
							break;
						case 2:
							futureFlightEty.setNum3(forecastNum);
							break;	
						case 3:
							futureFlightEty.setNum4(forecastNum);
							break;	
						case 4:
							futureFlightEty.setNum5(forecastNum);
							break;	
						case 5:
							futureFlightEty.setNum6(forecastNum);
							break;
						case 6:
							futureFlightEty.setNum7(forecastNum);
							break;	
		
						default:
							break;
					}
				}
				
				futureFlightEty.setCreateTime(new Date());
				futureFlightDao.insert(futureFlightEty);
			}
		}
		catch (Exception e) {
			logger.error("定时生成预估广告库存错误！", e);
		}
		logger.info("定时生成预估广告库存: " + new Date());
	}

	public FlowForecastDao getFlowForecastDao() {
		return flowForecastDao;
	}

	public void setFlowForecastDao(FlowForecastDao flowForecastDao) {
		this.flowForecastDao = flowForecastDao;
	}

	public FutureFlightDao getFutureFlightDao() {
		return futureFlightDao;
	}

	public void setFutureFlightDao(FutureFlightDao futureFlightDao) {
		this.futureFlightDao = futureFlightDao;
	}

	public StatisticsAdvbarMonthDao getStatisticsAdvbarMonthDao() {
		return statisticsAdvbarMonthDao;
	}

	public void setStatisticsAdvbarMonthDao(StatisticsAdvbarMonthDao statisticsAdvbarMonthDao) {
		this.statisticsAdvbarMonthDao = statisticsAdvbarMonthDao;
	}

	public AdvbarDao getAdvbarDao() {
		return advbarDao;
	}

	public void setAdvbarDao(AdvbarDao advbarDao) {
		this.advbarDao = advbarDao;
	}
	
}
