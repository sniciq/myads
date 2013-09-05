package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Area;
import com.ku6ads.dao.iface.sysconfig.AreaDao;

/**
 * 
 * @author liujunshi
 *
 */
public class AreaDaoImpl extends BaseAbstractDao implements AreaDao {

	@SuppressWarnings("unchecked")
	public List<Area> selectArea() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Area.selectAreas");
	}

}
