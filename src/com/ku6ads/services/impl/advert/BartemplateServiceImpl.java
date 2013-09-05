package com.ku6ads.services.impl.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.iface.advert.BartemplateDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.BartemplateService;

/**
 * 
 * @author liujunshi
 *
 */
public class BartemplateServiceImpl extends BaseAbstractService implements BartemplateService {

	BartemplateDao bartemplateDao;
	
	public List<Bartemplate> getEnableBartemplate(){
		return bartemplateDao.getEnableBartemplate();
	}

	public BartemplateDao getBartemplateDao() {
		return bartemplateDao;
	}

	public void setBartemplateDao(BartemplateDao bartemplateDao) {
		this.bartemplateDao = bartemplateDao;
	}
	
}
