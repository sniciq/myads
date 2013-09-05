package com.ku6ads.dao.iface.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advflight.BookPackage;

public interface AdvbarPreBookDao extends BaseDao {
	
	/**
	 * 得到本月广告条已订的排期
	 * @param searchEty
	 * @return
	 */
	public List<AdvbarPreBook> searchMonthBookedList(AdvbarPreBook searchEty);

	/**
	 * 释放资源
	 * @param paramMap
	 */
	public void releasePreBooks(Map paramMap);

	/**
	 * 释放排期包所占点位资源
	 * @param bookPackage
	 */
	public void releaseBookPackagePreBooks(BookPackage bookPackage);
}
