package com.ku6ads.struts.flowforecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarMonth;

/**
 * 一般工作日预测
 * @author yangHanguang
 *
 */
public class NormalDayForecast extends DayForecast {
	
	private List<StatisticsAdvbarMonth> hisData;//按时间倒排,格式yyyy-MM-dd
	private Map<String, StatisticsAdvbarMonth> hisDataMap = new HashMap<String, StatisticsAdvbarMonth>();
	
	public NormalDayForecast(ForecastAdvbarEty forecastEty) {
		super(forecastEty);
	}

	@Override
	public int getForecastNum() throws Exception {
		if(getForecastEty().getId().intValue() == 51) {
			System.out.println();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		hisData = getHisData();
		if(hisData.size() == 0) 
			return getAdvbarDefaultNum();
		
		for(int i = 0; i < hisData.size(); i++) {
			StatisticsAdvbarMonth ety = hisData.get(i);
			hisDataMap.put(df.format(ety.getStatTime()), ety);
		}
		
		Long[] a1 = new Long[3];
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(getForecastEty().getForecasrDate()));
		for(int i = 0; i < 3; i++) {
			a1[i] = 0L;
			cal.add(Calendar.WEEK_OF_YEAR, (i + 1) * - 1);
			StatisticsAdvbarMonth ety = hisDataMap.get(df.format(cal.getTime()));
			if(ety != null)
				a1[i] = ety.getPv();
		}
		
		if(a1[0] != 0 && a1[1] != 0 && a1[2] != 0) {//该广告位存在3周的有效数据.
			//取上三周同天的平均值
			return (int) ((a1[0] + a1[1] + a1[2]) / 3);
		}
		else if (hisData.size() > 0) {// 取平均数
			long sum = 0;
			for(int i = 0; i < hisData.size(); i++) {
				sum += hisData.get(i).getPv();
			}
			
			return (int) (sum / hisData.size());
		} 
		else {
			return getAdvbarDefaultNum();
		}
	}
	
	private List<StatisticsAdvbarMonth> getHisData() {
		try {
			return getStatisticsAdvbarMonthDao().selectThreeWeekHisData(getForecastEty());
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
