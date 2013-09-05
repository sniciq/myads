package com.ku6ads.services.impl.advflight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.dao.entity.advflight.AdvbarBooked;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.FutureFlight;
import com.ku6ads.dao.entity.sysconfig.Proportion;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advert.BarProportionDao;
import com.ku6ads.dao.iface.advflight.AdvbarBookedDao;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;
import com.ku6ads.dao.iface.sysconfig.ProportionDao;
import com.ku6ads.dao.iface.sysconfig.SiteDao;
import com.ku6ads.services.iface.advflight.AdvbarBookedService;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;

public class BookCommon {
	
	@Resource(name="FutureFlightDao")
	private FutureFlightDao futureFlightDao;
	
	@Resource(name="BarProportionDao")
	private BarProportionDao barProportionDao;
	
	@Resource(name="SiteDao")
	private SiteDao siteDao;
	
	@Resource(name="ProportionDao")
	private ProportionDao proportionDao;
	
	@Resource(name="AdvbarBookedService")
	private AdvbarBookedService advbarBookedService;
	
	@Resource(name="AdvbarPreBookService")
	private AdvbarPreBookService advbarPreBookService;
	
	@Resource(name="AdvbarDao")
	private AdvbarDao advbarDao;
	
	@Resource(name="AdvbarBookedDao")
	private AdvbarBookedDao advbarBookedDao;
	
	private Logger logger = Logger.getLogger(BookCommon.class);
	
	public static BookCommon BookCommonInstance;
	
	public void init() {
		BookCommonInstance = this;
//		BookCommonInstance.advbarDao = this.advbarDao;
//		BookCommonInstance.barProportionDao = this.barProportionDao;
	}
	
	/**
	 * 得到广告条某天的预估值(预估表中的值)
	 * @param advbarId
	 * @param bookDate
	 * @return
	 */
	public static synchronized int getAdvbarFutureFlight(int advbarId, Date bookDate) {
		FutureFlight futureFlightEty = new FutureFlight();
		futureFlightEty.setAdvbarId(advbarId);
		futureFlightEty.setCreateTime(new Date());
		List<FutureFlight> futureList = BookCommonInstance.futureFlightDao.selectAdvbarFutureFlight(futureFlightEty);// 预估量
		int futureFlight = 0;
		if(futureList.size() > 0) {
			futureFlightEty = futureList.get(0);
			int c = bookDate.getDay() - futureFlightEty.getCreateTime().getDay();
			if (c < 0) c = (-1) * c;
			if(c % 7 == 0) 
				futureFlight = futureFlightEty.getNum1();
			else if(c % 7 == 1) 
				futureFlight = futureFlightEty.getNum2();
			else if(c % 7 == 2) 
				futureFlight = futureFlightEty.getNum3();
			else if(c % 7 == 3) 
				futureFlight = futureFlightEty.getNum4();
			else if(c % 7 == 4) 
				futureFlight = futureFlightEty.getNum5();
			else if(c % 7 == 5) 
				futureFlight = futureFlightEty.getNum6();
			else if(c % 7 == 6) 
				futureFlight = futureFlightEty.getNum7();
		}
		return futureFlight;
	}
	
	/**
	 * 得到可用预估(值已*1000)
	 * @param advbarId 广告条ID
	 * @param bookDate 时间
	 * @return
	 */
	public static synchronized double getUseableFutureFlight(int advbarId, Date bookDate, int saleType) {
		if(saleType == 1) {//CPD形式，返回广告容量
			int advbarContent = BookCommonInstance.advbarDao.selectBarContent(advbarId);
			return advbarContent;
		}
		else if(saleType == 2) {
			int futureFlight = getAdvbarFutureFlight(advbarId, bookDate);
			Map paraMap = new HashMap();
			paraMap.put("advbarId", advbarId);
			paraMap.put("dayTime", bookDate);
			BarProportion barProportion = BookCommonInstance.barProportionDao.selectByBaridAndBookTime(paraMap);
			Double proportion = 1.0;
			if(barProportion != null)
				proportion = barProportion.getProportion();
			
			double siteProp = getSiteModulus(advbarId);
			
			return futureFlight * siteProp * proportion * 0.9 * 1000;
		}
		
		return 0;
	}
	
	/**
	 * 得到广告条的剩余量
	 * @param book
	 * @return
	 */
	public static double getAdvbarLastFlight(Book book) {
		AdvbarBooked bookedEty = new AdvbarBooked();
		bookedEty.setPriority(book.getPriority());
		bookedEty.setAdvbarId(book.getAdvbarId());
		bookedEty.setBookDate(book.getStartTime());
		bookedEty.setSaleType(book.getSaleType());
		
		//按售卖方式、时间、广告条查询，，
		List list = BookCommonInstance.advbarBookedDao.selectByEntity(bookedEty);
		if(list.size() > 0) {//如果有，则判断剩余量是否够
			bookedEty = (AdvbarBooked) list.get(0);
			return book.getFutureFlight() - bookedEty.getBookedFlight();
		}
		else {
			return book.getFutureFlight();
		}
	}
	
	/**
	 * 生成权重<br>
	 * 权重=10*使用方式系数*地域定向系数*小时定向系数*优先级
	 * @param bookPackage
	 * @return
	 */
	public static double createBPkgProportion(BookPackage bookPackage) {
		Proportion pop = new Proportion();
		pop.setType("使用方式");
		pop.setName(bookPackage.getUseTypeName());
		double useTypeProportion = getProportion(pop);
		
		pop.setType("定向策略");
		pop.setName("小时定向");
		double hourDirectProportion = getProportion(pop);
		
		pop.setName("区域定向");
		double areaDirectProportion = getProportion(pop);
		
		return 10 * useTypeProportion * hourDirectProportion * areaDirectProportion * bookPackage.getPriority();
	}
	
	public static double getProportion(Proportion pop) {
		
		List<Proportion> popList = BookCommonInstance.proportionDao.selectNowProportion(pop);
		if(popList.size() == 1) {
			return popList.get(0).getValue();
		}
		return 1.0;
	}
	
	
	public static double getSiteModulus(int advbarId) {
		double siteProp = 1;
		Site siteEty = BookCommonInstance.siteDao.selectByAdvbarId(advbarId);
		if(siteEty != null)
			siteProp = siteEty.getModulus();
		return siteProp;
	}
	
	/**
	 * 重新生成预订数据
	 */
	public static synchronized void reGenrateBookedPreBookData() {
		Connection conn = null;
		try {
			conn = BookCommonInstance.siteDao.getConnection();
			
			//杨汉光 
			Statement stmt = conn.createStatement();
			
			//1. 删除备份表
			String delBak = "truncate table t_advbarbooked_del";
			stmt.execute(delBak);
			delBak = "truncate table t_advbarprebook_del";
			stmt.execute(delBak);
			
			//2. 备份
			String bakSql = "insert into t_advbarbooked_del(advbarId,saleType,bookDate,bookedFlight,priority,hour,city,deleteTime) " +
					"select advbarId,saleType,bookDate,bookedFlight,priority,hour,city, now() from t_advbarbooked";
			stmt.execute(bakSql);
			bakSql = "insert into t_advbarprebook_del(advbarId,saleType,bookDate,bookedFlight,canBook,priority,deleteTime) select advbarId,saleType,bookDate,bookedFlight,canBook,priority,now() from t_advbarprebook ";
			stmt.execute(bakSql);
			
			//3. 删除
			delBak = "truncate table t_advbarbooked";
			stmt.execute(delBak);
			delBak = "truncate table t_advbarprebook";
			stmt.execute(delBak);
			
			//4. 根据Book表中的数据重新生成
			ResultSet rs = stmt.executeQuery("select * from t_book where DATE_FORMAT(startTime, '%Y-%m-%d') >= DATE_FORMAT(now(), '%Y-%m-%d') and status = 0");
			Book bookEty;
			while(rs.next()) {
				bookEty = new Book();
				bookEty.setId(rs.getInt("id"));
				bookEty.setAdvbarId(rs.getInt("advbarId"));
				bookEty.setBookPackageId(rs.getInt("bookPackageId"));
				bookEty.setSaleType(rs.getInt("saleType"));
				bookEty.setStartTime(rs.getDate("startTime"));
				bookEty.setEndTime(rs.getDate("endTime"));
				bookEty.setFlightNum(rs.getInt("flightNum"));
				bookEty.setPriority(rs.getInt("priority"));
				BookCommonInstance.advbarBookedService.updateBookedAdvbarPoint(bookEty, bookEty.getFlightNum());
				BookCommonInstance.advbarPreBookService.updatePreBook(bookEty, bookEty.getFlightNum());
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e) {
			BookCommonInstance.logger.error("重新生成预订数据错误！", e);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					BookCommonInstance.logger.error("重新生成预订数据错误！", e);
				}
			}
		}
	}

}
