package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveDay;

public interface StatisticsAdvActiveDayDao extends BaseDao {

	List<StatisticsAdvActiveDay> statisticsSearch(StatisticsAdvActiveDay searchEty);

	Integer statisticsSearchLimitCount(StatisticsAdvActiveDay searchEty);
	
	List<StatisticsAdvActiveDay> statisticsSearchByLimit(StatisticsAdvActiveDay searchEty);
	
}
