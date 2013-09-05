package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsChannelMonth;
import com.ku6ads.dao.iface.statistics.StatisticsChannelMonthDao;

public class StatisticsChannelMonthDaoImpl extends BaseAbstractDao implements StatisticsChannelMonthDao{

	@Override
	public List<StatisticsChannelMonth> statisticsSearch(StatisticsChannelMonth searchETy) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsChannelMonth.statisticsSearch", searchETy);
	}

	@Override
	public List<StatisticsChannelMonth> statisticsSearchByLimit(StatisticsChannelMonth searchETy) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsChannelMonth.statisticsSearchByLimit", searchETy);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsChannelMonth searchETy) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsChannelMonth.statisticsSearchLimitCount", searchETy);
	}

}
