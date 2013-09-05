package com.ku6ads.struts.flowforecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;

public class DayForecastFactory {
	public static DayForecast getDayForecast(ForecastAdvbarEty forecastEty) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(forecastEty.getForecasrDate()));
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.SATURDAY :
				case Calendar.SUNDAY:	
					return new WeekendDayForecast(forecastEty);
				default:
					return new NormalDayForecast(forecastEty);
			}
		}
		catch (Exception e) {
			return null;
		}
	}
}
