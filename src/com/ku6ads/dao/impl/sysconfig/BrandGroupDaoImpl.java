package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.BrandGroup;
import com.ku6ads.dao.iface.sysconfig.BrandGroupDao;
/**
 * 
 * @author liujunshi
 *
 */
public class BrandGroupDaoImpl extends SqlMapClientDaoSupport implements BrandGroupDao {

	@Override
	public void deleteById(int BrandGroupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object insertBrandGroup(BrandGroup BrandGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BrandGroup> selectBrandGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BrandGroup> selectByBrandGroup(BrandGroup BrandGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BrandGroup> selectByBrandGroupLimit(BrandGroup BrandGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BrandGroup selectById(int BrandGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateBrandGroup(BrandGroup BrandGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<AdvertiserCategory> selectBrandCategory() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Category.selectBrandGroupCategorys");
	}

}
