package com.ku6ads.services.iface.statistics;

import com.ku6ads.dao.entity.statistics.CpmhisAccount;
import com.ku6ads.dao.iface.statistics.CpmhisAccountDao;
import com.ku6ads.services.base.BaseServiceIface;

public interface CpmhisAccountService extends BaseServiceIface{
	
	public Long getAdvBarPV(CpmhisAccount cpmhisAccount);
	
	public Long getAdvPV(CpmhisAccount cpmhisAccount);

	public Integer getFlightNumByAdv(CpmhisAccount cpmhisAccount);
	
	public Integer getFlightNumByBook(CpmhisAccount cpmhisAccount);
}
