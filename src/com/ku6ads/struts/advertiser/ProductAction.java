package com.ku6ads.struts.advertiser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.sysconfig.ProductDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 产品Action
 * 
 * @author LiuJunshi
 * 
 */
public class ProductAction extends BaseAction {

	private static final long serialVersionUID = 3905159867337538938L;
	private ProductDao productDao;
	
	 private Logger logger = Logger.getLogger(ProductAction.class);

	/**
	 * 新增产品;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Product product = (Product) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Product.class);
			UserInfoEty user = this.getLoginUser();
			if (product.getProductId() == null || product.getProductId() == 0) {
				product.setStatus(Product.STATUS_USE);
				product.setCreator(user.getUsername());
				product.setCreateTime(new Date());
				productDao.insertProduct(product);
			} else {
				productDao.updateProduct(product);
				product.setModifier(user.getUsername());
				product.setModifyTime(new Date());
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			// TODO 刘 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 
	 */
	public void SelectByProductLineId() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);

			Product product = (Product) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Product.class);
			product.setExtLimit(limit);
			int count = productDao.selectProductCount(product);
			List<Product> roleList = productDao.selectByProductLimit(product);

			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除产品线记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("productList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				productDao.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			// TODO 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 根据产品线id获得对应的产品列表
	 */
	public void getProductByLineId() {
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			List<Product> productList = productDao.selectByLineId(TypeConverterUtil.parseInt(id));
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < productList.size(); i++) {
				Product product = productList.get(i);
				sb.append("['" + product.getProductId() + "','" + product.getProductName() + "']");
				if (i < productList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
		}
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}
