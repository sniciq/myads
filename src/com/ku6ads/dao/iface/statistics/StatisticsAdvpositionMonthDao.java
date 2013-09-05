package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionMonth;

public interface StatisticsAdvpositionMonthDao extends BaseDao {

	List<StatisticsAdvpositionMonth> statisticsSearch(StatisticsAdvpositionMonth searchEty);
	
	Integer statisticsSearchLimitCount(StatisticsAdvpositionMonth searchEty);
	
	List<StatisticsAdvpositionMonth> statisticsSearchByLimit(StatisticsAdvpositionMonth searchEty);

}
