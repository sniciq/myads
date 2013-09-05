package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsProjectMonth;
import com.ku6ads.dao.iface.statistics.StatisticsProjectMonthDao;

public class StatisticsProjectMonthDaoImpl extends BaseAbstractDao implements StatisticsProjectMonthDao {

	@Override
	public List<StatisticsProjectMonth> statisticsSearch(StatisticsProjectMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProjectMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsProjectMonth> statisticsSearchByLimit(StatisticsProjectMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProjectMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsProjectMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsProjectMonth.statisticsSearchLimitCount", searchEty);
	}


}
