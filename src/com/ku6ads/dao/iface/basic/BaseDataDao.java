package com.ku6ads.dao.iface.basic;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.basic.BaseData;

public interface BaseDataDao extends BaseDao {
	
	public List<BaseData> selectBaseDataTypes();
	
}
