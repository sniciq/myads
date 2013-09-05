package com.ku6ads.services.iface.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.base.BaseServiceIface;

public interface AdvbarPriceService extends BaseServiceIface {

	public Object insertAdvbarPrice(AdvbarPrice advbarPrice);

	public List<AdvbarPrice> selectByAdvbarId(Integer advbarId);

	/**
	 * 查询对应广告条的价格
	 * @param barId
	 * @return List<AdvbarPrice>
	 */
	public List<BaseData> getAdvbarSaleTypes(Integer barId);
	
	/**
	 * 根据广告条价格id查询广告条形式.
	 * @param priceId
	 * @return List<AdvbarPrice>
	 */
	public List<AdvbarPrice> selectAdvbarFormatByPriceId(Integer priceId);
	
	/**
	 * 根据广告条形式id查询广告条价格
	 * @param id
	 * @return AdvbarPrice
	 */
	public AdvbarPrice selectAdvbarPriceById(Integer id);
	/**
	 * 修改价格
	 * add by zhangyan
	 * @param advbarPrice
	 * @return
	 */
	public Object updateAdvbarPrice(AdvbarPrice advbarPrice);
	
	public List<AdvbarPrice> getAdvbarByStyle(AdvbarPrice advbarPrice);
}
