package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarMonth;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarMonthDao;

public class StatisticsAdvbarMonthDaoImpl extends BaseAbstractDao implements StatisticsAdvbarMonthDao {

	@Override
	public List<StatisticsAdvbarMonth> statisticsSearch(StatisticsAdvbarMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvbarMonth.statisticsSearch", searchEty);
	}

	@Override
	public List<StatisticsAdvbarMonth> selectThreeWeekHisData(ForecastAdvbarEty forecastEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvbarMonth.selectThreeWeekHisData", forecastEty);
	}

	@Override
	public int selectPass7AvgPv(ForecastAdvbarEty forecastEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvbarMonth.selectPass7AvgPv", forecastEty);
	}

	@Override
	public int selectPass8AvgPv(ForecastAdvbarEty forecastEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvbarMonth.selectPass8AvgPv", forecastEty);
	}

	@Override
	public List<StatisticsAdvbarMonth> statisticsSearchByLimit(StatisticsAdvbarMonth searchEty) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAdvbarMonth.statisticsSearchByLimit", searchEty);
	}

	@Override
	public Integer statisticsSearchLimitCount(StatisticsAdvbarMonth searchEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.StatisticsAdvbarMonth.statisticsSearchLimitCount", searchEty);
	}

}
