package com.ku6ads.struts.advflight;

import java.util.List;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.AdvFlightService;
import com.ku6ads.services.iface.advflight.AdvMaterialService;
import com.ku6ads.services.iface.advflight.AdvRelationBookService;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.services.iface.advflight.BookService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告活动
 * @author liujunshi
 *
 */
public class AdvFlightAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private AdvFlightService advFlightService;
	private AdvRelationBookService advRelationBookService;
	private AdvMaterialService advMaterialService;
	private AdvertisementService advertisementService;
	private BookService BookService;

	private Logger logger = Logger.getLogger(AdvFlightAction.class);
	
	/**
	 * 预投放
	 */
	public void save(){
		int advId = TypeConverterUtil.parseInt(ServletActionContext.getRequest().getParameter("id"));
	
			AdvFlight advFlight = new AdvFlight();
			advFlight.setAdvId(advId);
			advFlight.setFlightstatus(AdvFlight.STATUS_FLIGHT_WILL);
			saveAdvFlight(advFlight);

	}
	
	/**
	 * 
	 * @param advFlight
	 */
	public void saveAdvFlight(AdvFlight advFlight) {
		//logger.info("into AdvFlightAction saveAdvFlight method.");
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		boolean flag = false;
		try {
			int advId = advFlight.getAdvId();
			
			if(advId==0){
				obj.put("result", "advfail");
				AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
				return;
			}
			// 查看投放状态
			if(advFlight.getFlightstatus()!=AdvFlight.STATUS_FLIGHT_WILL){
				List list = advFlightService.selectByEntity(advFlight);
				if(!list.isEmpty()){
					obj.put("result", "multiple");
					AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
					return;
				}
			}
			//如果是预先投放查看是否已经投放
			if(advFlight.getFlightstatus()==AdvFlight.STATUS_FLIGHT_WILL){
				advFlight.setFlightstatus(AdvFlight.STATUS_FIGHTING);
				List listFlighting = advFlightService.selectByEntity(advFlight);
				if(!listFlighting.isEmpty()){
					obj.put("result", "flighting");
					AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
					return;
				}
				//将投放状态还原 继续执行
				advFlight.setFlightstatus(AdvFlight.STATUS_FLIGHT_WILL);
			}

			//取得广告和排期的关系
			AdvRelationBook advRelationBook = new AdvRelationBook();
			advRelationBook.setAdvertisementId(advId);
			List<AdvRelationBook> AdvRelationBookList = advRelationBookService.selectByProperty(advRelationBook);
			
			if(AdvRelationBookList!=null &&!AdvRelationBookList.isEmpty()){
				//取得广告和物料的的关系
				AdvMaterial advMaterial = new AdvMaterial();
				advMaterial.setAdvId(advId);
				List<AdvMaterial> advMaterialList = advMaterialService.selectByLimit(advMaterial);
				if(advMaterialList!=null||!advMaterialList.isEmpty()){
					for(int i=0;i<advMaterialList.size();i++){
						if(advMaterialList.get(i).getMaterialId()==null||(advMaterialList.get(i).getPath()==null&&advMaterialList.get(i).getTextContent()==null)){
							flag=true;
						}
					}
				}else{
					flag = true;
					obj.put("result", "advMaterialFail");
				}
				
				if(!flag){
					UserInfoEty user = this.getLoginUser();
					Advertisement advertisement = (Advertisement) advertisementService.selectById(advId);
					String isSuccess = advFlightService.insert(advertisement,AdvRelationBookList, advMaterialList,user.getUsername(),advFlight.getFlightstatus());
					
					if("true".equalsIgnoreCase(isSuccess)){
						obj.put("result", "success");
					}
					else if(AdvFlight.BINDFAIL.equalsIgnoreCase(isSuccess)){
						obj.put("result", "bindFail");
					}
					else if(AdvFlight.MEMORYFAIL.equalsIgnoreCase(isSuccess)){
						obj.put("result", "memoryfail");
					}
					else{
						obj.put("result", "codeFail");
					}
				}else{
					obj.put("result", "advMaterialFail");
				}
				
			}else{
					obj.put("result", "BookFail");
			}	
			//取广告条Id值
		
		}catch (Exception e) {
			logger.error("广告ID错误", e);
			obj.put("result", "fail");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	
	/**
	 * 获取点击地址
	 */
	public void clickAdress(){
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		int advId = TypeConverterUtil.parseInt(ServletActionContext.getRequest().getParameter("id"));
		if(advId == 0){
			retObj.put("result", "err");
			AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
			return;
		}
		
		//取得广告和排期的关系
		AdvRelationBook advRelationBook = new AdvRelationBook();
		advRelationBook.setAdvertisementId(advId);
		List<AdvRelationBook> AdvRelationBookList = advRelationBookService.selectByProperty(advRelationBook);
		
		//判断是否关联排期
		if(AdvRelationBookList==null ||AdvRelationBookList.isEmpty()){
			retObj.put("result", "bookFail");	
			AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
			return;
		}
		
		try{	
			String result = advFlightService.makeClickAdress(AdvRelationBookList, advId);
			//result = result.replaceAll("&", " &");
			retObj.put("result", result);	
		}catch(Exception e){
			logger.error(e);
			retObj.put("result","err");	
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	
	/**
	 * 预览
	 */
	@SuppressWarnings("unused")
	public String  getAdvFlightDetail() {
		
		String code ="fail";
		String PosCode="fail";
		try {
			int advId = TypeConverterUtil.parseInt(ServletActionContext.getRequest().getParameter("id"));
			AdvRelationBook advRelationBook = new AdvRelationBook();
			advRelationBook.setAdvertisementId(advId);
			List<AdvRelationBook> AdvRelationBookList = advRelationBookService.selectByProperty(advRelationBook);
			int bookId = AdvRelationBookList.get(0).getBookId();
			Book book=(Book) BookService.selectById(bookId);
			int bookPackageId = book.getBookPackageId();
			int projectId = book.getProjectId();
			AdvFlight advFlight = new AdvFlight();
			advFlight.setAdvId(advId);
			advFlight.setBookPackageId(bookPackageId);
			advFlight.setProjectId(projectId);
			advFlight.setAdvId(advId);
			List list = advFlightService.selectByEntity(advFlight);
			if(list!=null&&!list.isEmpty()){
				advFlight =(AdvFlight)list.get(list.size()-1);
				code = advFlight.getFlightcode();
			}
			//获取广告位代码
			int advBarId = book.getAdvbarId(); 
			PosCode = advFlightService.getPosCode(advBarId);
			if(PosCode!=""&&PosCode!= null){
//				System.out.println(PosCode);
//				System.out.println(code);
				PosCode = PosCode.replace("[%item1%]", code);
			}
			
		} catch (Exception e) {
			code = "获取代码失败";
			logger.error("", e);
			PosCode="预览失败~";
		}
		
		System.out.println(PosCode);
		ServletActionContext.getRequest().setAttribute("code", PosCode);
		return "success";
	}

	/**
	 * 投放停止
	 */
	public void changeStatus(){
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		int advId = TypeConverterUtil.parseInt(ServletActionContext.getRequest().getParameter("id"));
		if(advId !=0){
			// 查看投放状态
			try{
				AdvFlight advFlight = new AdvFlight();
				advFlight.setAdvId(advId);
				advFlight.setFlightstatus(AdvFlight.STATUS_FIGHTING);
				List<AdvFlight> list = advFlightService.selectByEntity(advFlight);
				logger.error("停止投放list size "+list.size());
				if(list.isEmpty()){
					obj.put("result", "multiple");
					AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
					return;
				}
				advFlight.setFlightstatus(AdvFlight.STATUS_FIGHT_STOP);
				advFlightService.stopFlight(advFlight,list);
				obj.put("result", "success");
			}catch(RuntimeException e){
				obj.put("result", AdvFlight.MEMORYFAIL);
				logger.error("更新投放状态失败", e);
			}catch(Exception e){
				obj.put("result", "err");
				logger.error("更新投放状态失败", e);
			}
		
		}else{
			obj.put("result", "advIdFail");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 进行投放。判断之前已经进行了预选投放
	 */
	public void filghtingStatus(){
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		int advId = TypeConverterUtil.parseInt(ServletActionContext.getRequest().getParameter("id"));
		logger.error("into AdvFlightAction filghtingStatus method.");
		if(advId !=0){
			// 查看投放状态
			try{
				AdvFlight advFlight = new AdvFlight();
				advFlight.setAdvId(advId);
				advFlight.setFlightstatus(AdvFlight.STATUS_FLIGHT_WILL);
				List list = advFlightService.selectByEntity(advFlight);
				//如果之前没有进行与投放，而直接进行投放
				if(list.isEmpty()){
					advFlight.setFlightstatus(AdvFlight.STATUS_FIGHTING);
					//如果已经是投放中
					list = advFlightService.selectByEntity(advFlight);
					if(!list.isEmpty()){
						obj.put("result", "multiple");
						AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
						return;
					}
					this.saveAdvFlight(advFlight);
					return;
				}
				advFlight.setFlightstatus(AdvFlight.STATUS_FIGHTING);
				advFlight.setFlightstatusBack(AdvFlight.STATUS_FLIGHT_WILL);
				advFlightService.updateStatus(advFlight);
				obj.put("result", "success");
			}catch(RuntimeException e){
				obj.put("result", AdvFlight.MEMORYFAIL);
				logger.error("更新投放状态失败", e);
			}catch(Exception e){
				obj.put("result", "err");
				logger.error("更新投放状态失败", e);
			}
		
		}else{
			obj.put("result", "fail");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	
	//++++++++++++++++GETTER SETTER++++++++++++++++++//


	

	public AdvFlightService getAdvFlightService() {
		return advFlightService;
	}

	public void setAdvFlightService(AdvFlightService advFlightService) {
		this.advFlightService = advFlightService;
	}



	public AdvRelationBookService getAdvRelationBookService() {
		return advRelationBookService;
	}

	public void setAdvRelationBookService(
			AdvRelationBookService advRelationBookService) {
		this.advRelationBookService = advRelationBookService;
	}

	public AdvMaterialService getAdvMaterialService() {
		return advMaterialService;
	}

	public void setAdvMaterialService(AdvMaterialService advMaterialService) {
		this.advMaterialService = advMaterialService;
	}



	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}



	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	public BookService getBookService() {
		return BookService;
	}

	public void setBookService(BookService bookService) {
		BookService = bookService;
	}
	
}
