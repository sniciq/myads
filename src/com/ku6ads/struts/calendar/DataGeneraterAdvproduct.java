package com.ku6ads.struts.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advproduct.AdvproductAdvbarEty;
import com.ku6ads.dao.entity.advproduct.AdvproductEty;
import com.ku6ads.dao.iface.advproduct.AdvproductAdvbarDao;
import com.ku6ads.dao.iface.advproduct.AdvproductDao;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;
import com.ku6ads.struts.advflight.CPMEty;

public class DataGeneraterAdvproduct {
	
	@Resource(name="AdvbarPreBookService")
	private AdvbarPreBookService advbarPreBookService;
	
	@Resource(name="AdvproductAdvbarDao")
	private AdvproductAdvbarDao advproductAdvbarDao;
	
	@Resource(name="AdvproductDao")
	private AdvproductDao advproductDao;
	
	@Resource(name="BookCalendarComm")
	private BookCalendarComm bookCalendarComm;
	
	public JSONArray getCalendarData(Integer bookId, Date showDate, String saleType, Integer saleTypeValue, Boolean isContentDirect) {
		try {
			AdvproductEty advproductEty = (AdvproductEty) advproductDao.selectById(bookId);
			
			AdvproductAdvbarEty advproductAdvbarEty = new AdvproductAdvbarEty();
			advproductAdvbarEty.setAdvproductId(bookId);
			List<AdvproductAdvbarEty> advbarList = advproductAdvbarDao.selectByEntity(advproductAdvbarEty);
			
			Map<String, CPMEty> retdataMap = new HashMap<String, CPMEty>();
			
			for(int i = 0; i < advbarList.size(); i++) {
				int advbarId = advbarList.get(i).getAdvbarId();
				AdvbarPreBook searchEty = new AdvbarPreBook();
				searchEty.setAdvbarId(advbarId);
				searchEty.setBookDate(showDate);
				
				int priority = (isContentDirect ? 5 : 3);
				String priorityStr = ServletActionContext.getRequest().getParameter("paramPriority");
				if(priorityStr != null && !priorityStr.trim().equals("")) {
					priority = Integer.parseInt(priorityStr);
				}
				
				searchEty.setPriority(priority);
				List<AdvbarPreBook> list = advbarPreBookService.searchMonthBookedList(searchEty);
				
				//1. 初始化日历
				Map<String, CPMEty> dataMap = new HashMap<String, CPMEty>();
				if(saleType.trim().equals("CPD") || saleType.trim().equals("1") ) {
					bookCalendarComm.initDateMap_CPD(showDate, dataMap, list, advbarId, saleTypeValue);
				}
				else if(saleType.trim().equals("CPM") || saleType.trim().equals("2")) {
					bookCalendarComm.initDateMap_CPM(showDate, dataMap, list, advbarId, saleTypeValue);
				}
				
				if(advproductEty.getType().equals("A")) {//可预订量为包内每个广告条可预订量累加
					addMapData(retdataMap, dataMap);
				}
				else if(advproductEty.getType().equals("B")) {//显示包内最小预估量的值为准
					minMapData(retdataMap, dataMap);
				} 
			}
			
			return bookCalendarComm.getCalData(showDate, retdataMap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 可预订量为包内每个广告条可预订量累加
	 * @param retdataMap
	 * @param dataMap
	 */
	private void addMapData(Map<String, CPMEty> retdataMap, Map<String, CPMEty> dataMap) {
		Set keySet = dataMap.keySet();
		Iterator<String> itor = keySet.iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			CPMEty ety = dataMap.get(key);
			CPMEty oldEty = retdataMap.get(key);
			if(oldEty == null) {
				retdataMap.put(key, ety);
			}
			else {
				CPMEty newEty = new CPMEty();
				newEty.setAlreadyFlight(oldEty.getAlreadyFlight() + ety.getAlreadyFlight());
				newEty.setDate(oldEty.getDate());
				newEty.setBarProportion(oldEty.getBarProportion());
				newEty.setSiteProp(oldEty.getSiteProp());
				
				if(oldEty.getLastContent() != -1) {
					newEty.setLastContent(oldEty.getLastContent() + ety.getLastContent());
				}
				
				newEty.setFutureFlight(oldEty.getFutureFlight() + ety.getFutureFlight());
				retdataMap.put(key, newEty);
			}
		}
	}
	
	/**
	 * 显示包内最小预估量的值为准
	 * @param retdataMap
	 * @param dataMap
	 */
	private void minMapData(Map<String, CPMEty> retdataMap, Map<String, CPMEty> dataMap) {
		Set keySet = dataMap.keySet();
		Iterator<String> itor = keySet.iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			CPMEty ety = dataMap.get(key);
			CPMEty oldEty = retdataMap.get(key);
			CPMEty newEty = new CPMEty();
			newEty.setDate(ety.getDate());
			newEty.setBarProportion(ety.getBarProportion());
			newEty.setSiteProp(ety.getSiteProp());
			
			if(oldEty == null) {
				Double n = ety.getLastFlight();
				newEty.setLastContent(n.intValue());
			}
			else {
				Double n = Math.min(oldEty.getLastFlight(), ety.getLastFlight());
				newEty.setLastContent(n.intValue());
			}
			retdataMap.put(key, newEty);
		}
	}


//	private void initDateMap_CPD(Date month, Map<String, CPMEty> dataMap, List<AdvbarPreBook> advbarPreBookList, int advbarId, int saleTypeValue) {
//		Advbar advbarEty = (Advbar) advbarDao.selectById(advbarId);
//		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance(); 
//		calendar.setTime(month);
//		int nowMonth = calendar.get(Calendar.MONTH);
//		while(calendar.get(Calendar.MONTH) == nowMonth) {
//			CPMEty cpmEty = new CPMEty();
//			cpmEty.setAdvbarId(advbarId);
//			cpmEty.setDate(calendar.getTime());
//			cpmEty.setLastContent(advbarEty.getContent());//余量为广告容量
//			cpmEty.setContentStatus(CPMEty.contentStatus_none);
//			dataMap.put(df.format(calendar.getTime()), cpmEty);
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
//		}
//		
//		AdvbarPreBook preBookEty = null;
//		for(int i = 0; i < advbarPreBookList.size(); i++) {
//			preBookEty = advbarPreBookList.get(i);
//			CPMEty cpmEty = dataMap.get(df.format(preBookEty.getBookDate()));
//			if(cpmEty == null) 
//				continue;
//			
//			if(preBookEty.getSaleType() == saleTypeValue) {//售卖方式相同
//				if(preBookEty.getCanBook()) {
//					cpmEty.setLastContent(advbarEty.getContent() - preBookEty.getBookedFlight());//余量 - 已订个数
//					if(cpmEty.getLastContent() < advbarEty.getContent())
//						cpmEty.setContentStatus(CPMEty.contentStatus_part);
//					else
//						cpmEty.setContentStatus(CPMEty.contentStatus_none);
//				}
//				else {
//					cpmEty.setLastContent(0);
//					cpmEty.setContentStatus(CPMEty.contentStatus_full);
//				}
//			}
//			else if(preBookEty.getBookedFlight().intValue() <= 0){//售卖方式不同但已订为0，此时可订
//				cpmEty.setLastContent(advbarEty.getContent());//余量为广告容量
//				cpmEty.setContentStatus(CPMEty.contentStatus_none);
//			}
//			else {//售卖方式相同且已订不为0,此时不可订
//				cpmEty.setLastContent(0);
//				cpmEty.setContentStatus(CPMEty.contentStatus_full);
//			}
//		}
//	}
//	
//	private void initDateMap_CPM(Date month, Map<String, CPMEty> dataMap, List<AdvbarPreBook> advbarPreBookList, int advbarId, int saleTypeValue) throws Exception {
//		double siteProp = 1;
//		Site siteEty = siteDao.selectByAdvbarId(advbarId);
//		if(siteEty != null)
//			siteProp = siteEty.getModulus();
//		
//		//1. 初始化日历
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance(); 
//		calendar.setTime(month);
//		int nowMonth = calendar.get(Calendar.MONTH);
//		while(calendar.get(Calendar.MONTH) == nowMonth) {
//			CPMEty cpmEty = new CPMEty();
//			cpmEty.setAdvbarId(advbarId);
//			cpmEty.setDate(calendar.getTime());
//			
//			cpmEty.setSiteProp(siteProp);
//			
//			dataMap.put(df.format(calendar.getTime()), cpmEty);
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
//		}
//		
//		//设置广告条系数
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("advbarId", advbarId);
//		paramMap.put("dayTime", month);
//		List<BarProportion> list = barProportionDao.selectByAdvbarIdAndTime(paramMap);
//		for(int i = 0; i < list.size(); i++) {
//			BarProportion barPropEty = (BarProportion) list.get(i);
//			Date startTime = barPropEty.getStartTime();
//			Date endtime =  barPropEty.getEndTime();
//			Calendar t_cal = Calendar.getInstance();
//			t_cal.setTime(startTime);
//			while(endtime.after(startTime) || endtime.equals(startTime)) {
//				CPMEty cpmEty = dataMap.get(df.format(startTime));
//				t_cal.add(Calendar.DAY_OF_MONTH, 1);
//				startTime = t_cal.getTime();
//				
//				if(cpmEty == null)
//					continue;
//				cpmEty.setBarProportion(barPropEty.getProportion());
//			}
//		}
//		
//		//2. 设置预估量
//		FutureFlight futureFlightEty = new FutureFlight();
//		futureFlightEty.setAdvbarId(advbarId);
//		futureFlightEty.setCreateTime(new Date());
//		List<FutureFlight> futureList = futureFlightDao.selectAdvbarFutureFlight(futureFlightEty);// 预估量
//		if(futureList.size() > 0) {
//			futureFlightEty = futureList.get(0);
//			Set<String> keySet = dataMap.keySet();
//			Iterator<String> iter = keySet.iterator();
//			while(iter.hasNext()) {
//				CPMEty cpmEty = dataMap.get(iter.next());
//				Date date1 = df.parse(df.format(futureFlightEty.getCreateTime()));
//				int c = (int) (date1.getTime() / 86400000 - cpmEty.getDate().getTime() / 86400000);
//				if (c < 0) c = (-1) * c;
//				if(c % 7 == 0) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum1() * 1000);
//				else if(c % 7 == 1) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum2() * 1000);
//				else if(c % 7 == 2) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum3() * 1000);
//				else if(c % 7 == 3) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum4() * 1000);
//				else if(c % 7 == 4) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum5() * 1000);
//				else if(c % 7 == 5) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum6() * 1000);
//				else if(c % 7 == 6) 
//					cpmEty.setFutureFlight(futureFlightEty.getNum7() * 1000);
//			}
//		}
//		
//		AdvbarPreBook preBookEty = null;
//		for(int i = 0; i < advbarPreBookList.size(); i++) {
//			preBookEty = advbarPreBookList.get(i);
//			CPMEty cpmEty = dataMap.get(df.format(preBookEty.getBookDate()));
//			if(cpmEty == null) 
//				continue;
//			
//			if(preBookEty.getSaleType() == saleTypeValue) {//售卖方式相同
//				if(preBookEty.getCanBook()) {
//					cpmEty.setAlreadyFlight(preBookEty.getBookedFlight());
//					cpmEty.setContentStatus(CPMEty.contentStatus_part);
//				}
//				else {
//					cpmEty.setLastContent(0);
//					cpmEty.setContentStatus(CPMEty.contentStatus_full);
//				}
//			}
//			else if(preBookEty.getBookedFlight().intValue() <= 0){//售卖方式不同但已订为0，此时可订
//				//默认即为全部
//			}
//			else {//售卖方式相同且已订不为0,此时不可订
//				cpmEty.setLastContent(0);
//				cpmEty.setContentStatus(CPMEty.contentStatus_full);
//			}
//		}
//	}
	
//	private JSONArray getCalData(Date showDate, Map<String, CPMEty> dateMap) {
//		JSONArray allArray = new JSONArray();
//		try {
//			JSONObject rowObj = new JSONObject();
//			
//			Calendar cal = Calendar.getInstance();
//			cal.setFirstDayOfWeek(Calendar.SUNDAY);
//			
//			if(showDate != null) {
//				cal.setTime(showDate);
//			}
//			
//			int nowDay = cal.get(Calendar.DAY_OF_MONTH);
//			int nowMonth = cal.get(Calendar.MONTH);
//			
//			cal.set(Calendar.DAY_OF_MONTH, 1);
//			int weekday = cal.get(Calendar.DAY_OF_WEEK);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			
//			for (int i = Calendar.SUNDAY; i < weekday; i++ ) {
//				BookCalendarEty cEty = new BookCalendarEty();
//				cEty.setText("");
//				cEty.setDay(0);
//				cEty.setWeekday(i);
//				rowObj.put("weekDay_" + (i - 1) , cEty);
//			}
//			
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//			String todayStr = sf.format(new Date());
//			Date nowTime = new Date();
//			String nowTimeStr = sf.format(nowTime);
//			nowTime = sf.parse(nowTimeStr);
//			
//			while(cal.get(Calendar.MONTH) == nowMonth) {
//				BookCalendarEty cEty = new BookCalendarEty();
//				cEty.setText(cal.get(Calendar.DAY_OF_MONTH)+ "");
//				cEty.setDay(cal.get(Calendar.DAY_OF_MONTH));
//				cEty.setDateStr(df.format(cal.getTime()));
//				cEty.setWeekday(cal.get(Calendar.DAY_OF_WEEK));
//				
//				CPMEty dataEty = dateMap.get(sf.format(cal.getTime()));
//				cEty.setLastSize((int)dataEty.getLastFlight());//取到余量
//				cEty.setContentStatus(dataEty.getContentStatus());//容量状态
//				
//				if(todayStr.equalsIgnoreCase(sf.format(cal.getTime()))) {//是否是今天
//					cEty.setToday(true);
//				}
//				
//				//过期，按天算，今天之前的不可选择，今天及以后的可选择
//				if(cal.getTime().before(nowTime)) {
//					cEty.setCanSelect(false);//不可选择
//					cEty.setLastSize(0);
//				}
//				
//				if(cEty.getLastSize() <= 0) {
//					cEty.setLastSize(0);
//					cEty.setCanSelect(false);
//					cEty.setContentStatus(CPMEty.contentStatus_full);
//				}
//				
//				rowObj.put("weekDay_" + (cal.get(Calendar.DAY_OF_WEEK) - 1), cEty);
//				
//				if (weekday == Calendar.SATURDAY) {//新的一周
//					allArray.add(rowObj);
//					rowObj = new JSONObject();
//				}
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//				weekday = cal.get(Calendar.DAY_OF_WEEK);
//			}
//			
//			if(rowObj.size() > 0) {
//				if(rowObj.size() < 7) {
//					for(int i = rowObj.size(); i <= 7; i++) {
//						rowObj.put("weekDay_" + i, new BookCalendarEty());
//					}
//				}
//				
//				allArray.add(rowObj);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return allArray;
//	}

}
