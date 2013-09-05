package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveMonth;

public interface StatisticsAdvActiveMonthDao extends BaseDao {

	public List<StatisticsAdvActiveMonth> statisticsSearch(StatisticsAdvActiveMonth searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsAdvActiveMonth searchEty);
	
	public List<StatisticsAdvActiveMonth> statisticsSearchByLimit(StatisticsAdvActiveMonth searchEty);
	
}

