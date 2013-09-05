package com.ku6ads.dao.impl.webdata;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.iface.webdata.FlowDataDao;
import com.ku6ads.struts.webdata.FlowData;

public class FlowDataDaoImpl extends BaseAbstractDao implements FlowDataDao {

	public List<FlowData> searchEveryDayData(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchEveryDayData", ety);
	}

	public Integer searchEveryDayDataCount(FlowData ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.FlowData.searchEveryDayDataCount", ety);
	}

	public List<FlowData> searchAvgData(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchAvgData", ety);
	}

	public Integer searchAvgDataCount(FlowData ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.FlowData.searchAvgDataCount", ety);
	}

	public List<FlowData> searchReportCity(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchReportCity", ety);
	}

	public Integer searchReportCityCount(FlowData ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.FlowData.searchReportCityCount", ety);
	}

	public List<FlowData> searchReportProvince(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchReportProvince", ety);
	}

	public Integer searchReportProvinceCount(FlowData ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.FlowData.searchReportProvinceCount", ety);
	}

	public List<FlowData> searchReportHour(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchReportHour", ety);
	}

	public Integer searchReportHourCount(FlowData ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.FlowData.searchReportHourCount", ety);
	}

	public List<FlowData> searchReportProd(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchReportProd", ety);
	}

	public Integer searchReportProdCount(FlowData ety) {
		return null;
	}

	public List<FlowData> searchReportWeekDay(FlowData ety) {
		return getSqlMapClientTemplate().queryForList("default.webdata.FlowData.searchReportWeekDay", ety);
	}

}
