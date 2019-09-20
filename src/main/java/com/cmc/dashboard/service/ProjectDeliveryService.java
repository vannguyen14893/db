package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.ProjectDeliveryDTO;
import com.cmc.dashboard.model.ProjectDelivery;

public interface ProjectDeliveryService {

	 public List<ProjectDelivery> getDeliveryByProjectId(int projectId );
	 
	 public List<ProjectDelivery> updateProjectDelivery(List<ProjectDeliveryDTO> projectDeliveries);
	 
	 public ProjectDelivery saveProjectDelivery(ProjectDeliveryDTO projectDeliveryDto);
	 
	 
}
