package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsProductDay;
import com.ku6ads.dao.iface.statistics.StatisticsProductDayDao;

public class StatisticsProductDayDaoImpl  extends BaseAbstractDao implements StatisticsProductDayDao {

	@Override
	public List<StatisticsProductDay> statisticsSearch(StatisticsProductDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProductDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsProductDay> statisticsSearchByLimit(StatisticsProductDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProductDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsProductDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsProductDay.statisticsSearchLimitCount", searchEty);
	}

}
