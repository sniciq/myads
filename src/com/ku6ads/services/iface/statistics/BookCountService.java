package com.ku6ads.services.iface.statistics;

import com.ku6ads.dao.entity.statistics.BookCount;
import com.ku6ads.services.base.BaseServiceIface;

public interface BookCountService extends BaseServiceIface{
	
	public Long selectAvgPv(BookCount bookCount);

	public Long selectFlightNum(BookCount bookCount);
	
	public Long selectClick(BookCount bookCount);
	
	public Long selectSumPv(BookCount bookCount);
}
