package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsProjectDay;

public interface StatisticsProjectDayDao extends BaseDao {
	
	List<StatisticsProjectDay> statisticsSearch(StatisticsProjectDay searchEty);

	public List<StatisticsProjectDay> statisticsSearchByLimit(StatisticsProjectDay searchEty);

	public Integer statisticsSearchLimitCount(StatisticsProjectDay searchEty);
}
