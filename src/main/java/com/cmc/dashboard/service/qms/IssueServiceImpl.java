package com.cmc.dashboard.service.qms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.IssueDTO;
import com.cmc.dashboard.qms.repository.IssuesRepository;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	IssuesRepository issuesRepository;

	@Override
	public List<IssueDTO> getListTasksAssignedToResource(int resourceId) {

		// Get list object from database
		List<Object> objects = issuesRepository.getListTasksAssignedToResource(resourceId);

		// Check data null
		if (null == objects) {
			return null;
		}

		IssueDTO issueDTO = null;

		List<IssueDTO> issueDTOs = new ArrayList<>();

		// convert list object to DTO
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			issueDTO = new IssueDTO();
			issueDTO.setIssueName(obj[0].toString());
			issueDTO.setStartDate(obj[1] == null ? "" : obj[1].toString());
			issueDTO.setDueDate(obj[2] == null ? "" : obj[2].toString());
			issueDTO.setEstimatedHours(obj[3] == null ? 0 : Float.parseFloat(obj[3].toString()));
			issueDTO.setStatus(obj[5].toString());
			issueDTOs.add(issueDTO);
		}

		return issueDTOs;
	}

	/**
	 * Get list tasks assigned to resource by time.
	 * 
	 * @return List<Object> 
	 * @author: HungNC
	 */
	@Override
	public List<IssueDTO> getListTasksAssignedToResourceByTime(int resourceId, String fromDate, String toDate) {

		// Get list object from database
		List<Object> objects = issuesRepository.getListTasksAssignedToResourceByTime(resourceId, fromDate, toDate);
		//List<Object> objects = null;
		
		// Check data null
		if (null == objects) {
			return null;
		}

		IssueDTO issueDTO = null;

		List<IssueDTO> issueDTOs = new ArrayList<>();

		// convert list object to DTO
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			issueDTO = new IssueDTO();
			issueDTO.setIssueName(obj[0].toString());
			issueDTO.setStartDate(obj[1] == null ? "" : obj[1].toString());
			issueDTO.setDueDate(obj[2] == null ? "" : obj[2].toString());
			issueDTO.setEstimatedHours(obj[3] == null ? 0 : Float.parseFloat(obj[3].toString()));
			issueDTO.setStatus(obj[5].toString());
			issueDTO.setProjectName(obj[6] == null ? "" : obj[6].toString());
			issueDTOs.add(issueDTO);
		}

		return issueDTOs;
	}

}
