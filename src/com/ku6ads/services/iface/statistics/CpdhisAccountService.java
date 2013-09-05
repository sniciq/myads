package com.ku6ads.services.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.statistics.CpdhisAccount;
import com.ku6ads.services.base.BaseServiceIface;

public interface CpdhisAccountService extends BaseServiceIface{
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);
	
	public Integer getFlightNumByAdv(CpdhisAccount cpdhisAccount);
	
	public Integer getFlightNumByBook(CpdhisAccount cpdhisAccount);
}
