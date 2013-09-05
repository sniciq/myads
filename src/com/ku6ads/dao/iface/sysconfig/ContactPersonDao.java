package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.ContactPerson;
/**
 * 联系人
 * @author liujunshi
 *
 */
public interface ContactPersonDao {
	/**
	 * 新增联系人
	 * @param department
	 * @return
	 */
	public Object insertContactPerson(ContactPerson ContactPerson);

	/**
	 * 按照联系人查询
	 * @param ContactPerson
	 * @return
	 */
	public List<ContactPerson> selectByContactPerson(ContactPerson ContactPerson);

	/**
	 * 更新联系人信息
	 * @param ContactPerson
	 * @return
	 */
	public Integer updateContactPerson(ContactPerson ContactPerson);

	/**
	 * 按照id查询联系人信息
	 * @param ContactPersonId
	 * @return
	 */
	public ContactPerson selectById(int ContactPersonId);

	/**
	 * 按照id删除联系人信息
	 * @param ContactPersonId
	 */
	public void deleteById(Integer ContactPersonId);

	/**
	 * 查询联系人信息,返回集合
	 * @return
	 */
	public List<ContactPerson> selectContactPerson();

	public List<ContactPerson> selectByContactPersonLimit(ContactPerson ContactPerson);

	public List<ContactPerson> selectContactPersonByType(ContactPerson contactPerson);

}
