package com.ku6ads.struts.statistics;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.StatisticsAdvbarDay;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvbarViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionDay;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvpositionViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsChannelDay;
import com.ku6ads.dao.entity.statistics.StatisticsChannelMonth;
import com.ku6ads.dao.entity.statistics.StatisticsChannelViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsSiteViews;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvbarViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvpositionDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvpositionMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvpositionViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsChannelDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsChannelMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsChannelViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsSiteViewsDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 媒介统计
 * @author yangHanguang 
 *
 */
public class StatisticsMediaAction extends ActionSupport {
	
	private static final long serialVersionUID = 1928337160819739872L;
	private Logger logger = Logger.getLogger(StatisticsMediaAction.class);
	
	@Resource(name="StatisticsChannelMonthDao")
	private StatisticsChannelMonthDao statisticsChannelMonthDao;
	
	@Resource(name="StatisticsChannelDayDao")
	private StatisticsChannelDayDao statisticsChannelDayDao;
	
	@Resource(name="StatisticsAdvpositionMonthDao")
	private StatisticsAdvpositionMonthDao statisticsAdvpositionMonthDao;
	
	@Resource(name="StatisticsAdvpositionDayDao")
	private StatisticsAdvpositionDayDao statisticsAdvpositionDayDao;
	
	@Resource(name="StatisticsAdvbarMonthDao")
	private StatisticsAdvbarMonthDao statisticsAdvbarMonthDao;
	
	@Resource(name="StatisticsAdvbarDayDao")
	private StatisticsAdvbarDayDao statisticsAdvbarDayDao;
	
	@Resource(name="StatisticsSiteViewsDao")
	private StatisticsSiteViewsDao statisticsSiteViewsDao;
	
	@Resource(name="StatisticsChannelViewsMonthDao")
	private StatisticsChannelViewsMonthDao statisticsChannelViewsMonthDao;
	
	@Resource(name="StatisticsAdvpositionViewsMonthDao")
	private StatisticsAdvpositionViewsMonthDao statisticsAdvpositionViewsMonthDao;
	
	@Resource(name="StatisticsAdvbarViewsMonthDao")
	private StatisticsAdvbarViewsMonthDao statisticsAdvbarViewsMonthDao;

	/**
	 * 频道月报导出
	 */
	public void exportChanelMonthData() {
		try {
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			StatisticsChannelMonth searchEty = new StatisticsChannelMonth();
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			searchEty.setChannelId(Integer.parseInt(channelId));
			
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"channelName", "statTime", "hour", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsChannelMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "频道日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("频道月报错误！！！", e);
		}
	}
	
	/**
	 * 频道月报
	 */
	public void getChanelMonthData() {
		try {
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			StatisticsChannelMonth searchEty = new StatisticsChannelMonth();
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			searchEty.setChannelId(Integer.parseInt(channelId));
			List<StatisticsChannelMonth> dataList = statisticsChannelMonthDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("频道月报错误！！！", e);
		}
	}
	
	/**
	 * 频道日报
	 */
	public void getChanelDailyData() {
		try {
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsChannelDay searchEty = new StatisticsChannelDay();
			searchEty.setChannelId(Integer.parseInt(channelId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsChannelDay> dataList = statisticsChannelDayDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("频道日报错误！！！", e);
		}
	}
	
	/**
	 * 频道日报导出
	 */
	public void exportChanelDailyData() {
		try {
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsChannelDay searchEty = new StatisticsChannelDay();
			searchEty.setChannelId(Integer.parseInt(channelId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"channelName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsChannelDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "频道日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("频道日报导出错误！！！", e);
		}
	}
	
	/**
	 * 广告位月报
	 */
	public void getAdvpositionMonthData() {
		try {
			String advPositionId = ServletActionContext.getRequest().getParameter("advPositionId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsAdvpositionMonth searchEty = new StatisticsAdvpositionMonth();
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			searchEty.setAdvPositionId(Integer.parseInt(advPositionId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM").parse(statTime));
			List<StatisticsAdvpositionMonth> dataList = statisticsAdvpositionMonthDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告位月报错误！！！", e);
		}
	}
	
	/**
	 * 广告条月报
	 */
	public void getAdvbarMonthData() {
		try {
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsAdvbarMonth searchEty = new StatisticsAdvbarMonth();
			searchEty.setAdvbarId(Integer.parseInt(advbarId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			List<StatisticsAdvbarMonth> dataList = statisticsAdvbarMonthDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告条月报错误！！！", e);
		}
	}
	
	/**
	 * 广告位月报导出
	 */
	public void exportAdvpositionMonthData() {
		try {
			String advPositionId = ServletActionContext.getRequest().getParameter("advPositionId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsAdvpositionMonth searchEty = new StatisticsAdvpositionMonth();
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			searchEty.setAdvPositionId(Integer.parseInt(advPositionId));
			
			String[] exp_column_names = {"名称", "日期", "month", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"advPositionName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvpositionMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告位月报", "data.xls", exp_column_names, exp_column_indexs);
			
		}
		catch (Exception e) {
			logger.error("广告位月报导出错误！！！", e);
		}
	}
	
	/**
	 * 广告条月报导出
	 */
	public void exportAdvbarMonthData() {
		try {
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeMonthEnd = ServletActionContext.getRequest().getParameter("stateTimeMonthEnd");
			
			StatisticsAdvbarMonth searchEty = new StatisticsAdvbarMonth();
			searchEty.setAdvbarId(Integer.parseInt(advbarId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			if(stateTimeMonthEnd != null && !stateTimeMonthEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeMonthEnd));
			
			String[] exp_column_names = {"名称", "日期", "month", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"advbarName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvbarMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告条日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告条月报导出错误！！！", e);
		}
	}
	
	/**
	 * 广告位日报
	 */
	public void getAdvpositionDailyData() {
		try {
			String advPositionId = ServletActionContext.getRequest().getParameter("advPositionId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvpositionDay searchEty = new StatisticsAdvpositionDay();
			searchEty.setAdvPositionId(Integer.parseInt(advPositionId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsAdvpositionDay> dataList = statisticsAdvpositionDayDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告位日报错误！！！", e);
		}
	}
	
	/**
	 * 广告位日报导出
	 */
	public void exportAdvpositionDailyData() {
		try {
			String advPositionId = ServletActionContext.getRequest().getParameter("advPositionId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvpositionDay searchEty = new StatisticsAdvpositionDay();
			searchEty.setAdvPositionId(Integer.parseInt(advPositionId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"advPositionName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvpositionDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告位日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告位日报错误！！！", e);
		}
	}
	
	/**
	 * 广告条日报
	 */
	public void getAdvbarDailyData() {
		try {
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvbarDay searchEty = new StatisticsAdvbarDay();
			searchEty.setAdvbarId(Integer.parseInt(advbarId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsAdvbarDay> dataList = statisticsAdvbarDayDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告条日报错误！！！", e);
		}
	}
	
	/**
	 * 广告条日报导出
	 */
	public void exportAdvbarDailyData() {
		try {
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvbarDay searchEty = new StatisticsAdvbarDay();
			searchEty.setAdvbarId(Integer.parseInt(advbarId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"advbarName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvbarDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告条日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告条日报导出错误！！！", e);
		}
	}
	
	/**
	 * 得到广告条的类电视数据
	 */
	public void getViewDataAdvbar() {
		try {
			StatisticsAdvbarViewsMonth statisticsAdvbarViewsMonth = new StatisticsAdvbarViewsMonth();
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStr = ServletActionContext.getRequest().getParameter("stateMonthTime");
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) {
				statisticsAdvbarViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			}

			if(stateMonthTimeStr != null && !stateMonthTimeStr.trim().equals("")) {
				statisticsAdvbarViewsMonth.setStateMonthTime(new SimpleDateFormat("yyyy-MM").parse(stateMonthTimeStr));
			}

			statisticsAdvbarViewsMonth.setAdvbarId(Integer.parseInt(advbarId));
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsAdvbarViewsMonth.setExtLimit(limit);
			int count = statisticsAdvbarViewsMonthDao.selectLimitCount(statisticsAdvbarViewsMonth);
			List list = statisticsAdvbarViewsMonthDao.selectByLimit(statisticsAdvbarViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("得到广告条的类电视数据错误！！！", e);
		}
	}
	
	
	/**
	 * 得到广告位的类电视数据
	 */
	public void getViewDataAdvposition() {
		try {
			StatisticsAdvpositionViewsMonth statisticsAdvpositionViewsMonth = new StatisticsAdvpositionViewsMonth();
			
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStr = ServletActionContext.getRequest().getParameter("stateMonthTime");
			String advPositionId = ServletActionContext.getRequest().getParameter("advPositionId");
			
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) {
				statisticsAdvpositionViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			}

			if(stateMonthTimeStr != null && !stateMonthTimeStr.trim().equals("")) {
				statisticsAdvpositionViewsMonth.setStateMonthTime(new SimpleDateFormat("yyyy-MM").parse(stateMonthTimeStr));
			}

			statisticsAdvpositionViewsMonth.setAdvpositionId(Integer.parseInt(advPositionId));
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsAdvpositionViewsMonth.setExtLimit(limit);
			
			int count = statisticsAdvpositionViewsMonthDao.selectLimitCount(statisticsAdvpositionViewsMonth);
			List list = statisticsAdvpositionViewsMonthDao.selectByLimit(statisticsAdvpositionViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("得到广告位的类电视数据错误！！！", e);
		}
	}
	
	/**
	 * 得到网站的类电视数据
	 */
	public void getViewDataSite() {
		try {
			StatisticsSiteViews statisticsSiteViews = (StatisticsSiteViews) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), StatisticsSiteViews.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsSiteViews.setExtLimit(limit);
			int count = statisticsSiteViewsDao.selectLimitCount(statisticsSiteViews);
			List list = statisticsSiteViewsDao.selectByLimit(statisticsSiteViews);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("得到网站的类电视数据！！！", e);
		}
	}
	
	/**
	 * 得到频道的类电视数据
	 */
	public void getViewDataChanel() {
		try {
			StatisticsChannelViewsMonth statisticsChannelViewsMonth = new StatisticsChannelViewsMonth();
			
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStr = ServletActionContext.getRequest().getParameter("stateMonthTime");
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			
			
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) {
				statisticsChannelViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			}
			
			if(stateMonthTimeStr != null && !stateMonthTimeStr.trim().equals("")) {
				statisticsChannelViewsMonth.setStateMonthTime(new SimpleDateFormat("yyyy-MM").parse(stateMonthTimeStr));
			}
			
			statisticsChannelViewsMonth.setChannelId(Integer.parseInt(channelId));
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsChannelViewsMonth.setExtLimit(limit);
			int count = statisticsChannelViewsMonthDao.selectLimitCount(statisticsChannelViewsMonth);
			List list = statisticsChannelViewsMonthDao.selectByLimit(statisticsChannelViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error(" 得到频道的类电视数据错误！！！", e);
		}
	}

}
