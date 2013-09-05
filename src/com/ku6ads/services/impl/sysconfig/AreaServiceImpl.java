package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Area;
import com.ku6ads.dao.iface.sysconfig.AreaDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.AreaService;

public class AreaServiceImpl extends BaseAbstractService implements AreaService {

	@Override
	public List<Area> selectArea() {
		return ((AreaDao) getBaseDao()).selectArea();
	}

}
