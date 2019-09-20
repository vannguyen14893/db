package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.EditProjectDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.MemberDTO;
import com.cmc.dashboard.dto.NoncomplianceTasksDto;
import com.cmc.dashboard.dto.OverdueTasksDto;
import com.cmc.dashboard.dto.ProjectBasicInfoDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.ProjectInfoDTO;
import com.cmc.dashboard.dto.ProjectJiraDTO;
import com.cmc.dashboard.dto.ProjectListDTO;
import com.cmc.dashboard.dto.ProjectListTableDTO;
import com.cmc.dashboard.dto.ProjectTableDTO;
import com.cmc.dashboard.dto.ProjectTimesheetsDto;
import com.cmc.dashboard.dto.ResourceFilterDto;
import com.cmc.dashboard.dto.TimeProjectDTO;
import com.cmc.dashboard.dto.TeamHourByActivityDto;
import com.cmc.dashboard.dto.ThroughtputBurndownDto;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserTimeSheetDTO;
import com.cmc.dashboard.dto.WorkProgressDTO;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.model.ProjectRole;

public interface ProjectService {

	/**
	 * Get list of project by user
	 *
	 * @param userId
	 * @return
	 * @author: ntquy
	 */
	public ProjectListTableDTO getProjectListAll( int userId, int page, int size, String column, String sort,
												  String projectName, String status, String PM, String startDateFrom, String startDateTo, String endDateFrom,
												  String endDateTo, String DU, String typeProject, String type);
	/**
	 * Get list of project billable
	 *
	 * @param projectId
	 * @return
	 * @author: NVKhoa
	 */

	public ProjectListDTO getProjectListByUser(int userId, int page, int size, String column, String sort, String projectName, String status, String PM, String startDateFrom, String startDateTo, String endDateFrom, String endDateTo, String DU, String typeProject, String groupName,int type);



	public List<BillableDTO> getBillableByProject(int projectId);

	/**
	 * Update project billable
	 *
	 * @param projectBillable
	 * @return String
	 * @author: NVKhoa
	 */
	public ListBillableDTO updateProjectBillable(String projectBillable);

	/**
	 * Update project billable
	 *
	 * @param projectBillable
	 * @return
	 * @author LXLinh
	 */
	public ListBillableDTO updateProjectBillable(List<BillableDTO> projectBillable);

	/**
	 *
	 * getProjectBillableByProjectId
	 *
	 * @param projectId
	 * @return
	 * @throws SQLException
	 *             List<ProjectBillable>
	 * @author: HungNC
	 */
	public List<ProjectBillable> getProjectBillableByProjectId(int projectId);

	/**
	 *
	 * Get info project about projectname, start date, end date
	 *
	 * @param projectId
	 * @return
	 * @author: HungNC
	 */
	public ProjectDTO getProjectInfo(int projectId);

	public List<String> getMonthByStartDateEndDateOfProject(String startDate, String endDate);

	/**
	 * Get Project Basic Info (project name, start date, end date, project type,
	 * delivery unit, project code) By ProjectId
	 *
	 * @param projectId
	 * @return ProjectDTO
	 * @author: GiangTM
	 */
	public List<ProjectBasicInfoDTO> getProjectBasicInfoByProjectId(int projectId);

	/**
	 * Get WorkProgress: attention tasks, estimated time today, closed tasks and time yesterday,
	 * open bugs, closed bugs yesterday, open risks
	 *
	 * @param projectId
	 * @return WorkProgressDTO
	 * @author: GiangTM
	 */
	public WorkProgressDTO getWorkProgressByProjectId(int projectId);

	/**
	 * Get Project Progress: timelines, timelog, work progress
	 * @param projectId
	 * @return ProjectDTO
	 * @author: GiangTM
	 */
	public ProjectDTO getProjectProgressByProjectId(int projectId);

	/**
	 * Get Overdue Tasks by ProjectId
	 * @param projectId
	 * @return List<OverdueTasksDto> 
	 * @author: GiangTM
	 */
	public List<OverdueTasksDto> getOverdueTasksByProjectId(int projectId);

	/**
	 * Get Project Timesheets By ProjectId
	 * @param projectId
	 * @return List<ProjectTimesheetsDto> 
	 * @author: GiangTM
	 */
	public List<ProjectTimesheetsDto> getProjectTimesheetsByProjectId(int projectId);

	/**
	 * Get Noncompliance Tasks By ProjectId
	 * @param projectId
	 * @return List<NoncomplianceTasksDto> 
	 * @author: GiangTM
	 */
	public List<NoncomplianceTasksDto> getNoncomplianceTasksByProjectId(int projectId);

	/**
	 * Get users who join in project
	 * @param projectId
	 * @return List<UserDTO> 
	 * @author: NVKhoa
	 */
	public List<UserDTO> getUserByProject(int projectId);

	/**
	 * Get Members With Last Activity By ProjectId
	 * @param projectId
	 * @return List<UserDTO>
	 * @author: GiangTM
	 */
	public List<UserDTO> getMembersWithLastActivityByProjectId(int projectId);

	/**
	 * Get TeamHour By Activities By ProjectId
	 * @param theProjectId
	 * @return List<TeamHourByActivityDto> 
	 * @author: GiangTM
	 */
	public List<TeamHourByActivityDto> getTeamHourByActivitiesByProjectId(int theProjectId);

	/**
	 * Get all project of user
	 *
	 * @param userId
	 * @return List<ProjectFilterDto>
	 * @author: NNDuy
	 */
	public List<ResourceFilterDto> getAllProjectByUser(int userId);

	/**
	 * check exist of project of user
	 *
	 * @param userId
	 * @param projectId
	 * @return boolean
	 * @author: NNDuy
	 */
	public boolean isExistProjectOfUser(int userId, int projectId);

	/**
	 * get Time Of Project
	 *
	 * @param projectId
	 * @return TimeProjectDTO
	 * @author: NNDuy
	 */
	public TimeProjectDTO getTimeOfProject(int projectId);

	/**
	 * get Time Of Project
	 *
	 * @param projectIds
	 * @return List<TimeProjectDTO>
	 * @author: NNDuy
	 */
	public List<TimeProjectDTO> getTimeOfListProject(List<Integer> projectIds);

	/**
	 * get Time Of Project
	 *
	 * @param projectIds
	 * @return List<TimeProjectDTO>
	 * @author: NNDuy
	 */
	public List<TimeProjectDTO> getTimeOfListProjectActiveByUserId(int userId);

	/**
	 * Get Throughtput Burndown By ProjectId
	 * @param projectId
	 * @return List<ThroughtputBurndownDto> 
	 * @author: GiangTM
	 */
	public List<ThroughtputBurndownDto> getThroughtputBurndownByProjectId(int theProjectId);


	/**
	 * Get User TimeSheet Last Week And This Week By ProjectId
	 * @param projectId
	 * @return
	 */
	public List<UserTimeSheetDTO> getUserTimeSheetLastWeekAndThisWeekByProjectId(int projectId);

	public EditProjectDTO getEditProject(int projectId);

	public Project findProjectById(int id);

	public void deleteSkill(int projectId,String skill);

	public void insertSkill(int projectId,String skill);
	
	public void deleteMember(int projectId,String member);

	public void insertMember(int projectId,String member);
	
    public void deletePM(int projectId,String pm);

   public void insertPM(int projectId,String pm);

	public void update(Project p);
	
	public List<ProjectRole> getAllProjectRole();

    public int changeStatus(int projectId, int status);
  
    public void updateLineOfCode(Project project);
    
    public Project getProjectByProjectId(int projectId);

    public Object getProjectOverviewByProjectId(int projectId);
    
    public boolean saveDataFromJira(ProjectJiraDTO[] array);
    
    public List<MemberDTO> getUserAllEditProject();
    
    public void updateMember(int projectId,String member);
    
    public String getNamePm(int projectId);

    
    public List<ProjectTableDTO> getProjectByDu(int groupId);
    
    public List<ProjectInfoDTO> getAllProject();
}
