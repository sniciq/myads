package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveDay;
import com.ku6ads.dao.iface.statistics.StatisticsAdvActiveDayDao;

public class StatisticsAdvActiveDayDaoImpl extends BaseAbstractDao implements StatisticsAdvActiveDayDao {

	@Override
	public List<StatisticsAdvActiveDay> statisticsSearch(StatisticsAdvActiveDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvActiveDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvActiveDay> statisticsSearchByLimit(StatisticsAdvActiveDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvActiveDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvActiveDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvActiveDay.statisticsSearchLimitCount", searchEty);

	}

}
