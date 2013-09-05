package com.ku6ads.services.base;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;

/**
 * 服务层抽象类
 * @author yanghanguang
 *
 */
public abstract class BaseAbstractService implements BaseServiceIface {
	
	private BaseDao baseDao; 
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void deleteById(Integer id) {
		baseDao.deleteById(id);
	}

	public void insert(Object obj) {
		baseDao.insert(obj);
	}
	
	public void updateById(Object obj) {
		baseDao.updateById(obj);
	}

	public List selectByLimit(Object object) {
		return baseDao.selectByLimit(object);
	}
	
	public Integer selectLimitCount(Object object) {
		return baseDao.selectLimitCount(object);
	}
	
	public List selectByEntity(Object object) {
		return baseDao.selectByEntity(object);
	}

	public Object selectById(Integer id) {
		return baseDao.selectById(id);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
