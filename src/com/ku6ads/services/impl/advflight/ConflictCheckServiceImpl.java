package com.ku6ads.services.impl.advflight;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.AdvbarBooked;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advflight.AdvbarBookedDao;
import com.ku6ads.services.iface.advflight.ConflictCheckService;

public class ConflictCheckServiceImpl implements ConflictCheckService {
	
	private AdvbarDao advbarDao;
	private AdvbarBookedDao advbarBookedDao;
	
	public Map<String, String> checkBook(Book book, int avlFlightNum) {
		//需要，上字典表取售卖方式名称 1: CPD, 2:CPM
		if(isSaleByOtherType(book)) {
			Map<String, String> retMap = new HashMap<String, String>();
			retMap.put("result", "fail");
			retMap.put("ConflictInfo", "SaledByOtherType");
			retMap.put("bookTime", new SimpleDateFormat("yyyy-MM-dd").format(book.getStartTime()));
			return retMap;
		}
		
		if(book.getSaleType().intValue() == 2) {//CPM只看量，跟城市地域时段没关系
			return checkBook_CPM(book, avlFlightNum);
		}
		else if(book.getSaleType().intValue() == 1){
			return checkBook_CPD(book, avlFlightNum);
		}
		else {
			
		}
		return null;
	}
	
	/**
	 * 同一优先级下是否已经按另一种形式预订<br>
	 * 同一优先级下，同一天只能有一种售卖方式CPM或者CPD
	 * @param book
	 * @return
	 */
	private boolean isSaleByOtherType(Book book) {
		AdvbarBooked bookedEty = new AdvbarBooked();
		bookedEty.setAdvbarId(book.getAdvbarId());
		bookedEty.setBookDate(book.getStartTime());
		bookedEty.setPriority(book.getPriority());
		bookedEty.setSaleType(book.getSaleType());
		
		int n = advbarBookedDao.selectIsSaledByOtherType(bookedEty);
		if(n > 0) 
			return true;
		else
			return false;
	}
	
	/**
	 * 检查CPM是否冲突<br>
	 * 按量进行检查，如果已经订总量已经大于可用预估值,则不可再订
	 * @param book
	 * @return
	 */
	private Map<String, String> checkBook_CPM(Book book, int avlFlightNum) {
		Map<String, String> retMap = new HashMap<String, String>();
		AdvbarBooked bookedEty = new AdvbarBooked();
		bookedEty.setPriority(book.getPriority());
		bookedEty.setAdvbarId(book.getAdvbarId());
		bookedEty.setBookDate(book.getStartTime());
		bookedEty.setSaleType(book.getSaleType());
		
		//按售卖方式、时间、广告条查询，，
		List list = advbarBookedDao.selectByEntity(bookedEty);
		if(list.size() > 0) {//如果有，则判断剩余量是否够
			bookedEty = (AdvbarBooked) list.get(0);
			if(book.getFutureFlight() - bookedEty.getBookedFlight() - avlFlightNum < 0) {
				retMap.put("result", "fail");
				retMap.put("ConflictInfo", "noLastContent");
				retMap.put("bookTime", new SimpleDateFormat("yyyy-MM-dd").format(book.getStartTime()));
			}
			else {
				retMap.put("result", "success");
			}
		}
		else {//如果没有，判断订量是否大于可用预估值
			if(book.getFutureFlight() - book.getFlightNum() < 0) {
				retMap.put("result", "fail");
				retMap.put("ConflictInfo", "noLastContent");
				retMap.put("bookTime", new SimpleDateFormat("yyyy-MM-dd").format(book.getStartTime()));
			}
			else {
				retMap.put("result", "success");
			}
		}
		return retMap;
	}
	
	/**
	 * CPD检查<br>
	 * CPD检查规则：同一广告条同一优先级下，按CPD形式订的的排期不能大于广告容量
	 * @param book
	 * @return
	 */
	private Map<String, String> checkBook_CPD(Book book, int avlFlightNum) {
		Map<String, String> retMap = new HashMap<String, String>();
		
		//按售卖方式、时间、广告条查询，
		AdvbarBooked bookedEty = new AdvbarBooked();
		bookedEty.setPriority(book.getPriority());
		bookedEty.setAdvbarId(book.getAdvbarId());
		bookedEty.setBookDate(book.getStartTime());
		bookedEty.setSaleType(book.getSaleType());
		
		//1. 得到按售卖形式已经订的次数
		List list = advbarBookedDao.selectByEntity(bookedEty);
		if(list.size() > 0) {//如果有，则判断剩余量是否够
			bookedEty = (AdvbarBooked) list.get(0);
			//2. 得到广告容量
			int advbarContent = advbarDao.selectBarContent(book.getAdvbarId());
			if(avlFlightNum > 0 && bookedEty.getBookedFlight() >= advbarContent) {
				retMap.put("result", "fail");
				retMap.put("ConflictInfo", "noLastContent");
				retMap.put("bookTime", new SimpleDateFormat("yyyy-MM-dd").format(book.getStartTime()));
			}
			else {
				retMap.put("result", "success");
			}
		}
		else {
			retMap.put("result", "success");
		}
		
		return retMap;
	}
	

	public AdvbarBookedDao getAdvbarBookedDao() {
		return advbarBookedDao;
	}

	public void setAdvbarBookedDao(AdvbarBookedDao advbarBookedDao) {
		this.advbarBookedDao = advbarBookedDao;
	}

	public AdvbarDao getAdvbarDao() {
		return advbarDao;
	}

	public void setAdvbarDao(AdvbarDao advbarDao) {
		this.advbarDao = advbarDao;
	}
	
}
