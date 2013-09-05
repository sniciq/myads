package com.ku6ads.struts.statistics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.CpdhisAccount;
import com.ku6ads.services.iface.statistics.CpdhisAccountService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 历史CPD统计
 * @author chenshaofeng
 * 
 */
public class CpdhisAccountAction extends ActionSupport {

	private static final long serialVersionUID = -9046615336882304229L;
	private Logger logger = Logger.getLogger(CpdhisAccountAction.class);
	private CpdhisAccountService cpdhisAccountService;

	public void showHisCpdByDate() {
		try {
			CpdhisAccount cpdhisAccount = (CpdhisAccount) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), CpdhisAccount.class);
			List cpdhisAccountList = cpdhisAccountService.selectByLimit(cpdhisAccount);
			CpdhisAccount CpAccount = null;
			
			NumberFormat nf = NumberFormat.getPercentInstance();
	        nf.setMaximumIntegerDigits(3); //  设置数的整数部分所允许的最大位数
	        nf.setMaximumFractionDigits(2);// 设置数的小数部分所允许的最大位数
	        
			for(int i = 0;i < cpdhisAccountList.size();i++)
			{
				CpAccount =  (CpdhisAccount)cpdhisAccountList.get(i);
				CpAccount.setStartTime(cpdhisAccount.getStartTime());
				CpAccount.setEndTime(cpdhisAccount.getEndTime());
				//设置广告条容量
				Integer barContent = CpAccount.getBarContent();
				Integer dateCount = cpdhisAccountService.selectDateDiff(cpdhisAccount.getEndTime(), cpdhisAccount.getStartTime())+1;
				CpAccount.setBarContent(barContent*dateCount);
				//设置执行预订量
				Integer execute = cpdhisAccountService.getFlightNumByAdv(CpAccount);
				if(execute == null) execute = 0;
				CpAccount.setExecute(execute);
				//设置预定量
				Integer sumFlightNum = cpdhisAccountService.getFlightNumByBook(CpAccount);
				if(sumFlightNum == null) sumFlightNum = 0;
				CpAccount.setAdvexecute(sumFlightNum);
				//设置未执行预定量
				Integer unexecute = sumFlightNum-execute;
				CpAccount.setAdvUnexecute(unexecute);
				//广告预订率
				if(barContent==0){
					CpAccount.setAdvexecuteRate("0%");
				}else{
					CpAccount.setAdvexecuteRate(nf.format((double)sumFlightNum/(double)CpAccount.getBarContent()));
				}
				//设置执行、未执行预订率
				if(sumFlightNum==0){
					CpAccount.setExecuteRate("0%");
					CpAccount.setAdvUnexecuteRate("0%");
				}else{
					CpAccount.setExecuteRate(nf.format((double)execute/(double)sumFlightNum));
					CpAccount.setAdvUnexecuteRate(nf.format((double)unexecute/(double)sumFlightNum));
				}
			}
			Integer cpdhisCount = cpdhisAccountService.selectLimitCount(cpdhisAccount);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), cpdhisAccountList, cpdhisCount, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CpdhisAccountService getCpdhisAccountService() {
		return cpdhisAccountService;
	}

	public void setCpdhisAccountService(CpdhisAccountService cpdhisAccountService) {
		this.cpdhisAccountService = cpdhisAccountService;
	}

}
