package com.ku6ads.dao.iface.webdata;

import java.util.List;

import com.ku6ads.dao.entity.webdata.DimArea;

public interface DimAreaDao {
	public List<DimArea> selectDimArea(DimArea ety);
	
	public List<DimArea> selectProvinceDimArea(DimArea ety);
	
	public List<DimArea> selectCityOfProvince(String Province);
	
	public List<DimArea> selectCountrys();
}
