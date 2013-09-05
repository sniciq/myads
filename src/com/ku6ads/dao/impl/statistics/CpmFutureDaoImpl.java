package com.ku6ads.dao.impl.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.CpmFuture;
import com.ku6ads.dao.iface.statistics.CpmFutureDao;

public class CpmFutureDaoImpl extends BaseAbstractDao implements CpmFutureDao{
	
	public Integer selectSumFlightNum(Integer advbarId){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectSumFlightNum",advbarId);
	}
	
	public Integer selectFlightNum(Integer advbarId,Integer dateNum){
		Map<String,Integer> map = new HashMap<String,Integer>();  
		map.put("advbarId", advbarId);  
		map.put("dateNum", dateNum);
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectFlightNum",map);
	}

	public Integer selectDateDiff(Date firstDate,Date secondDate){
		Map<String,Date> map = new HashMap<String,Date>();  
		map.put("firstDate", firstDate);  
		map.put("secondDate", secondDate);
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectDateDiff",map);
	}
	
	public Integer selectBookByDate(CpmFuture cpmFuture){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectBookByDate",cpmFuture);
	}
	
	public Integer selectConfirmByDate(CpmFuture cpmFuture){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmFuture.selectConfirmByDate",cpmFuture);
	}
}
