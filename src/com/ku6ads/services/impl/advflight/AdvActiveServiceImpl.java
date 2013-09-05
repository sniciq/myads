package com.ku6ads.services.impl.advflight;

import java.util.List;

import com.ku6ads.dao.entity.advflight.AdvActive;
import com.ku6ads.dao.iface.advflight.AdvActiveDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvActiveService;
/**
 * 广告活动ServiceImpl
 * @author chenshaofeng
 *
 */

public class AdvActiveServiceImpl extends BaseAbstractService implements AdvActiveService {
	
	public List<AdvActive> selectAdvactive(){
		return ((AdvActiveDao)getBaseDao()).selectAll();
	}
	
	public List<AdvActive> selectByName(AdvActive advActive){
		return ((AdvActiveDao)getBaseDao()).selectByName(advActive);
	}

//	public void insert(AdvActive advActive)
//	{
//		advActiveDao.insert(advActive);
//	}
//
//	public AdvActiveDao getAdvActiveDao() {
//		return advActiveDao;
//	}
//
//	public void setAdvActiveDao(AdvActiveDao advActiveDao) {
//		this.advActiveDao = advActiveDao;
//	}
	
}
