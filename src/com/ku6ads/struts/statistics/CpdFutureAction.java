package com.ku6ads.struts.statistics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.statistics.CpdFuture;
import com.ku6ads.services.iface.baisc.BaseDataService;
import com.ku6ads.services.iface.statistics.CpdFutureService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

public class CpdFutureAction extends ActionSupport{

	private Logger logger = Logger.getLogger(CpdFutureAction.class);
	private CpdFutureService cpdFutureService;
	private BaseDataService baseDataService;

	public void showFutureCpdByDate() {
		try {
			CpdFuture cpdFuture = (CpdFuture) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), CpdFuture.class);
			List cpdFutureList = cpdFutureService.selectByLimit(cpdFuture);
			Integer cpdFutureCount = cpdFutureService.selectLimitCount(cpdFuture);
			CpdFuture cFuture1 = null;
			
			NumberFormat nf = NumberFormat.getPercentInstance();
	        nf.setMaximumIntegerDigits(3); //  设置数的整数部分所允许的最大位数
	        nf.setMaximumFractionDigits(2);// 设置数的小数部分所允许的最大位数
	        
	        BaseData bData = new BaseData();
			for(int i = 0;i < cpdFutureList.size();i++)
			{
				cFuture1 =  (CpdFuture)cpdFutureList.get(i);
				
				String sFormat = cFuture1.getFormat();
				bData.setDataType("CPD");
				bData.setDataValue(sFormat);
				List<BaseData> list = baseDataService.selectByLimit(bData);
				if(list.size() == 0)
					cFuture1.setFormat("表现形式不存在");
				else{
					cFuture1.setFormat(list.get(0).getDataName());
				}
				
				cFuture1.setStartTime(cpdFuture.getStartTime());
				cpdFuture.setEndTime(cpdFuture.getEndTime());
				Integer date = cpdFutureService.selectDateDiff(cpdFuture.getEndTime(), cpdFuture.getStartTime())+1;
				Integer barC = cFuture1.getBarContent();
				//设置广告条容量
				cFuture1.setBarContent(barC*date);
				Integer barContent = cFuture1.getBarContent();
				//设置预订量
				Integer book = cpdFutureService.selectBookByDate(cFuture1);
				if(book==null) book = 0;
				cFuture1.setBook(book);
				//设置预订率
				if(barContent == 0)
				{
					cFuture1.setBookRate("0%");
				}else{
					cFuture1.setBookRate(nf.format((double)book/(double)barContent));
				}
				//设置确认量
				Integer confirm = cpdFutureService.selectConfirmByDate(cFuture1);
				if(confirm==null) confirm = 0;
				cFuture1.setConfirm(confirm);
				//设置确认率
				if(barContent == 0)
				{
					cFuture1.setConfirmRate("0%");
				}else{
					cFuture1.setConfirmRate(nf.format((double)confirm/(double)barContent));
				}
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), cpdFutureList, cpdFutureCount, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CpdFutureService getCpdFutureService() {
		return cpdFutureService;
	}

	public void setCpdFutureService(CpdFutureService cpdFutureService) {
		this.cpdFutureService = cpdFutureService;
	}

	public BaseDataService getBaseDataService() {
		return baseDataService;
	}

	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

}
