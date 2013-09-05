package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Menu;

/**
 * @author xuxianan
 *
 */
public interface MenuDao extends BaseDao {

	/**
	 * 查询所有父类菜单
	 * @return
	 */
	public List<Menu> selectUserMenu(int userId);

	/**
	 * 
	 * 查询所有pnodeid = nodeid的菜单
	 * 
	 * @param menu
	 * @return
	 */
	public List<Menu> selectMenuByPnodeId(Menu menu);
}
