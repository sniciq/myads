package com.ku6ads.struts.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;
import com.ku6ads.struts.advflight.CPMEty;

public class DataGeneraterAdvbar {

	@Resource(name="AdvbarPreBookService")
	private AdvbarPreBookService advbarPreBookService;
	
	
	@Resource(name="BookCalendarComm")
	private BookCalendarComm bookCalendarComm;
	
	public JSONArray getCalendarData(Integer bookId, Date showDate, String saleType, Integer saleTypeValue, Boolean isContentDirect) {
		try {
			//2. 查询一个月已订点位的可预订信息
			AdvbarPreBook searchEty = new AdvbarPreBook();
			searchEty.setAdvbarId(bookId);
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
				bookCalendarComm.initDateMap_CPD(showDate, dataMap, list, bookId, saleTypeValue);
			}
			else if(saleType.trim().equals("CPM") || saleType.trim().equals("2")) {
				bookCalendarComm.initDateMap_CPM(showDate, dataMap, list, bookId, saleTypeValue);
			}
			
			return bookCalendarComm.getCalData(showDate, dataMap);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
