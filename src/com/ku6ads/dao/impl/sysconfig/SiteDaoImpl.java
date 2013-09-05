package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.dao.iface.sysconfig.SiteDao;

public class SiteDaoImpl extends BaseAbstractDao implements SiteDao {

	@SuppressWarnings("unchecked")
	public List<Site> selectAll() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Site.selectAll");
	}

	@Override
	public Site selectByAdvbarId(int advbarId) {
		return (Site) getSqlMapClientTemplate().queryForObject("sysconfig.Site.selectByAdvbarId", advbarId);
	}

}
