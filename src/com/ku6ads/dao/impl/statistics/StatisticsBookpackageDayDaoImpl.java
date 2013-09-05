package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageDay;
import com.ku6ads.dao.iface.statistics.StatisticsBookpackageDayDao;

public class StatisticsBookpackageDayDaoImpl extends BaseAbstractDao implements StatisticsBookpackageDayDao{

	@Override
	public List<StatisticsBookpackageDay> statisticsSearch(StatisticsBookpackageDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsBookpackageDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsBookpackageDay> statisticsSearchByLimit(StatisticsBookpackageDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsBookpackageDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsBookpackageDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsBookpackageDay.statisticsSearchLimitCount", searchEty);
	}

}
