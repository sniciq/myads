package com.ku6ads.services.impl.statistics;

import com.ku6ads.dao.entity.statistics.CpmhisAccount;
import com.ku6ads.dao.iface.statistics.CpmhisAccountDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.statistics.CpmhisAccountService;

public class CpmhisAccountServiceImpl extends BaseAbstractService implements CpmhisAccountService{
	
	public Long getAdvBarPV(CpmhisAccount cpmhisAccount){
		return ((CpmhisAccountDao)getBaseDao()).getAdvBarPV(cpmhisAccount);
	}
	
	public Long getAdvPV(CpmhisAccount cpmhisAccount){
		return ((CpmhisAccountDao)getBaseDao()).getAdvPV(cpmhisAccount);
	}

	public Integer getFlightNumByAdv(CpmhisAccount cpmhisAccount){
		return ((CpmhisAccountDao)getBaseDao()).getFlightNumByAdv(cpmhisAccount);
	}
	
	public Integer getFlightNumByBook(CpmhisAccount cpmhisAccount){
		return ((CpmhisAccountDao)getBaseDao()).getFlightNumByBook(cpmhisAccount);
	}
}
