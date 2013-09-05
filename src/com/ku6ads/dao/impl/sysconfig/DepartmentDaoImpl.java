package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.iface.sysconfig.DepartmentDao;

public class DepartmentDaoImpl extends BaseAbstractDao implements DepartmentDao {

	@SuppressWarnings("unchecked")
	public List<Department> selectDepartment() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Department.selectByEntity");
	}

}
