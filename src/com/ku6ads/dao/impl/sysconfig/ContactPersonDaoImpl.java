package com.ku6ads.dao.impl.sysconfig;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.ContactPerson;
import com.ku6ads.dao.iface.sysconfig.ContactPersonDao;
/**
 * 联系人
 * @author liujunshi
 *
 */
public class ContactPersonDaoImpl extends SqlMapClientDaoSupport implements ContactPersonDao {

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#deleteById(java.lang.Integer)
	 */
	public void deleteById(Integer ContactPersonId) {
		getSqlMapClientTemplate().delete("sysconfig.ContactPerson.deleteById", ContactPersonId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#insertContactPerson(com.ku6ads.dao.entity.sysconfig.ContactPerson)
	 */
	public Object insertContactPerson(ContactPerson ContactPerson) {
		return getSqlMapClientTemplate().insert("sysconfig.ContactPerson.insertContactPerson", ContactPerson);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#selectByContactPerson(com.ku6ads.dao.entity.sysconfig.ContactPerson)
	 */
	public List<ContactPerson> selectByContactPerson(ContactPerson ContactPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#selectByContactPersonLimit(com.ku6ads.dao.entity.sysconfig.ContactPerson)
	 */
	public List<ContactPerson> selectByContactPersonLimit(
			ContactPerson ContactPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#selectById(int)
	 */
	@Override
	public ContactPerson selectById(int ContactPersonId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactPerson> selectContactPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#updateContactPerson(com.ku6ads.dao.entity.sysconfig.ContactPerson)
	 */
	@Override
	public Integer updateContactPerson(ContactPerson contactPerson) {
		return getSqlMapClientTemplate().update("sysconfig.ContactPerson.updateContactPerson", contactPerson);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ContactPersonDao#selectContactPersonByType(com.ku6ads.dao.entity.sysconfig.ContactPerson)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactPerson> selectContactPersonByType(ContactPerson contactPerson) {
		return getSqlMapClientTemplate().queryForList("sysconfig.ContactPerson.selectContactPerson",contactPerson);
	}

}
