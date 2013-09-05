package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.entity.advert.PostemBartem;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.services.iface.advert.BartemplateService;
import com.ku6ads.services.iface.advert.PostemplateService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 
 * @author LiuJunshi
 * 
 */
public class BartemplateAction extends BaseAction {

	private static final long serialVersionUID = 962044189606159217L;

	private BartemplateService bartemplateService;
	private AdvbarService advbarService;
	private PostemplateService postemplateService;
	private Logger logger = Logger.getLogger(BartemplateAction.class);

	/**
	 * 新增广告条模板
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Bartemplate bartemplate = (Bartemplate) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Bartemplate.class);
			UserInfoEty user = this.getLoginUser();
			if (bartemplate.getId() == null) {
				bartemplate.setCreator(user.getUsername());
				bartemplate.setCreateTime(new Date());
				bartemplateService.insert(bartemplate);
				obj.put("result", "success");
			} else {
				//TODO 状态改为停用时 验证
				if(bartemplate.getStatus()==Bartemplate.STATUS_STOP){
					List listBar = advbarService.selectAdvbarByBartemId(bartemplate.getId());
					if (listBar != null || listBar.isEmpty()) {
						//判断是否被广告条模板使用
						PostemBartem postemBartem = new PostemBartem();
						postemBartem.setBartemId(bartemplate.getId());
						int count = postemplateService.getBartemCountByPostemId(postemBartem);
						if(count>0){
							obj.put("result", "useTemplate");
						}else{
							//修改
							bartemplateService.updateById(bartemplate);
							obj.put("result", "success");
						}
					}else{
						obj.put("result", "use");
					}	
				}else{
					// 广告条模板发生修改时 修改关联广告位code字段
					List<Advbar> listBar = advbarService.selectAdvbarByBartemId(bartemplate.getId());
					if (!listBar.isEmpty()) {
						for (Advbar advbar : listBar) {
							advbar.setCode(bartemplate.getCode());
							advbarService.updateById(advbar);
						}
					}
					
					// 修改
					bartemplateService.updateById(bartemplate);
					obj.put("result", "success");
				}
			
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增广告条模板错误!", e);
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告条列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Bartemplate bartemplate = (Bartemplate) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Bartemplate.class);
			bartemplate.setExtLimit(limit);
			int count = bartemplateService.selectLimitCount(bartemplate);
			List<Bartemplate> advpositionList = bartemplateService.selectByLimit(bartemplate);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得广告条列表错误!", e);
		}
	}

	/**
	 * 获得广告条模板详细信息
	 */
	public void getBartemplateDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Bartemplate bartemplate = (Bartemplate) bartemplateService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", bartemplate);
		} catch (Exception e) {
			logger.error("获得广告条模板详细信息错误!", e);
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告条模板;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		List listBar = null;
		boolean flagUse = false;
		try {
			String ids = ServletActionContext.getRequest().getParameter("bartemplateList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				int id = TypeConverterUtil.parseInt(idList[i]);

				listBar = advbarService.selectAdvbarByBartemId(id);
				if (listBar != null || listBar.isEmpty()) {
					//判断是否被广告条模板使用
					PostemBartem postemBartem = new PostemBartem();
					postemBartem.setBartemId(id);
					int count = postemplateService.getBartemCountByPostemId(postemBartem);
					if(count>0){
						flagUse = true;
						retObj.put("info", "被广告位模板使用中");
						
					}else{
						bartemplateService.deleteById(id);
					}
					
				} else {
					flagUse = true;
					continue;
				}
			}

			if (flagUse) {
				retObj.put("result", "use");
			} else {
				retObj.put("result", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			logger.error("删除广告条模板错误!", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 获得可用的广告条模板列表
	 */
	public void getBartemplateList() {
		List<Bartemplate> list = bartemplateService.getEnableBartemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Bartemplate bartemplate = list.get(i);
			sb.append("['" + bartemplate.getId() + "','" + bartemplate.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	// ----------------------------GETTER SETTER-------------------//
	public BartemplateService getBartemplateService() {
		return bartemplateService;
	}

	public void setBartemplateService(BartemplateService bartemplateService) {
		this.bartemplateService = bartemplateService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AdvbarService getAdvbarService() {
		return advbarService;
	}

	public void setAdvbarService(AdvbarService advbarService) {
		this.advbarService = advbarService;
	}

	public PostemplateService getPostemplateService() {
		return postemplateService;
	}

	public void setPostemplateService(PostemplateService postemplateService) {
		this.postemplateService = postemplateService;
	}

}
