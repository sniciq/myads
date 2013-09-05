package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsProjectMonth;

public interface StatisticsProjectMonthDao extends BaseDao {
	
	List<StatisticsProjectMonth> statisticsSearch(StatisticsProjectMonth searchEty);

	public List<StatisticsProjectMonth> statisticsSearchByLimit(StatisticsProjectMonth searchEty);

	public Integer statisticsSearchLimitCount(StatisticsProjectMonth searchEty);
}
