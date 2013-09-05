package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageDay;

public interface StatisticsBookpackageDayDao extends BaseDao {
	List<StatisticsBookpackageDay> statisticsSearch(StatisticsBookpackageDay searchEty);
	
	public List<StatisticsBookpackageDay> statisticsSearchByLimit(StatisticsBookpackageDay searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsBookpackageDay searchEty);
}
