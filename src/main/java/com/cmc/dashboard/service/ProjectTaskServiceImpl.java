package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.IssueJiraDTO;
import com.cmc.dashboard.dto.ListProjectTaskDTO;
import com.cmc.dashboard.dto.ProjectTaskDTO;
import com.cmc.dashboard.dto.ProjectTaskFilterDTO;
import com.cmc.dashboard.dto.WorklogDTO;
import com.cmc.dashboard.model.ProjectTask;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.repository.ProjectTaskListRepository;
import com.cmc.dashboard.repository.ProjectTaskRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;

/**
 * @author: tvdungL
 * @Date: Feb 28, 2018
 */
@Service
public class ProjectTaskServiceImpl implements ProjectTaskService{

	@Autowired
	ProjectTaskListRepository projectTaskList;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private WorkLogService workLogService;
	
	
	/* (non-Javadoc)
	 * @see com.cmc.dashboard.service.ProjectTaskService#getListTask(int)
	 */
	@Override
	public ListProjectTaskDTO getListTask(int projectId, String search, int page, String sort, String typeSort,ProjectTaskFilterDTO filter) {
		ListProjectTaskDTO listProjectTaskDTOs = new ListProjectTaskDTO();
		Sort sorts = new Sort(Sort.Direction.ASC, sort);
		if (Constants.SortType.DESCENDING.equals(typeSort)) {
			sorts = new Sort(Sort.Direction.DESC, sort);
		}
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE, sorts);
		List<String> lstAssignee = this.getListAssignee(filter.getAssignee(), projectId);
		List<String> lstStatus = this.getListStatus(filter.getStatus(), projectId);
		Page<Object> tasks = projectTaskList.getListProjectTask(projectId, search, lstAssignee, lstStatus, 
				filter.getMinstartDate(), filter.getMaxstartDate(), filter.getMindueDate(), filter.getMaxdueDate(), pageable);
		List<ProjectTaskDTO> lstTask = new ArrayList<>();
		for(Object task: tasks) {
			Object[] objects = (Object[])task;
			ProjectTaskDTO projectTaskDTO = new ProjectTaskDTO();
			projectTaskDTO.setTaskId(Integer.parseInt(objects[0].toString()));
			projectTaskDTO.setStatus(objects[1].toString());
			projectTaskDTO.setSubject(objects[2].toString());
			projectTaskDTO.setAssingee(objects[3].toString());
			projectTaskDTO.setStartDate(objects[4] == null ? "" : objects[4].toString());
			projectTaskDTO.setDueDate(objects[5] == null ? "" :objects[5].toString());
			projectTaskDTO.setPercentDone(objects[6] == null ? "" :objects[6].toString());
			projectTaskDTO.setEstimateTime(objects[7] == null ? 0 :Float.parseFloat(objects[7].toString()));
			lstTask.add(projectTaskDTO);
		}
		listProjectTaskDTOs.setNames(projectTaskList.getListName(projectId));
		listProjectTaskDTOs.setStatus(projectTaskList.getListStatus(projectId));
		listProjectTaskDTOs.setData(lstTask);
		listProjectTaskDTOs.setTotal(projectTaskList.getCountProjectTask(projectId, search,lstAssignee, lstStatus, 
				filter.getMinstartDate(), filter.getMaxstartDate(), filter.getMindueDate(), filter.getMaxdueDate()));
		return listProjectTaskDTOs;
	}
	
	/**
	 * 
	 * get list assignee
	 * @param names
	 * @param projectId
	 * @return List<String> 
	 * @author: CMC-GLOBAL
	 */
	private List<String> getListAssignee(String names, int projectId) {
		List<String> lstName = new ArrayList<>();
		if (names.isEmpty() || names == null) {
			lstName = projectTaskList.getListName(projectId);
		} else {
			String[] arrName = names.split(",");
			for (int i = 0; i < arrName.length; i ++) {
				lstName.add(arrName[i]);
			}
		}
		return lstName;
	}
	
	/**
	 * 
	 * get list status
	 * @param status
	 * @param projectId
	 * @return List<String> 
	 * @author: CMC-GLOBAL
	 */
	private List<String> getListStatus(String status, int projectId) {
		List<String> lstStatus = new ArrayList<>();
		if ("".equals(status)) {
			lstStatus = projectTaskList.getListStatus(projectId);
		} else {
			String[] arrName = status.split(",");
			for (int i = 0; i < arrName.length; i ++) {
				lstStatus.add(arrName[i]);
			}
		}
		return lstStatus;
	}
	
	@Override
	  public boolean saveIssueJira(List<IssueJiraDTO> listIssue, int projectId) {
		 CalculateClass cal = new CalculateClass();
		 SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
		 List<Integer> listIssueNew= new ArrayList<Integer>();
		 try {
		 for(IssueJiraDTO issue : listIssue)listIssueNew.add((int)issue.getId());
		  List<Integer> listIssueOld = userRepository.listIssueIdByProjectId(projectId);
		  List<Integer> listDelete = cal.differentNumber(listIssueOld, listIssueNew);
		  List<Integer> listInsert = cal.differentNumber(listIssueNew, listIssueOld);
		  String deleteString = listDelete.toString();
		  String insertString = listInsert.toString();
		  for(IssueJiraDTO issue : listIssue) {
			  String assignee = issue.getAssignee();
			  User user = userRepository.findByUserName(assignee);
			  if (insertString.indexOf(String.valueOf(issue.getId()))!=-1){
				  ProjectTask pt = new ProjectTask();
				  pt.setId((int)issue.getId());
				  if(user!=null)
				  pt.setAssigneeId(user.getUserId());
				  pt.setStatus(issue.getStatus());
				  pt.setIssueType(issue.getIssueType());
				  pt.setSubject(issue.getSubject());
				  pt.setPriority(issue.getPriority());
				  pt.setProjectId(projectId);
				  pt.setEstimateTime(issue.getEstimateTime());
				  pt.setPercentDone(0);
				  try {
					if(issue.getCreated()!=null) {
					pt.setCreatedAt(formatter.parse(issue.getCreated()));
					pt.setStartDate(formatter.parse(issue.getCreated()));
					}
					if(issue.getUpdated()!=null)
					pt.setUpdatedAt(formatter.parse(issue.getUpdated()));
					if(issue.getEndDate()!=null)
						pt.setEndDate(formatter.parse(issue.getEndDate()));
				        } catch (ParseException e) {
				          	e.printStackTrace();
				          }
				  try {
				      projectTaskRepository.save(pt) ;
				      }catch(Exception e) {
					  pt.setEndDate(new Date());
					  projectTaskRepository.save(pt) ;
					  
				     }
			       }
			  else {
				  int id = (int)issue.getId();
				  ProjectTask pt=projectTaskRepository.findOne(id);
				  String oldTime="";
				  oldTime = formatter.format(pt.getUpdatedAt());
				  if(pt.getUpdatedAt()==null || oldTime.equals(issue.getUpdated())==false) {
				  if(user!=null)
				  pt.setAssigneeId(user.getUserId());
				  pt.setStatus(issue.getStatus());
				  pt.setIssueType(issue.getIssueType());
				  pt.setSubject(issue.getSubject());
				  pt.setPriority(issue.getPriority());
				  pt.setEstimateTime(issue.getEstimateTime());
				  pt.setPercentDone(0);
				  projectTaskRepository.save(pt) ;
				  }
			  }
			  List<WorklogDTO> listWorklog = issue.getWorklogsS();
			  workLogService.saveWorkLogJira(listWorklog, (int)issue.getId());
		  }
		  if(deleteString.equals("[]")==false) {
			  deleteString=deleteString.substring(1,deleteString.length()-1);
			  String[] array =deleteString.split(",");
			  System.out.println(deleteString);
			  for(String tem : array) {
			   int projectTaskId=Integer.parseInt(tem.trim());
			   workLogService.deleteWorkLogByProjectTaskId(projectTaskId);
			  projectTaskRepository.delete(projectTaskId);
			  }
		  }
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		return true;  
	  }

}
