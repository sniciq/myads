package com.ku6ads.services.iface.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 广告条系数
 * @author xuxianan
 *
 */
public interface BarProportionService extends BaseServiceIface {

	/**
	 * 根据广告条系数日期查询
	 * @param barProportion
	 * @return
	 */
	public List<BarProportion> selectProportionByDate(BarProportion barProportion);
}
