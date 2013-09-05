package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsChannelMonth;

public interface StatisticsChannelMonthDao extends BaseDao {
	
	public List<StatisticsChannelMonth> statisticsSearch(StatisticsChannelMonth searchETy);
	
	public Integer statisticsSearchLimitCount(StatisticsChannelMonth searchETy);
	
	public List<StatisticsChannelMonth> statisticsSearchByLimit(StatisticsChannelMonth searchETy);
	
}
