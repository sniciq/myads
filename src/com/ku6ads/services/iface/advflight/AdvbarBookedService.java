package com.ku6ads.services.iface.advflight;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 点位已经订的信息<br>
 * 用于排期预订时冲突检查
 * @author YangHanguang
 *
 */
public interface AdvbarBookedService extends BaseServiceIface {
	
//	/**
//	 * 当用户预订点位后将详细信息保存到详细已订点位表中
//	 * @param book
//	 */
//	public void saveBookedAdvbarPoint(Book book);
	
	/**
	 * 当用户修改预订点位后修改详细已订点位表
	 * @param book 排期
	 * @param flightNum 投放量
	 */
	public void updateBookedAdvbarPoint(Book book,int flightNum);
	
	/**
	 * 当用户删除预订点位后删除详细已订点位表
	 * @param book
	 */
	public void releaseDeleteBooks(Integer bookPackageId, Integer AdvbarId, Integer priority, String notDelPointIds);

	/**
	 * 释放排期包的所有点位
	 * @param bookPackageId
	 */
	public void releaseBookPackage(BookPackage bookPackage);
	
}
