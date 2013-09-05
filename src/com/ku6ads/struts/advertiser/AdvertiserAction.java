package com.ku6ads.struts.advertiser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.sysconfig.AdvertiserDao;
import com.ku6ads.dao.iface.sysconfig.BrandGroupDao;
import com.ku6ads.dao.iface.sysconfig.ProductDao;
import com.ku6ads.dao.iface.sysconfig.ProductLineDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.struts.sysconfig.ContactPersonAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;


/**
 * 广告主Action
 * 
 * @author LiuJunshi
 * 
 */
public class AdvertiserAction extends BaseAction {

	private static final long serialVersionUID = 3905159867337538938L;

	private AdvertiserDao advertiserDao;
	private BrandGroupDao brandGroupDao;
	private ProductLineDao productLineDao;
	private ProductDao productDao;
	
	private Logger logger = Logger.getLogger(AdvertiserAction.class);
	

	/**
	 * 新增广告主信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Advertiser advertiser = (Advertiser) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Advertiser.class);
			UserInfoEty user = this.getLoginUser();
			if (advertiser.getId() == null || advertiser.getId() == 0) {
				advertiser.setStatus(Advertiser.STATUS_USE);
				advertiser.setCreator(user.getUsername());
				advertiser.setCreateTime(new Date());
				advertiser.setModifier(user.getUsername());
				advertiser.setModifyTime(new Date());
				advertiserDao.insertAdvertiser(advertiser);

			} else {
				advertiser.setModifier(user.getUsername());
				advertiser.setModifyTime(new Date());
				advertiserDao.updateAdvertiser(advertiser);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			//e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			logger.error(e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告主列表;
	 */
	public void getAdvertiserList() {
		List<Advertiser> list = advertiserDao.selectAdvertiser();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Advertiser advertiser = list.get(i);
			sb.append("['" + advertiser.getId() + "','" + advertiser.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得品牌组类别列表;
	 */
	public void getBrandCategorylist() {
		List<AdvertiserCategory> list = brandGroupDao.selectBrandCategory();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			AdvertiserCategory advertiserCategory = list.get(i);
			sb.append("['" + advertiserCategory.getCategoryId() + "','" + advertiserCategory.getCategoryName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得类别列表;
	 */
	public void Categorylist() {
		List<AdvertiserCategory> list = advertiserDao.selectAdvertiserCategory();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			AdvertiserCategory advertiserCategory = list.get(i);
			sb.append("['" + advertiserCategory.getCategoryId() + "','" + advertiserCategory.getCategoryName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得广告主列表全部信息;
	 */
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);

			Advertiser advertiser = (Advertiser) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Advertiser.class);

			advertiser.setExtLimit(limit);

			int count = advertiserDao.selectBytAdvertiserCount(advertiser);
			List<Advertiser> roleList = advertiserDao.selectByAdvertiserLimit(advertiser);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * 获得对应的广告主信息;
	 */
	public void getAdvertiserDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String AdvertiserId = ServletActionContext.getRequest().getParameter("id");
			int id = TypeConverterUtil.parseInt(AdvertiserId);
			Advertiser advertiser = advertiserDao.selectById(id);
			retObj.put("data", advertiser);

		} catch (Exception e) {
			//e.printStackTrace();
			retObj.put("success", false);
			logger.error(e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告主记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("advertiserList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				int advertiserId = TypeConverterUtil.parseInt(idList[i]);
				advertiserDao.deleteById(advertiserId);
				
				//获得产品线List
				List<ProductLine> ProductLineList =productLineDao.selectProductLineByAdvertiser(advertiserId);
				
				//删除ProductLine
				productLineDao.deleteByAdvertiserId(advertiserId);
				
				if(ProductLineList !=null &&ProductLineList.size()>0){
					for(int j=0;j<ProductLineList.size();j++){
						//删除Product
						productDao.deleteByLineId(ProductLineList.get(j).getProductLineId());
					}
				}
			
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			//e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			logger.error(e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public AdvertiserDao getAdvertiserDao() {
		return advertiserDao;
	}

	public void setAdvertiserDao(AdvertiserDao advertiserDao) {
		this.advertiserDao = advertiserDao;
	}

	public BrandGroupDao getBrandGroupDao() {
		return brandGroupDao;
	}

	public void setBrandGroupDao(BrandGroupDao brandGroupDao) {
		this.brandGroupDao = brandGroupDao;
	}

	public ProductLineDao getProductLineDao() {
		return productLineDao;
	}

	public void setProductLineDao(ProductLineDao productLineDao) {
		this.productLineDao = productLineDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}
