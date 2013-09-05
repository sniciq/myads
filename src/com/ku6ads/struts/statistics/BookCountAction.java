package com.ku6ads.struts.statistics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.BookCount;
import com.ku6ads.services.iface.statistics.BookCountService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

public class BookCountAction extends ActionSupport {

	private Logger logger = Logger.getLogger(CpdFutureAction.class);
	private BookCountService bookCountService;

	public void showBookCountByDate() {
		try {
			BookCount bookCount = (BookCount) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BookCount.class);
			List bookCountList = bookCountService.selectByLimit(bookCount);
			int bookCCount = bookCountService.selectLimitCount(bookCount);
			BookCount cBookCount = null;
			
			NumberFormat nf = NumberFormat.getPercentInstance();
	        nf.setMaximumIntegerDigits(3); //  设置数的整数部分所允许的最大位数
	        nf.setMaximumFractionDigits(2);// 设置数的小数部分所允许的最大位数
	        
			for (int i = 0; i < bookCountList.size(); i++) {
				cBookCount = (BookCount) bookCountList.get(i);
				cBookCount.setStartTime(bookCount.getStartTime());
				cBookCount.setEndTime(bookCount.getEndTime());
				//设置日均曝光量
				Long impression = bookCountService.selectAvgPv(cBookCount);
				if(impression==null){
					cBookCount.setImpression(0L);
				}else{
					cBookCount.setImpression(impression);
				}
				//总曝光量
				Long sumImpression = bookCountService.selectSumPv(cBookCount);
				//设置使用量
				cBookCount.setUseType(null);
				Long use = bookCountService.selectFlightNum(cBookCount);
				//设置购买量、购买率
				cBookCount.setUseType(1);
				Long buy = bookCountService.selectFlightNum(cBookCount);
				if (buy == null)
					buy = 0L;
				cBookCount.setBuy(buy);
				if (use == null)
					cBookCount.setBuyRate("0%");
				else
					cBookCount.setBuyRate(nf.format((double) buy / (double) use));
				//设置配送总量、配送总量率
				cBookCount.setUseType(2);
				Long free = bookCountService.selectFlightNum(cBookCount);
				if (free == null)
					free = 0L;
				cBookCount.setFree(free);
				if (use == null)
					cBookCount.setFreeRate("0%");
				else
					cBookCount.setFreeRate(nf.format((double) free / (double) use));
				//设置补偿总量、补偿率
				cBookCount.setUseType(3);
				Long compensate = bookCountService.selectFlightNum(cBookCount);
				if (compensate == null)
					compensate = 0L;
				cBookCount.setCompensate(compensate);
				if (use == null)
					cBookCount.setCompensateRate("0%");
				else 
					cBookCount.setCompensateRate(nf.format((double) compensate / (double) use));
				//设置测试量、测试率
				cBookCount.setUseType(4);
				Long test = bookCountService.selectFlightNum(cBookCount);
				if (test == null)
					test = 0L;
				cBookCount.setTest(test);
				if (use == null)
					cBookCount.setTestRate("0%");
				else
					cBookCount.setTestRate(nf.format((double) test / (double) use));
				//BD推广量、BD推广率
				cBookCount.setUseType(5);
				Long bd = bookCountService.selectFlightNum(cBookCount);
				if (bd == null)
					bd = 0L;
				cBookCount.setBd(bd);
				if (use == null)
					cBookCount.setBdRate("0%");
				else
					cBookCount.setBdRate(nf.format((double) bd / (double) use));
				//设置软性总量、软性率
				cBookCount.setUseType(6);
				Long pr = bookCountService.selectFlightNum(cBookCount);
				if (pr == null)
					pr = 0L;
				cBookCount.setPr(pr);
				if (use == null)
					cBookCount.setPrRate("0%");
				else
					cBookCount.setPrRate(nf.format((double) pr / (double) use));
				//设置电商量、电商率
				cBookCount.setUseType(7);
				Long ecommerce = bookCountService.selectFlightNum(cBookCount);
				if (ecommerce == null)
					ecommerce = 0L;
				cBookCount.setEcommerce(ecommerce);
				if (use == null)
					cBookCount.setEcommerceRate("0%");
				else
					cBookCount.setEcommerceRate(nf.format((double) ecommerce / (double) use));
				//设置置换量、置换率
				cBookCount.setUseType(8);
				Long replace = bookCountService.selectFlightNum(cBookCount);
				if (replace == null)
					replace = 0L;
				cBookCount.setReplace(replace);
				if (use == null)
					cBookCount.setReplaceRate("0%");
				else
					cBookCount.setReplaceRate(nf.format((double) replace / (double) use));
				//设置游戏量，游戏率
				cBookCount.setUseType(9);
				Long game = bookCountService.selectFlightNum(cBookCount);
				if (game == null)
					game = 0L;
				cBookCount.setGame(game);
				if (use == null)
					cBookCount.setGameRate("0%");
				else
					cBookCount.setGameRate(nf.format((double) game / (double) use));
				//设置内部使用量、内部使用率
				cBookCount.setUseType(10);
				Long inuse = bookCountService.selectFlightNum(cBookCount);
				if (inuse == null)
					inuse = 0L;
				cBookCount.setInuse(inuse);
				if (use == null)
					cBookCount.setInuseRate("0%");
				else
					cBookCount.setInuseRate(nf.format((double) inuse / (double) use));
				//设置剩余量、剩余率
				if(use == null)
					use = 0L;
				Long leaving = use - buy - free - compensate - test - bd - ecommerce - pr - replace - game - inuse;
				cBookCount.setLeaving(leaving);
				if (use == 0L)
					cBookCount.setLeavingRate("0%");
				else
					cBookCount.setLeavingRate(nf.format((double) leaving / (double) use));
				//点击量、点击率
				Long Click = bookCountService.selectClick(cBookCount);
				if (sumImpression == null) {
					cBookCount.setClickRate("0%");
				} else
					cBookCount.setClickRate(nf.format((double) Click / (double) sumImpression));
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), bookCountList, bookCCount, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BookCountService getBookCountService() {
		return bookCountService;
	}

	public void setBookCountService(BookCountService bookCountService) {
		this.bookCountService = bookCountService;
	}

}
