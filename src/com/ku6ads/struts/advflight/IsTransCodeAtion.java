package com.ku6ads.struts.advflight;



import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.services.iface.advflight.MaterialService;
import com.ku6ads.util.PropertiesUtils;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

public class IsTransCodeAtion extends ActionSupport{

	MaterialService materialService;
	private Logger log = Logger.getLogger(IsTransCodeAtion.class);
	private static final long serialVersionUID = 1L;

	//转码失败数据库置为0
	private static final int TransCode_fail= 0;
	//转码成功数据库置为1
	private static final int TransCode_success= 1;
	//转码成功置为1
	private static final String T_SUCCESS= "1";
	private static final String T_TRSFAIL= "2";
	private static final String T_CDNFAIL= "3";
//	private String message; 

	/**
	 * 转码接口;
	 */
	public void isTransCode() {
	
		try {
			String materialId = ServletActionContext.getRequest().getParameter("id");
			String isSuccess = ServletActionContext.getRequest().getParameter("isSuccess");
			String domain = ServletActionContext.getRequest().getParameter("domain");
			String path = ServletActionContext.getRequest().getParameter("path");
			String playtime = ServletActionContext.getRequest().getParameter("playtime");
			
			PropertiesUtils.load(new ClassPathResource("upload.properties"));
			String flvAddr_suf = PropertiesUtils.getValue("flvAddr_suf");
			String blsvHost = PropertiesUtils.getValue("blsvHost");
		    //String flvAddr_suf ="?ref=888";//?ref=888
			//String blsvHost = "http://main.gslb.ku6.com/"; // http://blsv.ku6.com/
			Integer success = new Integer(TransCode_fail);
			Material material=new Material();
			
			if(materialId!=null &&isSuccess!=null&&domain!=null&&path!=null){
				material = (Material) materialService.selectById(Integer.parseInt(materialId));
				
				//可以根据ID 找到物料信息
				if(material!=null&&material.getMaterialId()!=null){
					//判断视频物料是否是已经转码成功的
					if(material.getIsSuccess()!=TransCode_success){
						material.setMaterialId((TypeConverterUtil.parseInt(materialId)));
						if(isSuccess.equals(T_SUCCESS)){
							success =new Integer(T_SUCCESS);
						}
						else if(isSuccess.equals(T_TRSFAIL)){
							success =new Integer(T_TRSFAIL);
						}
						else if(isSuccess.equals(T_CDNFAIL)){
							success =new Integer(T_CDNFAIL);
						}
						material.setIsSuccess(success);
						material.setPath(materialService.getBlsvFlvAddr(domain, path,flvAddr_suf,blsvHost).toString());
						material.setPlayTime(TypeConverterUtil.parseInt(playtime));
						materialService.isTransCode(material);
						ServletActionContext.getResponse().getWriter().write("ok");
						
					
					}else{
						log.error("该物料已经完成一次转码，ID＝"+material.getMaterialId());
						ServletActionContext.getResponse().getWriter().write("ok");
					}
				}else{
					log.error("返回Id错误!无法根据ID在数据库中找到正确的物料信息，物料ID="+ materialId);
					ServletActionContext.getResponse().getWriter().write("ok");
					
				}
			}else{
				//获得参数错误
				log.error("获得参数错误! id ="+materialId+" isSuccess-"+isSuccess+" domain="+domain+" path="+path );
				ServletActionContext.getResponse().getWriter().write("err");
			}	
		}catch (Exception e) {
			//e.printStackTrace();
			log.error("转码接口错误!", e);
				try {
					ServletActionContext.getResponse().getWriter().write("err");
				}catch (IOException e1) {
					//e1.printStackTrace();
					log.error("转码接口错误!", e1);
				}
		}
	}
	

	//-------------GETTER、SERTTER---------------//
	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	
}
