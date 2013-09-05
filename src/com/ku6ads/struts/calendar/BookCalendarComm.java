package com.ku6ads.struts.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.FutureFlight;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advert.BarProportionDao;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;
import com.ku6ads.dao.iface.sysconfig.SiteDao;
import com.ku6ads.struts.advflight.BookCalendarEty;
import com.ku6ads.struts.advflight.CPMEty;

public class BookCalendarComm {
	
	@Resource(name="SiteDao")
	private SiteDao siteDao;
	
	@Resource(name="AdvbarDao")
	private AdvbarDao advbarDao;
	
	@Resource(name="BarProportionDao")
	private BarProportionDao barProportionDao;
	
	@Resource(name="FutureFlightDao")
	private FutureFlightDao futureFlightDao;
	
	public void initDateMap_CPD(Date month, Map<String, CPMEty> dataMap, List<AdvbarPreBook> advbarPreBookList, int advbarId, int saleTypeValue) {
		Advbar advbarEty = (Advbar) advbarDao.selectById(advbarId);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(month);
		int nowMonth = calendar.get(Calendar.MONTH);
		while(calendar.get(Calendar.MONTH) == nowMonth) {
			CPMEty cpmEty = new CPMEty();
			cpmEty.setAdvbarId(advbarId);
			cpmEty.setDate(calendar.getTime());
			cpmEty.setLastContent(advbarEty.getContent());//余量为广告容量
			cpmEty.setContentStatus(CPMEty.contentStatus_none);
			dataMap.put(df.format(calendar.getTime()), cpmEty);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		AdvbarPreBook preBookEty = null;
		for(int i = 0; i < advbarPreBookList.size(); i++) {
			preBookEty = advbarPreBookList.get(i);
			CPMEty cpmEty = dataMap.get(df.format(preBookEty.getBookDate()));
			if(cpmEty == null) 
				continue;
			
			if(preBookEty.getSaleType() == saleTypeValue) {//售卖方式相同
				if(preBookEty.getCanBook()) {
					cpmEty.setLastContent(advbarEty.getContent() - preBookEty.getBookedFlight());//余量 - 已订个数
					if(cpmEty.getLastContent() < advbarEty.getContent())
						cpmEty.setContentStatus(CPMEty.contentStatus_part);
					else
						cpmEty.setContentStatus(CPMEty.contentStatus_none);
				}
				else {
					cpmEty.setLastContent(0);
					cpmEty.setContentStatus(CPMEty.contentStatus_full);
				}
			}
			else if(preBookEty.getBookedFlight().intValue() <= 0){//售卖方式不同但已订为0，此时可订
				cpmEty.setLastContent(advbarEty.getContent());//余量为广告容量
				cpmEty.setContentStatus(CPMEty.contentStatus_none);
			}
			else {//售卖方式相同且已订不为0,此时不可订
				cpmEty.setLastContent(0);
				cpmEty.setContentStatus(CPMEty.contentStatus_full);
			}
		}
	}
	
	public void initDateMap_CPM(Date month, Map<String, CPMEty> dataMap, List<AdvbarPreBook> advbarPreBookList, int advbarId, int saleTypeValue) throws Exception {
		double siteProp = 1;
		Site siteEty = siteDao.selectByAdvbarId(advbarId);
		if(siteEty != null)
			siteProp = siteEty.getModulus();
		
		//1. 初始化日历
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(month);
		int nowMonth = calendar.get(Calendar.MONTH);
		while(calendar.get(Calendar.MONTH) == nowMonth) {
			CPMEty cpmEty = new CPMEty();
			cpmEty.setAdvbarId(advbarId);
			cpmEty.setDate(calendar.getTime());
			
			cpmEty.setSiteProp(siteProp);
			
			dataMap.put(df.format(calendar.getTime()), cpmEty);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		//设置广告条系数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("advbarId", advbarId);
		paramMap.put("dayTime", month);
		List<BarProportion> list = barProportionDao.selectByAdvbarIdAndTime(paramMap);
		for(int i = 0; i < list.size(); i++) {
			BarProportion barPropEty = (BarProportion) list.get(i);
			Date startTime = barPropEty.getStartTime();
			Date endtime =  barPropEty.getEndTime();
			Calendar t_cal = Calendar.getInstance();
			t_cal.setTime(startTime);
			while(endtime.after(startTime) || endtime.equals(startTime)) {
				CPMEty cpmEty = dataMap.get(df.format(startTime));
				t_cal.add(Calendar.DAY_OF_MONTH, 1);
				startTime = t_cal.getTime();
				
				if(cpmEty == null)
					continue;
				cpmEty.setBarProportion(barPropEty.getProportion());
			}
		}
		
		//2. 设置预估量
		FutureFlight futureFlightEty = new FutureFlight();
		futureFlightEty.setAdvbarId(advbarId);
		futureFlightEty.setCreateTime(new Date());
		List<FutureFlight> futureList = futureFlightDao.selectAdvbarFutureFlight(futureFlightEty);// 预估量
		if(futureList.size() > 0) {
			futureFlightEty = futureList.get(0);
			Set<String> keySet = dataMap.keySet();
			Iterator<String> iter = keySet.iterator();
			while(iter.hasNext()) {
				CPMEty cpmEty = dataMap.get(iter.next());
				Date date1 = df.parse(df.format(futureFlightEty.getCreateTime()));
				int c = (int) (date1.getTime() / 86400000 - cpmEty.getDate().getTime() / 86400000);
				if (c < 0) c = (-1) * c;
				if(c % 7 == 0) 
					cpmEty.setFutureFlight(futureFlightEty.getNum1() * 1000);
				else if(c % 7 == 1) 
					cpmEty.setFutureFlight(futureFlightEty.getNum2() * 1000);
				else if(c % 7 == 2) 
					cpmEty.setFutureFlight(futureFlightEty.getNum3() * 1000);
				else if(c % 7 == 3) 
					cpmEty.setFutureFlight(futureFlightEty.getNum4() * 1000);
				else if(c % 7 == 4) 
					cpmEty.setFutureFlight(futureFlightEty.getNum5() * 1000);
				else if(c % 7 == 5) 
					cpmEty.setFutureFlight(futureFlightEty.getNum6() * 1000);
				else if(c % 7 == 6) 
					cpmEty.setFutureFlight(futureFlightEty.getNum7() * 1000);
			}
		}
		
		AdvbarPreBook preBookEty = null;
		for(int i = 0; i < advbarPreBookList.size(); i++) {
			preBookEty = advbarPreBookList.get(i);
			CPMEty cpmEty = dataMap.get(df.format(preBookEty.getBookDate()));
			if(cpmEty == null) 
				continue;
			
			if(preBookEty.getSaleType() == saleTypeValue) {//售卖方式相同
				if(preBookEty.getCanBook()) {
					cpmEty.setAlreadyFlight(preBookEty.getBookedFlight());
					cpmEty.setContentStatus(CPMEty.contentStatus_part);
				}
				else {
					cpmEty.setLastContent(0);
					cpmEty.setContentStatus(CPMEty.contentStatus_full);
				}
			}
			else if(preBookEty.getBookedFlight().intValue() <= 0){//售卖方式不同但已订为0，此时可订
				//默认即为全部
			}
			else {//售卖方式相同且已订不为0,此时不可订
				cpmEty.setLastContent(0);
				cpmEty.setContentStatus(CPMEty.contentStatus_full);
			}
		}
	}
	
	public JSONArray getCalData(Date showDate, Map<String, CPMEty> dateMap) {
		JSONArray allArray = new JSONArray();
		try {
			JSONObject rowObj = new JSONObject();
			
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			
			if(showDate != null) {
				cal.setTime(showDate);
			}
			
			int nowDay = cal.get(Calendar.DAY_OF_MONTH);
			int nowMonth = cal.get(Calendar.MONTH);
			
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int weekday = cal.get(Calendar.DAY_OF_WEEK);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			for (int i = Calendar.SUNDAY; i < weekday; i++ ) {
				BookCalendarEty cEty = new BookCalendarEty();
				cEty.setText("");
				cEty.setDay(0);
				cEty.setWeekday(i);
				rowObj.put("weekDay_" + (i - 1) , cEty);
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sf.format(new Date());
			Date nowTime = new Date();
			String nowTimeStr = sf.format(nowTime);
			nowTime = sf.parse(nowTimeStr);
			
			while(cal.get(Calendar.MONTH) == nowMonth) {
				BookCalendarEty cEty = new BookCalendarEty();
				cEty.setText(cal.get(Calendar.DAY_OF_MONTH)+ "");
				cEty.setDay(cal.get(Calendar.DAY_OF_MONTH));
				cEty.setDateStr(df.format(cal.getTime()));
				cEty.setWeekday(cal.get(Calendar.DAY_OF_WEEK));
				
				CPMEty dataEty = dateMap.get(sf.format(cal.getTime()));
				if(dataEty != null) {
					cEty.setLastSize((int)dataEty.getLastFlight());//取到余量
					cEty.setContentStatus(dataEty.getContentStatus());//容量状态
				}
				
				if(todayStr.equalsIgnoreCase(sf.format(cal.getTime()))) {//是否是今天
					cEty.setToday(true);
				}
				
				//过期，按天算，今天之前的不可选择，今天及以后的可选择
				if(cal.getTime().before(nowTime)) {
					cEty.setCanSelect(false);//不可选择
					cEty.setLastSize(0);
				}
				
				if(cEty.getLastSize() <= 0) {
					cEty.setLastSize(0);
					cEty.setCanSelect(false);
					cEty.setContentStatus(CPMEty.contentStatus_full);
				}
				
				rowObj.put("weekDay_" + (cal.get(Calendar.DAY_OF_WEEK) - 1), cEty);
				
				if (weekday == Calendar.SATURDAY) {//新的一周
					allArray.add(rowObj);
					rowObj = new JSONObject();
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);
				weekday = cal.get(Calendar.DAY_OF_WEEK);
			}
			
			if(rowObj.size() > 0) {
				if(rowObj.size() < 7) {
					for(int i = rowObj.size(); i <= 7; i++) {
						rowObj.put("weekDay_" + i, new BookCalendarEty());
					}
				}
				
				allArray.add(rowObj);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return allArray;
	}
}
