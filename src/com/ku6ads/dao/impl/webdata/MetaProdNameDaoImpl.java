package com.ku6ads.dao.impl.webdata;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.webdata.MetaProdName;
import com.ku6ads.dao.iface.webdata.MetaProdNameDao;

public class MetaProdNameDaoImpl extends SqlMapClientDaoSupport implements MetaProdNameDao {

	public List<MetaProdName> selectMetaProdName(MetaProdName ety) {
		return getSqlMapClientTemplate().queryForList("webdata.MetaProdName.selectMetaProdName", ety);
	}

}
