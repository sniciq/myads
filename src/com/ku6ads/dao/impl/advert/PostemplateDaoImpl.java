package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.PostemBartem;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.iface.advert.PostemplateDao;

/**
 * @author liujunshi
 *
 */
public class PostemplateDaoImpl extends BaseAbstractDao implements PostemplateDao {

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advert.PostemplateDao#getEnablePostemplate()
	 */
	@SuppressWarnings("unchecked")
	public List<Postemplate> getEnablePostemplate() {
		return getSqlMapClientTemplate().queryForList("advert.Postemplate.selectEnablePostemplate");
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advert.PostemplateDao#getBartemByPostemId(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	@SuppressWarnings("unchecked")
	public List<PostemBartem> getBartemByPostemId(PostemBartem postemBartem) {
		return getSqlMapClientTemplate().queryForList("advert.Postemplate.selectBartemByPostemId",postemBartem);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advert.PostemplateDao#getBartemCountByPostemId(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	public Integer getBartemCountByPostemId(PostemBartem postemBartem) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advert.Postemplate.selectBartemCountByPostemId",postemBartem);
	}

/*
 * \(non-Javadoc)
 * @see com.ku6ads.dao.iface.advert.PostemplateDao#insertPostemBartem(com.ku6ads.dao.entity.advert.PostemBartem)
 */
	public void insertPostemBartem(PostemBartem postemBartem) {
		getSqlMapClientTemplate().insert("advert.Postemplate.insertPostemBartem", postemBartem);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advert.PostemplateDao#deletePostemBartem(int)
	 */
	public void deletePostemBartem(int postemBartemId) {
		getSqlMapClientTemplate().delete("advert.Postemplate.deletePostemBartem", postemBartemId);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.advert.PostemplateDao#deletePostemBartem(int)
	 */
	public void deletePostemBartemByPId(int postemId) {
		getSqlMapClientTemplate().delete("advert.Postemplate.deletePostemBartemByPId", postemId);
		
	}

	@Override
	public Object selectByTwoId(PostemBartem postemBartem) {
		return getSqlMapClientTemplate().queryForObject("advert.Postemplate.selectByTwoId", postemBartem);
		
	}
}
