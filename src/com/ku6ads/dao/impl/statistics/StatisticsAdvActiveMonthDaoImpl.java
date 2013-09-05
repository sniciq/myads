package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveMonth;
import com.ku6ads.dao.iface.statistics.StatisticsAdvActiveMonthDao;

public class StatisticsAdvActiveMonthDaoImpl extends BaseAbstractDao implements StatisticsAdvActiveMonthDao {

	@Override
	public List<StatisticsAdvActiveMonth> statisticsSearch(StatisticsAdvActiveMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvActiveMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvActiveMonth> statisticsSearchByLimit(StatisticsAdvActiveMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvActiveMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvActiveMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvActiveMonth.statisticsSearchLimitCount", searchEty);
	}

}
