package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.ku6ads.dao.entity.sysconfig.AdvertiserCategory;
import com.ku6ads.dao.entity.sysconfig.Company;
import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.iface.sysconfig.ConsumerDao;
import com.ku6ads.dao.iface.sysconfig.DepartmentDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 客户action
 * 
 * @author LiuJunshi
 * 
 */
public class ConsumerAction extends ActionSupport {

	private static final long serialVersionUID = 3905159867337538938L;
	private DepartmentDao departmentDao;
	private ConsumerDao consumerDao;
	
	//FIXME 刘 log日志呢

	/**
	 * 新增客户信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Consumer consumer = (Consumer) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Consumer.class);
			if (consumer.getConsumerId() == null) {
				consumerDao.insertConsumer(consumer);
			} else {
				consumerDao.updateConsumer(consumer);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得类别列表;
	 */
	public void categorylist() {
		List<AdvertiserCategory> list = consumerDao.selectConsumerCategory();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			AdvertiserCategory advertiserCategory = list.get(i);
			sb.append("['" + advertiserCategory.getCategoryId() + "','" + advertiserCategory.getCategoryName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得客户名称列表;"
	 */
	public void getCompanylist() {
		Consumer consumer = new Consumer();
		consumer.setStatus(0);
		List<Consumer> list = consumerDao.selectByConsumerLimit(consumer);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("[0,'无上级公司'],");
		for (int i = 0; i < list.size(); i++) {
			Consumer consumer1 = list.get(i);
			sb.append("['" + consumer1.getConsumerId() + "','" + consumer1.getConsumerName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得公司列表;"
	 */
	public void companylist() {
		List<Company> list = consumerDao.selectCompanyList();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Company Company = list.get(i);
			sb.append("['" + Company.getCompanyId() + "','" + Company.getCompanyName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得客户列表全部信息;
	 */
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);

			Consumer consumer = (Consumer) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Consumer.class);
			consumer.setExtLimit(limit);
			int count = consumerDao.selectByConsumerCount(consumer);
			List<Consumer> roleList = consumerDao.selectByConsumerLimit(consumer);

			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得对应的客户信息;
	 */
	public void getConsumerDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("consumerId");
			Consumer consumer = consumerDao.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除客户记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("consumerList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				Consumer consumer =  new Consumer();
				consumer.setConsumerPid(TypeConverterUtil.parseInt(idList[i]));
				int count = consumerDao.selectByConsumerCount(consumer);
				if(count==0){
					consumerDao.deleteById(TypeConverterUtil.parseInt(idList[i]));
					retObj.put("result", "success");
				}else{
					retObj.put("result", "use");
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	public ConsumerDao getConsumerDao() {
		return consumerDao;
	}
	public void setConsumerDao(ConsumerDao consumerDao) {
		this.consumerDao = consumerDao;
	}
}
