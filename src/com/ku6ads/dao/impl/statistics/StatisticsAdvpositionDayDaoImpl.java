package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionDay;
import com.ku6ads.dao.iface.statistics.StatisticsAdvpositionDayDao;

public class StatisticsAdvpositionDayDaoImpl extends BaseAbstractDao implements StatisticsAdvpositionDayDao {

	@Override
	public List<StatisticsAdvpositionDay> statisticsSearch(StatisticsAdvpositionDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvpositionDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvpositionDay> statisticsSearchByLimit(StatisticsAdvpositionDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvpositionDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvpositionDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvpositionDay.statisticsSearchLimitCount", searchEty);
	}

}
