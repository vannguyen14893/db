package com.cmc.dashboard.validator;

import java.util.List;

import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cmc.dashboard.model.ProjectPcvRate;
import com.cmc.dashboard.service.ProjectPcvRateService;

@Component("projectPcvRateValidator")
public class ProjectPcvRateValidator implements Validator {
	@Autowired
	private ProjectPcvRateService projectPcvRateService;

	@Autowired
	private ProjectService projectService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectPcvRate.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProjectPcvRate pcvRateUpdate = (ProjectPcvRate) target;
		int projectID = pcvRateUpdate.getProjectId();
		Project projectByID = projectService.findProjectById(projectID);
		List<ProjectPcvRate> projectPcvRates = projectPcvRateService.getProjectPcvRatesByProjectId(projectID);
		int index = projectPcvRates.indexOf(projectPcvRateService.findOne(pcvRateUpdate.getProjectPcvRateId()));

		if (!pcvRateUpdate.getEndTime().after(pcvRateUpdate.getStartTime())){
			System.out.println("ProjectPcvRateValidator.Case_01");
			errors.reject("time range", "End time must be after Start time!");
		}
		// Check Creating or Updating
		if (projectPcvRateService.isExist(pcvRateUpdate.getProjectPcvRateId())) {
			// Check before record exist
			if (index - 1 >= 0 && pcvRateUpdate.getEndTime().before(projectPcvRates.get(index - 1).getStartTime())) {
				System.out.println("ProjectPcvRateValidator.Case_03");
				errors.reject("start time", "End time must not be before next Start time!");
			}
			// Check after record exist
			if (index + 1 <= projectPcvRates.size() - 1 && pcvRateUpdate.getStartTime().after(projectPcvRates.get(index + 1).getEndTime())){
				System.out.println("ProjectPcvRateValidator.Case_04");
				errors.reject("end time", "Start time must not be after previous End time!");
			}
			// Check Time range processing in project (Linh Gia)
			if(!pcvRateUpdate.getStartTime().after(projectByID.getStartDate()) || !pcvRateUpdate.getEndTime().before(projectByID.getEndDate())){
				errors.reject("time range", "Time range is not valid!");
			}
		} else {
			System.out.println("ProjectPcvRateValidator.Case_05");
			if (!projectPcvRates.isEmpty() && pcvRateUpdate.getStartTime().before(projectPcvRates.get(0).getEndTime())) {
				errors.reject("time range", "Start time must not be before last End time!");
			}
		}
	}

}
