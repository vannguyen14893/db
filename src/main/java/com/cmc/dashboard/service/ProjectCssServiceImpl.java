/**
 * dashboard-phase2-backend- - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ProjectCssRestResponse;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;

/**
 * @author: ngocd
 * @Date: Apr 3, 2018
 */
@Service
public class ProjectCssServiceImpl implements ProjectCssService {

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	private ProjectRepository projectRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectCssService#getByProjectId(int)
	 */
	@Override
	public ProjectCssRestResponse getByProjectId(int projectId) {
		ProjectDTO projectDTO = projectService.getProjectInfo(projectId);
		ProjectCssRestResponse result = new ProjectCssRestResponse();
		result.setProjectId(projectId);
		result.setProjectName(projectDTO.getProjectName());
		result.setStartDate(projectDTO.getStartDate());
		result.setEndDate(projectDTO.getEndDate());
		result.setProjectCsses(projectCssRepository.findCssByProjectId(projectId));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectCssService#getById(int)
	 */
	@Override
	public ProjectCss getById(int projectCssId) {
		// TODO Auto-generated method stub
		return projectCssRepository.findOne(projectCssId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectCssService#create(com.cmc.dashboard.model.
	 * ProjectCss)
	 */
	@Override
	public ProjectCssRestResponse create(ProjectCss projectCss) {
		String DeliveryUnit = projectRepository.getProjectDeliveryUnitById(projectCss.getProjectId());
		projectCss.setDeliveryUnit(DeliveryUnit);
		List<ProjectCss> projectCsses = projectCssRepository.findCssByProjectId(projectCss.getProjectId());
		int maxTime = 0;
		if (!projectCsses.isEmpty()) {
			final Comparator<ProjectCss> comp = (p1, p2) -> Integer.compare(p1.getTime(), p2.getTime());
			maxTime = projectCsses.stream().max(comp).get().getTime();
		}
		projectCss.setTime(maxTime + 1);
		projectCssRepository.save(projectCss);
		ProjectCssRestResponse response = this.getByProjectId(projectCss.getProjectId());
		response.setMessage(MessageUtil.SUCCESS);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectCssService#delete(com.cmc.dashboard.model.
	 * ProjectCss)
	 */
	@Override
	public ProjectCssRestResponse delete(int projectCssId) {
		ProjectCss projectCss = projectCssRepository.findOne(projectCssId);
		projectCssRepository.delete(projectCss);
		List<ProjectCss> projectCssList = projectCssRepository.findCssByProjectId(projectCss.getProjectId());
		for (int time = 0; time < projectCssList.size(); time++) {
			ProjectCss css = projectCssList.get(time);
			css.setTime(time + 1);
		}
		projectCssRepository.save(projectCssList);
		return ProjectCssRestResponse.success();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectCssService#validate(com.cmc.dashboard.model.
	 * ProjectCss)
	 */
	@Override
	public boolean validate(ProjectCss projectCss) {
		int maxTime = 0;
		if (!projectCss.getStartDate().before(projectCss.getEndDate())) {
			return false;
		}
		List<ProjectCss> projectCsses = projectCssRepository.findCssByProjectId(projectCss.getProjectId());
		if (!projectCsses.isEmpty()) {
			final Comparator<ProjectCss> comp = (p1, p2) -> Integer.compare(p1.getTime(), p2.getTime());
			maxTime = projectCsses.stream().max(comp).get().getTime();
		}
//		String projectStartDate = projectRepository.getProjectStartDateById(projectCss.getProjectId());
//		String projectEndDate = projectRepository.getProjectEndDateById(projectCss.getProjectId());
		SimpleDateFormat formatter = new SimpleDateFormat(CustomValueUtil.DATE_FORMAT);
		Date startDate;
		Date endDate;
		try {
			startDate = formatter
					.parse(projectRepository.getProjectStartDateById(projectCss.getProjectId()).toString());
			endDate = formatter.parse(projectRepository.getProjectEndDateById(projectCss.getProjectId()).toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		if (maxTime == 0) {
			return ((!projectCss.getStartDate().before(startDate)) && (!projectCss.getEndDate().after(endDate))
					&& projectCss.getScoreValue() <= 100 && projectCss.getScoreValue() >= 0) ? true : false;
		} else {
			ProjectCss lastProjectCss = projectCssRepository.getLastestProjectCssByProjectId(maxTime,
					projectCss.getProjectId());
			if (lastProjectCss != null) {
				return (projectCss.getStartDate().before(lastProjectCss.getEndDate())
						|| projectCss.getEndDate().after(endDate) || projectCss.getScoreValue() > 100
						|| projectCss.getScoreValue() < 0) ? false : true;
			} else
				return true;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectCssService#isExist(int)
	 */
	@Override
	public boolean checkExist(int projectCssId) {
		ProjectCss projectCss;
		try {
			projectCss = projectCssRepository.getOne(projectCssId);
			if (!(projectCss.getProjectCssId() == projectCssId))
				return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ProjectCss update(ProjectCss projectCss) {
		return projectCssRepository.save(projectCss);
	}

}
