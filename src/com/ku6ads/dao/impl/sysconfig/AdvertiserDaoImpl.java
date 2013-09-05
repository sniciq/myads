package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.iface.sysconfig.AdvertiserDao;
/**
 * 
 * @author liujunshi
 *
 */
public class AdvertiserDaoImpl extends SqlMapClientDaoSupport implements AdvertiserDao {

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#deleteById(int)
	 */
	public void deleteById(int AdvertiserId) {
		getSqlMapClientTemplate().delete("sysconfig.Advertiser.deleteById", AdvertiserId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#insertAdvertiser(com.ku6ads.dao.entity.sysconfig.Advertiser)
	 */
	public Object insertAdvertiser(Advertiser Advertiser) {
		return getSqlMapClientTemplate().insert("sysconfig.Advertiser.insertAdvertiser", Advertiser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#selectAdvertiser()
	 */
	@SuppressWarnings("unchecked")
	public List<Advertiser> selectAdvertiser() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Advertiser.selectByEntity");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advertiser> selectByAdvertiserLimit(Advertiser advertiser) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Advertiser.selectByAdvertiserLimit", advertiser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#selectById(int)
	 */
	@Override
	public Advertiser selectById(int AdvertiserId) {
		return (Advertiser) getSqlMapClientTemplate().queryForObject("sysconfig.Advertiser.selectById", AdvertiserId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#updateAdvertiser(com.ku6ads.dao.entity.sysconfig.Advertiser)
	 */
	@Override
	public Integer updateAdvertiser(Advertiser advertiser) {
		return getSqlMapClientTemplate().update("sysconfig.Advertiser.updateAdvertiser", advertiser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#selectBytAdvertiserCount(com.ku6ads.dao.entity.sysconfig.Advertiser)
	 */
	@Override
	public Integer selectBytAdvertiserCount(Advertiser advertiser) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.Advertiser.selectByAdvertiserCount",advertiser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#selectByAdvertiser(com.ku6ads.dao.entity.sysconfig.Advertiser)
	 */
	@Override
	public List<Advertiser> selectByAdvertiser(Advertiser Advertiser) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.AdvertiserDao#selectAdvertiserCategory()
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertiserCategory> selectAdvertiserCategory() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Category.selectCategorys");
	
	}

	

}
