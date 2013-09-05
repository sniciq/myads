package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementDay;

public interface StatisticsAdvertisementDayDao extends BaseDao {

	List<StatisticsAdvertisementDay> statisticsSearch(StatisticsAdvertisementDay searchEty);

	Integer statisticsSearchLimitCount(StatisticsAdvertisementDay searchEty);
	
	List<StatisticsAdvertisementDay> statisticsSearchByLimit(StatisticsAdvertisementDay searchEty);
	
}
