package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.Company;
import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.iface.sysconfig.ConsumerDao;
/**
 * 
 * @author liujunshi
 *
 */
public class ConsumerDaoImpl  extends SqlMapClientDaoSupport implements ConsumerDao {

	@Override
	public void deleteById(int ConsumerId) {
		getSqlMapClientTemplate().delete("sysconfig.Consumer.deleteById", ConsumerId);

	}

	@Override
	public Object insertConsumer(Consumer consumer) {
		return getSqlMapClientTemplate().insert("sysconfig.Consumer.insertConsumer", consumer);
		
	}

	@Override
	public List<Consumer> selectByConsumer(Consumer Consumer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectByConsumerCount(Consumer consumer) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.Consumer.selectByConsumerCount",consumer);
	}

	@SuppressWarnings("unchecked")
	public List<Consumer> selectByConsumerLimit(Consumer consumer) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Consumer.selectByConsumerLimit", consumer);	
	}

	@Override
	public Consumer selectById(int ConsumerId) {
		return (Consumer) getSqlMapClientTemplate().queryForObject("sysconfig.Consumer.selectById", ConsumerId);
		
	}

	@SuppressWarnings("unchecked")
	public List<Consumer> selectConsumer() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Consumer.selectByEntity");
	}

	@Override
	public Integer updateConsumer(Consumer Consumer) {
		return getSqlMapClientTemplate().update("sysconfig.Consumer.updateConsumer", Consumer);
	}


	@SuppressWarnings("unchecked")
	public List<AdvertiserCategory> selectConsumerCategory() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Category.selectConsumerCategorys");
	}

	@SuppressWarnings("unchecked")
	public List<Company> selectCompanyList() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Company.selectCompanyList");
	}

}
