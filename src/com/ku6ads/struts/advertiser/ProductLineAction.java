package com.ku6ads.struts.advertiser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.sysconfig.ProductDao;
import com.ku6ads.dao.iface.sysconfig.ProductLineDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 产品线Action
 * 
 * @author LiuJunshi
 * 
 */
public class ProductLineAction extends BaseAction {

	private static final long serialVersionUID = 3905159867337538938L;
	private ProductLineDao productLineDao;
	private ProductDao productDao;
	
	private Logger logger = Logger.getLogger(ProductLineAction.class);

	/**
	 * 新增产品线信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			ProductLine productLine = (ProductLine) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProductLine.class);
			UserInfoEty user = this.getLoginUser();
			if (productLine.getProductLineId() == null) {
				productLine.setStatus(Product.STATUS_USE);
				productLine.setCreator(user.getUsername());
				productLine.setCreateTime(new Date());
				productLineDao.insertProductLine(productLine);

			} else {
				productLineDao.updateProductLine(productLine);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			//TODO 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得产品线列表全部信息;
	 */
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);

			ProductLine productLine = (ProductLine) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProductLine.class);
			productLine.setExtLimit(limit);
			int count = productLineDao.selectBytProductLineCount(productLine);
			List<ProductLine> roleList = productLineDao.selectByProductLineLimit(productLine);

			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * 获得对应广告主的产品线列表全部信息;
	 */
	public void getProductLineList() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);

			ProductLine productLine = (ProductLine) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProductLine.class);

			String advertiserId = ServletActionContext.getRequest().getParameter("advertiserId");
			productLine.setAdvertiserId(TypeConverterUtil.parseInt(advertiserId));
			productLine.setExtLimit(limit);
			int count = productLineDao.selectBytProductLineCount(productLine);
			List<ProductLine> roleList = productLineDao.selectByProductLineLimit(productLine);

			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得对应的产品线信息;
	 */
	public void getProductLineDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("productLineId");
			ProductLine productLine = productLineDao.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", productLine);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除产品线记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("productLineList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				productLineDao.deleteById(TypeConverterUtil.parseInt(idList[i]));
				//删除产品线下的产品
				productDao.deleteByLineId(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			//TODO 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 根据广告主id获得对应的产品线
	 */
	public void getProductLineById() {
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			List<ProductLine> productLineList = productLineDao.selectProductLineByAdvertiser(TypeConverterUtil.parseInt(id));
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < productLineList.size(); i++) {
				ProductLine productLine = productLineList.get(i);
				sb.append("['" + productLine.getProductLineId() + "','" + productLine.getProductLineName() + "']");
				if (i < productLineList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
		}
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
