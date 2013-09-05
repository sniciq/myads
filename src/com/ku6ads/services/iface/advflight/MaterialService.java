package com.ku6ads.services.iface.advflight;

import java.io.File;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.services.base.BaseServiceIface;
/**
 * 物料
 * @author liujunshi
 *
 */
public interface MaterialService extends BaseServiceIface{
	
	/**
	 * 添加文字链物料并建立到广告物料关系
	 * @param advId
	 * @param material
	 * @param user
	 * @param AdvMaterialId
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void insertText(int advId,Material material,String user,int AdvMaterialId);

	/**
	 *  添加
	 * @param material
	 * @return
	 */
	public int insertMaterial(Material material);
	
	/**
	 * FTP 上传
	 * @param formFile
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Material upload(File formFile, String name,int materId,String materialType) throws Exception;
	
	/**
	 * 转码完成
	 * @param FileName
	 * @param isSucess
	 */
	public void isTransCode(Material material)throws NullPointerException;
	
	/**
	 * 判断文件类型
	 * 
	 * @param FileName
	 * @return
	 */
	public String chenkFileType(String FileName);
		
	/**
	 * 视频上传完成调用转码接口
	 */
	public int callTransCode(String sid,String path);
	
	/**
	 * 获取视频调度地址
	 * @param videoAd
	 * @return
	 */
	public StringBuilder getBlsvFlvAddr(String domain,String path,String flvAddr_suf,String blsvHost);
	
	
	
}
