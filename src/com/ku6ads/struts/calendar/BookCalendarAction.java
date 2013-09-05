package com.ku6ads.struts.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

public class BookCalendarAction extends ActionSupport {
	private static final long serialVersionUID = 5282194938650300639L;
	private Integer bookId;//
	private Integer bookType;//1：广告条预订，2: 广告产品预订
	private String saleType;//售卖方式CPM、CPD
	private Integer saleTypeValue;//售卖方式CPM、CPD
	private Boolean isContentDirect;
	
	@Resource(name="DataGeneraterAdvbar")
	private DataGeneraterAdvbar dataGeneraterAdvbar;
	
	@Resource(name="DataGeneraterAdvproduct")
	private DataGeneraterAdvproduct dataGeneraterAdvproduct;
	
	public void getCalendar() {
		try {
			String showDateStr = ServletActionContext.getRequest().getParameter("showDate");
			Date showDate = new SimpleDateFormat("yyyy-MM").parse(showDateStr);
			
			JSONArray dataArray = null;
			if(bookType.intValue() == 1) {
				dataArray = dataGeneraterAdvbar.getCalendarData(bookId, showDate, saleType, saleTypeValue, isContentDirect);
			}
			else if(bookType.intValue() == 2) {
				dataArray = dataGeneraterAdvproduct.getCalendarData(bookId, showDate, saleType, saleTypeValue, isContentDirect);
			} 
			
			JSONObject dataObj = new JSONObject();
			dataObj.put("total", dataArray.size());
			dataObj.put("invdata", dataArray);
			AjaxOut.responseText(ServletActionContext.getResponse(), dataObj.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Integer getSaleTypeValue() {
		return saleTypeValue;
	}

	public void setSaleTypeValue(Integer saleTypeValue) {
		this.saleTypeValue = saleTypeValue;
	}

	public Boolean getIsContentDirect() {
		return isContentDirect;
	}

	public void setIsContentDirect(Boolean isContentDirect) {
		this.isContentDirect = isContentDirect;
	}
}
