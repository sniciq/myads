package com.ku6ads.services.impl.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpmFuture;
import com.ku6ads.dao.iface.statistics.CpmFutureDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.statistics.CpmFutureService;

public class CpmFutureServiceImpl extends BaseAbstractService implements CpmFutureService{

	public Integer selectSumFlightNum(Integer advbarId){
		return ((CpmFutureDao)getBaseDao()).selectSumFlightNum(advbarId);
	}
	
	public Integer selectFlightNum(Integer advbarId,Integer dateNum){
		return ((CpmFutureDao)getBaseDao()).selectFlightNum(advbarId,dateNum);
	}
	
	public Integer selectDateDiff(Date firstDate,Date secondDate){
		return ((CpmFutureDao)getBaseDao()).selectDateDiff(firstDate,secondDate);
	}
	
	public Integer selectBookByDate(CpmFuture cpmFuture){
		return ((CpmFutureDao)getBaseDao()).selectBookByDate(cpmFuture);
	}
	
	public Integer selectConfirmByDate(CpmFuture cpmFuture){
		return ((CpmFutureDao)getBaseDao()).selectConfirmByDate(cpmFuture);
	}
}
