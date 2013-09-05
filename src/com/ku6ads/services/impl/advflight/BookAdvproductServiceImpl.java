package com.ku6ads.services.impl.advflight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advproduct.AdvproductAdvbarEty;
import com.ku6ads.dao.entity.advproduct.AdvproductEty;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.dao.iface.advproduct.AdvproductAdvbarDao;
import com.ku6ads.dao.iface.advproduct.AdvproductDao;
import com.ku6ads.services.iface.advflight.AdvFlightService;
import com.ku6ads.services.iface.advflight.BookAdvproductService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.Dozer;

public class BookAdvproductServiceImpl implements BookAdvproductService {
	private Logger logger = Logger.getLogger(BookAdvproductServiceImpl.class);
	
	@Resource(name="ProjectDao")
	private ProjectDao projectDao;
	
	@Resource(name="BookPackageDao")
	private BookPackageDao bookPackageDao;
	
	@Resource(name="BookDao")
	private BookDao bookDao;
	
	@Resource(name="BookPackageService")
	private BookPackageService bookPackageService;
	
	@Resource(name="AdvproductAdvbarDao")
	private AdvproductAdvbarDao advproductAdvbarDao;
	
	@Resource(name="AdvproductDao")
	private AdvproductDao advproductDao;
	
	@Resource(name="BookAdvproductFlightDivider")
	private BookAdvproductFlightDivider bookAdvproductFlightDivider;
	
	@Resource(name="AdvFlightService")
	private AdvFlightService advFlightService;
	
	@Override
	public Map<String, Object> saveBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			Project projectEty = (Project) projectDao.selectById(bookPackage.getProjectId());
			configProjectStatus(projectEty, bookPackage);
			
			AdvproductEty advpEty = (AdvproductEty) advproductDao.selectById(bookPackage.getAdvproductId());
			
			Integer bookPackageId = bookPackageDao.insert(bookPackage);
			bookPackage.setId(bookPackageId);
			
			//1. 得到所有的广告条
			AdvproductAdvbarEty ety = new AdvproductAdvbarEty();
			ety.setAdvproductId(bookPackage.getAdvproductId());
			List<AdvproductAdvbarEty> advpList = advproductAdvbarDao.selectByEntity(ety);
			
			//2. 根据广告产品类型为广告条分配预订量
			List<Book> bookList = createBookList(advpList, pointDataArray, bookPackage, advpEty);
			
			//3. 申请点位
			bookPackageService.applyBookPoint(bookPackage, bookList);
			
			//4.1 修改执行单状态
			projectDao.updateById(projectEty);
			//4.2 更新排期包的期限
			bookPackageDao.updateRunTimeRarge(bookPackage.getId());
			
			//4.3 更新排期表的流量数据等
			for(int k = 0; k < advpList.size(); k++) {
				AdvproductAdvbarEty apety = advpList.get(k);
				updateookPackageState(bookPackageId, apety.getAdvbarId(), bookPackage.getPriceId());
			}
			
			retMap.put("result", "success");
			return retMap;
		}
		catch (ConflictExcption ce) {//排期有冲突
			logger.debug("点位不足", ce);
			throw ce;
		}
		catch (Exception e) {
			logger.error("修改排期包错误", e);
			throw e;
		}
	}

	@Override
	public Map<String, Object> updateBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			Project projectEty = (Project) projectDao.selectById(bookPackage.getProjectId());
			configProjectStatus(projectEty, bookPackage);
			
			//1. 释放点位
//			BookPackage oldBookPackage = (BookPackage) bookPackageDao.selectById(bookPackage.getId());
			bookPackageService.releaseBookPoint(bookPackage.getId());
			
			AdvproductEty advpEty = (AdvproductEty) advproductDao.selectById(bookPackage.getAdvproductId());
			
			//1. 得到所有的广告条
			AdvproductAdvbarEty ety = new AdvproductAdvbarEty();
			ety.setAdvproductId(bookPackage.getAdvproductId());
			List<AdvproductAdvbarEty> advpList = advproductAdvbarDao.selectByEntity(ety);
			
			//2. 根据广告产品类型为广告条分配预订量
			List<Book> bookList = createBookList(advpList, pointDataArray, bookPackage, advpEty);
			
			//2. 申请新点位
			bookPackageService.applyBookPoint(bookPackage, bookList);
			
			//3. 保存基本信息
			//3.1 修改执行单状态
			projectDao.updateById(projectEty);
			
			//3.2 保存基本信息
			bookPackageDao.updateById(bookPackage);
			
			//3.3 更新排期包的期限
			bookPackageDao.updateRunTimeRarge(bookPackage.getId());
			
			//3.4 更新排期表的流量数据等
			for(int k = 0; k < advpList.size(); k++) {
				AdvproductAdvbarEty apety = advpList.get(k);
				updateookPackageState(bookPackage.getId(), apety.getAdvbarId(), bookPackage.getPriceId());
			}
			
			retMap.put("result", "success");
			return retMap;
		}
		catch (ConflictExcption ce) {//排期有冲突
			logger.debug("点位不足", ce);
			throw ce;
		}
		catch (Exception e) {
			logger.error("修改排期包错误", e);
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> updateBookPackagePriority(BookPackage bookPackage) throws Exception {
		try {
			Project projectEty = (Project) projectDao.selectById(bookPackage.getProjectId());
			configProjectStatus(projectEty, bookPackage);
			
			AdvproductAdvbarEty ety = new AdvproductAdvbarEty();
			ety.setAdvproductId(bookPackage.getAdvproductId());
			List<AdvproductAdvbarEty> advpList = advproductAdvbarDao.selectByEntity(ety);
			
			BookPackage oldBookPackage = (BookPackage) bookPackageDao.selectById(bookPackage.getId());
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			//1. 得到原先的点位数据
			List<Book> newBookList = new ArrayList<Book>();
			List<Book> bookedList = bookDao.selectByBookPackageId(bookPackage.getId());
			for(int i = 0; i < bookedList.size(); i++) {
				Book bookedEty = bookedList.get(i);
				Book bookEty = (Book) Dozer.getInstance().mapper(bookPackage, Book.class);
				if(bookEty.getAdvbarId() == null) {
					bookEty.setAdvbarId(bookedEty.getAdvbarId());
				}
				
				bookEty.setBookPackageId(bookPackage.getId());
				bookEty.setCreateTime(oldBookPackage.getCreateTime());
				bookEty.setCreator(oldBookPackage.getCreator());
				bookEty.setStartTime(bookedEty.getStartTime());
				bookEty.setFlightNum(bookedEty.getFlightNum());
				bookEty.setEndTime(bookedEty.getEndTime());
				
				double futureFlight = BookCommon.getUseableFutureFlight(bookedEty.getAdvbarId(), bookEty.getStartTime(), bookedEty.getSaleType());
				bookEty.setFutureFlight(futureFlight);
				
				newBookList.add(bookEty);
			}
			
			//2. 释放点位
			bookPackageService.releaseBookPoint(bookPackage.getId());
			
			//3. 申请新点位
			bookPackageService.applyBookPoint(bookPackage, newBookList);
			
			//3. 保存基本信息
			//3.1 修改执行单状态
			projectDao.updateById(projectEty);
			//3.1 保存基本信息
			bookPackageDao.updateById(bookPackage);
			//3.2 更新排期包的期限
			bookPackageDao.updateRunTimeRarge(bookPackage.getId());
			//3.3 更新排期表的流量数据等
			for(int k = 0; k < advpList.size(); k++) {
				AdvproductAdvbarEty apety = advpList.get(k);
				updateookPackageState(bookPackage.getId(), apety.getAdvbarId(), bookPackage.getPriceId());
			}
			
			//修改
			advFlightService.modifyByPackageId(bookPackage.getPriority(), bookPackage.getId());
			
			retMap.put("result", "success");
			return retMap;
		}
		catch (ConflictExcption ce) {//排期有冲突
			logger.debug("点位不足", ce);
			throw ce;
		}
		catch (Exception e) {
			logger.error("修改排期包错误", e);
			throw e;
		}
	}
	
	private List<Book> createBookList(List<AdvproductAdvbarEty> advpList, JSONArray pointDataArray, BookPackage bookPackage, AdvproductEty advpEty) throws Exception {
		List<Book> bookList = new ArrayList<Book>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int j = 0; j < pointDataArray.size(); j++) {
			JSONObject jsonObj = JSONObject.fromObject(pointDataArray.get(j));
			if(jsonObj.isNullObject())
				continue;
			
			List<Book> tempList = new ArrayList<Book>();
			Date startTime = df.parse((String)jsonObj.get("startTime"));
			int newBookFlight = jsonObj.getInt("flightNum");
			
			for(int i = 0; i < advpList.size(); i++) {
				AdvproductAdvbarEty advbarEty = advpList.get(i);
				Book bookEty = (Book) Dozer.getInstance().mapper(bookPackage, Book.class);
				bookEty.setChannelId(advbarEty.getChannelId());
				bookEty.setAdvbarId(advbarEty.getAdvbarId());
				bookEty.setBookPackageId(bookPackage.getId());
				bookEty.setScrBPackageId(advbarEty.getAdvproductId());//关联ID，存放广告产品ID
				bookEty.setStartTime(startTime);
				bookEty.setEndTime(startTime);
				
				double futureFlight = BookCommon.getUseableFutureFlight(bookEty.getAdvbarId(), bookEty.getStartTime(), bookEty.getSaleType());
				bookEty.setFutureFlight(futureFlight);//预估总量
				
				int lastFlight = (int) BookCommon.getAdvbarLastFlight(bookEty);//广告条余量
				bookEty.setLastFlight(lastFlight);
				
				tempList.add(bookEty);
			}
			
			divideFlight(tempList, newBookFlight, bookPackage.getSaleType(), advpEty.getType());
			bookList.addAll(tempList);
		}
		
		return bookList;
	}
	
	public void updateookPackageState(int bookPackageId, int barId, int priceId) throws Exception{
		List<Book> list = bookDao.selectByBookPackageId(bookPackageId);
		
		int period = list.size();
		double allflux = 0.0;
		double periodSum = 0.0;
		double priceSum = 0.0;
		
		for(int i = 0; i < list.size(); i++) {
			Book bookEty = list.get(i);
			allflux += BookCommon.getAdvbarFutureFlight(bookEty.getAdvbarId(), bookEty.getStartTime());
			
			if(bookEty.getSaleTypeName().equalsIgnoreCase("CPM")) {
				double ap = bookEty.getPrice() * bookEty.getFlightNum() / 1000;
				priceSum += ap;
			}
			else if(bookEty.getSaleTypeName().equalsIgnoreCase("CPD")) {
				priceSum += bookEty.getPrice() * bookEty.getFlightNum();
			}
		}
		periodSum = allflux;
		allflux = allflux / period;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("period", period);
		paramMap.put("priceId", priceId);
		paramMap.put("allflux", allflux);
		paramMap.put("periodSum", periodSum);
		paramMap.put("priceSum", priceSum);
		paramMap.put("bookPackageId", bookPackageId);
		paramMap.put("advbarId", barId);
		bookPackageDao.updateBookPackageState(paramMap);
	}

	/**
	 * 设置执行单状态
	 * @param projectEty
	 * @param bookPackage
	 */
	private void configProjectStatus(Project projectEty, BookPackage bookPackage) {
		if(projectEty.getBussinessStatus() == BussinessStatus.draft) {
		}
		else if(projectEty.getBussinessStatus() == BussinessStatus.verify_customer) {
			projectEty.setBussinessStatus(BussinessStatus.back);
			bookPackage.setBussinessStatus(BussinessStatus.back);
		}
		else if(projectEty.getBussinessStatus() == BussinessStatus.verify_first || projectEty.getBussinessStatus() == BussinessStatus.verify_second ) {
		}
		else if(projectEty.getBussinessStatus() == BussinessStatus.verify_first || projectEty.getBussinessStatus() == BussinessStatus.verify_second ) {
		}
		else if(projectEty.getBussinessStatus() == BussinessStatus.running ) {
			projectEty.setStatusFlag(BussinessStatus.statusFlag_hasExcuted);
			projectEty.setBussinessStatus(BussinessStatus.running_update);
			bookPackage.setBussinessStatus(BussinessStatus.running_update);
		}
	}
	
	/**
	 * 注意：输入中的预估值是不以是千次为单位，计算时需要改用千次来计算！
	 * @param bookList
	 * @param flight 总量
	 * @param saleType 售卖方式
	 * @param advpdtType 产品类型
	 */
	private void divideFlight(List<Book> bookList, int flight, int saleType, String advpdtType) {
		
		if(advpdtType.equals("B") && saleType == 2) {
			bookAdvproductFlightDivider.divideFlight_CPM_B(bookList, flight);
		}
		else if(advpdtType.equals("B") && saleType == 1) {
			bookAdvproductFlightDivider.divideFlight_CPD_B(bookList, flight);
		}
		else if(advpdtType.equals("A") && saleType == 2) {
			bookAdvproductFlightDivider.divideFlight_CPM_A(bookList, flight);
		}
		else if(advpdtType.equals("A") && saleType == 1) {
			bookAdvproductFlightDivider.divideFlight_CPD_A(bookList, flight);
		}
	}
}
