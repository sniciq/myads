package com.ku6ads.dao.impl.statistics;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.BookCount;
import com.ku6ads.dao.iface.statistics.BookCountDao;

public class BookCountDaoImpl extends BaseAbstractDao implements BookCountDao{
	
	public Long selectAvgPv(BookCount bookCount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.BookCount.selectAvgPv",bookCount);
	}

	public Long selectFlightNum(BookCount bookCount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.BookCount.selectFlightNum",bookCount);
	}
	
	public Long selectClick(BookCount bookCount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.BookCount.selectClick",bookCount);
	}
	
	public Long selectSumPv(BookCount bookCount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.BookCount.selectSumPv",bookCount);
	}
}
