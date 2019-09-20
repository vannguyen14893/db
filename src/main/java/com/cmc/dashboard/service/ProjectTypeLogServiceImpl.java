package com.cmc.dashboard.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ProjectTypeLogDTO;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectTypeLog;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectTypeLogRepository;
import com.cmc.dashboard.repository.UserRepository;

@Service
public class ProjectTypeLogServiceImpl implements ProjectTypeLogService {

	@Autowired
	private ProjectTypeLogRepository projectTypeLogrepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public ProjectTypeLog save(ProjectTypeLog ptl) {

		return projectTypeLogrepo.save(ptl);
	}

	@Override
	public List<ProjectTypeLogDTO> getListByprojectId(int projectId) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Profit");
		map.put(2, "Start");
		map.put(3, "Pool");
		map.put(4, "Investment");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		List<ProjectTypeLog> list = projectTypeLogrepo.findByProjectId(projectId);
		List<ProjectTypeLogDTO> result = new ArrayList<ProjectTypeLogDTO>();
		String editorName = null;
		for (ProjectTypeLog ptn : list) {
			if(ptn.getEditorId()!=0)
				editorName = userRepository.findOne(ptn.getEditorId()).getFullName();
				else editorName="";
			result.add(new ProjectTypeLogDTO(ptn.getProjectTypeLogId(), map.get(ptn.getProjectTypeId()), editorName,
					formatter.format(ptn.getStartDate()), formatter.format(ptn.getEndDate())));
		}
		return result;
	}

	@Override
	public void setProjectTypeLog() {
		List<Project> listProject = projectRepository.findAll();
		for (Project p : listProject) {
			if(p.getStatus()!=0) {
				ProjectTypeLog ptl = new ProjectTypeLog(p.getId(),p.getType(),p.getStartDate(), p.getEndDate(), 0);
				projectTypeLogrepo.save(ptl);
			}
		}

	}

}
