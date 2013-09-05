package com.ku6ads.struts.advflight;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.MaterialService;
import com.ku6ads.services.iface.advflight.PathToCDNService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;



public class FileUploadAction extends BaseAction implements ServletContextAware {

	private static final long serialVersionUID = 1L;
	private File upload;// 实际上传文件
	private String uploadContentType; // 文件的内容类型
	private String uploadFileName; // 上传文件名
	private String note;// 上传文件时的备注
	private String advId;// 广告Id
	private ServletContext context;
	private String advertiserId;
	private String name;

	MaterialService materialService;
	PathToCDNService pathToCDNService;
	private Logger logger = Logger.getLogger(FileUploadAction.class);

	/**
	 * 保存物料
	 * @throws Exception
	 */
	
	public void save() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			//uploadFileName=ServletActionContext.getRequest().getParameter("FileName");
			String mType = materialService.chenkFileType(uploadFileName);
			int aId = TypeConverterUtil.parseInt(advertiserId);
			if(mType!=null&&aId!=0){
				UserInfoEty user = this.getLoginUser();
				Material material = new Material();
				//TODO  需要先添加进如数据库获得ID
				material.setMaterialName(uploadFileName);
				material.setStatus(Material.STATUS_DEL);
				material.setNote(note);
				material.setCreator(user.getUsername());
				material.setCreateTime(new Date());
				material.setMeterialType(mType);
				material.setName(name);
				material.setAdvertiserId(aId);
				//如果是纯code上传
				if("CODE".equalsIgnoreCase(mType)){
					
				}
				int materId = materialService.insertMaterial(material);
				material = materialService.upload(upload, uploadFileName,materId,mType);
				
				//更新存放路径等属性
				if(material!=null){
					materialService.updateById(material);
					obj.put("result", "上传物料成功");
				}else{
					obj.put("result", "上传物料失败");
				}
				
				
				//CALL转码借口
				if("VIDEO".equalsIgnoreCase(mType)){
					int sid = material.getMaterialId();
					int TransCode_Ok = materialService.callTransCode(String.valueOf(sid),material.getPath());
					if(TransCode_Ok==1){
						material.setIsSuccess(material.TRANSING);
						materialService.updateById(material);
						obj.put("result", "上传物料成功");
					}else{
						obj.put("result", "上传物料失败，转码失败");
					}
				}
			}
			else{
				obj.put("result", "上传物料失败，该类型不允许上传");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
			obj.put("result", e.getMessage());
			
		}
	
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 添加文字链物料
	 */
	public void saveText() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Material material = (Material) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),Material.class);
			//Material material = new Material ();
			//取得文字链内容
			String id = ServletActionContext.getRequest().getParameter("id");
			String textContent = ServletActionContext.getRequest().getParameter("textContent");
			UserInfoEty user = this.getLoginUser();
			if (material.getMaterialId() == null) {
				//two project have same property name;
				material.setMeterialType("TEXT");
				material.setIsSuccess(1);
				material.setStatus(Material.STATUS_USE);
				material.setCreator(user.getUsername());
				material.setCreateTime(new Date());
				//将文字链内容放入路径字段
				material.setTextContent(textContent);
				materialService.insertText(TypeConverterUtil.parseInt(advId), material,user.getUsername(),TypeConverterUtil.parseInt(id));
			} else {
				//将文字链内容放入路径字段
				material.setTextContent(textContent);
				material.setModifier(user.getUsername());
				material.setModifyTime(new Date());
				materialService.updateById(material);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	/**
	 * 生成路径
	 * @return
	 */
	private static synchronized String createtDataPath() {
		java.util.Date dt = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String Path = fmt.format(dt)+"/";
	
		return Path;
	}



	
	/**
	 * 获得物料列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			//TODO
//			String filePath = ServletActionContext.getRequest().getRealPath("/");
//			pathToCDNService.writeFile(filePath);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Material material = (Material) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Material.class);
			material.setExtLimit(limit);
			String type = ServletActionContext.getRequest().getParameter("DoubleType");

			if(type!=null){
				type.toUpperCase();
				material.setMeterialType(type);
				material.setIsSuccess(Material.TRANSSUCCESS);
				if("IMAGE".equalsIgnoreCase(type)){
					material.setMeterialType("'IMAGE','FLASH'");
				}
				else if("VIDEO".equalsIgnoreCase(type)){
					material.setMeterialType("'VIDEO'");
				}
				else if("TEXT".equalsIgnoreCase(type)){
					material.setMeterialType("'TEXT'");
				}
				else if("FLASH".equalsIgnoreCase(type)){
					material.setMeterialType("'FLASH'");
				}
				else if("CODE".equalsIgnoreCase(type)){
					material.setMeterialType("'CODE'");
				}
				else if("ALL".equalsIgnoreCase(type)){
					material.setMeterialType("'IMAGE','FLASH','VIDEO','CODE'");
				}
			}
			if(material.getMeterialType()==null){
				material.setMeterialType("'FLASH','IMAGE','VIDEO','TEXT','CODE'");
			}
			int count = materialService.selectLimitCount(material);
			List<Material> materialList = materialService.selectByLimit(material);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), materialList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得广告条列表错误!", e);
		}
	}
	
	/**
	 * 删除物料;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("materialList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				materialService.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			// TODO  修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	


	/**
	 * 获得物料列表COMBO;
	 */
	public void MaterialList() {
		Material material = new Material ();
		List<Material> list = materialService.selectByLimit(material);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Material materialRes = list.get(i);
			sb.append("['" + materialRes.getModifyTime() + "','" + materialRes.getMaterialName()+ "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	
	/**
	 * 获得物料详细信息
	 */
	public void getDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Material material = (Material) materialService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", material);
		} catch (Exception e) {
			logger.error("获得广告条模板详细信息错误!", e);
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	//-------GETTER SETTER------//

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	public String getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public PathToCDNService getPathToCDNService() {
		return pathToCDNService;
	}

	public void setPathToCDNService(PathToCDNService pathToCDNService) {
		this.pathToCDNService = pathToCDNService;
	}
	

}