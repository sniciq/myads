package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Menu;
import com.ku6ads.dao.iface.sysconfig.MenuDao;

public class MenuDaoImpl extends BaseAbstractDao implements MenuDao {

	@SuppressWarnings("unchecked")
	public List<Menu> selectUserMenu(int userId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Menu.selectUserMenu", userId);
	}

	@SuppressWarnings("unchecked")
	public List<Menu> selectMenuByPnodeId(Menu menu) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Menu.selectMenuByPnodeId", menu);
	}

}
