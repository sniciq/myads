package com.ku6ads.dao.impl.advflight;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.iface.advflight.AdvertisementDao;
/**
 * 广告DAO
 * @author liyonghui
 *
 */
public class AdvertisementDaoImpl extends BaseAbstractDao implements AdvertisementDao {

	@Override
	public int insertAdv(Object obj) {
		return Integer.parseInt(getSqlMapClientTemplate().insert(getMapping().getInsertMethod(), obj).toString());
	}
	
	public List<Advertisement> selectMailNotify(Date date){
		return getSqlMapClientTemplate().queryForList("advflight.Advertisement.selectMailNotify",date);
	}

	public List<Advertisement> selectAll(Integer advactiveId){
		return getSqlMapClientTemplate().queryForList("advflight.Advertisement.selectAll",advactiveId);
	}


	public Integer getBarTemType(int advId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advflight.Advertisement.selectBarTemType", advId);
	}
}
