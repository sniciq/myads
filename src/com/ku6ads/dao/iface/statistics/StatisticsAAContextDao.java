package com.ku6ads.dao.iface.statistics;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.StatisticsAAContext;
import com.ku6ads.struts.statistics.StatisticsAAContextForm;

public interface StatisticsAAContextDao extends BaseDao {
	
	public List<StatisticsAAContext> statistSearch(StatisticsAAContextForm sf);

}
