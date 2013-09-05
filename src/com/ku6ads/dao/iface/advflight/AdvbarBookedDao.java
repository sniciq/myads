package com.ku6ads.dao.iface.advflight;

import java.util.Map;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.AdvbarBooked;
import com.ku6ads.dao.entity.advflight.BookPackage;

public interface AdvbarBookedDao extends BaseDao {

	/**
	 * 得到 是否被别的售卖方式订过
	 * @param bookedEty
	 * @return
	 */
	public int selectIsSaledByOtherType(AdvbarBooked bookedEty);

	/**
	 * 得以该售卖方式预订的点位个数(点位个数，不是CPM量)
	 * @param bookedEty
	 * @return
	 */
	public int selectSaleTypeBookedCount(AdvbarBooked bookedEty);

	/**
	 * 删除点位后释放对应资源
	 * @param paramMap
	 */
	public void releaseDeleteBooks(Map paramMap);
	
	/**
	 * 释放排期包的所有点位
	 * @param bookPackageId
	 */
	public void releaseBookPackage(BookPackage bookPackage);

}
