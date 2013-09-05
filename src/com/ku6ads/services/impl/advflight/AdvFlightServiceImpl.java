package com.ku6ads.services.impl.advflight;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advflight.AdvFlight;
import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Material;
import com.ku6ads.dao.iface.advflight.AdvFlightDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.advflight.AdvFlightService;
import com.ku6ads.services.iface.advflight.AdvFlightTransService;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.BookService;
import com.ku6ads.services.iface.advflight.MaterialService;
import com.ku6ads.util.PropertiesUtils;

/**
 * 广告投放ServiceImpl
 * 
 * @author liujunshi
 * 
 */

public class AdvFlightServiceImpl extends BaseAbstractService implements AdvFlightService {

	private BookService BookService;
	private AdvbarService advbarService;
	private MaterialService materialService;
	private AdvFlightDao advFlightDao;
	private SqlMapClientTemplate sqlMapClientTemplate;
	private AdvertisementService advertisementService;
	private BookPackageService bookPackageService;
	private AdvpositionService advpositionService;
	private AdvFlightTransService advFlightTransService;

	public static final String MATERIAL_PATH = "MPATH";
	public static final String DIR_URL = "DIRURL";
	public static final String MONITION = "MONITION";
	public static final String EXPOSURE = "EXPOSURE";
	public static final String ITEM = "ITEM";
	public static final String AFTER = "\\%\\]";
	public static final String BEFORE = "\\[\\%";
	public static final String ThirdDirectName = "direct";
	public static final String LINE = "LINE";
	public static final String IMAGE = "IMAGE";
	public static final String OP_ADD = "add";
	public static final String OP_DELETE = "del";

	private Logger logger = Logger.getLogger(AdvFlightServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ku6ads.services.iface.advflight.AdvFlightService#insert(com.ku6ads
	 * .dao.entity.advflight.Advertisement, java.util.List, java.util.List,
	 * java.lang.String)
	 */
	public String insert(Advertisement advertisement,List<AdvRelationBook> AdvRelationBookList,List<AdvMaterial> advMaterialList, String user,int flightStatus) {
		String isSuccess = "false";
		//logger.info("into AdvFlightServiceImpl insert method.");	
		if (AdvRelationBookList == null || advMaterialList == null) {
			return isSuccess;
		}
		
		List<AdvFlight> advFlightList = new ArrayList<AdvFlight>();
		
		Book book = new Book();
		String advbarCode = null;
		Date maxTime = new Date();
		for (int i = 0; i < AdvRelationBookList.size(); i++) {
			int bookId = AdvRelationBookList.get(i).getBookId();
			book = (Book) BookService.selectById(bookId);
			//获得排期最后时间
			if(maxTime.before(book.getEndTime())){
				maxTime = book.getEndTime();
			}
			int advbarId = book.getAdvbarId();
			Advbar advbar = (Advbar) advbarService.selectById(advbarId);
			advbarCode = advbar.getCode();
			if (advbarCode != null) {
				// 将物料地址放入代码中
				advbarCode = makeMateriialCode(advbarCode, advMaterialList,advertisement);
				// 替换跳转代码
				advbarCode = makeDirectCode(advbarCode, advertisement,book);
				// 替换第三方监控地址
				advbarCode = addMonitionCode(advbarCode, advertisement);
				// 替换曝光地址
				advbarCode = makeExposure(advbarCode, advertisement,book);
				
				AdvFlight advFlight = new AdvFlight();
				// 将排期信息出入投放表中
				BeanUtils.copyProperties(book, advFlight);
				BeanUtils.copyProperties(advertisement, advFlight);
				advFlight.setId(null);		 
				//advFlight.setAdvActiveId(advertisement.getAdvActiveId());
				advFlight.setAreaDirect(book.getAreaDirectCode());
				advFlight.setAdvposId(advbar.getPosId());
				//TODO 将播放器代码XML和JSON分开
				if(advbarCode.contains("[%"+LINE+"%]")){
					String[] tems = advbarCode.split(BEFORE+LINE+AFTER);
					advbarCode = tems [0];
					String advbarCodeJson = tems [1];
					advFlight.setFlightcodejson(advbarCodeJson.trim());
				}
				advFlight.setFlightcode(advbarCode.trim());
				advFlight.setAdvId(advertisement.getId());
				advFlight.setBookId(bookId);
				advFlight.setFlightstatus(flightStatus);
				advFlight.setCreator(user);
				advFlight.setCreateTime(new Date());
//				//排期包绑定
//				if(book.getScrBPackageId()!=null){
//					Integer bindingFid = bindingFlight(advFlight);
//					//绑定的排期包Id错误。
//					if(bindingFid == null){
//						return AdvFlight.BINDFAIL;
//					}
//					advFlight.setScrBPackageId(bindingFid);
//				}
				// 加入LIST
				advFlightList.add(advFlight);
			} else {
				// 广告代码为空指针异常
				NullPointerException e = new NullPointerException("广告代码为空ID=" + advbarId);
				logger.error(e);
				return isSuccess = "false";
			}
		}
		// TODO 插入投放表并加入到内存中
		List<Integer> fidList = new ArrayList<Integer>();
		try {
			fidList = advFlightTransService.insertList(advFlightList,maxTime);
			
			if(flightStatus == AdvFlight.STATUS_FIGHTING){
				for(int fid :fidList){
					advFlightTransService.pushMemory(fid, OP_ADD);
				}
			}
			isSuccess = "true";
		} catch (Exception e) {
			for(int fid :fidList){
				advFlightDao.deleteById((Integer)fid);
			}
			logger.error("推入内存失败,事务回滚",e);
			isSuccess="memoryfail";
		}

		return isSuccess;

	}

	/**
	 * 替换标签
	 * @param advBarCode
	 * @param advMaterialList
	 * @return
	 */
	private String makeMateriialCode(String advBarCode, List<AdvMaterial> advMaterialList,Advertisement advertisement) {

		Map<Integer, String> map = new HashMap<Integer, String>();
		//取得广告条模板类型
		//如果是IMAGE FLASH兼容的模板
		if(advBarCode.startsWith("[%IMAGE%]")){
			advBarCode = advBarCode.replace("[%IMAGE%]", "");
			int mid = advMaterialList.get(0).getMaterialId();
			String mtype = ((Material)materialService.selectById(mid)).getMeterialType();
			String[] tems = advBarCode.split(BEFORE+LINE+AFTER);
			
			if("IMAGE".equalsIgnoreCase(mtype)){
				advBarCode = tems[tems.length-1];
			}else if("FLASH".equalsIgnoreCase(mtype)){
				advBarCode = tems[0];
			}
		}
		
		// 构造SNO PATH的map
		for (int i = 0; i < advMaterialList.size(); i++) {
			map.put(advMaterialList.get(i).getsNumber(),advMaterialList.get(i).getTextContent()==null?advMaterialList.get(i).getPath():advMaterialList.get(i).getTextContent());
		}

		for (int i = 0; i < advMaterialList.size(); i++) {

			advBarCode = advBarCode.replaceAll(BEFORE + ITEM + (i + 1) + AFTER, map.get(i + 1));
		}

		return advBarCode;
	}

	/**
	 * 替换跳转地址和第三方监控地址
	 * @param advBarCode
	 * @param DirectURL
	 * @return
	 */
	private String makeDirectCode(String advBarCode, Advertisement advertisement,Book book) {
		if(advertisement.getRedirect()==null){
			advBarCode = advBarCode.replaceAll(BEFORE + DIR_URL + AFTER,"");
			return advBarCode;
		}
		int advBarId = book.getAdvbarId();
		Advbar  advbar  = (Advbar)advbarService.selectById(advBarId);
		int advposId = advbar.getPosId();
		
		//放在配置文件中
		String clickAdress = "http://kal.ku6.com/click.htm?";
		StringBuffer sbRes = new StringBuffer();
		
		sbRes.append(clickAdress)
		.append("channelId=").append(book.getChannelId())
		.append("&advpositionId=").append(advposId)
		.append("&barId=").append(book.getAdvbarId())
		.append("&advactiveId=").append(advertisement.getAdvActiveId())
		.append("&advertisementId=").append(advertisement.getId())
		.append("&projectId=").append(book.getProjectId())
		.append("&barpriceId=").append(book.getPriceId())
		.append("&format=").append(book.getFormat())
		.append("&saletype=").append(book.getSaleType())
		.append("&bookpackageId=").append(book.getBookPackageId())
		.append("&redirect=").append(advertisement.getRedirect());

		//不加入锚点
		advBarCode = advBarCode.replaceAll(BEFORE + DIR_URL + AFTER, sbRes.toString());
	
		
		return advBarCode;
	}
	
	/**
	 * 加入第三方检测地址
	 * @param advBarCode
	 * @param advertisement
	 * @return
	 */
	private String addMonitionCode(String advBarCode, Advertisement advertisement) {
		
		advBarCode = advBarCode.replaceAll(BEFORE + MONITION + AFTER, advertisement.getMonition()==null?"":advertisement.getMonition());

		return advBarCode;
	}
	/**
	 * 替换曝光地址
	 * @param advBarCode
	 * @param DirectURL
	 * @return
	 */
	private String makeExposure (String advBarCode, Advertisement advertisement,Book book) {
	
		int advBarId = book.getAdvbarId();
		Advbar  advbar  = (Advbar)advbarService.selectById(advBarId);
		int advposId = advbar.getPosId();
		
		//放在配置文件中
		String exposure = "http://kal.ku6.com/pv.htm?";
		StringBuffer sbRes = new StringBuffer();
		
		sbRes.append(exposure)
		.append("channelId=").append(book.getChannelId())
		.append("&advpositionId=").append(advposId)
		.append("&advbarId=").append(book.getAdvbarId())
		.append("&advactiveId=").append(advertisement.getAdvActiveId())
		.append("&advertisementId=").append(advertisement.getId())
		.append("&projectId=").append(book.getProjectId())
		.append("&bookpackageId=").append(book.getBookPackageId())
		.append("&bookId=").append(book.getId())
		.append("&barpriceId=").append(book.getPriceId())
		.append("&format=").append(book.getFormat())
		.append("&saletype=").append(book.getSaleType());

		//不加入锚点
		advBarCode = advBarCode.replaceAll(BEFORE + EXPOSURE + AFTER, sbRes.toString());
	
		return advBarCode;
	}



	/*
	 * 更改投放状态
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.AdvFlightService#updateStatus(com.ku6ads.dao.entity.advflight.AdvFlight)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer stopFlight(AdvFlight advFlight,List<AdvFlight> advflightList) throws Exception {
		int res = 0;
		 int ERRBookId= 0;
		try {
			//如果是停止投放 清除缓存
		    res = advFlightDao.updateStatus(advFlight);
		    //List<AdvFlight> list = advFlightDao.selectByEntity(advFlight);
		    ERRBookId = advflightList.get(advflightList.size()-1).getBookId();
		    //如果是停止直接清缓存.不是做修改
			if(advFlight.getFlightstatus() == AdvFlight.STATUS_FIGHT_STOP){				
				for(int i=0;i<advflightList.size();i++){
					//清除内存
					advFlightTransService.pushMemory(advflightList.get(i).getId(), OP_DELETE);
					//System.out.println(i);
				}
			}
			if(advFlight.getFlightstatus() == AdvFlight.STATUS_FIGHTING){				
				for(int i=0;i<advflightList.size();i++){
					//推入内存内存
					//TODO
					advFlightTransService.pushMemory(advflightList.get(i).getId(), OP_DELETE);
					advFlightTransService.pushMemory(advflightList.get(i).getId(), OP_ADD);
				}
			}
		    
			
		} catch (Exception e) {
			logger.error("清除投放缓存失败 投放广告ID为： "+ advFlight.getAdvId() +"排期ID为: "+ ERRBookId, e);
			throw e;
		}
		return res;
	}
	
	public Integer updateStatus(AdvFlight advFlight) throws Exception {
		int res = 0;
		 int ERRBookId= 0;
		try {
			//如果是停止投放 清除缓存
		    res = advFlightDao.updateStatus(advFlight);
		    List<AdvFlight> list = advFlightDao.selectByEntity(advFlight);
		    ERRBookId = list.get(list.size()-1).getBookId();
		    //如果是停止直接清缓存.不是做修改
			if(advFlight.getFlightstatus() == AdvFlight.STATUS_FIGHT_STOP){				
				for(int i=0;i<list.size();i++){
					//清除内存
					advFlightTransService.pushMemory(list.get(i).getId(), OP_DELETE);
					//System.out.println(i);
				}
			}
			if(advFlight.getFlightstatus() == AdvFlight.STATUS_FIGHTING){				
				for(int i=0;i<list.size();i++){
					//推入内存内存
					//TODO
					advFlightTransService.pushMemory(list.get(i).getId(), OP_DELETE);
					advFlightTransService.pushMemory(list.get(i).getId(), OP_ADD);
				}
			}
		    
			
		} catch (Exception e) {
			logger.error("清除投放缓存失败 投放广告ID为： "+ advFlight.getAdvId() +"排期ID为: "+ ERRBookId, e);
			throw e;
		}
		return res;
	}
	/**
	 * 根据排期包Id 更改优先级并修改缓存
	 * @param BookPackageId
	 * @return
	 */
	public boolean modifyByPackageId(int priority, int bookPackageId){
		boolean flag = true;
		AdvFlight advFlight = new AdvFlight();
		advFlight.setPriority(priority);
		advFlight.setBookPackageId(bookPackageId);
		advFlight.setFlightstatus(AdvFlight.STATUS_FIGHT_COM);
		
		try{
			advFlightDao.modifyByPackageId(advFlight);
			
		}catch (Exception e) {
			logger.error(e);
			flag = false;
		}
		//修改缓存
		try{
			List<AdvFlight> AdvFlightList = advFlightDao.selectByPackageId(advFlight);
			for(AdvFlight advflight:AdvFlightList){
				advFlightTransService.pushMemory(advflight.getId(),OP_DELETE);
				advFlightTransService.pushMemory(advflight.getId(),OP_ADD);
			}
		}catch(Exception e){
			logger.error(e);
		}
		return flag;
	}
	/**
	 * 获得点击地址 按排期包展示
	 * @param AdvRelationBookList
	 * @return
	 */
	public String makeClickAdress(List<AdvRelationBook> AdvRelationBookList,int advId)throws Exception{
		//放在配置文件中
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String clickAdress = PropertiesUtils.getValue("clickAdress");
		
		Book book_previous = new Book();
		Advbar Advbar;
		StringBuffer sbRes = new StringBuffer();
		Advertisement advertisement = new Advertisement();
		BookPackage bookPackage = new BookPackage();

		try{
			for(int i = 0;i<AdvRelationBookList.size();i++){
				int bookId = AdvRelationBookList.get(i).getBookId();
				Book book = (Book)BookService.selectById(bookId);
				//如果相同排期包下的排期
				if(i>0&&book.getBookPackageId().equals(book_previous.getBookPackageId())){
					book_previous = book;
					continue;
				}
				book_previous = book;
				int advBarId = book.getAdvbarId();
				
				Advbar  = (Advbar)advbarService.selectById(advBarId);
				int advposId = Advbar.getPosId();
				//获得广告
				advertisement = (Advertisement)advertisementService.selectById(advId);
				//获得排期包拼名字
				bookPackage =(BookPackage)bookPackageService.selectById(book.getBookPackageId());
				
				sbRes.append(bookPackage.getChannelName()).append(bookPackage.getAdvbarName()).append(bookPackage.getUseTypeName()).append("<br>")
				.append(clickAdress)
				.append("channelId=").append(book.getChannelId())
				.append("&advpositionId=").append(advposId)
				.append("&barId=").append(book.getAdvbarId())
				.append("&advactiveId=").append(advertisement.getAdvActiveId())
				.append("&advertisementId=").append(advId)
				.append("&projectId=").append(book.getProjectId())
				.append("&barpriceId=").append(book.getPriceId())
				.append("&format=").append(book.getFormat())
				.append("&saletype=").append(book.getSaleType())
				.append("&bookpackageId=").append(book.getBookPackageId())
				.append("&bookpackageId=").append(book.getBookPackageId())
				.append("&redirect=").append(advertisement.getRedirect())
				.append("&productId=").append(book.getScrBPackageId()==null?0:book.getScrBPackageId())
				.append("</br>");
			}
			
		
		}catch(Exception e){
			logger.error(e);
			//e.printStackTrace();
			throw e;
		}
		return sbRes.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advflight.AdvFlightService#getPosCode(int)
	 */
	@Override
	public String getPosCode(int advBarId) {
		Advbar advBar = (Advbar)advbarService.selectById(advBarId);
		Advposition advposition = (Advposition)advpositionService.selectById(advBar.getPosId());
		
		return advposition.getCode();
	}
	
	/**
	 * 绑定排期包投放
	 * @param advf
	 * @return 成功返回绑定排期包啊
	 */
	public Integer bindingFlight(AdvFlight advf){
		Integer bindingPid = advf.getScrBPackageId();
		AdvFlight bindingAdvFlight = new AdvFlight(); 
		bindingAdvFlight.setBookPackageId(bindingPid);
		bindingAdvFlight.setStartTime(advf.getStartTime());
		bindingAdvFlight.setStatus(AdvFlight.STATUS_FIGHTING);
		List<AdvFlight> list  = super.selectByEntity(bindingAdvFlight);
		
		if(list==null||list.isEmpty()){
			return null;
		}
		Integer bindingFid = list.get(0).getId();
		
		
		return bindingFid;
	}
	
	@Override
	public List<AdvFlight> getFlightList() {
		return advFlightDao.getFlightList();
	}
	
	/**
	 * 测试用main方法
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String s = "channelId=26&advpositionId=64&barId=73&advactiveId=47&advertisementId=100&projectId=63&barpriceId=117&format=1&saletype=1&bo";
		s = s.replaceAll("&", " &");
		System.out.println(s);
	}

	// ---------------------------GETTER/SERTTER-------------///
	public BookService getBookService() {
		return BookService;
	}

	public void setBookService(BookService bookService) {
		BookService = bookService;
	}

	public AdvbarService getAdvbarService() {
		return advbarService;
	}

	public void setAdvbarService(AdvbarService advbarService) {
		this.advbarService = advbarService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public AdvFlightDao getAdvFlightDao() {
		return advFlightDao;
	}

	public void setAdvFlightDao(AdvFlightDao advFlightDao) {
		this.advFlightDao = advFlightDao;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}

	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	public BookPackageService getBookPackageService() {
		return bookPackageService;
	}

	public void setBookPackageService(BookPackageService bookPackageService) {
		this.bookPackageService = bookPackageService;
	}

	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}

	public AdvFlightTransService getAdvFlightTransService() {
		return advFlightTransService;
	}

	public void setAdvFlightTransService(AdvFlightTransService advFlightTransService) {
		this.advFlightTransService = advFlightTransService;
	}

}
