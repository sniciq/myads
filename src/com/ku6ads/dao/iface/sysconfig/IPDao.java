package com.ku6ads.dao.iface.sysconfig;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.IP;

public interface IPDao extends BaseDao {

	public IP selectEtyByIP(String ipAddress);
}
