package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarDay;

public interface StatisticsAdvbarDayDao extends BaseDao {

	List<StatisticsAdvbarDay> statisticsSearch(StatisticsAdvbarDay searchEty);

	Integer statisticsSearchLimitCount(StatisticsAdvbarDay searchEty);
	
	List<StatisticsAdvbarDay> statisticsSearchByLimit(StatisticsAdvbarDay searchEty);
	
}
