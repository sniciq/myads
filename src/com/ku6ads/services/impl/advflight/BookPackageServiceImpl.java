package com.ku6ads.services.impl.advflight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvFlightService;
import com.ku6ads.services.iface.advflight.AdvbarBookedService;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.ConflictCheckService;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.Dozer;

public class BookPackageServiceImpl extends BaseAbstractService implements BookPackageService {
	private Logger logger = Logger.getLogger(BookPackageServiceImpl.class);
	private BookDao bookDao;
	private ProjectDao projectDao;
	private AdvbarBookedService advbarBookedService;
	private AdvbarPreBookService advbarPreBookService;
	private ConflictCheckService conflictCheckService;
	private AdvFlightService advFlightService;
	
	public List<BookPackage> selectByProjectId(BookPackage bookPackage){
		return ((BookPackageDao)getBaseDao()).selectByProjectId(bookPackage);	
	}
	
	public void update(BookPackage bookPackage){
		((BookPackageDao)getBaseDao()).updateByProjectId(bookPackage);
	}
	
	@Override
	public List<BookPackage> selectRelationBackgroudBookPkgLimit(BookPackage bookPackage) {
		return  ((BookPackageDao)getBaseDao()).selectRelationBackgroudBookPkgLimit(bookPackage);
	}

	@Override
	public int selectRelationBackgroudBookPkgLimitCount(BookPackage bookPackage) {
		return ((BookPackageDao)getBaseDao()).selectRelationBackgroudBookPkgLimitCount(bookPackage);
	}

	@Override
	public Map<String, Object> updateBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			Project projectEty = (Project) projectDao.selectById(bookPackage.getProjectId());
			configProjectStatus(projectEty, bookPackage);
			
			//1. 释放点位
			BookPackage oldBookPackage = (BookPackage) getBaseDao().selectById(bookPackage.getId());
			releaseBookPoint(bookPackage.getId());
			//2. 申请新点位
			if(pointDataArray != null && pointDataArray.size() > 0) {
				List<Book> bookList = new ArrayList<Book>();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				for(int i = 0; i < pointDataArray.size(); i++) {
					Book bookEty = (Book) Dozer.getInstance().mapper(bookPackage, Book.class);
					bookEty.setBookPackageId(bookPackage.getId());
					bookEty.setCreateTime(oldBookPackage.getCreateTime());
					bookEty.setCreator(oldBookPackage.getCreator());
					
					JSONObject jsonObj = JSONObject.fromObject(pointDataArray.get(i));
					if(jsonObj.isNullObject())
						continue;
					
					bookEty.setStartTime(df.parse((String)jsonObj.get("startTime")));
					bookEty.setEndTime(df.parse((String)jsonObj.get("startTime")));
					
					int newBookFlight = jsonObj.getInt("flightNum");
					if(bookEty.getSaleType().intValue() == 2) {
						newBookFlight *= 1000;
					}
					bookEty.setFlightNum(newBookFlight);
					
					double futureFlight = BookCommon.getUseableFutureFlight(bookEty.getAdvbarId(), bookEty.getStartTime(), bookEty.getSaleType());
					bookEty.setFutureFlight(futureFlight);
					
					bookList.add(bookEty);
				}
				applyBookPoint(bookPackage, bookList);
			}
			
			//3. 保存基本信息
			//3.1 修改执行单状态
			projectDao.updateById(projectEty);
			
			//3.2 保存基本信息
			getBaseDao().updateById(bookPackage);
			
			//3.3 更新排期包的期限
			((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackage.getId());
			//3.4 更新排期表的流量数据等
			updateookPackageState(bookPackage.getId(), bookPackage.getAdvbarId(), bookPackage.getPriceId());
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
			
			BookPackage oldBookPackage = (BookPackage) getBaseDao().selectById(bookPackage.getId());
			
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
			releaseBookPoint(bookPackage.getId());
			
			//3. 申请新点位
			applyBookPoint(bookPackage, newBookList);
			
			//3. 保存基本信息
			//3.1 修改执行单状态
			projectDao.updateById(projectEty);
			//3.1 保存基本信息
			getBaseDao().updateById(bookPackage);
			//3.2 更新排期包的期限
			((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackage.getId());
			//3.3 更新排期表的流量数据等
			updateookPackageState(bookPackage.getId(), bookPackage.getAdvbarId(), bookPackage.getPriceId());
			
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
	
	@Override
	public Map<String, Object> saveBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception {
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			Project projectEty = (Project) projectDao.selectById(bookPackage.getProjectId());
			configProjectStatus(projectEty, bookPackage);
			
			Integer bookPackageId = getBaseDao().insert(bookPackage);
			
			//2. 申请新点位
			if(pointDataArray != null && pointDataArray.size() > 0) {
				List<Book> bookList = new ArrayList<Book>();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				for(int i = 0; i < pointDataArray.size(); i++) {
					Book bookEty = (Book) Dozer.getInstance().mapper(bookPackage, Book.class);
					bookEty.setBookPackageId(bookPackageId);
					JSONObject jsonObj = JSONObject.fromObject(pointDataArray.get(i));
					if(jsonObj.isNullObject())
						continue;
					
					bookEty.setStartTime(df.parse((String)jsonObj.get("startTime")));
					bookEty.setEndTime(df.parse((String)jsonObj.get("startTime")));
					
					int newBookFlight = jsonObj.getInt("flightNum");
					if(bookEty.getSaleType().intValue() == 2) {
						newBookFlight *= 1000;
					}
					bookEty.setFlightNum(newBookFlight);
					
					double futureFlight = BookCommon.getUseableFutureFlight(bookEty.getAdvbarId(), bookEty.getStartTime(), bookEty.getSaleType());
					bookEty.setFutureFlight(futureFlight);
					
					bookList.add(bookEty);
				}
				applyBookPoint(bookPackage, bookList);
			}
			
			//3. 保存基本信息
			//3.1 修改执行单状态
			projectDao.updateById(projectEty);
			//3.2 更新排期包的期限
			((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackage.getId());
			//3.3 更新排期表的流量数据等
			updateookPackageState(bookPackageId, bookPackage.getAdvbarId(), bookPackage.getPriceId());
			
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
	public void releaseBookPoint(int bookPackageId) throws Exception {
		// 1. 修改advbarBooked和advbarPreBook表数据
		BookPackage bookPackage = (BookPackage) getBaseDao().selectById(bookPackageId);
		
		bookDao.deleteByBookPackageId(bookPackageId);//删除排期
		
		//已订表
		advbarBookedService.releaseBookPackage(bookPackage);
		//可订表
		advbarPreBookService.releaseBookPackage(bookPackage);
	}
	
	@Override
	public void applyBookPoint(BookPackage bookPackage, List<Book> bookList) throws Exception {
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			Map<String, String> conflictMap = conflictCheckService.checkBook(bookEty, bookEty.getFlightNum());
			if(!conflictMap.get("result").equals("success")) {//有冲突，则不保存，直接返回
				ConflictExcption ce = new ConflictExcption("BookConflict");
				ce.setConflictMap(conflictMap);
				throw ce;
			}
			
			//保存已订点位策略表
			advbarBookedService.updateBookedAdvbarPoint(bookEty, bookEty.getFlightNum());
			
			//更新点位剩余预订表
			advbarPreBookService.updatePreBook(bookEty, bookEty.getFlightNum());
			bookDao.insert(bookEty);
		}
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
		((BookPackageDao)getBaseDao()).updateBookPackageState(paramMap);
	}
	
	public Map<String, Object> deleteBookPackage(int bookPackageId, Project projectEty) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(projectEty.getStatusFlag().intValue() == BussinessStatus.statusFlag_notExcuted) {//未执行过的执行单，可以任意删改
				//1. 释放资源
				releaseBookPoint(bookPackageId);
				//2. 修改状态
				((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackageId);
				//3. 删除排期基本信息
				getBaseDao().deleteById(bookPackageId);
			}
			else if(projectEty.getStatusFlag().intValue() == BussinessStatus.statusFlag_hasExcuted) {//执行过的执行单, 只能删改未执行的排期
				int executedBookCount = bookDao.selectExecutedBookCount(bookPackageId);//得到执行过的排期数量---排期
				if(executedBookCount <= 0) {
					//1. 释放资源
					releaseBookPoint(bookPackageId);
					//2. 修改状态
					((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackageId);
					//3. 删除排期基本信息
					getBaseDao().deleteById(bookPackageId);
				}
				else {
					//1. 释放资源
					releaseBookPoint(bookPackageId);
					//2. 修改状态
					((BookPackageDao)getBaseDao()).updateRunTimeRarge(bookPackageId);
				}
			}
			
			retMap.put("result", "success");
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("删除排期包错误", e);
			retMap.put("result", "error");
			retMap.put("info", "删除排期包错误" + e.getMessage());
			throw e;
		}
		return retMap;
	}
	
	public Map<String, Object> deleteBookPackage(int bookPackageId, int projectId) throws Exception {
		Project projectEty = (Project) projectDao.selectById(projectId);
		return deleteBookPackage(bookPackageId, projectEty);
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
	
	public Integer selectBookPackageById(int bookPackageId) {
		return bookDao.selectExecutedBookCount(bookPackageId);
	}
	
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public AdvbarBookedService getAdvbarBookedService() {
		return advbarBookedService;
	}

	public void setAdvbarBookedService(AdvbarBookedService advbarBookedService) {
		this.advbarBookedService = advbarBookedService;
	}

	public AdvbarPreBookService getAdvbarPreBookService() {
		return advbarPreBookService;
	}

	public void setAdvbarPreBookService(AdvbarPreBookService advbarPreBookService) {
		this.advbarPreBookService = advbarPreBookService;
	}

	public ConflictCheckService getConflictCheckService() {
		return conflictCheckService;
	}

	public void setConflictCheckService(ConflictCheckService conflictCheckService) {
		this.conflictCheckService = conflictCheckService;
	}

	public AdvFlightService getAdvFlightService() {
		return advFlightService;
	}

	public void setAdvFlightService(AdvFlightService advFlightService) {
		this.advFlightService = advFlightService;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
}
