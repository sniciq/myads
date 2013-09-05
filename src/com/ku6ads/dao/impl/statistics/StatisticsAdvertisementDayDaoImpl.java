package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementDay;
import com.ku6ads.dao.iface.statistics.StatisticsAdvertisementDayDao;

public class StatisticsAdvertisementDayDaoImpl extends BaseAbstractDao implements StatisticsAdvertisementDayDao {

	@Override
	public List<StatisticsAdvertisementDay> statisticsSearch(StatisticsAdvertisementDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvertisementDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvertisementDay> statisticsSearchByLimit(StatisticsAdvertisementDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvertisementDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvertisementDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvertisementDay.statisticsSearchLimitCount", searchEty);

	}

}
