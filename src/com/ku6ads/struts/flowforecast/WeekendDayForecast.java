package com.ku6ads.struts.flowforecast;

import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;

/**
 * 双休日预测
 * @author yangHanguang
 *
 */
public class WeekendDayForecast extends DayForecast {
	
	public WeekendDayForecast(ForecastAdvbarEty forecastEty) {
		super(forecastEty);
	}

	public int getForecastNum() {
		
		
		return 0;
	}
	
}
