package com.ku6ads.struts.statistics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.statistics.CpmhisAccount;
import com.ku6ads.services.iface.baisc.BaseDataService;
import com.ku6ads.services.iface.statistics.CpmhisAccountService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 历史CPM统计
 * @author chenshaofeng
 * 
 */
public class CpmhisAccountAction extends ActionSupport {

	private static final long serialVersionUID = -9046615336882304229L;
	private Logger logger = Logger.getLogger(CpmhisAccountAction.class);
	private CpmhisAccountService cpmhisAccountService;
	private BaseDataService baseDataService;

	public void showHisCpmByDate() {
		try {
			CpmhisAccount cpmhisAccount = (CpmhisAccount) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), CpmhisAccount.class);
			List cpmhisAccountList = cpmhisAccountService.selectByLimit(cpmhisAccount);
			CpmhisAccount CpAccount = null;
			
			NumberFormat nf = NumberFormat.getPercentInstance();
	        nf.setMaximumIntegerDigits(3); //  设置数的整数部分所允许的最大位数
	        nf.setMaximumFractionDigits(2);// 设置数的小数部分所允许的最大位数
	        
	        BaseData bData = new BaseData();
			for(int i = 0;i < cpmhisAccountList.size();i++)
			{
				CpAccount =  (CpmhisAccount)cpmhisAccountList.get(i);
				
				String sFormat = CpAccount.getFormat();
				bData.setDataType("CPM");
				bData.setDataValue(sFormat);
				List<BaseData> list = baseDataService.selectByLimit(bData);
				if(list.size() == 0)
					CpAccount.setFormat("表现形式不存在");
				else{
					CpAccount.setFormat(list.get(0).getDataName());
				}
				
				CpAccount.setStartTime(cpmhisAccount.getStartTime());
				CpAccount.setEndTime(cpmhisAccount.getEndTime());
				//设置广告条曝光量
				Long barImpression = cpmhisAccountService.getAdvBarPV(CpAccount);
				if(barImpression==null) barImpression = 0L;
				CpAccount.setBarImpression(barImpression);
				//设置广告曝光量
				Long advPV = cpmhisAccountService.getAdvPV(CpAccount);
				if(advPV==null) advPV = 0L;
				CpAccount.setAdvImpression(advPV);
				//设置广告曝光率
				if(barImpression==0){
					CpAccount.setImpressionRate("0%");
				}else{
					String impressionRate = nf.format((double)advPV/(double)barImpression);
					CpAccount.setImpressionRate(impressionRate);
				}
				//设置执行预订量
				Integer execute = cpmhisAccountService.getFlightNumByAdv(CpAccount);
				if(execute == null) execute = 0;
				CpAccount.setExecute(execute);
				//得到总预订量
				Integer sumFlightNum = cpmhisAccountService.getFlightNumByBook(CpAccount);
				if(sumFlightNum == null) sumFlightNum = 0;
				//设置未执行预定量
				Integer unexecute = sumFlightNum-execute;
				CpAccount.setUnexecute(unexecute);
				if(sumFlightNum==0){
					CpAccount.setExecuteRate("0%");
				}else{
					CpAccount.setExecuteRate(nf.format((double)execute/(double)sumFlightNum));
					CpAccount.setUnexecuteRate(nf.format((double)unexecute/(double)sumFlightNum));
				}
			}
			Integer cpmhisCount = cpmhisAccountService.selectLimitCount(cpmhisAccount);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), cpmhisAccountList, cpmhisCount, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public CpmhisAccountService getCpmhisAccountService() {
		return cpmhisAccountService;
	}

	public void setCpmhisAccountService(CpmhisAccountService cpmhisAccountService) {
		this.cpmhisAccountService = cpmhisAccountService;
	}


	public BaseDataService getBaseDataService() {
		return baseDataService;
	}


	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

}
