package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementMonth;
import com.ku6ads.dao.iface.statistics.StatisticsAdvertisementMonthDao;

public class StatisticsAdvertisementMonthDaoImpl extends BaseAbstractDao implements StatisticsAdvertisementMonthDao {

	@Override
	public List<StatisticsAdvertisementMonth> statisticsSearch(StatisticsAdvertisementMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvertisementMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvertisementMonth> statisticsSearchByLimit(StatisticsAdvertisementMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvertisementMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvertisementMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvertisementMonth.statisticsSearchLimitCount", searchEty);
	}

}
