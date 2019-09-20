package com.cmc.dashboard.service;

import com.cmc.dashboard.dto.ListProjectTaskDTO;
import com.cmc.dashboard.dto.ProjectTaskFilterDTO;

import java.util.List;

import com.cmc.dashboard.dto.IssueJiraDTO;

/**
 * @author: tvdung
 * @Date: Feb 28, 2018
 */
public interface ProjectTaskService {
	
	/**
	 * get list project task
	 * @param projectId
	 * @return List<ProjectTaskDTO>
	 * @author: tvdung
	 */
	public ListProjectTaskDTO getListTask(int projectId, String search, int page, String sort, String typeSort,ProjectTaskFilterDTO filter);
	
	
	 public boolean saveIssueJira(List<IssueJiraDTO> listIssue, int projectId);
}
