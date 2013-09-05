package com.ku6ads.struts.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 通用日历ACTION
 * @author yanghanguang
 *
 */
public class CalendarDataAction extends ActionSupport {
	
	private static final long serialVersionUID = 2177543758252369577L;

	public void getCalendar() {
		try {
			String showDate = ServletActionContext.getRequest().getParameter("showDate");
			JSONArray dataArray = getCalData(showDate);
			
			JSONObject dataObj = new JSONObject();
			dataObj.put("total", dataArray.size());
			dataObj.put("invdata", dataArray);
			AjaxOut.responseText(ServletActionContext.getResponse(), dataObj.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONArray getCalData(String showDate) {
		JSONArray allArray = new JSONArray();
		try {
			JSONObject rowObj = new JSONObject();
			
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			
			if(showDate != null) {
				cal.setTime(new SimpleDateFormat("yyyy-MM").parse(showDate));
			}
			
			int nowDay = cal.get(Calendar.DAY_OF_MONTH);
			int nowMonth = cal.get(Calendar.MONTH);
			
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int weekday = cal.get(Calendar.DAY_OF_WEEK);
			
//			System.out.println("日\t一\t二\t三\t四\t五\t六");
			for (int i = Calendar.SUNDAY; i < weekday; i++ ) {
//				System.out.print("\t");
				CalendarDataEty cEty = new CalendarDataEty();
				cEty.setText("");
				cEty.setDay(0);
				cEty.setWeekday(i);
				rowObj.put("weekDay_" + (i - 1) , cEty);
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sf.format(new Date());
			
			while(cal.get(Calendar.MONTH) == nowMonth) {
//				System.out.print(cal.get(Calendar.DAY_OF_MONTH) + "\t");
				
				CalendarDataEty cEty = new CalendarDataEty();
				cEty.setText(cal.get(Calendar.DAY_OF_MONTH)+ "");
				cEty.setDay(cal.get(Calendar.DAY_OF_MONTH));
				cEty.setWeekday(cal.get(Calendar.DAY_OF_WEEK));
				
				if(todayStr.equalsIgnoreCase(sf.format(cal.getTime()))) {//是否是今天
					cEty.setToday(true);
				}
				
				rowObj.put("weekDay_" + (cal.get(Calendar.DAY_OF_WEEK) - 1), cEty);
				
				if (weekday == Calendar.SATURDAY) {
//					System.out.println();
					allArray.add(rowObj);
					rowObj = new JSONObject();
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);
				weekday = cal.get(Calendar.DAY_OF_WEEK);
			}
			
			if(rowObj.size() > 0) {
				if(rowObj.size() < 7) {
					for(int i = rowObj.size(); i <= 7; i++) {
						rowObj.put("weekDay_" + i, new CalendarDataEty());
					}
				}
				
				allArray.add(rowObj);
			}
//			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return allArray;
	}
}
