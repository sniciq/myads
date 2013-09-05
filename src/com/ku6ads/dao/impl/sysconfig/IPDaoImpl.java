package com.ku6ads.dao.impl.sysconfig;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.IP;
import com.ku6ads.dao.iface.sysconfig.IPDao;

public class IPDaoImpl extends BaseAbstractDao implements IPDao {

	@Override
	public IP selectEtyByIP(String ipAddress) {
		return (IP) getSqlMapClientTemplate().queryForObject("sysconfig.IP.selectEtyByIP", ipAddress);
	}

}
