package com.ku6ads.dao.iface.webdata;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.struts.webdata.FlowData;

public interface FlowDataDao extends BaseDao {
	
	/**
	 * 流量数据每日详细查询<br>
	 * 查询记录数
	 * @param ety
	 * @return
	 */
	public Integer searchEveryDayDataCount(FlowData ety);
	
	/**
	 * 流量数据每日详细查询<br>
	 * 查询详细信息
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchEveryDayData(FlowData ety);
	
	/**
	 * 流量数据周期平均<br>
	 * 查询记录数
	 * @param ety
	 * @return
	 */
	public Integer searchAvgDataCount(FlowData ety);
	
	/**
	 * 流量数据周期平均<br>
	 * 查询详细信息
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchAvgData(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分城市数据
	 * @param ety
	 * @return
	 */
	public Integer searchReportCityCount(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分城市数据
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchReportCity(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分省份记录数
	 * @param ety
	 * @return
	 */
	public Integer searchReportProvinceCount(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分省份记录
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchReportProvince(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分时段记录数
	 * @param ety
	 * @return
	 */
	public Integer searchReportHourCount(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分时段记录
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchReportHour(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分频道记录数
	 * @param ety
	 * @return
	 */
	public Integer searchReportProdCount(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分频道记录
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchReportProd(FlowData ety);
	
	/**
	 * 报表数据查询<br>
	 * 分星期日记录
	 * @param ety
	 * @return
	 */
	public List<FlowData> searchReportWeekDay(FlowData ety);
	
}
