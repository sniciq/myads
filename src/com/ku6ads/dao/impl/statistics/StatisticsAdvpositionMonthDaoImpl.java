package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionMonth;
import com.ku6ads.dao.iface.statistics.StatisticsAdvpositionMonthDao;

public class StatisticsAdvpositionMonthDaoImpl extends BaseAbstractDao implements StatisticsAdvpositionMonthDao{

	@Override
	public List<StatisticsAdvpositionMonth> statisticsSearch(StatisticsAdvpositionMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvpositionMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvpositionMonth> statisticsSearchByLimit(StatisticsAdvpositionMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvpositionMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvpositionMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvpositionMonth.statisticsSearchLimitCount", searchEty);
	}

}
