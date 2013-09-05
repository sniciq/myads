package com.ku6ads.dao.iface.sysconfig;

import java.util.List;
import com.ku6ads.dao.entity.sysconfig.Proportion;
/**
 * 权重参数dao
 * @author chenshaofeng
 * @createTime 2010-11-27
 */
public interface ProportionDao {
	/**
	 * 获得符合条件的权重参数条数
	 * @param Proportion
	 * @return Integer
	 */
	public Integer selectByProportionCount(Proportion proportion);
	/**
	 * 获得符合条件的权重参数
	 * @param Proportion
	 * @return List<Proportion>
	 */
	public List<Proportion> selectByProportionLimit(Proportion proportion);
	/**
	 * 判断在启动状态下名字是否唯一
	 * @param Integer
	 * @return Boolean
	 */
	public List<Proportion> selectByName(Proportion proportion);
	/**
	 * 根据ID查找权重参数
	 * @param Integer
	 * @return List<Proportion>
	 */
	public Proportion selectById(Integer id);
	/**
	 * 新增权重参数
	 * @param proportion
	 * @return
	 */
	public void insertProportion(Proportion proportion);
	/**
	 * 编辑权重参数
	 * @param proportion
	 * @return
	 */
	public void updateProportion(Proportion proportion);
	/**
	 * 根据ID删除权重参数
	 * @param proportion
	 * @return 
	 */
	public void deleteById(Integer id);
	
	/**
	 * 得到当前的系数
	 * @param pop
	 * @return
	 */
	public List<Proportion> selectNowProportion(Proportion pop);

}
