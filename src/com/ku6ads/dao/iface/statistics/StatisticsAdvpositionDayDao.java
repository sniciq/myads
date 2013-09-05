package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionDay;

public interface StatisticsAdvpositionDayDao extends BaseDao {

	List<StatisticsAdvpositionDay> statisticsSearch(StatisticsAdvpositionDay searchEty);
	
	Integer statisticsSearchLimitCount(StatisticsAdvpositionDay searchEty);
	
	List<StatisticsAdvpositionDay> statisticsSearchByLimit(StatisticsAdvpositionDay searchEty);
	
	

}
