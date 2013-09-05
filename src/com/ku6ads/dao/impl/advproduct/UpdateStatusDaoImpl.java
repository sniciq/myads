package com.ku6ads.dao.impl.advproduct;

import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.iface.advproduct.UpdateStatusDao;

public class UpdateStatusDaoImpl extends BaseAbstractDao implements UpdateStatusDao {

	@Override
	public void updateStatus(Map<String, String> paraMap) {
		getSqlMapClientTemplate().update("default.updateStatus.updateStatus", paraMap);
		
	}
	
}
