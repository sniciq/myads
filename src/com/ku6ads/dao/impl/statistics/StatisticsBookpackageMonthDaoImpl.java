package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageMonth;
import com.ku6ads.dao.iface.statistics.StatisticsBookpackageMonthDao;

public class StatisticsBookpackageMonthDaoImpl extends BaseAbstractDao implements StatisticsBookpackageMonthDao {

	@Override
	public List<StatisticsBookpackageMonth> statisticsSearch(StatisticsBookpackageMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsBookpackageMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsBookpackageMonth> statisticsSearchByLimit(StatisticsBookpackageMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsBookpackageMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsBookpackageMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsBookpackageMonth.statisticsSearchLimitCount", searchEty);
	}



}
