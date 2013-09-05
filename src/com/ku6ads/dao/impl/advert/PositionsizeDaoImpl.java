package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.iface.advert.PositionsizeDao;

public class PositionsizeDaoImpl extends BaseAbstractDao implements PositionsizeDao {

	@SuppressWarnings("unchecked")
	public List<Positionsize> getEnablePositionsize(Positionsize positionsize) {
		return getSqlMapClientTemplate().queryForList("advert.Positionsize.selectEnablePositionsize",positionsize);
	}

}
