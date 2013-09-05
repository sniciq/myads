package com.ku6ads.dao.iface.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.entity.advert.Postemplate;

/**
 * 
 * @author liujunshi
 *
 */
public interface PositionsizeDao extends BaseDao {
	
	public List<Positionsize> getEnablePositionsize(Positionsize positionsize);
}
