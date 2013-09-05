package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarDay;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarDayDao;

public class StatisticsAdvbarDayDaoImpl extends BaseAbstractDao implements StatisticsAdvbarDayDao {

	@Override
	public List<StatisticsAdvbarDay> statisticsSearch(StatisticsAdvbarDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvbarDay.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvbarDay> statisticsSearchByLimit(StatisticsAdvbarDay searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvbarDay.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvbarDay searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvbarDay.statisticsSearchLimitCount", searchEty);

	}

}
