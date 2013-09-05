package com.ku6ads.services.impl.advert;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.iface.advert.PositionsizeDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.PositionsizeService;

/**
 * 
 * @author liujunshi
 *
 */
public class PositionsizeServiceImpl extends BaseAbstractService implements PositionsizeService {

	PositionsizeDao positionsizeDao;
	
	public static final int POSITIONSIZE_TYPE = 0;
	public static final int BARSIZE_TYPE = 1;

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PositionsizeService#getEnablePositionsize()
	 */
	public List<Positionsize> getEnablePositionsize() {
		Positionsize positionsize = new Positionsize();
		positionsize.setType(POSITIONSIZE_TYPE);
		return positionsizeDao.getEnablePositionsize(positionsize);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PositionsizeService#getEnableBarsize()
	 */
	public List<Positionsize> getEnableBarsize() {
		Positionsize positionsize = new Positionsize();
		positionsize.setType(BARSIZE_TYPE);
		return positionsizeDao.getEnablePositionsize(positionsize);
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PositionsizeService#getSizebyProperty(com.ku6ads.dao.entity.advert.Positionsize)
	 */
	public List<Positionsize> getSizeWithNotName(Positionsize positionsize) {
		Positionsize p_target = new Positionsize ();
		BeanUtils.copyProperties(positionsize, p_target);
		p_target.setName(null);
		return positionsizeDao.getEnablePositionsize(p_target);
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PositionsizeService#getSizebyName(com.ku6ads.dao.entity.advert.Positionsize)
	 */
	public List<Positionsize> getSizebyName(Positionsize positionsize) {
		Positionsize p = new Positionsize();
		p.setName(positionsize.getName());
		p.setType(positionsize.getType());
		p.setStatus(positionsize.getStatus());
		
		return positionsizeDao.getEnablePositionsize(p);
	}
	///-----------------------------GETTER SETTER----------------------------///
	
	public PositionsizeDao getPositionsizeDao() {
		return positionsizeDao;
	}

	public void setPositionsizeDao(PositionsizeDao positionsizeDao) {
		this.positionsizeDao = positionsizeDao;
	}



	



}
