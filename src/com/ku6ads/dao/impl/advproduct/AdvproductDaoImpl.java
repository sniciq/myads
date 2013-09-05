package com.ku6ads.dao.impl.advproduct;

import java.util.List;

import com.ku6ads.dao.entity.advproduct.AdvproductEty;

public class AdvproductDaoImpl extends com.ku6ads.dao.base.BaseAbstractDao implements com.ku6ads.dao.iface.advproduct.AdvproductDao{

	@Override
	public List selectByAdvproductName(AdvproductEty advproductEty) {
		return getSqlMapClientTemplate().queryForList("default.t_advproduct.selectByAdvproductName", advproductEty);
	}
}