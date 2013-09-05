package com.ku6ads.services.impl.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advflight.AdvbarPreBookDao;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;

public class AdvbarPreBookServiceImpl extends BaseAbstractService implements AdvbarPreBookService {
	
	private FutureFlightDao futureFlightDao;
	private AdvbarDao advbarDao;
	/**
	 * 得到广告条的预估值
	 * @param prebook
	 * @return
	 */
	private double getFutureFlight(AdvbarPreBook prebook) {
		return BookCommon.getUseableFutureFlight(prebook.getAdvbarId(), prebook.getBookDate(), prebook.getSaleType());
	}
	
	public void updatePreBook(Book book, int flightNum) {
		AdvbarPreBook prebook = new AdvbarPreBook();
		prebook.setSaleType(book.getSaleType());
		prebook.setAdvbarId(book.getAdvbarId());
		prebook.setBookDate(book.getStartTime());
		prebook.setPriority(book.getPriority());
		
		AdvbarPreBookDao prebookDao = (AdvbarPreBookDao)getBaseDao();
		
		List list = prebookDao.selectByEntity(prebook);//
		if(list.size() >= 1) {//已有，则更新
			double barFutureFlight = getFutureFlight(prebook);
			AdvbarPreBook _prebook = (AdvbarPreBook) list.get(0);
			double n = barFutureFlight - _prebook.getBookedFlight();
			_prebook.setBookedFlight(_prebook.getBookedFlight() + flightNum);
			if(n <= 0) 
				_prebook.setCanBook(false);
			else 
				_prebook.setCanBook(true);
			prebookDao.updateById(_prebook);
		}
		else {
			prebook.setCanBook(true);
			prebook.setBookedFlight(flightNum);
			prebookDao.insert(prebook);
		}
	}
	
	public void releaseDeleteBooks(Integer bookPackageId, Integer AdvbarId, Integer priority, String notDelPointIds) {
		Map paramMap = new HashMap();
		paramMap.put("bookPackageId", bookPackageId);
		paramMap.put("advbarId", AdvbarId);
		paramMap.put("priority", priority);
		paramMap.put("notDelBookIds", notDelPointIds);
		((AdvbarPreBookDao) getBaseDao()).releasePreBooks(paramMap);
	}
	
	@Override
	public void releaseBookPackage(BookPackage bookPackage) {
		((AdvbarPreBookDao) getBaseDao()).releaseBookPackagePreBooks(bookPackage);
	}
	
	public List<AdvbarPreBook> searchMonthBookedList(AdvbarPreBook searchEty) {
		return ((AdvbarPreBookDao)getBaseDao()).searchMonthBookedList(searchEty);
	}

	public FutureFlightDao getFutureFlightDao() {
		return futureFlightDao;
	}

	public void setFutureFlightDao(FutureFlightDao futureFlightDao) {
		this.futureFlightDao = futureFlightDao;
	}

	public AdvbarDao getAdvbarDao() {
		return advbarDao;
	}

	public void setAdvbarDao(AdvbarDao advbarDao) {
		this.advbarDao = advbarDao;
	}
}
