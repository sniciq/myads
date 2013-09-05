package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.AdvbarPrice;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.AdvbarPriceService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告条刊例 action
 * 
 * @author LiYonghui
 * 
 */
public class AdvbarPriceAction extends BaseAction {

	// FIXME 李 log日志呢

	private static final long serialVersionUID = 555440062108557068L;
	private Logger logger = Logger.getLogger(AdvbarPriceAction.class);

	private AdvbarPriceService advbarPriceService;

	public AdvbarPriceService getAdvbarPriceService() {
		return advbarPriceService;
	}

	public void setAdvbarPriceService(AdvbarPriceService advbarPriceService) {
		this.advbarPriceService = advbarPriceService;
	}

	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			AdvbarPrice advbarPrice = (AdvbarPrice) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvbarPrice.class);
			advbarPrice.setExtLimit(limit);
			int count = advbarPriceService.selectLimitCount(advbarPrice);
			List<AdvbarPrice> advbarPriceList = advbarPriceService.selectByLimit(advbarPrice);
			if(advbarPriceList !=null && advbarPriceList.size()==1){
				AdvbarPrice price = advbarPriceList.get(0);
				if(price == null || (price!=null && price.getSaleType()==0)){
					advbarPriceList.remove(price);
				}
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advbarPriceList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 刊例概要编辑
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			AdvbarPrice advbarPrice = (AdvbarPrice) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvbarPrice.class);
			UserInfoEty user = this.getLoginUser();
			if (advbarPrice.getId() == 0) {// 新增
				advbarPrice.setCreator(user.getUsername());
				advbarPrice.setCreateTime(new Date());
				advbarPrice.setStatus(0);
				int id = (Integer)advbarPriceService.insertAdvbarPrice(advbarPrice);
				obj.put("priceId", id);
			}
			if (advbarPrice.getId() != null && advbarPrice.getId() != 0) {	// 更新
				List prices = advbarPriceService.selectByLimit(advbarPrice);
				if (prices != null && prices.size() > 0) {
					for (int i = 0; i < prices.size(); i++) {
						AdvbarPrice price = (AdvbarPrice) prices.get(i);
						price.setMateriel(advbarPrice.getMateriel());
						price.setImpression(advbarPrice.getImpression());
						price.setFormat(advbarPrice.getFormat());
						price.setClickRate(advbarPrice.getClickRate());
						price.setStorage(advbarPrice.getStorage());
						price.setModifier(user.getUsername());
						price.setModifyTime(new Date());
						price.setStatus(0);
						advbarPriceService.updateById(price);
					}
				}
			}
			obj.put("result", "success");
		} catch (Exception e) {
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			// FIXME 李 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 根据广告条价格id查询对应价格
	 */
	public void getAdvbarFormatByPriceId() {

		String priceId = ServletActionContext.getRequest().getParameter("priceId").trim();
		List<AdvbarPrice> list = advbarPriceService.selectAdvbarFormatByPriceId(TypeConverterUtil.parseInt(priceId));

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			AdvbarPrice advbarPrice = list.get(i);
			sb.append("['" + advbarPrice.getId() + "','" + advbarPrice.getFormat() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	/**
	 * 价格编辑
	 */
	public void savePrice() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String saleType = ServletActionContext.getRequest().getParameter("saleType");
			AdvbarPrice advbarPrice = (AdvbarPrice) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvbarPrice.class);
			// 新增
			if(saleType.equalsIgnoreCase("CPM")){
				advbarPrice.setSaleType(2);
			}else if(saleType.equalsIgnoreCase("CPD")){
				advbarPrice.setSaleType(1);
			}
			List<AdvbarPrice> advbarpList = advbarPriceService.getAdvbarByStyle(advbarPrice);
			if(advbarpList.size()>0)
			{
				obj.put("result", "exists");
				AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
				return;
			}
			if (advbarPrice != null && advbarPrice.getBarId() != null && advbarPrice.getId() == null) {
				List prices = advbarPriceService.selectByLimit(advbarPrice);
				if (prices != null && prices.size() > 0) {
					AdvbarPrice price = (AdvbarPrice) prices.get(0);
					if(prices.size()==1 && price!=null && price.getSaleType()==0){
							price.setFormat(advbarPrice.getFormat());
							price.setSaleType(advbarPrice.getSaleType());
							price.setPrice(advbarPrice.getPrice());
							price.setModifyTime(new Date());
							price.setModifier("");
							advbarPriceService.updateAdvbarPrice(price);
					}else {
						advbarPrice.setMateriel(price.getMateriel());
						advbarPrice.setImpression(price.getImpression());
						advbarPrice.setStorage(price.getImpression());
						advbarPrice.setClickRate(price.getClickRate());
						advbarPrice.setPos(price.getPos());
						advbarPrice.setCreateTime(new Date());
						advbarPrice.setStatus(0);
						advbarPriceService.insertAdvbarPrice(advbarPrice);
					}
				}
			} else if (advbarPrice != null && advbarPrice.getBarId() != null && advbarPrice.getId() != null) {// 编辑
				advbarPriceService.updateAdvbarPrice(advbarPrice);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			// FIXME 李 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 根据广告条形式id获得对应价格
	 */
	public void getAdvbarPriceById() {
		try {
			String id = ServletActionContext.getRequest().getParameter("id").trim();
			AdvbarPrice advbarPrice = advbarPriceService.selectAdvbarPriceById(TypeConverterUtil.parseInt(id));

			String result = advbarPrice.getPrice();
			AjaxOut.responseText(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void getAdvbarPriceDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String barId = ServletActionContext.getRequest().getParameter("barId");
			AdvbarPrice advbarPrice = new AdvbarPrice();
			advbarPrice.setBarId(Integer.parseInt(barId));
			List priceLise = advbarPriceService.selectByLimit(advbarPrice);
			if (priceLise != null && priceLise.size() > 0) {
				advbarPrice = (AdvbarPrice) priceLise.get(0);
			}
			retObj.put("data", advbarPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public void showAdvbarPriceById() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			AdvbarPrice advbarPrice = new AdvbarPrice();
			advbarPrice = (AdvbarPrice) advbarPriceService.selectById(Integer.parseInt(id));
			retObj.put("data", advbarPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("priceList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				advbarPriceService.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void AdvbarPriceByBarId(){
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String barId = ServletActionContext.getRequest().getParameter("barId");
			AdvbarPrice advbarPrice = new AdvbarPrice();
			advbarPrice.setBarId(Integer.parseInt(barId));
			List prices = advbarPriceService.selectByLimit(advbarPrice);
			if(prices !=null && prices.size()>0){
				retObj.put("result", "success");
			}else{
				retObj.put("result", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
}
