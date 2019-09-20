package com.cmc.dashboard.service.qms;

import java.util.List;

import com.cmc.dashboard.dto.ProjectMemberDTO;
import com.cmc.dashboard.dto.ProjectMemberSolutionDTO;

public interface ProjectMemberService {
	
	List<ProjectMemberDTO> getListProjectMember(int projectId);
	
	List<ProjectMemberSolutionDTO> getProjectMembers(int projectId);
	
}
