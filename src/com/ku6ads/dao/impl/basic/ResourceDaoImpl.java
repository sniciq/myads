package com.ku6ads.dao.impl.basic;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.basic.Resource;
import com.ku6ads.dao.iface.basic.ResourceDao;

public class ResourceDaoImpl extends SqlMapClientDaoSupport implements ResourceDao {

	public Integer deleteByResourceId(Integer id) {
		return new Integer(this.getSqlMapClientTemplate().delete("basic.resource.deleteByResourceId", id));
	}

	public Object insertResource(Resource menu) {
		return getSqlMapClientTemplate().insert("basic.resource.insertResource", menu);
	}

	public Resource selectByResourceId(Integer id) {
		return (Resource) getSqlMapClientTemplate().queryForObject("basic.resource.selectByResourceId", id);
	}

	public Integer updateResource(Resource menu) {
		return new Integer(getSqlMapClientTemplate().update("basic.resource.updateResource", menu));
	}

	@SuppressWarnings("unchecked")
	public List<Resource> selectByResource(Resource res) {
		return getSqlMapClientTemplate().queryForList("basic.resource.selectByResource", res);
	}

	@SuppressWarnings("unchecked")
	public List<Resource> selectNavigateList(Resource res) {
		return getSqlMapClientTemplate().queryForList("basic.resource.selectNavigateList", res);
	}

	@Override
	public Integer selectByResourceCount(Resource res) {
		return (Integer) getSqlMapClientTemplate().queryForObject("basic.resource.selectByResourceCount", res);
	}

	@Override
	public List<Resource> selectByResourceLimit(Resource res) {
		return getSqlMapClientTemplate().queryForList("basic.resource.selectByResourceLimit", res);
	}
}
