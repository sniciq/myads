package com.ku6ads.services.impl.advflight;

import java.util.List;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.advflight.ProjectReason;
import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.dao.iface.sysconfig.AdvertiserDao;
import com.ku6ads.dao.iface.sysconfig.ConsumerDao;
import com.ku6ads.dao.iface.sysconfig.ProductDao;
import com.ku6ads.dao.iface.sysconfig.ProductLineDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.ProjectService;
import com.ku6ads.util.TypeConverterUtil;

/**
 * @author xuxianan
 *
 */
public class ProjectServiceImpl extends BaseAbstractService implements ProjectService {

	private ConsumerDao consumerDao;
	private AdvertiserDao advertiserDao;
	private ProductDao productDao;
	private ProductLineDao productLineDao;
	private BookPackageDao bookPackageDao;
	private BookDao bookDao;
	private BookPackageService bookPackageService;
	
	public List<Consumer> selectConsumer() {
		return consumerDao.selectConsumer();
	}

	public List<Advertiser> selectAdvertiser() {
		return advertiserDao.selectAdvertiser();
	}

	public List<Product> selectProduct() {
		return productDao.selectProduct();
	}

	public List<ProductLine> selectProductLine() {
		return productLineDao.selectProductLine();
	}

	@SuppressWarnings("unchecked")
	public List<Project> selectProjectList() {
		return ((ProjectDao) getBaseDao()).selectByEntity(null);
	}
	
	public Project selectById(int ProductId){
		return (Project) getBaseDao().selectById(ProductId);
	}
	
	public void update(Project project){
		getBaseDao().updateById(project);
	}
	
	/**
	 * 更新执行单、排期包，排期的状态值
	 * @return
	 */
	public void update(int projectId,Integer bussinessStatus) {
		
		Project project = selectById(projectId);
		BookPackage bp = new BookPackage();
		bp.setProjectId(projectId);
		List<BookPackage> bookPackageList = bookPackageDao.selectByProjectId(bp);
		Book bo = new Book();
		bo.setProjectId(projectId);
		List<Book> bookList = bookDao.selectByProjectId(bo);

		project.setBussinessStatus(bussinessStatus);
		update(project);	
		if(bookPackageList.size() > 0) {
			for(BookPackage bookPackage : bookPackageList)
			{
				bookPackage.setBussinessStatus(bussinessStatus);
				bookPackageDao.updateByProjectId(bookPackage);
			}
		}
		
		if(bookList.size() > 0) {
			for(Book book : bookList)
			{
				book.setBussinessStatus(bussinessStatus);
				bookDao.updateByProjectId(book);
			}
		}
	}
	
	@Override
	public boolean deleteProject(int projectId)  throws Exception {
		// 1. 得到执行单(用于判断执行单状态,执行中的执行单不满足删除条件,需编辑排期信息后在执行删除);
		//2. 正在执行中的执行单中的已过的排期包不能删除
		//3. 未执行的执行单可以删除
		boolean flag = true;
		if(((ProjectDao) getBaseDao()).selectOveredBPCount(projectId).intValue() <= 0)//查询已经执行的排期包数量--排期包
			deleteById(projectId);
		else 
			flag = false;
		
		Project projectEty = (Project) getBaseDao().selectById(projectId);
		BookPackage bPackage = new BookPackage();
		bPackage.setProjectId(projectId);
		bPackage.setStatus(0);
		List<BookPackage> bookPackageList = bookPackageDao.selectByProjectId(bPackage);//得到所有的排期包
		for(int j = 0; j < bookPackageList.size(); j++){
			bookPackageService.deleteBookPackage(bookPackageList.get(j).getId(), projectEty);
		}
		
		return flag;
	}

	public List<ProjectCount> statisticProjectCount(Project project) {
		return ((ProjectDao) getBaseDao()).statisticProjectCount(project);
	}
	
	public ConsumerDao getConsumerDao() {
		return consumerDao;
	}

	public void setConsumerDao(ConsumerDao consumerDao) {
		this.consumerDao = consumerDao;
	}

	public AdvertiserDao getAdvertiserDao() {
		return advertiserDao;
	}

	public void setAdvertiserDao(AdvertiserDao advertiserDao) {
		this.advertiserDao = advertiserDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public ProductLineDao getProductLineDao() {
		return productLineDao;
	}

	public void setProductLineDao(ProductLineDao productLineDao) {
		this.productLineDao = productLineDao;
	}

	public BookPackageDao getBookPackageDao() {
		return bookPackageDao;
	}

	public void setBookPackageDao(BookPackageDao bookPackageDao) {
		this.bookPackageDao = bookPackageDao;
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public void saveProjectReason(ProjectReason projectReason) {
		((ProjectDao) getBaseDao()).saveProjectVerifyReason(projectReason);
	}

	@Override
	public ProjectReason selectProjectReasonById(int projectId) {
		return ((ProjectDao) getBaseDao()).selectProjectVerifyReasonById(projectId);
	}

	@Override
	public void updateProjectReason(ProjectReason projectReason) {
		((ProjectDao) getBaseDao()).updateProjectReason(projectReason);
	}

	public BookPackageService getBookPackageService() {
		return bookPackageService;
	}

	public void setBookPackageService(BookPackageService bookPackageService) {
		this.bookPackageService = bookPackageService;
	}

}
