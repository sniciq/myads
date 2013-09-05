package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ku6ads.dao.entity.sysconfig.Industry;
import com.ku6ads.dao.iface.sysconfig.IndustryDao;
/**
 * 
 * @author liujunshi
 *
 */
public class IndustryDaoImpl extends SqlMapClientDaoSupport implements IndustryDao {

	@Override
	public void deleteById(int IndustryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object insertIndustry(Industry Industry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Industry selectById(int IndustryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Industry> selectByIndustry(Industry Industry) {
		return null;
	}

	@Override
	public List<Industry> selectByIndustryLimit(Industry Industry) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Industry> selectIndustry() {
		return getSqlMapClientTemplate().queryForList("sysconfig.industry.selectindustrys");
	}

	@Override
	public Integer updateIndustry(Industry Industry) {
		// TODO Auto-generated method stub
		return null;
	}

}
