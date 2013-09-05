package com.ku6ads.dao.impl.basic;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.basic.BaseDataDao;

public class BaseDataDaoImpl extends BaseAbstractDao implements BaseDataDao {

	@SuppressWarnings("unchecked")
	public List<BaseData> selectBaseDataTypes() {
		return getSqlMapClientTemplate().queryForList("basic.BaseData.selectBaseDataTypes");
	}
}
