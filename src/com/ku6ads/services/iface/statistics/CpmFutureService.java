package com.ku6ads.services.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpmFuture;
import com.ku6ads.services.base.BaseServiceIface;

public interface CpmFutureService extends BaseServiceIface{
	
	public Integer selectSumFlightNum(Integer advbarId);
	
	public Integer selectFlightNum(Integer advbarId,Integer dateNum);
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);

	public Integer selectBookByDate(CpmFuture cpmFuture);
	
	public Integer selectConfirmByDate(CpmFuture cpmFuture);
}
