package com.ku6ads.dao.iface.statistics;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.BookCount;

public interface BookCountDao extends BaseDao{
	
	public Long selectAvgPv(BookCount bookCount);

	public Long selectFlightNum(BookCount bookCount);
	
	public Long selectClick(BookCount bookCount);
	
	public Long selectSumPv(BookCount bookCount);
}
