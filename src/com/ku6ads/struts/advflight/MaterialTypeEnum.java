package com.ku6ads.struts.advflight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.ku6ads.util.PropertiesUtils;

/**
 * 物料类型
 * @author liujunshi
 *
 */
public class MaterialTypeEnum {
	
	public  List<String> VIDEOLIST;
	public  List<String> IMAGELIST;
	public  List<String>  TEXTLIST;
	public  List<String> FLASHLIST;
	public  List<String> CODELIST;

	/**
	 *单例模式
	 */
	  private static MaterialTypeEnum instance = null;   
	    
	   private MaterialTypeEnum() {   
			PropertiesUtils.load(new ClassPathResource("upload.properties"));
			String VIDEO = PropertiesUtils.getValue("VIDEOTYPES");
			String IMAGE = PropertiesUtils.getValue("IMAGETYPES");
			String TEXT = PropertiesUtils.getValue("TEXTTYPES");
			String FLASH = PropertiesUtils.getValue("FLASHTYPES");
			String CODE = PropertiesUtils.getValue("CODETYPES");
			
			if(VIDEO!=null&&IMAGE!=null&&TEXT!=null&&FLASH!=null){
				VIDEOLIST = Arrays.asList(VIDEO.split(","));
				IMAGELIST = Arrays.asList(IMAGE.split(","));
				TEXTLIST = Arrays.asList(TEXT.split(","));
				FLASHLIST = Arrays.asList(FLASH.split(","));
				CODELIST = Arrays.asList(CODE.split(","));
			}
	
	      // Exists only to defeat instantiation.   
	   }   
	   public static MaterialTypeEnum getInstance() {   
	      if(instance == null) {   
	         instance = new MaterialTypeEnum();   
	      }   
	      return instance;   
	   }   
	   
	
}
