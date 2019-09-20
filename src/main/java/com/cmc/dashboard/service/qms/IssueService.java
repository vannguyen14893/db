package com.cmc.dashboard.service.qms;

import java.util.List;

import com.cmc.dashboard.dto.IssueDTO;

public interface IssueService {
	List<IssueDTO> getListTasksAssignedToResource(int resourceId);
	List<IssueDTO> getListTasksAssignedToResourceByTime(int resourceId, String fromDate, String toDate);
}
