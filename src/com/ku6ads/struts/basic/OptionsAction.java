package com.ku6ads.struts.basic;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advert.AdvbarPriceDao;
import com.ku6ads.dao.iface.basic.BaseDataDao;
import com.opensymphony.xwork2.ActionSupport;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.TypeConverterUtil;

public class OptionsAction extends ActionSupport {
	private static final long serialVersionUID = -2946928611103334587L;
	private BaseDataDao baseDataDao;
	private AdvbarPriceDao advbarPriceDao;
	private AdvbarDao advbarDao;
	
	public void getBaseDataTypes() {
		List<BaseData> list = baseDataDao.selectBaseDataTypes();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < list.size(); i++) {
			BaseData ety = list.get(i);
			sb.append("['" + ety.getDataType() + "','" + ety.getDataType()  + "']");
			if(i < list.size() - 1) 
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	public void getBaseDataNamesByType() {
		String type = ServletActionContext.getRequest().getParameter("dataType");
		BaseData searchEty = new BaseData();
		searchEty.setDataType(type);
		searchEty.setStatus(0);
		List<BaseData> list = baseDataDao.selectByEntity(searchEty);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < list.size(); i++) {
			BaseData ety = list.get(i);
			sb.append("['" + ety.getDataValue() + "','" + ety.getDataName()  + "']");
			if(i < list.size() - 1) 
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	
	public void getAdvbarSaleTypes() {
		String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
		List<BaseData> list = advbarPriceDao.getAdvbarSaleTypes(Integer.parseInt(advbarId));
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < list.size(); i++) {
			BaseData ety = list.get(i);
			sb.append("['" + ety.getDataValue() + "','" + ety.getDataName()  + "']");
			if(i < list.size() - 1) 
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	public void getAdvbarSaleTypeFormat() {
		String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
		String saleTypeValue = ServletActionContext.getRequest().getParameter("saleTypeValue");
		String saleTypeName = ServletActionContext.getRequest().getParameter("saleTypeName");
		
		
		AdvbarPrice advbarPriceEty = new AdvbarPrice();
		advbarPriceEty.setBarId(Integer.parseInt(advbarId));
		advbarPriceEty.setSaleType(Integer.parseInt(saleTypeValue));
		advbarPriceEty.setPrice(saleTypeName);
		
		List<AdvbarPrice> list = advbarPriceDao.selectPriceFormatBaseData(advbarPriceEty);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < list.size(); i++) {
			advbarPriceEty = list.get(i);
			sb.append("['" + advbarPriceEty.getId() + "','" + advbarPriceEty.getFormat()  + "', '" + advbarPriceEty.getPos() + "']");
			if(i < list.size() - 1) 
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	public void getAllAdvBarInChannel() {
		String channelId = ServletActionContext.getRequest().getParameter("channelId");
		Advbar ety = new Advbar();
		ety.setChannelId(TypeConverterUtil.parseInt(channelId));
		ety.setStatus(0);
//		List<Advbar> advbarList = advbarDao.selectByEntity(ety);
		List<Advbar> advbarList = advbarDao.selectUsebleAdvbar(ety);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < advbarList.size(); i++) {
			Advbar advbar = advbarList.get(i);
			sb.append("['" + advbar.getId() + "','" + advbar.getName() + "']");
			if (i < advbarList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	public BaseDataDao getBaseDataDao() {
		return baseDataDao;
	}

	public void setBaseDataDao(BaseDataDao baseDataDao) {
		this.baseDataDao = baseDataDao;
	}

	public AdvbarPriceDao getAdvbarPriceDao() {
		return advbarPriceDao;
	}

	public void setAdvbarPriceDao(AdvbarPriceDao advbarPriceDao) {
		this.advbarPriceDao = advbarPriceDao;
	}

	public AdvbarDao getAdvbarDao() {
		return advbarDao;
	}

	public void setAdvbarDao(AdvbarDao advbarDao) {
		this.advbarDao = advbarDao;
	}
}
