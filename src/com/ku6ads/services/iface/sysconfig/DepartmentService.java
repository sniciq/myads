package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.services.base.BaseServiceIface;

public interface DepartmentService extends BaseServiceIface {

	/**
	 * 查询部门信息,返回集合
	 * @return List<Department>
	 */
	public List<Department> selectDepartment();
	
}
