package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Site;

/**
 * @author liyonghui
 *
 */
public interface SiteDao extends BaseDao {

	List<Site> selectAll();
	
	public Site selectByAdvbarId(int advbarId);

}
