package com.ku6ads.dao.iface.statistics;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.CpmhisAccount;

public interface CpmhisAccountDao extends BaseDao {
	
	public Long getAdvBarPV(CpmhisAccount cpmhisAccount);
	
	public Long getAdvPV(CpmhisAccount cpmhisAccount);
	
	public Integer getFlightNumByAdv(CpmhisAccount cpmhisAccount);
	
	public Integer getFlightNumByBook(CpmhisAccount cpmhisAccount);
}
