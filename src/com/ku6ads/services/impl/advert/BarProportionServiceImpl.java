package com.ku6ads.services.impl.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.dao.iface.advert.BarProportionDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.BarProportionService;

/**
 * 广告条系数
 * @author xuxianan
 *
 */
public class BarProportionServiceImpl extends BaseAbstractService implements BarProportionService {

	public List<BarProportion> selectProportionByDate(BarProportion barProportion) {
		return ((BarProportionDao) getBaseDao()).selectProportionByDate(barProportion);
	}

}
