package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvbarPriceDao;

/**
 * @author liyonghui
 *
 */
public class AdvbarPriceDaoImpl extends BaseAbstractDao implements AdvbarPriceDao {

	@Override
	public Object insertAdvbarPrice(AdvbarPrice advbarPrice) {
		return getSqlMapClientTemplate().insert("advert.AdvbarPrice.insertAdvbarPrice", advbarPrice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvbarPrice> selectByAdvbarId(Integer advbarId) {
		return getSqlMapClientTemplate().queryForList("advert.AdvbarPrice.selectByAdvbarId", advbarId);
	}
	
	public Object updateAdvbarPrice(AdvbarPrice advbarPrice) {
		return getSqlMapClientTemplate().insert("advert.AdvbarPrice.updatePriceById", advbarPrice);
	}
	@SuppressWarnings("unchecked")
	public List<BaseData> getAdvbarSaleTypes(Integer barId) {
		return getSqlMapClientTemplate().queryForList("advert.AdvbarPrice.getAdvbarSaleTypes", barId);
	}

	@SuppressWarnings("unchecked")
	public List<AdvbarPrice> selectAdvbarFormatByPriceId(Integer priceId) {
		return getSqlMapClientTemplate().queryForList("advert.AdvbarPrice.selectAdvbarFormatByPriceId", priceId);
	}

	public AdvbarPrice selectAdvbarPriceById(Integer id) {
		return (AdvbarPrice) getSqlMapClientTemplate().queryForObject("advert.AdvbarPrice.selectAdvbarPriceById", id);
	}

	@Override
	public List<AdvbarPrice> selectPriceFormatBaseData(AdvbarPrice advbarPriceEty) {
		return getSqlMapClientTemplate().queryForList("advert.AdvbarPrice.selectPriceFormatBaseData", advbarPriceEty);
	}
	
	public List<AdvbarPrice> getAdvbarByStyle(AdvbarPrice advbarPrice){
		return getSqlMapClientTemplate().queryForList("advert.AdvbarPrice.getAdvbarByStyle", advbarPrice);
	}

}
