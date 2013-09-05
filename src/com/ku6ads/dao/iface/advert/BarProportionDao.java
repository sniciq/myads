package com.ku6ads.dao.iface.advert;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.BarProportion;

/**
 * 广告条系数
 * @author xuxianan
 *
 */
public interface BarProportionDao extends BaseDao {
	
	/**
	 * 根据广告条ID和时间查询广告条的系统
	 * @param paraMap
	 * @return
	 */
	public List<BarProportion> selectByAdvbarIdAndTime(Map paraMap);
	
	/**
	 * 查询某广告条在某天的系数
	 * 只返回一条
	 * @param paraMap
	 * @return
	 */
	public BarProportion selectByBaridAndBookTime(Map paraMap);
	
	/**
	 * 根据广告条系数日期查询
	 * @param barProportion
	 * @return
	 */
	public List<BarProportion> selectProportionByDate(BarProportion barProportion);

}
