/**
 * dashboard-phase2-backend- - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import com.cmc.dashboard.dto.ProjectCssRestResponse;
import com.cmc.dashboard.model.ProjectCss;

/**
 * @author: ngocd
 * @Date: Apr 3, 2018
 */
public interface ProjectCssService {
	/**
	 * Get CSS of project by projectId
	 * @param projectId
	 * @return ProjectCssRestResponse 
	 * @author: ngocd
	 */
	public ProjectCssRestResponse getByProjectId(int projectId);

	/**
	 * get css of project by id
	 * @param projectCssId
	 * @return ProjectCss 
	 * @author: ngocd
	 */
	public ProjectCss getById(int projectCssId);

	/**
	 * create a css of project
	 * @param projectCss
	 * @return ProjectCssRestResponse 
	 * @author: ngocd
	 */
	public ProjectCssRestResponse create(ProjectCss projectCss);

	/**
	 * delete a css of project
	 * @param projectCssId
	 * @return ProjectCssRestResponse 
	 * @author: ngocd
	 */
	public ProjectCssRestResponse delete(int projectCssId);
	
	/**
	 * validate a css to create
	 * @param projectCss
	 * @return boolean 
	 * @author: ngocd
	 */
	public boolean validate(ProjectCss projectCss);
	
	/**
	 * check a css is exist
	 * @param projectCssId
	 * @return boolean 
	 * @author: ngocd
	 */
	public boolean checkExist(int projectCssId);
	//PNTHANH
	public ProjectCss update(ProjectCss projectCss);
}

