package com.ku6ads.dao.iface.webdata;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.struts.webdata.HotFilmData;
import com.ku6ads.struts.webdata.ProgramData;

public interface HotDataDao extends BaseDao {
	
	public Integer selectProgramLimitCount(ProgramData programData);
	
	public List<ProgramData> selectProgramLimit(ProgramData programData);
	
	public Integer searchEveryDayDataCount(HotFilmData hotFilmData); 
	
	public List<HotFilmData> searchEveryDayData(HotFilmData hotFilmData);
	
	public Integer searchAvgDataCount(HotFilmData hotFilmData);
	
	public List<HotFilmData> searchAvgData(HotFilmData hotFilmData); 
	
	public Integer searchEveryDayVVDataCount(HotFilmData hotFilmData); 
	
	public List<HotFilmData> searchEveryDayVVData(HotFilmData hotFilmData);
	
	public Integer searchAvgVVDataCount(HotFilmData hotFilmData);
	
	public List<HotFilmData> searchAvgVVData(HotFilmData hotFilmData);
	

}
