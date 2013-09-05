package com.ku6ads.dao.iface.sysconfig;

import java.util.List;
import com.ku6ads.dao.entity.sysconfig.Company;
/**
 * 公司
 * @author liujunshi
 *
 */
public interface CompanyDao {
	/**
	 * 新增公司
	 * @param department
	 * @return
	 */
	public Object insertCompany(Company Company);

	/**
	 * 按照公司查询
	 * @param Company
	 * @return
	 */
	public List<Company> selectByCompany(Company Company);

	/**
	 * 更新公司信息
	 * @param Company
	 * @return
	 */
	public Integer updateCompany(Company Company);

	/**
	 * 按照id查询公司信息
	 * @param CompanyId
	 * @return
	 */
	public Company selectById(int CompanyId);

	/**
	 * 按照id删除公司信息
	 * @param CompanyId
	 */
	public void deleteById(int CompanyId);

	/**
	 * 查询公司信息,返回集合
	 * @return
	 */
	public List<Company> selectCompany();

	public List<Company> selectByCompanyLimit(Company Company);

}
