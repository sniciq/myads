package com.ku6ads.services.impl.common;

import java.util.List;

import org.apache.log4j.Logger;

import com.ku6ads.dao.entity.sysconfig.City;
import com.ku6ads.dao.entity.sysconfig.Province;
import com.ku6ads.dao.iface.sysconfig.CityDao;
import com.ku6ads.dao.iface.sysconfig.ProvinceDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.common.ProviceService;

public class ProviceServiceImpl extends BaseAbstractService implements ProviceService {
	
private Logger logger = Logger.getLogger(ProviceServiceImpl.class);

	private CityDao cityDao;
	private ProvinceDao provinceDao;
	
	public List<City> getCityList(int pcode) {
		try {
			City cityEty = new City();
			cityEty.setPcode(pcode);
			List<City> list = cityDao.selectByEntity(cityEty);
			return list;
		}
		catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public List<Province> getProviceList() {
		try {
			List<Province> list = provinceDao.selectByEntity(null);
			return list;
		}
		catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	public ProvinceDao getProvinceDao() {
		return provinceDao;
	}

	public void setProvinceDao(ProvinceDao provinceDao) {
		this.provinceDao = provinceDao;
	}
}
