package com.ku6ads.struts.statistics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.statistics.CpmFuture;
import com.ku6ads.services.iface.baisc.BaseDataService;
import com.ku6ads.services.iface.statistics.CpmFutureService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

public class CpmFutureAction extends ActionSupport {

	private Logger logger = Logger.getLogger(CpmFutureAction.class);
	private CpmFutureService cpmFutureService;
	private BaseDataService baseDataService;

	public void showFuCpmByDate() {
		try {
			CpmFuture cpmFuture = (CpmFuture) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), CpmFuture.class);
			List cpmFutureList = cpmFutureService.selectByLimit(cpmFuture);
			Integer cpmFutureCount = cpmFutureService.selectLimitCount(cpmFuture);

			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumIntegerDigits(3); // 设置数的整数部分所允许的最大位数
			nf.setMaximumFractionDigits(2);// 设置数的小数部分所允许的最大位数

			Integer theDate = cpmFutureService.selectDateDiff(cpmFuture.getStartTime(), new Date());
			Integer forDate = cpmFutureService.selectDateDiff(cpmFuture.getEndTime(), cpmFuture.getStartTime());
			Integer thedif = theDate % 7;
			CpmFuture cFuture = null;
			BaseData bData = new BaseData();
			for (int i = 0; i < cpmFutureList.size(); i++) {
				cFuture = (CpmFuture) cpmFutureList.get(i);

				String sFormat = cFuture.getFormat();
				bData.setDataType("CPM");
				bData.setDataValue(sFormat);
				List<BaseData> list = baseDataService.selectByLimit(bData);
				if (list.size() == 0)
					cFuture.setFormat("表现形式不存在");
				else {
					cFuture.setFormat(list.get(0).getDataName());
				}

				cFuture.setStartTime(cpmFuture.getStartTime());
				cFuture.setEndTime(cpmFuture.getEndTime());
				Integer advbarId = cFuture.getAdvbarId();
				Integer sum = 0;
				for (int j = 0; j <= forDate; j++) {
					Integer flightNum = cpmFutureService.selectFlightNum(advbarId, (j + thedif) % 7);
					if (flightNum == null)
						flightNum = 0;
					sum += flightNum;
				}
				cFuture.setFutrueImpression(sum);
				Integer book = cpmFutureService.selectBookByDate(cFuture);
				if (book == null)
					book = 0;
				cFuture.setBook(book);
				if (sum == 0) {
					cFuture.setBookRate("0%");
				} else {
					cFuture.setBookRate(nf.format((double) book / (double) sum));
				}
				Integer confirm = cpmFutureService.selectConfirmByDate(cFuture);
				if (confirm == null)
					confirm = 0;
				cFuture.setConfirm(confirm);
				if (sum == 0) {
					cFuture.setConfirmRate("0%");
				} else {
					cFuture.setConfirmRate(nf.format((double) confirm / (double) sum));
				}
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), cpmFutureList, cpmFutureCount, new SimpleDateFormat("yyyy-MM-dd"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CpmFutureService getCpmFutureService() {
		return cpmFutureService;
	}

	public void setCpmFutureService(CpmFutureService cpmFutureService) {
		this.cpmFutureService = cpmFutureService;
	}

	public BaseDataService getBaseDataService() {
		return baseDataService;
	}

	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

}
