package com.ku6ads.services.iface.advert;

import java.util.List;
import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 
 * @author liujunshi
 *
 */
public interface PositionsizeService extends BaseServiceIface {

	/**
	 * 取得可用的广告位规格
	 * @return
	 */
	public List<Positionsize> getEnablePositionsize();
	
	/**
	 * 取得可用的广告条规格
	 * @return
	 */
	public List<Positionsize> getEnableBarsize();
	
	/**
	 * 根据宽高取得规格
	 * @param positionsize
	 * @return
	 */
	public List<Positionsize> getSizeWithNotName(Positionsize positionsize);
	
	/**
	 * 根据名字取得规格
	 * @param positionsize
	 * @return
	 */
	public List<Positionsize> getSizebyName(Positionsize positionsize);
}
