package com.ku6ads.struts.webdata;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.DimArea;
import com.ku6ads.dao.entity.webdata.MetaProdName;
import com.ku6ads.dao.iface.webdata.DimAreaDao;
import com.ku6ads.dao.iface.webdata.MetaProdNameDao;
import com.opensymphony.xwork2.ActionSupport;
import com.ku6ads.util.AjaxOut;

public class ConstantAction extends ActionSupport {
	
	private static final long serialVersionUID = 1286506082457518424L;
	private DimAreaDao dimAreaDao;
	private MetaProdNameDao metaProdNameDao;
	
	public void getDimAreaList() {
		try {
			String node = ServletActionContext.getRequest().getParameter("node");
			JSONArray array = new JSONArray();
			
			if(node.equals("0")) {//要结点
				List<DimArea> list = dimAreaDao.selectCountrys();
				for(int i = 0; i < list.size(); i++) {
					DimArea ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getCountry() + "_1");
					obj.put("text", ety.getCountry());
					obj.put("leaf", false);
					array.add(obj);
				}
			}
			else if(node.endsWith("_1")) {//查二级目录
				node = node.replace("_1", "");
				DimArea ety = new DimArea();
				ety.setCountry(node);
				List<DimArea> list = dimAreaDao.selectProvinceDimArea(ety);
				for(int i = 0; i < list.size(); i++) {
					ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getProvince() + "_2");
					obj.put("text", ety.getProvince());
					obj.put("leaf", false);
					array.add(obj);
				}
			}
			else if(node.endsWith("_2")) {//查三级结点
				node = node.replace("_2", "");
				List<DimArea> list = dimAreaDao.selectCityOfProvince(node);
				for(int i = 0; i < list.size(); i++) {
					DimArea ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getArea_id());
					obj.put("text", ety.getCity());
					obj.put("leaf", true);
					array.add(obj);
				}
			}
			
			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getProdNameList() {
		try {
			List<MetaProdName> list = metaProdNameDao.selectMetaProdName(null);
			JSONArray array = new JSONArray();
			for(int i = 0; i < list.size(); i++) {
				MetaProdName ety = list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("id", ety.getId());
				obj.put("text", ety.getProd_name());
				obj.put("leaf", true);
				array.add(obj);
			}
			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DimAreaDao getDimAreaDao() {
		return dimAreaDao;
	}
	public void setDimAreaDao(DimAreaDao dimAreaDao) {
		this.dimAreaDao = dimAreaDao;
	}
	public MetaProdNameDao getMetaProdNameDao() {
		return metaProdNameDao;
	}
	public void setMetaProdNameDao(MetaProdNameDao metaProdNameDao) {
		this.metaProdNameDao = metaProdNameDao;
	}
	
}
