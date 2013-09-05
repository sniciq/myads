package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.entity.advert.PostemBartem;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.advert.PostemplateService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;
/**
 * 
 * @author liujunshi
 *
 */
public class PostemplateAction  extends BaseAction{

	private static final long serialVersionUID = -4909029991028983773L;
	private PostemplateService postemplateService;
	private AdvpositionService advpositionService;
	
	/**
	 * 新增广告位模板
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Postemplate postemplate = (Postemplate) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					Postemplate.class);
			UserInfoEty user = this.getLoginUser();
			if (postemplate.getId() == null) {
				postemplate.setCreator(user.getUsername());
				postemplate.setCreateTime(new Date());
				postemplate.setModifier(user.getUsername());
				postemplate.setModifyTime(new Date());
				postemplateService.insert(postemplate);
				obj.put("result", "success");
			} 
			else{
				//TODO 状态改为停用时 验证
				if(postemplate.getStatus()==Bartemplate.STATUS_STOP){
					List listPos = advpositionService.selectAdvpositionByPostemId(postemplate.getId());
					if(listPos ==null || listPos.isEmpty()){
						PostemBartem postemBartem = new PostemBartem();
						postemBartem.setPostemId(postemplate.getId());
						//判断是否被广告条模板使用
	
							postemplateService.updateById(postemplate);
							//删除关系
							postemplateService.deletePostemBartemByPId(postemplate.getId());
							obj.put("result", "success");
														
					}else{
						obj.put("result", "use");
					}
				}else{
					//修改
					postemplate.setModifier(user.getUsername());
					postemplate.setModifyTime(new Date());
					
					// 广告位模板code变更后 更新相应广告位code
					List<Advposition> listPos = advpositionService.selectAdvpositionByPostemId(postemplate.getId());
					if (!listPos.isEmpty()) {
						for (Advposition advposition : listPos) {
							advposition.setCode(postemplate.getCode());
							advpositionService.updateById(advposition);
						}
					}
					postemplateService.updateById(postemplate);
					obj.put("result", "success");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "fail");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告位模板列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			Postemplate postemplate = (Postemplate) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					Postemplate.class);
			postemplate.setExtLimit(limit);
			int count = postemplateService.selectLimitCount(postemplate);
			List<Postemplate> advpositionList = postemplateService.selectByLimit(postemplate);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得广告位模板详细信息
	 */
	public void getPostemplateDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Postemplate postemplate = (Postemplate) postemplateService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", postemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告位模板;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		List listPos = null;	
		boolean flagUse = false;
		try {
			String ids = ServletActionContext.getRequest().getParameter("postemplateList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				int id = TypeConverterUtil.parseInt(idList[i]);
			
				listPos = advpositionService.selectAdvpositionByPostemId(id);
				if(listPos ==null || listPos.isEmpty()){
					PostemBartem postemBartem = new PostemBartem();
					postemBartem.setPostemId(id);
					//判断是否被广告条模板使用
					//int count = postemplateService.getBartemCountByPostemId(postemBartem);
					//if(count>0){
					//	flagUse = true;
					//}else{
						postemplateService.deleteById(id);
						//删除关系
						postemplateService.deletePostemBartemByPId(id);
					//}
							
				}else{
					flagUse = true;
					continue;
				}		
			}
			if(flagUse){
				retObj.put("result", "use");
			}else{
				retObj.put("result", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	
	/**
	 * 获得广告位模板列表;
	 */
	@SuppressWarnings("unchecked")
	public void showBartemplate() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			String id = ServletActionContext.getRequest().getParameter("postemplateId");
			
			PostemBartem postemBartem = new PostemBartem();
			postemBartem.setPostemId(TypeConverterUtil.parseInt(id));
			postemBartem.setExtLimit(limit);
			int count = postemplateService.getBartemCountByPostemId(postemBartem);
			List<PostemBartem> advpositionList = postemplateService.getBartemByPostemId(postemBartem);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加广告位模板 广告条模板关系
	 */
	public void savePostemBartem() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			PostemBartem postemBartem = (PostemBartem) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					PostemBartem.class);
		
			if(postemplateService.selectByTwoId(postemBartem)== null){
				postemBartem.setCreator("admin");
				postemBartem.setCreateTime(new Date());
				postemBartem.setModifier("admin");
				postemBartem.setModifyTime(new Date());
				postemplateService.insertPostemBartem(postemBartem);
				obj.put("result", "success");
			}else{
				obj.put("result", "multiple");
				//obj.put("info", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	
	
	
	/**
	 * 删除广告位模板广告条模板关系;
	 */
	public void deleteBartem() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);

		try {
			String ids = ServletActionContext.getRequest().getParameter("productList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
		
					postemplateService.deletePostemBartem(TypeConverterUtil.parseInt(idList[i]));	
	
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	//------------------------------GETTER/SETTER-----------------------------//

	public PostemplateService getPostemplateService() {
		return postemplateService;
	}

	public void setPostemplateService(PostemplateService postemplateService) {
		this.postemplateService = postemplateService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}

	
}
