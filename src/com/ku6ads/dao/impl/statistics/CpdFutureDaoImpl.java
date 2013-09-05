package com.ku6ads.dao.impl.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.CpdFuture;
import com.ku6ads.dao.iface.statistics.CpdFutureDao;

public class CpdFutureDaoImpl extends BaseAbstractDao implements CpdFutureDao {
	
	public Integer selectDateDiff(Date firstDate,Date secondDate){
		Map<String,Date> map = new HashMap<String,Date>();  
		map.put("firstDate", firstDate);  
		map.put("secondDate", secondDate);
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectDateDiff",map);
	}
	
	public Integer selectBookByDate(CpdFuture cpdFuture){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpdFuture.selectBookByDate",cpdFuture);
	}
	
	public Integer selectConfirmByDate(CpdFuture cpdFuture){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpdFuture.selectConfirmByDate",cpdFuture);
	}
}
