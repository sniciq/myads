package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsProductDay;

public interface StatisticsProductDayDao extends BaseDao {
	
	List<StatisticsProductDay> statisticsSearch(StatisticsProductDay searchEty);
	
	public List<StatisticsProductDay> statisticsSearchByLimit(StatisticsProductDay searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsProductDay searchEty);

}
