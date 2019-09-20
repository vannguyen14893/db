package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.model.ProjectPcvRate;

/**
 * @author GiangTM
 */
public interface ProjectPcvRateService {
	List<ProjectPcvRate> getProjectPcvRatesByProjectId(int theProjectId);

	ProjectPcvRate createProjectPcvRate(ProjectPcvRate theProjectPcvRate);

	ProjectPcvRate updateProjectPcvRate(ProjectPcvRate theProjectPcvRate);

	boolean isExist(int theProjectPcvRateId);

	ProjectPcvRate findOne(int theProjectPcvRateId);
	
	boolean deletePrjectPcvRate(int theProjectPcvRateId);
}