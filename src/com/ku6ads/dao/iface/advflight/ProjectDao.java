package com.ku6ads.dao.iface.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.advflight.ProjectReason;
import com.ku6ads.struts.advflight.ProjectForm;

/**
 * 执行单DAO
 * @author xuxianan
 */
public interface ProjectDao extends BaseDao {

	public List<ProjectCount> statisticProjectCount(Project project);

	public ProjectForm selectProjectDetailById(int parseInt);

	/**
	 * save执行单审核失败原因
	 */
	public void saveProjectVerifyReason(ProjectReason projectReason);

	/**
	 * 查询执行单审核失败原因
	 * @param projectId
	 * @return
	 */
	public ProjectReason selectProjectVerifyReasonById(int projectId);

	/**
	 * 更新执行单审核失败原因
	 * @param projectReason
	 */
	public void updateProjectReason(ProjectReason projectReason);

	/**
	 * 查询已经执行的排期包数量--排期包
	 * @param projectId
	 * @return
	 */
	public Integer selectOveredBPCount(int projectId);
}
