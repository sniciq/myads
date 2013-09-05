package com.ku6ads.dao.iface.statistics;

import java.util.Date;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.statistics.CpdhisAccount;

public interface CpdhisAccountDao extends BaseDao {
	
	public Integer selectDateDiff(Date firstDate,Date secondDate);
	
	public Integer getFlightNumByAdv(CpdhisAccount cpdhisAccount);
	
	public Integer getFlightNumByBook(CpdhisAccount cpdhisAccount);
}

