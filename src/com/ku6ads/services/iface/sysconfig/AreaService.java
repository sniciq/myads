package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Area;
import com.ku6ads.services.base.BaseServiceIface;

public interface AreaService extends BaseServiceIface {
	
	/**
	 * 获得区域列表
	 * @return
	 */
	public List<Area> selectArea();
}
