package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsChannelDay;
import com.ku6ads.dao.iface.statistics.StatisticsChannelDayDao;

public class StatisticsChannelDayDaoImpl extends BaseAbstractDao implements StatisticsChannelDayDao {

	@Override
	public List<StatisticsChannelDay> statisticsSearch(StatisticsChannelDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsChannelDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsChannelDay> statisticsSearchByLimit(StatisticsChannelDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsChannelDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsChannelDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsChannelDay.statisticsSearchLimitCount", searchEty);
	}

}
