package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.User;

/**
 * @author xuxianan
 *
 */
public interface DepartmentDao extends BaseDao {

	public List<Department> selectDepartment();

}
