package com.ku6ads.services.impl.advflight;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.iface.advflight.AdvFlightDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvFlightTransService;
import com.ku6ads.struts.sysconfig.SysConfig;
import com.ku6ads.util.PropertiesUtils;
//import com.ku6ads.util.PushMemoryUtil;

/**
 * 广告投放ServiceImpl
 * 
 * @author liujunshi
 * 
 */

public class AdvFlightTransServiceImpl extends BaseAbstractService implements AdvFlightTransService {
	
	private SysConfig config;
	private Logger logger = Logger.getLogger(AdvFlightTransServiceImpl.class);
	public static final String OP_ADD = "add";
	public static final String HTTP_LEAD = "http://";
	
	
	/**
	 * 添加到投放表中，并推入内存 保持事务原子性
	 */
	public List<Integer> insertList(List<AdvFlight> advFlightList,Date maxTime) throws Exception {
		String falg = "true";
		//logger.info("into AdvFlightTransServiceImpl insertList method.");
		int bId = 0;
		int fid = 0;
		AdvFlight advFlight;
		List<Integer> fidList = new ArrayList<Integer>();
		try {
			for (int i = 0; i < advFlightList.size(); i++) {
				advFlight = advFlightList.get(i);
				advFlight.setMaxTime(maxTime);
				bId = advFlight.getBookId();
				AdvFlightDao advFlightDao =  (AdvFlightDao) getBaseDao();
				fid = advFlightDao.insertList(advFlight);
				fidList.add(fid);
			}
		} catch (Exception e) {
			fidList=new ArrayList<Integer>();
			logger.error("投放失败, 投放排期id= " + bId, e);
			falg = "false";
			throw e;
		} 
		return fidList;
	}
	
	/**
	 *TODO　推入内存 抛出异常为了保证InsertList的事务
	 */
	public boolean pushMemory(int fid, String op)throws Exception{
		//logger.info("into AdvFlightTransServiceImpl pushMemory method.");
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String FLIGHTURL_FLIGHTID = PropertiesUtils.getValue("FLIGHTURL_FLIGHTID");
		String FLIGHTURL_OP = PropertiesUtils.getValue("FLIGHTURL_OP");	
		boolean falg = false;
		try
		{
//			FIXME 刘钧石
//			falg = PushMemoryUtil.PushMemoryList(FLIGHTURL_FLIGHTID, fid, FLIGHTURL_OP, op);
			if(!falg){
				Exception ne = new RuntimeException("投放信息推内存失败，参数为 ："+FLIGHTURL_FLIGHTID+fid+FLIGHTURL_OP+op);
				throw ne;
			}
		}catch(RuntimeException e){
			throw e;
		}catch(Exception e){
			throw new RuntimeException(e);
			//如不过时runtime或者其子类的异常事务则不会回滚。
			//throw new Exception(e);
		}
		return falg;
	}

	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

	// ---------------------------GETTER/SERTTER-------------///


	

}
