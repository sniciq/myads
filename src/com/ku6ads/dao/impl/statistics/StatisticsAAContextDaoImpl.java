package com.ku6ads.dao.impl.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.StatisticsAAContext;
import com.ku6ads.dao.iface.statistics.StatisticsAAContextDao;
import com.ku6ads.struts.statistics.StatisticsAAContextForm;

public class StatisticsAAContextDaoImpl extends BaseAbstractDao implements StatisticsAAContextDao {

	@Override
	public List<StatisticsAAContext> statistSearch(StatisticsAAContextForm sf) {
		return getSqlMapClientTemplate().queryForList("default.StatisticsAAContext.statistSearch", sf);
	}

}
