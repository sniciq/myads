package com.ku6ads.services.impl.advflight;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.dao.iface.advflight.MaterialDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.PathToCDNService;

public class PathToCDNServiceImpl extends BaseAbstractService implements PathToCDNService {

	MaterialDao materialDao;

	@SuppressWarnings("unchecked")
	public void writeFile(String filePath){
		Material m = new Material();
		m.setMeterialType("'VIDEO'");
		StringBuffer sb = new StringBuffer();
		String path;
		List<Material> mList = materialDao.selectByLimit(m);
		
		for(Material material:mList){
			
			path = makeCDNPath(material.getPath());
			sb.append(path).append("\r\n");
		}
		
		writeToFile(filePath+"/mpath.txt",sb.toString());
	}
	
	public static String  makeCDNPath(String path){
		String [] s = path.split("/");
		StringBuffer sbRes = new StringBuffer();
		for(int i=8;i<s.length;i++){
			sbRes.append("/").append(s[i]);
		}
	
		String result = sbRes.toString().split("\\?")[0];
		
		return result;
	}
	
	/**
	 * 文件写出
	 * @param filepath
	 * @param StartIdString
	 */
	public static void writeToFile(String filepath,String StartIdString){
		PrintWriter pw;
		try {
			
			pw = new PrintWriter( new FileWriter(filepath));
			pw.print(StartIdString);
		    pw.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
	}
	
	public static void main(String [] args){
//		String s = makeCDNPath("http://main.gslb.ku6.com/s1/91oUoexazVo./20110216190729083/894584773aca51c29e2c61a1573f98ca/1297854449083/Adomain/Bpath?ref=888");
//		String s1 = makeCDNPath("http://main.gslb.ku6.com/s1/91oUoexazVo./20110217152326880/8560e5c483429cd96448b7a922d77d40/1297927406880/v105/56/14/3f2c27274bbb245e1186344782cd54ed.f4v?ref=888");
//		
//		System.out.println(s);
//		System.out.println(s1);

		String str = "a\r\nb\r\nb\r\nb\r\nb\r\nb\r\nb\r\nb\r\nb\r\nb\r\n";
		
		writeToFile("./CDNCDNCDNpath.txt",str);
	
		
	}
	//----------------------GETTER SETTER-----------------//
	public MaterialDao getMaterialDao() {
		return materialDao;
	}

	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}
	
	
}
