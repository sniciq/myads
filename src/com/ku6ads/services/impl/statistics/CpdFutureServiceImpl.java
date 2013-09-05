package com.ku6ads.services.impl.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpdFuture;
import com.ku6ads.dao.iface.statistics.CpdFutureDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.statistics.CpdFutureService;

public class CpdFutureServiceImpl extends BaseAbstractService implements CpdFutureService{
	
	public Integer selectDateDiff(Date firstDate,Date secondDate){
		return ((CpdFutureDao)getBaseDao()).selectDateDiff(firstDate,secondDate);
	}

	public Integer selectBookByDate(CpdFuture cpdFuture){
		return ((CpdFutureDao)getBaseDao()).selectBookByDate(cpdFuture);
	}
	
	public Integer selectConfirmByDate(CpdFuture cpdFuture){
		return ((CpdFutureDao)getBaseDao()).selectConfirmByDate(cpdFuture);
	}
}
