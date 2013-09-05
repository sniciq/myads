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
public interface AdvFlightService extends BaseServiceIface{

	/**
	 * 
	 * @param advertisement
	 * @param AdvRelationBookList
	 * @param advMaterialList
	 * @param user
	 * @return 投放成功返回TRUE
	 */
	public String insert(Advertisement advertisement,List<AdvRelationBook> AdvRelationBookList,List<AdvMaterial> advMaterialList,String user,int flightStatus);
	

	
	/**
	 * 更新投放状态
	 * @param advFlight
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer updateStatus(AdvFlight advFlight) throws Exception;
	
	/**
	 * 
	 * @param advFlight
	 * @param advflightList
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	Integer stopFlight(AdvFlight advFlight, List<AdvFlight> advflightList)throws Exception;
	
	/**
	 * 获得点击地址 按排期包展示
	 * @param AdvRelationBookList
	 * @return
	 */
	public String makeClickAdress(List<AdvRelationBook> AdvRelationBookList,int advId) throws Exception;

	/**
	 * 
	 * @param priority
	 * @param bookPackageId
	 * @return
	 */
	public boolean modifyByPackageId(int priority, int bookPackageId);
	
	/**
	 * 获得广告位代码
	 * @param advBarId
	 * @return
	 */
	public String getPosCode(int advBarId);

	/**
	 * 查找当天的所有投放信息
	 * @return
	 */
	public List<AdvFlight> getFlightList();

}
