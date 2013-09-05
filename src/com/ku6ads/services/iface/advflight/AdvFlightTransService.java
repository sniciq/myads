package com.ku6ads.services.iface.advflight;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.services.base.BaseServiceIface;
/**
 * 广告活动
 * @author liujunshi
 *
 */
public interface AdvFlightTransService extends BaseServiceIface{

	/**
	 * 
	 * @param advFlightList
	 * @param maxTime
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Integer> insertList(List<AdvFlight> advFlightList,Date maxTime) throws Exception;
	
	/**
	 * 
	 * @param fid
	 * @param op
	 * @return
	 * @throws Exception
	 */
	public boolean pushMemory(int fid, String op)throws Exception;
	
}
