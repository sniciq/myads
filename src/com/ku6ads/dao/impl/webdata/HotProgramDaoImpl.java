package com.ku6ads.dao.impl.webdata;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.webdata.HotProgram;
import com.ku6ads.dao.iface.webdata.HotProgramDao;

public class HotProgramDaoImpl extends SqlMapClientDaoSupport implements HotProgramDao {

	public List<HotProgram> searchByHotProgram(HotProgram ety) {
		return getSqlMapClientTemplate().queryForList("webdata.HotProgram.searchByHotProgram", ety);
	}

	public Integer searchByHotProgramCount(HotProgram ety) {
		return (Integer) getSqlMapClientTemplate().queryForObject("webdata.HotProgram.searchByHotProgramCount", ety);
	}
	
	public Integer insertHotProgram(HotProgram ety) {
		return (Integer) getSqlMapClientTemplate().insert("webdata.HotProgram.insertHotProgram", ety);
	}

	public HotProgram selectById(Integer id) {
		return (HotProgram) getSqlMapClientTemplate().queryForObject("webdata.HotProgram.selectById", id);
	}

	public Integer updateHotProgram(HotProgram ety) {
		return getSqlMapClientTemplate().update("webdata.HotProgram.updateHotProgram", ety);
	}

	public Integer deleteById(Integer id) {
		return getSqlMapClientTemplate().delete("webdata.HotProgram.deleteById", id);
	}

}
