package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Area;
/**
 * 地域
 * @author liujunshi
 *
 */
public interface AreaDao extends BaseDao {

	/**
	 * 查询地域信息,返回集合
	 * @return
	 */
	public List<Area> selectArea();

}
