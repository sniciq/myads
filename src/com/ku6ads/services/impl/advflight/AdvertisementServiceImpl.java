package com.ku6ads.services.impl.advflight;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.iface.advflight.AdvertisementDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvertisementService;

public class AdvertisementServiceImpl extends BaseAbstractService implements AdvertisementService {
	
	AdvertisementDao advertisementDao;
	
	public AdvertisementDao getAdvertisementDao() {
		return advertisementDao;
	}

	public void setAdvertisementDao(AdvertisementDao advertisementDao) {
		this.advertisementDao = advertisementDao;
	}

	public int insertAdv(Object obj) {
		return ((AdvertisementDao)getBaseDao()).insertAdv(obj);
	}
	
	public List<Advertisement> selectMailNotify(Date date) {
		return ((AdvertisementDao)getBaseDao()).selectMailNotify(date);
	}
	
	public List<Advertisement> selectAdvertisement(Integer advactiveId){
		return ((AdvertisementDao)getBaseDao()).selectAll(advactiveId);
	}


	public int getBarTemType(int advId) {
		return ((AdvertisementDao)getBaseDao()).getBarTemType(advId);
	}
}
