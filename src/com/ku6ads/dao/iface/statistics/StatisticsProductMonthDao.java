package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsProductMonth;

public interface StatisticsProductMonthDao extends BaseDao {
	List<StatisticsProductMonth> statisticsSearch(StatisticsProductMonth searchEty);
	
	Integer statisticsSearchLimitCount(StatisticsProductMonth searchEty);
	
	List<StatisticsProductMonth> statisticsSearchByLimit(StatisticsProductMonth searchEty);
	
}
