package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsProductMonth;
import com.ku6ads.dao.iface.statistics.StatisticsProductMonthDao;

public class StatisticsProductMonthDaoImpl extends BaseAbstractDao implements StatisticsProductMonthDao {

	@Override
	public List<StatisticsProductMonth> statisticsSearch(StatisticsProductMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProductMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsProductMonth> statisticsSearchByLimit(StatisticsProductMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProductMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsProductMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsProductMonth.statisticsSearchLimitCount", searchEty);
	}

}
