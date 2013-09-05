package com.ku6ads.dao.iface.webdata;

import java.util.List;

import com.ku6ads.dao.entity.webdata.MetaProdName;

public interface MetaProdNameDao {
	public List<MetaProdName> selectMetaProdName(MetaProdName ety);
}
