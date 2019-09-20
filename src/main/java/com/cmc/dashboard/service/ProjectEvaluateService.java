package com.cmc.dashboard.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.cmc.dashboard.dto.EvaluateCommentDTO;
import com.cmc.dashboard.dto.EvaluateDTO;

import com.cmc.dashboard.dto.DuMonitoringDTO;

import com.cmc.dashboard.dto.ProjectEvaluateDTO;
import com.cmc.dashboard.dto.ProjectEvaluateWeekDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.model.ProjectEvaluate;
import com.cmc.dashboard.model.ProjectEvaluateComment;
import com.cmc.dashboard.model.ProjectEvaluateLevel;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;

public interface ProjectEvaluateService {
	
	public ProjectEvaluate saveProjectEvaluate(ProjectEvaluateDTO projectEvaluateDTO);
	
	public Optional<ProjectEvaluateLevel> findById(int id);
	
	public List<UserInfoDTO> getUserEvaluateByProject(int projectId,Date startDate);
	
	public ProjectEvaluate getUserEvaluateOfProjectByWeek (int projectId,String userName,Date startDate);
	
	public List<EvaluateDTO>  getEvaluateProjects(int projectId,int month,int year);
	
	public QmsUser  getMemberEvluateProject(String userName,int projectId);
	
	public User getMemberEvluateOfProject(int userId,int projectId);
	
	public ProjectEvaluate save(ProjectEvaluate projectEvaluate);
	
	public ProjectEvaluate createProjectEvaluate(String subject,int qualityId,int deliveryId,int processId,int projectId,String comment
			, String userName, String startDate,String endDate);
	

	public ProjectEvaluateComment saveComment(ProjectEvaluateComment projectEvaluateComment);
	
	public List<EvaluateCommentDTO> getListCommentEvaluate(int id);
	
	public EvaluateDTO  getEvaluateProject(ProjectEvaluate pe);

	public List<DuMonitoringDTO> getListMonitoring(Date startDate, Date endDate, List<String> groupId);
	
	public List<DuMonitoringDTO> getAllMonitoring(Date startDate, Date endDat);
	
	public String getEvaluateLevel(int id);

}
