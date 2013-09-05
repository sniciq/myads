package com.ku6ads.struts.flowforecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarMonth;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarMonthDao;

public class DayForecast {
	
	private ForecastAdvbarEty forecastEty;
	private StatisticsAdvbarMonthDao statisticsAdvbarMonthDao;
	private AdvbarDao advbarDao;
	
	private List<StatisticsAdvbarMonth> hisData;//按时间倒排,格式yyyy-MM-dd
	private Map<String, StatisticsAdvbarMonth> hisDataMap = new HashMap<String, StatisticsAdvbarMonth>();
	
	public DayForecast(ForecastAdvbarEty forecastEty) {
		this.forecastEty = forecastEty;
	}
	
	public int getForecastNum() throws Exception {
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
			cal.add(Calendar.WEEK_OF_YEAR,  -1);
			StatisticsAdvbarMonth ety = hisDataMap.get(df.format(cal.getTime()));
			if(ety != null)
				a1[i] = ety.getPv() / 1000;
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
			//算出最小的时间，不用SQL计算
			return getStatisticsAdvbarMonthDao().selectThreeWeekHisData(getForecastEty());
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	/**
//	 * 线性矩阵算法得到预估值
//	 * @param arrayA 前三周数据
//	 * @param arrayB 同工作日历史数据
//	 * @return
//	 */
//	public int getNumByLinearMatrix(double[][] arrayA, double[][] arrayB) {
//		Matrix A = new Matrix(arrayA);
//		Matrix B = new Matrix(arrayB);
//		Matrix x = A.solve(B);
//		
//		double[][] arrayC = new double[arrayA.length][1];
//		for(int i = 0; i < arrayA.length; i++) {
//			arrayC[i][0] = arrayA[0][i];
//		}
//		
//		Matrix C = new Matrix(arrayC);
//		
//		return (int) (C.times(x).normInf() * 0.9);
//	}
//	
//	/**
//	 * 	得到该广告位的等级,<br>
//		使用同一等级的广告位的八天前的投放量的平均值和七天前的投放量的平均值<br>
//		得到这两天的投放量的相对关系<br>
//		前一天的该广告位的数据结合这个相对关系来推断出当天的投放量<br>
//	 * @param passAvg7 同一等级的广告位8天前的投放量的平均值
//	 * @param passAvg8 同一等级的广告位7天前的投放量的平均值
//	 * @param pass1 前一天的该广告位的数据
//	 * @return
//	 */
//	public int getNumByHisAverage(double passAvg7, double passAvg8, double pass1) {
//		return (int) ((pass1 * passAvg7 / passAvg8) * 0.9);
//	}
	
	/**
	 * 取广告条的默认预估值
	 * @return
	 */
	public int getAdvbarDefaultNum() {
		Advbar advbarEty = (Advbar) advbarDao.selectById(forecastEty.getId());
		return advbarEty.getFutureFlight();
	}
	
	public ForecastAdvbarEty getForecastEty() {
		return forecastEty;
	}

	public void setForecastEty(ForecastAdvbarEty forecastEty) {
		this.forecastEty = forecastEty;
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
