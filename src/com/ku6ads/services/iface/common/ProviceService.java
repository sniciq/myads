package com.ku6ads.services.iface.common;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.City;
import com.ku6ads.dao.entity.sysconfig.Province;

public interface ProviceService {
	
	public List<Province> getProviceList();
	
	public List<City> getCityList(int pcode);
}
