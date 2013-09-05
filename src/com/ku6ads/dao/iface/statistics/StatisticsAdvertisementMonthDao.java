package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementMonth;

public interface StatisticsAdvertisementMonthDao extends BaseDao {

	List<StatisticsAdvertisementMonth> statisticsSearch(StatisticsAdvertisementMonth searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsAdvertisementMonth searchEty);
	
	public List<StatisticsAdvertisementMonth> statisticsSearchByLimit(StatisticsAdvertisementMonth searchEty);
}

