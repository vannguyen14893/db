package com.cmc.dashboard.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ProjectDeliveryDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.ProjectDelivery;
import com.cmc.dashboard.repository.ProjectDeliveryRepository;
import com.cmc.dashboard.util.MessageUtil;

@Service
public class ProjectDeliveryServiceImpl implements ProjectDeliveryService{

	@Autowired
     private ProjectDeliveryRepository  projectDeliveryRepository;

	@Override
	public List<ProjectDelivery> getDeliveryByProjectId(int projectId ) {
		return projectDeliveryRepository.getDeliveryByProjectId(projectId).stream()
				.sorted(Comparator.comparing(ProjectDelivery::getScheduleDate)).collect(Collectors.toList());
	}

	@Override
	 public List<ProjectDelivery> updateProjectDelivery(List<ProjectDeliveryDTO> projectDeliveries) {
		if(projectDeliveries.isEmpty())
			throw new BusinessException(MessageUtil.NOT_EXIST);
		List<Integer> ids=projectDeliveries.stream().map(ProjectDeliveryDTO::getProjectDeliveryId).collect(Collectors.toList());
		if(ids.isEmpty())
			throw new BusinessException(MessageUtil.NOT_EXIST);
		List<ProjectDelivery> projectDelivery=projectDeliveryRepository.findAll(ids);
		
		if (!projectDelivery.isEmpty()) {
			projectDelivery.stream()
					.forEach(
							proDelivery -> projectDeliveries.stream().filter(
							proDeliveryDto -> proDeliveryDto.getProjectDeliveryId() == proDelivery.getProjectDeliveryId())
							.forEach(proDeliveryDto -> {
								proDelivery.setComment(proDeliveryDto.getComment());
								proDelivery.setName(proDeliveryDto.getName());
								proDelivery.setRealDate(proDeliveryDto.getRealDate());
								proDelivery.setScheduleDate(proDeliveryDto.getScheduleDate());
								proDelivery.setOntime(proDeliveryDto.isOntime());
								proDelivery.setProjectId(proDeliveryDto.getProjectId());
							})
							);
			
			return projectDeliveryRepository.save(projectDelivery);
		}
		
		return null;
	}

	@Override
	 public ProjectDelivery saveProjectDelivery(ProjectDeliveryDTO projectDeliveryDto) {
		
		Optional<ProjectDelivery> projectDelivery=projectDeliveryRepository.getDeliveryByProjectId(projectDeliveryDto.getProjectId()).stream()
				.sorted(Comparator.comparing(ProjectDelivery::getScheduleDate).reversed()).findFirst();
		if(projectDelivery.isPresent())
		{
			if(projectDeliveryDto.getScheduleDate().before(projectDelivery.get().getScheduleDate()))
				throw new BusinessException(MessageUtil.MessageProjectDelivery.COMPARE_DATE_SHEDULE);
		}
		return projectDeliveryRepository.save(projectDeliveryDto.convertToModel());
	}



}
