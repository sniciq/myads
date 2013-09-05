package com.ku6ads.dao.iface.webdata;

import java.util.List;

import com.ku6ads.dao.entity.webdata.HotProgram;

public interface HotProgramDao {
	
	public List<HotProgram> searchByHotProgram(HotProgram ety);
	
	public Integer searchByHotProgramCount(HotProgram ety);
	
	public Integer insertHotProgram(HotProgram ety);
	
	public Integer updateHotProgram(HotProgram ety);
	
	public HotProgram selectById(Integer id);
	
	public Integer deleteById(Integer id);
}
