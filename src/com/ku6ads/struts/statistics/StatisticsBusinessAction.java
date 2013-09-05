package com.ku6ads.struts.statistics;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.StatisticsBookpackageDay;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageMonth;
import com.ku6ads.dao.entity.statistics.StatisticsBookpackageViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsProductDay;
import com.ku6ads.dao.entity.statistics.StatisticsProductMonth;
import com.ku6ads.dao.entity.statistics.StatisticsProductViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsProjectDay;
import com.ku6ads.dao.entity.statistics.StatisticsProjectMonth;
import com.ku6ads.dao.entity.statistics.StatisticsProjectViewsMonth;
import com.ku6ads.dao.iface.statistics.StatisticsBookpackageDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsBookpackageMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsBookpackageViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsProductDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsProductMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsProductViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsProjectDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsProjectMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsProjectViewsMonthDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 业务统计
 * @author YangHanguang
 *
 */
public class StatisticsBusinessAction extends ActionSupport {
	
	private static final long serialVersionUID = -9046615336882304229L;
	private Logger logger = Logger.getLogger(StatisticsBusinessAction.class);
	
	@Resource(name="StatisticsProjectMonthDao")
	private StatisticsProjectMonthDao statisticsProjectMonthDao;
	
	@Resource(name="StatisticsBookpackageMonthDao")
	private StatisticsBookpackageMonthDao statisticsBookpackageMonthDao;
	
	@Resource(name="StatisticsProjectDayDao")
	private StatisticsProjectDayDao statisticsProjectDayDao;
	
	@Resource(name="StatisticsBookpackageDayDao")
	private StatisticsBookpackageDayDao statisticsBookpackageDayDao;
	
	@Resource(name="StatisticsProductDayDao")
	private StatisticsProductDayDao statisticsProductDayDao;
	
	@Resource(name="StatisticsBookpackageViewsMonthDao")
	private StatisticsBookpackageViewsMonthDao statisticsBookpackageViewsMonthDao;
	
	@Resource(name="StatisticsProjectViewsMonthDao")
	private StatisticsProjectViewsMonthDao statisticsProjectViewsMonthDao;
	
	@Resource(name="StatisticsProductMonthDao")
	private StatisticsProductMonthDao statisticsProductMonthDao;
	
	@Resource(name="StatisticsProductViewsMonthDao")
	private StatisticsProductViewsMonthDao statisticsProductViewsMonthDao;
	
	/**
	 * 执行单位月报
	 */
	public void getProjectMonthData() {
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			StatisticsProjectMonth statEty = new StatisticsProjectMonth();
			statEty.setProjectId(Integer.parseInt(projectId));
			
			if(statTime != null && !statTime.trim().equals(""))
				statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				statEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			List<StatisticsProjectMonth> dataList = statisticsProjectMonthDao.statisticsSearch(statEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("频道月报错误！！！", e);
		}
	}
	
	/**
	 * 执行单位日报
	 */
	public void getProjectDayData() {
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsProjectDay statEty = new StatisticsProjectDay();
			statEty.setProjectId(Integer.parseInt(projectId));
			statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsProjectDay> dataList = statisticsProjectDayDao.statisticsSearch(statEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("频道月报错误！！！", e);
		}
	}
	
	/**
	 * 执行单月报导出
	 */
	public void exportProjectMonthData() {
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			StatisticsProjectMonth statEty = new StatisticsProjectMonth();
			statEty.setProjectId(Integer.parseInt(projectId));
			if(statTime != null && !statTime.trim().equals(""))
				statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				statEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			String[] exp_column_names = {"名称", "日期", "月份", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"projectName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsProjectMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", statEty, "执行单月报", "data.xls", exp_column_names, exp_column_indexs);
			
		}
		catch (Exception e) {
			logger.error("执行单位月报导出错误！！！", e);
		}
	}
	
	/**
	 * 执行单日报导出
	 */
	public void exportProjectDayData() {
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsProjectDay statEty = new StatisticsProjectDay();
			statEty.setProjectId(Integer.parseInt(projectId));
			statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"projectName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsProjectDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", statEty, "执行单日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("执行单位日报导出错误！！！", e);
		}
	}
	
	/**
	 * 排期包月报
	 */
	public void getBookpackageMonthData() {
		try {
			String bookpackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsBookpackageMonth searchETy = new StatisticsBookpackageMonth();
			searchETy.setBookpackageId(Integer.parseInt(bookpackageId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchETy.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			List<StatisticsBookpackageMonth> dataList = statisticsBookpackageMonthDao.statisticsSearch(searchETy);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("排期包月报错误！！！", e);
		}
	}
	
	public void getProductBPMonthData() {
		try {
			String productId = ServletActionContext.getRequest().getParameter("productId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsProductMonth searchETy = new StatisticsProductMonth();
			searchETy.setProductId(Integer.parseInt(productId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchETy.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			List<StatisticsProductMonth> dataList = statisticsProductMonthDao.statisticsSearch(searchETy);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("排期包月报错误！！！", e);
		}
	}
	
	public void exportProductBPMonthData() {
		try {
			String productId = ServletActionContext.getRequest().getParameter("productId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsProductMonth searchETy = new StatisticsProductMonth();
			searchETy.setProductId(Integer.parseInt(productId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchETy.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			String[] exp_column_names = {"产品ID","产品名称", "日期", "月份", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"productId", "productName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsProductMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchETy, "广告产品月报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("排期包月报导出错误！！！", e);
		}
	}
	
	public void getProductBPMonthViewData() {
		try {
			StatisticsProductViewsMonth statisticsProductViewsMonth = new StatisticsProductViewsMonth();
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStart = ServletActionContext.getRequest().getParameter("stateMonthTimeStart");
			String stateMonthTimeEnd = ServletActionContext.getRequest().getParameter("stateMonthTimeEnd");
			String productId = ServletActionContext.getRequest().getParameter("productId");
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) 
				statisticsProductViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			if(stateMonthTimeStart != null && !stateMonthTimeStart.trim().equals(""))
				statisticsProductViewsMonth.setStateMonthTimeStart(df.parse(stateMonthTimeStart));
			if(stateMonthTimeEnd != null && !stateMonthTimeEnd.trim().equals(""))
				statisticsProductViewsMonth.setStateMonthTimeEnd(df.parse(stateMonthTimeEnd));

			statisticsProductViewsMonth.setProductId(Integer.parseInt(productId));
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsProductViewsMonth.setExtLimit(limit);
			
			int count = statisticsProductViewsMonthDao.selectLimitCount(statisticsProductViewsMonth);
			List list = statisticsProductViewsMonthDao.selectByLimit(statisticsProductViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("！！！", e);
		}
	}
	
	/**
	 * 排期包日报
	 */
	public void getBookPackageDayData() {
		try {
			String bookpackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsBookpackageDay searchETy = new StatisticsBookpackageDay();
			searchETy.setBookpackageId(Integer.parseInt(bookpackageId));
			searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsBookpackageDay> dataList = statisticsBookpackageDayDao.statisticsSearch(searchETy);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("排期包月报错误！！！", e);
		}
	}
	
	public void getProductBPDayData() {
		try {
			String productId = ServletActionContext.getRequest().getParameter("productId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsProductDay searchETy = new StatisticsProductDay();
			searchETy.setProductId(Integer.parseInt(productId));
			searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsProductDay> dataList = statisticsProductDayDao.statisticsSearch(searchETy);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("排期包月报错误！！！", e);
		}
	}
	
	public void exportProductBPDayData() {
		try {
			String productId = ServletActionContext.getRequest().getParameter("productId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsProductDay searchETy = new StatisticsProductDay();
			searchETy.setProductId(Integer.parseInt(productId));
			searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"产品ID", "产品名称", "日期", "月份", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"productId", "productName", "statTime", "month", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsProductDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchETy, "广告产品日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("排期包月报错误！！！", e);
		}
	}
	
	/**
	 * 排期包月报导出
	 */
	public void exportBookpackageMonthData() {
		try {
			String bookpackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			String statTime = ServletActionContext.getRequest().getParameter("stateTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			StatisticsBookpackageMonth searchETy = new StatisticsBookpackageMonth();
			searchETy.setBookpackageId(Integer.parseInt(bookpackageId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchETy.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchETy.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			String[] exp_column_names = {"排期包ID", "日期", "月份", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"bookpackageId", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsBookpackageMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchETy, "排期包月报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("排期包月报导出错误！！！", e);
		}
	}
	
	/**
	 * 排期包日报导出
	 */
	public void exportBookpackageDayData() {
		try {
			String bookpackageId = ServletActionContext.getRequest().getParameter("bookpackageId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsBookpackageDay statEty = new StatisticsBookpackageDay();
			statEty.setBookpackageId(Integer.parseInt(bookpackageId));
			statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"执行单ID", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"bookpackageId", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsBookpackageDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", statEty, "执行单日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("期包日报导出错误！！！", e);
		}
	}
	
	public void getViewDataBookpackage() {
		try {
			StatisticsBookpackageViewsMonth statisticsBookpackageViewsMonth = new StatisticsBookpackageViewsMonth();
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStart = ServletActionContext.getRequest().getParameter("stateMonthTimeStart");
			String stateMonthTimeEnd = ServletActionContext.getRequest().getParameter("stateMonthTimeEnd");
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) 
				statisticsBookpackageViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			if(stateMonthTimeStart != null && !stateMonthTimeStart.trim().equals(""))
				statisticsBookpackageViewsMonth.setStateMonthTimeStart(df.parse(stateMonthTimeStart));
			if(stateMonthTimeEnd != null && !stateMonthTimeEnd.trim().equals(""))
				statisticsBookpackageViewsMonth.setStateMonthTimeEnd(df.parse(stateMonthTimeEnd));

			statisticsBookpackageViewsMonth.setBookpackageId(Integer.parseInt(bookPackageId));
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsBookpackageViewsMonth.setExtLimit(limit);
			int count = statisticsBookpackageViewsMonthDao.selectLimitCount(statisticsBookpackageViewsMonth);
			List list = statisticsBookpackageViewsMonthDao.selectByLimit(statisticsBookpackageViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getViewDataProject() {
		try {
			StatisticsProjectViewsMonth statisticsProjectViewsMonth = new StatisticsProjectViewsMonth();
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStart = ServletActionContext.getRequest().getParameter("stateMonthTimeStart");
			String stateMonthTimeEnd = ServletActionContext.getRequest().getParameter("stateMonthTimeEnd");
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			
			statisticsProjectViewsMonth.setProjectId(Integer.parseInt(projectId));
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals(""))
				statisticsProjectViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			if(stateMonthTimeStart != null && !stateMonthTimeStart.trim().equals(""))
				statisticsProjectViewsMonth.setStateMonthTimeStart(df.parse(stateMonthTimeStart));
			if(stateMonthTimeEnd != null && !stateMonthTimeEnd.trim().equals(""))
				statisticsProjectViewsMonth.setStateMonthTimeEnd(df.parse(stateMonthTimeEnd));
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsProjectViewsMonth.setExtLimit(limit);
			int count = statisticsProjectViewsMonthDao.selectLimitCount(statisticsProjectViewsMonth);
			List list = statisticsProjectViewsMonthDao.selectByLimit(statisticsProjectViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
