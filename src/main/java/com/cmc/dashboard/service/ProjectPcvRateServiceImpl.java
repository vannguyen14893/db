package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.ProjectPcvRate;
import com.cmc.dashboard.repository.ProjectPcvRateRepository;

/**
 * @author GiangTM
 */
@Service
public class ProjectPcvRateServiceImpl implements ProjectPcvRateService {
	@Autowired
	ProjectPcvRateRepository projectPcvRateRepository;

	@Override
	public List<ProjectPcvRate> getProjectPcvRatesByProjectId(int theProjectId) {
		Optional<List<ProjectPcvRate>> projectPcvRates=Optional.ofNullable(projectPcvRateRepository.getProjectPcvRatesByProjectId(theProjectId));
		if(projectPcvRates.isPresent())
		  return	projectPcvRates.get().stream().sorted(Comparator.comparing(ProjectPcvRate::getStartTime)).collect(Collectors.toList());
		return new ArrayList<>();
	}

	@Override
	public ProjectPcvRate createProjectPcvRate(ProjectPcvRate theProjectPcvRate) {
		return projectPcvRateRepository.save(theProjectPcvRate);
	}

	@Override
	public ProjectPcvRate updateProjectPcvRate(ProjectPcvRate theProjectPcvRate) {
		ProjectPcvRate projectPcvRate = projectPcvRateRepository.findOne(theProjectPcvRate.getProjectPcvRateId());
		projectPcvRate.setName(theProjectPcvRate.getName());
		projectPcvRate.setStartTime(theProjectPcvRate.getStartTime());
		projectPcvRate.setEndTime(theProjectPcvRate.getEndTime());
		projectPcvRate.setScore(theProjectPcvRate.getScore());
		projectPcvRate.setComment(theProjectPcvRate.getComment());
		projectPcvRate.setAccumulated(theProjectPcvRate.getAccumulated());
		return projectPcvRateRepository.save(projectPcvRate);
	}
	
	@Override
	public boolean isExist(int theProjectPcvRateId) {
		return projectPcvRateRepository.exists(theProjectPcvRateId);
	}
	
	@Override
	public ProjectPcvRate findOne(int theProjectPcvRateId) {
		return projectPcvRateRepository.findOne(theProjectPcvRateId);
	}
	
	@Override
	public boolean deletePrjectPcvRate(int theProjectPcvRateId) {
		if(this.isExist(theProjectPcvRateId)) {
			projectPcvRateRepository.delete(theProjectPcvRateId);
			return true;
		}
		return false;
	}

}