package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageMonth;

public interface StatisticsBookpackageMonthDao extends BaseDao {
	
	List<StatisticsBookpackageMonth> statisticsSearch(StatisticsBookpackageMonth searchEty);
	
	public List<StatisticsBookpackageMonth> statisticsSearchByLimit(StatisticsBookpackageMonth searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsBookpackageMonth searchEty);
}
