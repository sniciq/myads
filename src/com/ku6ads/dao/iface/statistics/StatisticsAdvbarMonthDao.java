package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarMonth;

public interface StatisticsAdvbarMonthDao extends BaseDao {

	List<StatisticsAdvbarMonth> statisticsSearch(StatisticsAdvbarMonth searchEty);
	
	Integer statisticsSearchLimitCount(StatisticsAdvbarMonth searchEty);
	
	List<StatisticsAdvbarMonth> statisticsSearchByLimit(StatisticsAdvbarMonth searchEty);

	List<StatisticsAdvbarMonth> selectThreeWeekHisData(ForecastAdvbarEty forecastEty);

	int selectPass7AvgPv(ForecastAdvbarEty forecastEty);

	int selectPass8AvgPv(ForecastAdvbarEty forecastEty);


}
