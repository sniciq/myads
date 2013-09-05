package com.ku6ads.dao.impl.sysconfig;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ku6ads.dao.entity.sysconfig.Proportion;
import com.ku6ads.dao.iface.sysconfig.ProportionDao;

/**
 * 权重参数daoImp
 * 
 * @author chenshaofeng
 * @createTime 2010-11-27
 * @lastModifyTime 2010-11-27
 */
public class ProportionDaoImpl extends SqlMapClientDaoSupport implements ProportionDao {
	/**
	 * 获得符合条件的权重参数条数
	 * 
	 * @param Proportion
	 * @return Integer
	 */
	public Integer selectByProportionCount(Proportion proportion) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.Proportion.selectByProportionCount", proportion);
	}

	/**
	 * 获得符合条件的权重参数
	 * 
	 * @param Proportion
	 * @return List<Proportion>
	 */
	public List<Proportion> selectByProportionLimit(Proportion proportion) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Proportion.selectByProportionLimit", proportion);
	}

	/**
	 * 判断在启动状态下存在相同名字个数
	 * 
	 * @param Integer
	 * @return Boolean
	 */
	public List<Proportion> selectByName(Proportion proportion) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Proportion.selectByName", proportion);
	}

	/**
	 * 根据ID查找权重参数
	 * 
	 * @param Integer
	 * @return List<Proportion>
	 */
	public Proportion selectById(Integer id) {
		return (Proportion) getSqlMapClientTemplate().queryForObject("sysconfig.Proportion.selectById", id);
	}

	/**
	 * 新增权重参数
	 * 
	 * @param proportion
	 * @return
	 */
	public void insertProportion(Proportion proportion) {
		getSqlMapClientTemplate().insert("sysconfig.Proportion.insertProportion", proportion);
	}

	/**
	 * 编辑权重参数
	 * 
	 * @param proportion
	 * @return
	 */
	public void updateProportion(Proportion proportion) {
		getSqlMapClientTemplate().update("sysconfig.Proportion.updateProportion", proportion);
	}

	/**
	 * 根据ID删除权重参数
	 * 
	 * @param proportion
	 * @return
	 */
	public void deleteById(Integer id) {
		getSqlMapClientTemplate().delete("sysconfig.Proportion.deleteById", id);
	}

	@Override
	public List<Proportion> selectNowProportion(Proportion pop) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Proportion.selectNowProportion", pop);
	}
}
