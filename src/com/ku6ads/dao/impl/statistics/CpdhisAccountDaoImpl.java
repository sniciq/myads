package com.ku6ads.dao.impl.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.CpdhisAccount;
import com.ku6ads.dao.iface.statistics.CpdhisAccountDao;

public class CpdhisAccountDaoImpl extends BaseAbstractDao implements CpdhisAccountDao {
	
	public Integer selectDateDiff(Date firstDate,Date secondDate){
		Map<String,Date> map = new HashMap<String,Date>();  
		map.put("firstDate", firstDate);  
		map.put("secondDate", secondDate);
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpdFuture.selectDateDiff",map);
	}
	
	public Integer getFlightNumByAdv(CpdhisAccount cpdhisAccount){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpdhisAccount.getFlightNumByAdv",cpdhisAccount);	
	}
	
	public Integer getFlightNumByBook(CpdhisAccount cpdhisAccount){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpdhisAccount.getFlightNumByBook",cpdhisAccount);	
	}
}