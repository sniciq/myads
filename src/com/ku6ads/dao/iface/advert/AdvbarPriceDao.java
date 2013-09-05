package com.ku6ads.dao.iface.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.basic.BaseData;

public interface AdvbarPriceDao extends BaseDao {
	
	public Object insertAdvbarPrice(AdvbarPrice advbarPrice);
	
	public List<AdvbarPrice> selectByAdvbarId(Integer advbarId);
	/**
	 * 修改价格
	 * add by zhangyan
	 * @param advbarPrice
	 * @return
	 */
	public Object updateAdvbarPrice(AdvbarPrice advbarPrice);
	
	public List<BaseData> getAdvbarSaleTypes(Integer barId);
	
	public List<AdvbarPrice> selectAdvbarFormatByPriceId(Integer priceId);
	
	public AdvbarPrice selectAdvbarPriceById(Integer id);

	public List<AdvbarPrice> selectPriceFormatBaseData(AdvbarPrice advbarPriceEty);

	public List<AdvbarPrice> getAdvbarByStyle(AdvbarPrice advbarPrice);
}
