package com.ku6ads.dao.impl.webdata;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.webdata.DimArea;
import com.ku6ads.dao.iface.webdata.DimAreaDao;

public class DimAreaDaoImpl extends SqlMapClientDaoSupport implements DimAreaDao {

	public List<DimArea> selectDimArea(DimArea ety) {
		return getSqlMapClientTemplate().queryForList("webdata.DimArea.selectDimArea", ety);
	}

	public List<DimArea> selectCountrys() {
		return getSqlMapClientTemplate().queryForList("webdata.DimArea.selectCountrys");
	}

	public List<DimArea> selectProvinceDimArea(DimArea ety) {
		return getSqlMapClientTemplate().queryForList("webdata.DimArea.selectProvinceDimArea", ety);
	}

	public List<DimArea> selectCityOfProvince(String Province) {
		return getSqlMapClientTemplate().queryForList("webdata.DimArea.selectCityOfProvince", Province);
	}

}
