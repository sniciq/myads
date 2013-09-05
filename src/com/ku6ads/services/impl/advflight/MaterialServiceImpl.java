package com.ku6ads.services.impl.advflight;


import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.dao.iface.advflight.MaterialDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.AdvMaterialService;
import com.ku6ads.services.iface.advflight.MaterialService;
import com.ku6ads.struts.advflight.MaterialTypeEnum;
import com.ku6ads.util.FTPClientApache;
import com.ku6ads.util.FileToStringUtil;
import com.ku6ads.util.MD5Encode;
import com.ku6ads.util.PropertiesUtils;
import com.ku6ads.util.TypeConverterUtil;
import com.ku6ads.util.XXTEA;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
/**
 * 物料
 * @author liujunshi
 *
 */
public class MaterialServiceImpl extends BaseAbstractService implements MaterialService{

	private Logger logger = Logger.getLogger(MaterialServiceImpl.class);
	AdvMaterialService advMaterialService;

	public static final String MTYPE_TEXT = "TEXT";
	public static final String MTYPE_IMAGE = "IMAGE";
	public static final String MTYPE_VIDEO = "VIDEO";
	public static final String MTYPE_FLASH = "FLASH";
	public static final String MTYPE_CODE = "CODE";
	
	public static final int CALL_OK = 1;
	public static final int CALL_ERR= 0;
	
	private String uploaderBaseUrl = PropertiesUtils.getValue("uploaderBaseUrl");
	private String blsvPKey = "drwxr-xr-xab0543a06ac80d706"; // new blsv private key

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.MaterialService#upload(java.io.File, java.lang.String, int)
	 */
	@Override
	public Material upload(File formFile, String fileName,int materId,String materialType) throws Exception {
		// TODO Auto-generated method stub
		String ftphost= null;
		String ftpuser= null;
		String ftppassword = null;
		String ftpdomian= null;
		String ftppath= null;
		Integer ftpport = null;
		
		if(formFile == null||fileName==null||materId==0||materialType==null){
			return null;
		}
		Material material = new Material();
		material.setMaterialId(materId);
		
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		ftpdomian = PropertiesUtils.getValue("FTPDOMAINFLV");
		ftppath = PropertiesUtils.getValue("FTPPATHFLV");
		
		//视频文件
		if(MTYPE_VIDEO.equalsIgnoreCase(materialType)){
		     ftphost = PropertiesUtils.getValue("FTPHOSTFLV");
			 ftpuser = PropertiesUtils.getValue("NAMFTPUSERFLVE");
			 ftppassword = PropertiesUtils.getValue("FTPPASSWORDFLV");
			 ftpport = TypeConverterUtil.parseInt(PropertiesUtils.getValue("FTPPORT"));
		
		}else{
			ftphost = PropertiesUtils.getValue("ftpadhost");
			ftpuser = PropertiesUtils.getValue("ftpaduser");
			ftppassword = PropertiesUtils.getValue("ftpadpassword");
			ftpport = TypeConverterUtil.parseInt(PropertiesUtils.getValue("ftpadport"));
		}
		
		Date now = new Date();
		
		//获得文件扩展名
		String fileEx=getFileEx(fileName).toLowerCase();
		
		if (formFile.length() != 0) {
			//按年月日生产路径
			String ym = DateFormatUtils.format(now, "yyyyMM");
			String ymdhss = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
			String filename = "KU6" + ymdhss + "." + fileEx;
			filename = filename.toLowerCase();
			FTPClientApache fpflv = null;
			//FTP上传
			fpflv = new FTPClientApache(ftphost, ftpuser, ftppassword,ftpport);
	
			String dirflv =  ftppath+ym + "/";
			//InputStream input = formFile.getInputStream();
			FileInputStream input = new FileInputStream(formFile);
			try {
				
				//如果是纯代码读取文件放入对象中
				if(MTYPE_CODE.equalsIgnoreCase(materialType)){
					material.setTextContent(FileToStringUtil.readFile(formFile));
				}
				
				fpflv.upload(input, filename, dirflv);
				//判断文件类型，如果是FLV调用转码接口
				if(materialType.equalsIgnoreCase(MTYPE_VIDEO)){
					//TODO CALL视频转码接口 materId
					material.setIsSuccess(Material.TRANSING);
					material.setPath(dirflv+filename);
					material.setMeterialType(MTYPE_VIDEO);
				}else{
					material.setIsSuccess(Material.TRANSSUCCESS);
					material.setPath(ftpdomian+dirflv+filename);
				}
				
				//构建mater对象
				material.setPhysicalName(filename);
				
				//置为可用
				material.setStatus(Material.STATUS_USE);
				//updateById(material);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}finally{
				fpflv.closeConnect();
				input.close();
			}
	
		}else{
			return null;
		}
		return material;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.MaterialService#insertMaterial(com.ku6ads.dao.entity.advflight.Material)
	 */
	public int insertMaterial(Material material) {
		//将视频格式文件扩展名转成flv
		if(MTYPE_VIDEO.equalsIgnoreCase(material.getMeterialType())){
			
		}
		return ((MaterialDao)getBaseDao()).insertMaterial(material);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.MaterialService#isTransCode(java.lang.String, java.lang.String)
	 */
	public void isTransCode(Material material)throws NullPointerException {
		
		Material m1=(Material) ((MaterialDao)getBaseDao()).selectById(material.getMaterialId());
		
		if(m1!=null){
			((MaterialDao)getBaseDao()).updateById(material);
		}else{
			throw new NullPointerException("无法根据ID找到文件");
		}
		
		
	}
	
	
	/**
	 * 判断文件类型
	 * 
	 * @param FileName
	 * @return
	 */
	public String chenkFileType(String FileName) {
		if (FileName == null || FileName.length() == 0) {
			return null;
		}
		String[] FileTypes = FileName.split("\\.");
		if (FileTypes.length < 0) {
			return null;
		}
		String FileType = FileTypes[FileTypes.length - 1].toLowerCase();
		

		if (MaterialTypeEnum.getInstance().TEXTLIST.contains(FileType)) {
			return MTYPE_TEXT;
		} else if (MaterialTypeEnum.getInstance().IMAGELIST.contains(FileType)) {
			return  MTYPE_IMAGE ;
		} else if (MaterialTypeEnum.getInstance().VIDEOLIST.contains(FileType)) {
			return MTYPE_VIDEO;
		} else if (MaterialTypeEnum.getInstance().FLASHLIST.contains(FileType)) {
			return MTYPE_FLASH;
		}else if (MaterialTypeEnum.getInstance().CODELIST.contains(FileType)) {
			return MTYPE_CODE;
		}
		return null;
	}
	
	/**
	 * 取得文件扩展名
	 * @param FileName
	 * @return
	 */
	private String getFileEx(String FileName) {
		if (FileName == null || FileName.length() == 0) {
			return null;
		}
		String[] FileTypes = FileName.split("\\.");
		if (FileTypes.length < 2) {
			return null;
		}
		String FileType = FileTypes[FileTypes.length - 1].toLowerCase();
		
		return FileType;
	}
	/**
	 * 替换视频文件扩展名
	 * @param FileName
	 * @return
	 */
	private String changeVideoFileEx(String FileName){
		if (FileName == null || FileName.length() == 0) {
			return null;
		}
		String[] FileTypes = FileName.split("\\.");
		if (FileTypes.length < 2) {
			return null;
		}
		String FileType = FileTypes[FileTypes.length - 1];
		FileName = FileName.substring(0,FileName.length()-FileType.length())+"flv";
		
		return FileName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.MaterialService#callTransCode(java.lang.String)
	 */

	public int callTransCode(String sid,String path) {
		// TODO Auto-generated method stub
		int TransCode_Ok = CALL_OK;
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String TransCodeURL=PropertiesUtils.getValue("TRANSCODEURL");
		String TransCodeFrom=PropertiesUtils.getValue("TRANSCODEFROM");
		String TransCodePath=PropertiesUtils.getValue("TRANSCODEPATH");
	     String urlStr = TransCodeURL+sid+TransCodeFrom+TransCodePath+path;  
         URL url;
         try {
             url = new URL(urlStr);
             URLConnection URLconnection = url.openConnection();  
             HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;  
             int responseCode = httpConnection.getResponseCode();  
             if (responseCode == HttpURLConnection.HTTP_OK) {  
                 System.err.println("CALL转码接口成功");
                 InputStream urlStream = httpConnection.getInputStream();  
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));  
                 String sCurrentLine = "";  
                 String sTotalString = "";  
                 while ((sCurrentLine = bufferedReader.readLine()) != null) {  
                     sTotalString += sCurrentLine;  
                 }  
                 System.err.println(sTotalString);
                 //判断转码接口是否成功
                if(!"ok".equalsIgnoreCase(sTotalString)){
                	TransCode_Ok=CALL_ERR;
                }
                
             }else{
                 System.err.println("CALL转码接口失败");
                 logger.error("CALL转码接口失败");
                 TransCode_Ok=CALL_ERR;
              }
         } catch (Exception e) {
             // TODO Auto-generated catch blockeb
             e.printStackTrace();
             logger.error("CALL转码接口失败",e);
             TransCode_Ok=CALL_ERR;

         }  
         return  TransCode_Ok;
	}

	/**
	 * 获取视频调度地址
	 * @param videoAd
	 * @return
	 */
	public StringBuilder getBlsvFlvAddr(String domain,String path,String flvAddr_suf,String blsvHost) {
		
		StringBuilder flvAddr = new StringBuilder();
		String envFileId = XXTEA.encryptVid("0");
		String flagChar = "s";//频道
		
		String currentTime = Long.toString(System.currentTimeMillis());
		Integer segCount = new Integer(1);//视频的第几分段
		Date now =new Date();
		String ymdhss = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
		Long uploadTime = Long.parseLong(ymdhss);
			StringBuilder sb0 = new StringBuilder(blsvHost);//域名
			StringBuilder sb1 = new StringBuilder(flagChar).append(segCount).append("/").append(envFileId).append("/");
			
			if (uploadTime != null) {
				sb1.append(uploadTime).append("/");
			} else {
				sb1.append("0/");
			}
			StringBuilder sb2 = new StringBuilder(domain);
			if (path.startsWith("/")) {
				sb2.append(path);
			} else {
				sb2.append("/").append(path);
			}
			StringBuilder sbkey = new StringBuilder(currentTime).append(",").append(sb2).append(",").append(blsvPKey);
			String pkey = MD5Encode.MD5Encode(sbkey.toString());
			flvAddr.append(sb0).append(sb1).append(pkey).append("/").append(currentTime).append("/").append(sb2);
			if(flvAddr != null && flvAddr.length() != 0){
				flvAddr.append(flvAddr_suf);
			}
		return flvAddr;
	
	}
	
	/*
	 * (non-Javadoc)
	 * TODO 建立事物
	 * @see com.ku6ads.services.iface.advflight.MaterialService#insertText(com.ku6ads.dao.entity.advflight.Material, com.ku6ads.dao.entity.advflight.AdvMaterial)
	 */
	public void insertText(int advId,Material material,String user,int AdvMaterialId) {
		int materialId = ((MaterialDao)getBaseDao()).insertMaterial(material);
		AdvMaterial advMaterial = new AdvMaterial();
		advMaterial.setId(AdvMaterialId);
		advMaterial.setAdvId(advId);
		advMaterial.setMaterialId(materialId);
		advMaterial.setStatus(0);
		advMaterial.setCreator(user);
		advMaterial.setCreateTime(new Date());
		advMaterial.setType("text");
		advMaterialService.updateById(advMaterial);
	}
	
	//------------------------------------GETTER SETTER-------------------------------//
	public String getUploaderBaseUrl() {
		return uploaderBaseUrl;
	}

	public void setUploaderBaseUrl(String uploaderBaseUrl) {
		this.uploaderBaseUrl = uploaderBaseUrl;
	}

	public AdvMaterialService getAdvMaterialService() {
		return advMaterialService;
	}

	public void setAdvMaterialService(AdvMaterialService advMaterialService) {
		this.advMaterialService = advMaterialService;
	}

	
}
