package com.ku6ads.services.impl.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.AdvbarBooked;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.iface.advflight.AdvbarBookedDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvbarBookedService;

public class AdvbarBookedServiceImpl extends BaseAbstractService implements AdvbarBookedService {

	public void updateBookedAdvbarPoint(Book book, int flightNum) {
		AdvbarBookedDao bookedDao = (AdvbarBookedDao) getBaseDao();
		AdvbarBooked bookedEty = new AdvbarBooked();
		bookedEty.setAdvbarId(book.getAdvbarId());
		bookedEty.setBookDate(book.getStartTime());
		bookedEty.setSaleType(book.getSaleType());
		bookedEty.setPriority(book.getPriority());
		
		List list = bookedDao.selectByEntity(bookedEty);//需要改进，只查记录数
		if(list.size() >= 1) {//如果有，则更新
			AdvbarBooked _tbookedEty = (AdvbarBooked) list.get(0);
			_tbookedEty.setBookedFlight(_tbookedEty.getBookedFlight() + flightNum);
			bookedDao.updateById(_tbookedEty);
		}
		else {//插入
			bookedEty.setBookedFlight(flightNum);
			bookedDao.insert(bookedEty);
		}
	}
	
	/**
	 * 删除排期时，需要释放资源
	 */
	public void releaseDeleteBooks(Integer bookPackageId, Integer AdvbarId, Integer priority, String notDelPointIds) {
		Map paramMap = new HashMap();
		paramMap.put("bookPackageId", bookPackageId);
		paramMap.put("advbarId", AdvbarId);
		paramMap.put("priority", priority);
		paramMap.put("notDelBookIds", notDelPointIds);
		((AdvbarBookedDao) getBaseDao()).releaseDeleteBooks(paramMap);
	}

	@Override
	public void releaseBookPackage(BookPackage bookPackage) {
		((AdvbarBookedDao) getBaseDao()).releaseBookPackage(bookPackage);
	}
}
