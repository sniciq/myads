package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsProjectDay;
import com.ku6ads.dao.iface.statistics.StatisticsProjectDayDao;

public class StatisticsProjectDayDaoImpl extends BaseAbstractDao implements StatisticsProjectDayDao {

	@Override
	public List<StatisticsProjectDay> statisticsSearch(StatisticsProjectDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProjectDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsProjectDay> statisticsSearchByLimit(StatisticsProjectDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsProjectDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsProjectDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsProjectDay.statisticsSearchLimitCount", searchEty);
	}


}
