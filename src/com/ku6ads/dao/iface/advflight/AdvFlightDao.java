package com.ku6ads.dao.iface.advflight;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.AdvFlight;

/**
 * 投放DAO
 * @author liujunshi
 *
 */
public interface AdvFlightDao extends BaseDao {

	/**
	 * 投放状态完成，根据投放结束时间。
	 * @param advFlight
	 * @return
	 */
	public Integer flightComplete(AdvFlight advFlight);
	/**
	 * 更新投放状态
	 * @param advFlight
	 * @return
	 */
	public Integer updateStatus(AdvFlight advFlight) ;
	
	/**
	 * 根据排期包ID修改优先级
	 * @param advFlight
	 * @return
	 */
	public Integer modifyByPackageId(AdvFlight advFlight) ;
	
	/**
	 * 查询根据排期修改了优先级的投放
	 * @param po
	 * @return
	 */
	public List selectByPackageId(AdvFlight advFlight);
	
	/**
	 * 插入一个排期包下的所有排期
	 * 保持事务
	 * @param advFlightList
	 * @return
	 */
	public Integer insertList(AdvFlight AdvFlight,SqlMapClient sqlMapClient) throws Exception;
	
	/**
	 * 插入一个排期包下的所有排期
	 * 保持事务
	 * @param advFlightList
	 * @return
	 */
	public Integer insertList(AdvFlight AdvFlight) throws Exception;
	
	/**
	 * 根据Id删除
	 */
	public void deleteById(Integer fid) ;
	
	/**
	 * 获得当天所有投放信息
	 * @return
	 */
	public List<AdvFlight> getFlightList();
}
