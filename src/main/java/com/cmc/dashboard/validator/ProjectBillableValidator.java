package com.cmc.dashboard.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.service.ProjectBillableSevice;
import com.cmc.dashboard.service.ProjectService;

@Component("projectBillableValidator")
public class ProjectBillableValidator implements Validator {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectBillableSevice projectBillableSevice;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void validate(Object target, Errors errors) {
		ProjectBillable projectBillable = (ProjectBillable) target;
		int projectId = projectBillable.getProjectId();
		Project project = projectService.findProjectById(projectId);
		
		if(!projectBillable.getStartDate().before(projectBillable.getEndDate())) {
			errors.reject("time range", "End time must be after Start time!");
		}
		
		if(projectBillable.getStartDate().before(project.getStartDate()) || projectBillable.getStartDate().after(project.getEndDate())) {
			errors.reject("time range", "Time range is not valid!");
		}
		
		if(projectBillable.getEndDate().before(project.getStartDate()) || projectBillable.getEndDate().after(project.getEndDate())) {
			errors.reject("time range", "Time range is not valid!");
		}
		
		
	}
	
	
}
