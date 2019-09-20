package com.cmc.dashboard.service.qms;

import java.util.List;

import com.cmc.dashboard.dto.DuAllocation;
import com.cmc.dashboard.dto.ProjectAllocationDTO;

public interface ProjectAllocationService {
	
	List<ProjectAllocationDTO> getAllProjectByDuWithoutPM(int userId, String dateMonth);
	List<ProjectAllocationDTO> getProjectAllocationfromObj(List<Object> listObj,String dateMonth);
	List<DuAllocation> getDuAllocationfromObj(int projectId, String dateMonth);
	
	List<ProjectAllocationDTO> getListProject(int mont, int year);
}
