package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsChannelDay;

public interface StatisticsChannelDayDao extends BaseDao {

	List<StatisticsChannelDay> statisticsSearch(StatisticsChannelDay searchEty);
	
	public List<StatisticsChannelDay> statisticsSearchByLimit(StatisticsChannelDay searchEty);
	
	public Integer statisticsSearchLimitCount(StatisticsChannelDay searchEty);

}
