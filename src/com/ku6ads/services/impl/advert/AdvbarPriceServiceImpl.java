package com.ku6ads.services.impl.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvbarPriceDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.AdvbarPriceService;

/**
 * @author liyonghui
 *
 */
public class AdvbarPriceServiceImpl extends BaseAbstractService implements AdvbarPriceService {

	private AdvbarPriceDao advbarPriceDao;
	
	public AdvbarPriceDao getAdvbarPriceDao() {
		return advbarPriceDao;
	}

	public void setAdvbarPriceDao(AdvbarPriceDao advbarPriceDao) {
		this.advbarPriceDao = advbarPriceDao;
	}

	@Override
	public Object insertAdvbarPrice(AdvbarPrice advbarPrice) {
		return advbarPriceDao.insertAdvbarPrice(advbarPrice);
	}

	@Override
	public List<AdvbarPrice> selectByAdvbarId(Integer advbarId) {
		return advbarPriceDao.selectByAdvbarId(advbarId);
	}


	@Override
	public Object updateAdvbarPrice(AdvbarPrice advbarPrice) {
		// TODO Auto-generated method stub
		return advbarPriceDao.updateAdvbarPrice(advbarPrice);
	}


	public List<BaseData> getAdvbarSaleTypes(Integer barId) {
		return advbarPriceDao.getAdvbarSaleTypes(barId);
	}

	public List<AdvbarPrice> selectAdvbarFormatByPriceId(Integer priceId) {
		return advbarPriceDao.selectAdvbarFormatByPriceId(priceId);
	}

	public AdvbarPrice selectAdvbarPriceById(Integer id) {
		return advbarPriceDao.selectAdvbarPriceById(id);
	}
	
	public List<AdvbarPrice> getAdvbarByStyle(AdvbarPrice advbarPrice){
		return advbarPriceDao.getAdvbarByStyle(advbarPrice);
	}
	
	

}
