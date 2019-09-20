package com.cmc.dashboard.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.ProjectTableDTO;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.util.MethodUtil;
@Service
public class ProjectBillableSeviceImpl implements ProjectBillableSevice {
	@Autowired
	ProjectBillableRepository projectBillableRepository;
	@Autowired
	ProjectRepository projectRepository;

	@Override
	public List<BillableDTO> getBillableByProject(int projectId) {
		
		Project project = Optional.ofNullable(projectRepository.getOne(projectId)).get();
		Set<String> projectManager = new HashSet<>(
				Optional.ofNullable(projectRepository.getProjectManagerById(projectId)).get());
		ProjectDTO projectDTO = new ProjectDTO(projectId, project.getName(), projectManager, project.getStartDate().toString(), project.getEndDate().toString());
		
		
//		List<ProjectBillable> projectBillables = projectBillableRepository.getListBillableByProjectId(projectId);
		List<ProjectBillable> projectBillables = this.addListOfBillable(projectDTO);
		List<BillableDTO> billableDTOs = new ArrayList<BillableDTO>();
		// Get project info
		ProjectDTO projectInfo = this.getProjectInfo(projectId);
		// Get list month of Project from startDate to End date
		List<String> listMonthOfProject = this.getMonthByStartDateEndDateOfProject(projectInfo.getStartDate(),
				projectInfo.getEndDate());
		if(projectBillables == null) {
			return billableDTOs;
		}
		for (ProjectBillable projectBillable : projectBillables) {
				if(listMonthOfProject.contains(projectBillable.getBillableMonth())) {
					billableDTOs.add(new BillableDTO(projectBillable, this.getManMonthByProjectIdAndBillableMonth(projectId, projectBillable.getBillableMonth()), false));
				} else {
					billableDTOs.add(new BillableDTO(projectBillable, this.getManMonthByProjectIdAndBillableMonth(projectId, projectBillable.getBillableMonth()), true));
				}
		}
		return billableDTOs;
	}
	
	@Override
	public ProjectDTO getProjectInfo(int projectId) {
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setStartDate(projectRepository.getProjectStartDateById(projectId));
		projectDTO.setEndDate(projectRepository.getProjectEndDateById(projectId));
		projectDTO.setProjectName(projectRepository.findOne(projectId).getName());
		return projectDTO;
	}
	
	@Override
	public List<String> getMonthByStartDateEndDateOfProject(String startDate, String endDate) {
		return MethodUtil.getMonthBetween2Date(startDate, endDate);
	}

	@Override
	public double getManMonthByProjectIdAndBillableMonth(int projectId, String billableMonth) {
		int sumManMonth = 0;
		try {
			sumManMonth = projectBillableRepository.getManMonthByProjectIdAndBillableMonth(projectId,billableMonth );
		} catch (Exception e) {
			// TODO: handle exception
			sumManMonth = 0;
		}
		
		String[] businessDays=billableMonth.split("\\-");
		int month = Integer.parseInt(businessDays[0]);
		int year = Integer.parseInt(businessDays[1]);
		int workingDay = MethodUtil.calculateWorkingDay(month, year);
		BigDecimal b1 = new BigDecimal((double)sumManMonth/(double)workingDay);  
        MathContext m = new MathContext(2);
		double manMonth = (sumManMonth == 0 ? 0 : b1.round(m).doubleValue());
		return manMonth;
	}

	private List<ProjectBillable> addListOfBillable(ProjectDTO projectDto) {
		List<ProjectBillable> lstBillable = new ArrayList<ProjectBillable>();
		List<String> lstMonth = MethodUtil.getMonthBetween2Date(projectDto.getStartDate(), projectDto.getEndDate());
		ProjectBillable projectBillable;
		for (String month : lstMonth) {
			projectBillable = new ProjectBillable();
			projectBillable.setBillableMonth(month);
			projectBillable.setProjectId(projectDto.getProjectId());
			projectBillable.setPmName(projectDto.toStringManager());
			if (projectDto.getDeliveryUnit() == null) {
//				projectBillable.setDeliveryUnit("");
			} else {
//				projectBillable.setDeliveryUnit(projectDto.getDeliveryUnit());
			}
			projectBillable.setStartDate(MethodUtil.convertStringToDate(projectDto.getStartDate()));
			projectBillable.setEndDate(MethodUtil.convertStringToDate(projectDto.getEndDate()));
			lstBillable.add(projectBillable);
		}
		//this.getValueOfBillable(lstBillable, projectDto.getProjectId());
		return lstBillable;
	}
	
	private void getValueOfBillable(List<ProjectBillable> lstBillable, int projectId) {
		List<ProjectBillable> billables = projectBillableRepository.getBillableByProject(projectId);

		List<ProjectBillable> list = new ArrayList<>();
		for (ProjectBillable element : lstBillable) {
			boolean isExist = false;
			for (ProjectBillable item : billables) {
				if (element.getBillableMonth().equals(item.getBillableMonth())) {
					item.setStartDate(element.getStartDate());
					item.setEndDate(element.getEndDate());
					item.setPmName(element.getPmName());
					isExist = true;
				}
			}
			if (!isExist) {
				list.add(element);
			}
		}
		billables.addAll(list);
		projectBillableRepository.save(billables);

		for (ProjectBillable element : billables) {
			boolean isExist = false;
			for (ProjectBillable item : lstBillable) {
				if (item.getBillableMonth().equals(element.getBillableMonth())) {
					isExist = true;
				}
			}
			if (!isExist) {
				projectBillableRepository.delete(element);
			}
		}
	}

	@Override
	public List<ProjectBillableDTO> getProjectBillableByProjectId(int projectId) {
		List<Object> objects = projectBillableRepository.getBillableByProjectId(projectId);
		List<ProjectBillableDTO> billableDTOs = new ArrayList<>();
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			ProjectBillableDTO projectBillableDTO = new ProjectBillableDTO();
			projectBillableDTO.setProjectBillableId(Integer.parseInt(obj[0].toString()));
			projectBillableDTO.setBillableValue(Float.parseFloat(obj[1].toString()));
			projectBillableDTO.setIssueCode(obj[2].toString());
			projectBillableDTO.setStartDate(obj[3].toString());
			projectBillableDTO.setEndDate(obj[4].toString());
			projectBillableDTO.setGroupName(obj[5].toString());
			projectBillableDTO.setGroupId(Integer.parseInt(obj[6].toString()));
			billableDTOs.add(projectBillableDTO);
		}
		
		return billableDTOs;
	}

	@Override
	public ProjectBillable create(ProjectBillable projectBillable) {		
		return projectBillableRepository.save(projectBillable);
	}

	@Override
	public ProjectBillable update(ProjectBillable projectBillable) {
		ProjectBillable billableUpdate = this.findOne(projectBillable.getProjectBillableId());
		billableUpdate.setIssueCode(projectBillable.getIssueCode());
		billableUpdate.setBillableValue(projectBillable.getBillableValue());
		billableUpdate.setStartDate(projectBillable.getStartDate());
		billableUpdate.setEndDate(projectBillable.getEndDate());
		billableUpdate.setGroupId(projectBillable.getGroupId());
		return projectBillableRepository.save(billableUpdate);
	}

	@Override
	public boolean delete(int projectBillableId) {
		if(this.isExist(projectBillableId)) {
			projectBillableRepository.delete(projectBillableId);
			return true;
		}
		return false;
	}

	@Override
	public ProjectBillable findOne(int projectBillableId) {
		// TODO Auto-generated method stub
		return projectBillableRepository.getOne(projectBillableId);
	}

	@Override
	public boolean isExist(int id) {
		// TODO Auto-generated method stub
		return projectBillableRepository.exists(id);
	}

	@Override
	public List<GroupDTO> getGroupByProjectMember(int projectId) {
		List<GroupDTO> groupDTOs = new ArrayList<>();
		List<Object> objects = new ArrayList<Object>();
		try {
			objects= projectBillableRepository.getGroupByProjectMember(projectId);
			 for(Iterator<Object> it = objects.iterator(); it.hasNext();) {
			    	Object[] object = (Object[]) it.next();
			    	Integer idGroup =(Integer)object[0];
			    	if(idGroup!=null) {
			    	GroupDTO gr= new GroupDTO((Integer)object[0],(String)object[1]);
			    	groupDTOs.add(gr);
			    	}
			    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//List<Integer> listId = new ArrayList<>();
//		if(objects.isPresent()) {
//			for (Object object : objects.get()) {
//				Object[] obj = (Object[]) object;
//				if(obj[0] != null) {
//					if(!listId.contains(Integer.parseInt(obj[0].toString())))
//						groupDTOs.add(new GroupDTO(Integer.parseInt(obj[0].toString()), obj[1].toString()));
//						listId.add(Integer.parseInt(obj[0].toString()));
//				}
//				else {
//					return groupDTOs;
//				}	
//			}
//		}
		
		return groupDTOs;
	}

}
