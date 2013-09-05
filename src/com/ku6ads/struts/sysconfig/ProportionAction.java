package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Proportion;
import com.ku6ads.dao.iface.sysconfig.ProportionDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 权重参数action
 * 
 * @author chenshaofeng
 * @createTime 2010-11-27
 * @lastMofifyTime 2010-11-27
 */

public class ProportionAction extends BaseAction {
	private static final long serialVersionUID = -1023333890399328185L;
	private ProportionDao proportionDao;
	
	//FIXME 陈 log日志呢

	/**
	 * 获得权重参数信息表;
	 */
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Proportion proportion = (Proportion) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Proportion.class);
			proportion.setExtLimit(limit);
			int count = proportionDao.selectByProportionCount(proportion);
			List<Proportion> roleList = proportionDao.selectByProportionLimit(proportion);

			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增或更新权重参数信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			//FIXME 陈 if else太多，需要改写
			Proportion proportion = (Proportion) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Proportion.class);
			Date startTime = proportion.getStartTime();
			Date endTime = proportion.getEndTime();
			if (proportion.getStatus() == 0) {
				Integer existNo = 0;
				List<Proportion> proportionList = proportionDao.selectByName(proportion);
				for(int i = 0;i < proportionList.size();i++)
				{
					Proportion pro = proportionList.get(i);
					if(startTime.compareTo(pro.getStartTime())>=0&&startTime.compareTo(pro.getEndTime())<=0)
					{
						existNo = 1;
						break;
					}
					if(endTime.compareTo(pro.getStartTime())>=0&&endTime.compareTo(pro.getEndTime())<=0)
					{
						existNo = 1;
						break;
					}
				}
				if (existNo != 0) {
					obj.put("result", "isMoreThanOne");
					AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
					return;
				}
			}
			if (proportion.getId() == null) {
				proportion.setCreateTime(new Date());
				proportionDao.insertProportion(proportion);
			} else {
				proportion.setModifyTime(new Date());
				proportionDao.updateProportion(proportion);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			//FIXME 陈 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 删除权重参数信息;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("proportionList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				proportionDao.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			//FIXME 陈 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 获得权重参数详细信息;
	 */
	public void getProportionDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Proportion proportion = proportionDao.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", EntityReflect.getObjectJSonString(proportion, new SimpleDateFormat("yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public ProportionDao getProportionDao() {
		return proportionDao;
	}

	public void setProportionDao(ProportionDao proportionDao) {
		this.proportionDao = proportionDao;
	}

}
