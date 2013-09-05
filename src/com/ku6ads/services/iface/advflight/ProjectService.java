package com.ku6ads.services.iface.advflight;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.advflight.ProjectReason;
import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 执行单Service
 * @author xuxianan
 *
 */
public interface ProjectService extends BaseServiceIface {

	/**
	 * 查询客户列表
	 * @return
	 */
	public List<Consumer> selectConsumer();

	/**
	 * 查询广告主列表
	 * @return
	 */
	public List<Advertiser> selectAdvertiser();

	/**
	 * 查询产品列表
	 * @return
	 */
	public List<Product> selectProduct();

	/**
	 * 查询产品线信息,返回集合
	 * @return
	 */
	public List<ProductLine> selectProductLine();

	/**
	 * 查询执行单列表
	 * @return
	 */
	public List<Project> selectProjectList();

	/**
	 * 根据ID查找到Project
	 * @return
	 */
	public Project selectById(int projectId);

	/**
	 * 对Project进行更新
	 * @return
	 */
	public void update(Project project);

	/**
	 * 更新执行单、排期包，排期的状态值
	 * @return
	 */
	public void update(int id, Integer bussinessStatus);

	public List<ProjectCount> statisticProjectCount(Project project);

	/**
	 * save执行单审核失败原因
	 */
	public void saveProjectReason(ProjectReason projectReason);

	/**
	 * 查询执行单审核失败原因
	 * @param projectId
	 * @return
	 */
	public ProjectReason selectProjectReasonById(int projectId);
	
	/**
	 * 更新执行单审核失败原因
	 * @param projectReason
	 */
	public void updateProjectReason(ProjectReason projectReason);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteProject(int projectId) throws Exception;
}
