package com.ku6ads.services.impl.statistics;

import com.ku6ads.dao.entity.statistics.BookCount;
import com.ku6ads.dao.iface.statistics.BookCountDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.statistics.BookCountService;

public class BookCountServiceImpl extends BaseAbstractService implements BookCountService{
	
	public Long selectAvgPv(BookCount bookCount){
		return ((BookCountDao)getBaseDao()).selectFlightNum(bookCount);
	}

	public Long selectFlightNum(BookCount bookCount){
		return ((BookCountDao)getBaseDao()).selectFlightNum(bookCount);
	}
	
	public Long selectClick(BookCount bookCount){
		return ((BookCountDao)getBaseDao()).selectClick(bookCount);
	}
	
	public Long selectSumPv(BookCount bookCount){
		return ((BookCountDao)getBaseDao()).selectSumPv(bookCount);
	}
}
