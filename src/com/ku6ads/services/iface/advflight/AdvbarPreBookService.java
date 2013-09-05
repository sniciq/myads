package com.ku6ads.services.iface.advflight;

import java.util.List;

import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 点位可订的信息<br>
 * 用于排期预订时的日历显示
 * @author YangHanguang
 *
 */
public interface AdvbarPreBookService extends BaseServiceIface {
	
//	/**
//	 * 用户预订点位后保存点位剩余预订表
//	 * @param book
//	 */
//	public void savePreBook(Book book);
	
	/**
	 * 用户修改预订点位后修改点位剩余预订表
	 * @param book 排期
	 * @param flightNum 投放量
	 */
	public void updatePreBook(Book book, int flightNum);
	
	/**
	 * 得到本月广告条已订的排期
	 * @param searchEty
	 * @return
	 */
	public List<AdvbarPreBook> searchMonthBookedList(AdvbarPreBook searchEty);
	
	/**
	 * 释放资源
	 * @param bookPackageId
	 * @param AdvbarId
	 * @param priority
	 * @param notDelPointIds
	 */
	public void releaseDeleteBooks(Integer bookPackageId, Integer AdvbarId, Integer priority, String notDelPointIds);
	
	/**
	 * 释放排期包的所有点位
	 * @param bookPackageId
	 */
	public void releaseBookPackage(BookPackage bookPackage);
}
