package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.BarProportion;
import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.BarProportionService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告条系数
 * @author XuXianan
 *
 */
public class BarProportionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(BarProportionAction.class);
	private BarProportionService barProportionService;
	
	private static final Integer START_STATUS = 0;	// 启用

	/**
	 * 新增系数;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			BarProportion barProportion = (BarProportion) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BarProportion.class);
			
			List<BarProportion> proportionList = barProportionService.selectProportionByDate(barProportion);
			UserInfoEty user = this.getLoginUser();
			if (barProportion.getId() == null) {
				if (proportionList.isEmpty()) {
					barProportion.setStatus(START_STATUS);
					barProportion.setCreateTime(new Date());
					barProportion.setCreator(user.getUsername());
					barProportionService.insert(barProportion);
					obj.put("result", "success");
				} else {
					obj.put("result", "use");	// use 表示要插入或更改的日期已经存在.
				}
			} else {
				if (proportionList.isEmpty()) {
					barProportionService.updateById(barProportion);
					obj.put("result", "success");
				} else {
					obj.put("result", "use");
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得系数列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			String barId = ServletActionContext.getRequest().getParameter("barId");
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			BarProportion barProportion = (BarProportion) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BarProportion.class);
			barProportion.setExtLimit(limit);
			barProportion.setBarId(TypeConverterUtil.parseInt(barId));
			int count = barProportionService.selectLimitCount(barProportion);
			List<Department> barProportionList = barProportionService.selectByLimit(barProportion);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), barProportionList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得对应的部门信息;
	 */
	public void getBarProportionDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("barProportionId");
			BarProportion barProportion = (BarProportion) barProportionService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", barProportion);
		} catch (Exception e) {
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除系数;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("barProportionList");
			String[] idList = StringUtils.split(ids, ",");
			for (int i = 0; i < idList.length; i++) {
				barProportionService.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			retObj.put("result", "error");
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public BarProportionService getBarProportionService() {
		return barProportionService;
	}

	public void setBarProportionService(BarProportionService barProportionService) {
		this.barProportionService = barProportionService;
	}

}
