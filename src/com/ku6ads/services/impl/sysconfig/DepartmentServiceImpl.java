package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.iface.sysconfig.DepartmentDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.DepartmentService;

public class DepartmentServiceImpl extends BaseAbstractService implements DepartmentService {

	public List<Department> selectDepartment() {
		return ((DepartmentDao)getBaseDao()).selectDepartment();
	}

}
