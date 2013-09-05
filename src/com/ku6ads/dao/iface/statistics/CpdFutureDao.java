package com.ku6ads.dao.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.CpdFuture;

public interface CpdFutureDao extends BaseDao{
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);
	
	public Integer selectBookByDate(CpdFuture cpdFuture);
	
	public Integer selectConfirmByDate(CpdFuture cpdFuture);

}
