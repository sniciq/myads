package com.ku6ads.dao.impl.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.advflight.ProjectReason;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.struts.advflight.ProjectForm;

/**
 * @author xuxianan
 *
 */
public class ProjectDaoImpl extends BaseAbstractDao implements ProjectDao {

	@SuppressWarnings("unchecked")
	public List<ProjectCount> statisticProjectCount(Project project) {
		return getSqlMapClientTemplate().queryForList("advflight.project.statisticProjectCount", project);
	}

	@Override
	public ProjectForm selectProjectDetailById(int id) {
		return (ProjectForm) getSqlMapClientTemplate().queryForObject("advflight.project.selectProjectDetailById", id);
	}

	@Override
	public void saveProjectVerifyReason(ProjectReason projectReason) {
		getSqlMapClientTemplate().insert("advflight.project.insertProjectVerifyReason", projectReason);
	}

	@Override
	public ProjectReason selectProjectVerifyReasonById(int projectId) {
		return (ProjectReason) getSqlMapClientTemplate().queryForObject("advflight.project.selectVerifyReasonByProjectId", projectId);
	}

	@Override
	public void updateProjectReason(ProjectReason projectReason) {
		getSqlMapClientTemplate().update("advflight.project.updateProjectReason", projectReason);
	}

	@Override
	public Integer selectOveredBPCount(int projectId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advflight.project.selectOveredBPCount", projectId);
	}

}
