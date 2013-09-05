package com.ku6ads.dao.impl.advert;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.dao.iface.advert.BarProportionDao;

/**
 * 广告条系数
 * @author xuxianan
 *
 */
public class BarProportionDaoImpl extends BaseAbstractDao implements BarProportionDao {

	public List<BarProportion> selectByAdvbarIdAndTime(Map paraMap) {
		return getSqlMapClientTemplate().queryForList("advert.BarProportion.selectByAdvbarIdAndTime", paraMap);
	}

	public BarProportion selectByBaridAndBookTime(Map paraMap) {
		return (BarProportion) getSqlMapClientTemplate().queryForObject("advert.BarProportion.selectByBaridAndBookTime", paraMap);
	}

	public List<BarProportion> selectProportionByDate(BarProportion barProportion) {
		return getSqlMapClientTemplate().queryForList("advert.BarProportion.selectProportionByDate", barProportion);
	}

}
