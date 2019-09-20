package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.model.ProjectBillable;

public interface ProjectBillableSevice {
	public List<BillableDTO> getBillableByProject(int projectId);
	public ProjectDTO getProjectInfo(int projectId);
	public List<String> getMonthByStartDateEndDateOfProject(String startDate, String endDate);
	public double getManMonthByProjectIdAndBillableMonth(int projectId, String billableMonth);
	
	//PNTHANH
	public List<ProjectBillableDTO> getProjectBillableByProjectId(int projectId);
	public ProjectBillable create(ProjectBillable projectBillable);
	public ProjectBillable update(ProjectBillable projectBillable);
	public boolean delete(int projectBillableId);
	public ProjectBillable findOne(int projectBillableId);
	public boolean isExist(int id);
	public List<GroupDTO> getGroupByProjectMember(int projectId);
}
