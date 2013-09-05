package com.ku6ads.services.impl.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpdhisAccount;
import com.ku6ads.dao.iface.statistics.CpdhisAccountDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.statistics.CpdhisAccountService;

public class CpdhisAccountServiceImpl extends BaseAbstractService implements CpdhisAccountService{
	
	public Integer selectDateDiff(Date firstDate,Date secondDate){
		return ((CpdhisAccountDao)getBaseDao()).selectDateDiff(firstDate,secondDate);
	}

	public Integer getFlightNumByAdv(CpdhisAccount cpdhisAccount){
		return ((CpdhisAccountDao)getBaseDao()).getFlightNumByAdv(cpdhisAccount);
	}
	
	public Integer getFlightNumByBook(CpdhisAccount cpdhisAccount){
		return ((CpdhisAccountDao)getBaseDao()).getFlightNumByBook(cpdhisAccount);
	}
}
