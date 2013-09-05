package com.ku6ads.services.impl.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.PostemBartem;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.iface.advert.PostemplateDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.PostemplateService;

/**
 * 
 * @author liujunshi
 *
 */
public class PostemplateServiceImpl extends BaseAbstractService implements PostemplateService {

	PostemplateDao postemplateDao;

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#getEnablePostemplate()
	 */
	public List<Postemplate> getEnablePostemplate() {
		return postemplateDao.getEnablePostemplate();
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#getBartemByPostemId(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	public List<PostemBartem> getBartemByPostemId(PostemBartem postemBartem) {
		// TODO Auto-generated method stub
		return postemplateDao.getBartemByPostemId(postemBartem);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#getBartemCountByPostemId(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	public Integer getBartemCountByPostemId(PostemBartem postemBartem) {
		return postemplateDao.getBartemCountByPostemId(postemBartem);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#insertPostemBartem(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	public void insertPostemBartem(PostemBartem postemBartem) {
		postemplateDao.insertPostemBartem(postemBartem);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#deletePostemBartem(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	public void deletePostemBartem(int postemBartemId) {
		postemplateDao.deletePostemBartem(postemBartemId);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#deletePostemBartemByPid(int)
	 */
	public void deletePostemBartemByPId(int postemBartemId) {
		postemplateDao.deletePostemBartemByPId(postemBartemId);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.PostemplateService#selectByTwoId(com.ku6ads.dao.entity.advert.PostemBartem)
	 */
	@Override
	public Object selectByTwoId(PostemBartem postemBartem) {
		return postemplateDao.selectByTwoId(postemBartem);
		
	}
	
//----------GETTER SETTER---------//
	public PostemplateDao getPostemplateDao() {
		return postemplateDao;
	}

	public void setPostemplateDao(PostemplateDao postemplateDao) {
		this.postemplateDao = postemplateDao;
	}











	

}
