package com.ku6ads.services.base;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;

/**
 * 服务层基础接口
 * @author yanghanguang
 *
 */
public interface BaseServiceIface {
	
	public BaseDao getBaseDao();
	
	/**
	 * 插入
	 * @param obj
	 */
	public void insert(Object obj);
	
	/**
	 * 根据ID号修改单个实体
	 * @param obj
	 */
	public void updateById(Object obj);
	
	/**
	 * 根据ID号查询单个实体
	 * @param obj
	 */
	public Object selectById(Integer id);
	
	/**
	 * 根据ID号删除单个实体
	 * @param id
	 */
	public void deleteById(Integer id);
	
	public List selectByEntity(Object object);
	
	/**
	 * 由分页信息查询分页记录
	 * @param object
	 * @return
	 */
	public List selectByLimit(Object object);
	
	/**
	 * 为分页查询出记录总数
	 * @param object
	 * @return
	 */
	public Integer selectLimitCount(Object object);
	
}
