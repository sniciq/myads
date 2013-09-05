package com.ku6ads.services.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpdFuture;
import com.ku6ads.services.base.BaseServiceIface;

public interface CpdFutureService extends BaseServiceIface{
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);
	
	public Integer selectBookByDate(CpdFuture cpdFuture);
	
	public Integer selectConfirmByDate(CpdFuture cpdFuture);
}
