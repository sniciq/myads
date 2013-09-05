package com.ku6ads.dao.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.CpdFuture;
import com.ku6ads.dao.entity.statistics.CpmFuture;

public interface CpmFutureDao extends BaseDao{
	
	public Integer selectSumFlightNum(Integer advbarId);
	
	public Integer selectFlightNum(Integer advbarId,Integer dateNum);
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);
	
	public Integer selectBookByDate(CpmFuture cpdFuture);
	
	public Integer selectConfirmByDate(CpmFuture cpdFuture);
}
