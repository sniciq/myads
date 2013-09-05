package com.ku6ads.dao.impl.advflight;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.iface.advflight.AdvFlightDao;

/**
 * 投放DAO
 * 
 * @author liujunshi
 * 
 */
public class AdvFlightDaoImpl extends BaseAbstractDao implements AdvFlightDao {

	private Logger logger = Logger.getLogger(AdvFlightDaoImpl.class);
	@Override
	public Integer updateStatus(AdvFlight advFlight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("advflight.AdvFlight.updateStatus", advFlight);
	}
	
	
	/**
	 * 投放状态完成，根据投放结束时间。
	 * @param advFlight
	 * @return
	 */
	public Integer flightComplete(AdvFlight advFlight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("advflight.AdvFlight.flightComplete", advFlight);
	}

	/**
	 * 保持事务
	 * 
	 * @param advFlightList
	 * @return
	 * @throws SQLException 
	 */
	public Integer insertList(AdvFlight advFlight,SqlMapClient sqlMapClient) throws SQLException {
		//logger.error("into AdvFlightDaoImpl insertList(AdvFlight,SqlMapClient) method advFlight advbarid = " +advFlight.getAdvbarId());
		return	(Integer)sqlMapClient.insert("advflight.AdvFlight.insert", advFlight);
			
	}
	
	/**
	 * 保持事务
	 * 
	 * @param advFlightList
	 * @return
	 * @throws SQLException 
	 */
	public Integer insertList(AdvFlight advFlight) throws SQLException {
		//logger.error("into AdvFlightDaoImpl insertList(AdvFlight) method advFlight advbarid = " +advFlight.getAdvbarId());
		return	(Integer)getSqlMapClientTemplate().insert("advflight.AdvFlight.insert", advFlight);		
	}
	

	public Integer modifyByPackageId(AdvFlight advFlight){
		return getSqlMapClientTemplate().update("advflight.AdvFlight.ModifyByPackageId", advFlight);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advflight.AdvFlightDao#selectByPackageId(com.ku6ads.dao.entity.advflight.AdvFlight)
	 */
	public List selectByPackageId(AdvFlight advFlight) {
		return getSqlMapClientTemplate().queryForList("advflight.AdvFlight.selectByPackageId", advFlight);
	} 
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.base.BaseAbstractDao#deleteById(java.lang.Integer)
	 */
	public void deleteById(Integer fid) {
		getSqlMapClientTemplate().delete("advflight.AdvFlight.deleteById", fid);
	}


	@Override
	public List<AdvFlight> getFlightList() {
		return getSqlMapClientTemplate().queryForList("advflight.AdvFlight.selectFlightListByDate");
	} 
}
