package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.services.base.BaseServiceIface;

public interface SiteService extends BaseServiceIface {

	List<Site> selectSite();
	
	/**
	 * 根据ID号删除单个网站
	 * add by zhangyan
	 * @param id
	 */
	public Integer deleteCheckChannelsById(Integer id);
}
