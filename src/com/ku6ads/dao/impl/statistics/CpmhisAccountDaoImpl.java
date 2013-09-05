package com.ku6ads.dao.impl.statistics;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.statistics.CpmhisAccount;
import com.ku6ads.dao.iface.statistics.CpmhisAccountDao;

public class CpmhisAccountDaoImpl extends BaseAbstractDao implements CpmhisAccountDao {
	
	public Long getAdvBarPV(CpmhisAccount cpmhisAccount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.CpmhisAccount.getAdvBarPV",cpmhisAccount);	
	}
	
	public Long getAdvPV(CpmhisAccount cpmhisAccount){
		return (Long)getSqlMapClientTemplate().queryForObject("default.CpmhisAccount.getAdvPV",cpmhisAccount);	
	}
	
	public Integer getFlightNumByAdv(CpmhisAccount cpmhisAccount){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmhisAccount.getFlightNumByAdv",cpmhisAccount);	
	}
	
	public Integer getFlightNumByBook(CpmhisAccount cpmhisAccount){
		return (Integer)getSqlMapClientTemplate().queryForObject("default.CpmhisAccount.getFlightNumByBook",cpmhisAccount);	
	}
}
