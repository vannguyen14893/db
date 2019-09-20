package com.cmc.dashboard.service;

import java.util.List;
import java.util.Map;

import com.cmc.dashboard.dto.IssueDashboardDTO;
import com.cmc.dashboard.model.Issue;

public interface IssueDashboardService {

	public List<IssueDashboardDTO> getIssueByProject(int projectId);
	public Map<String, Object> getOptional();
	public Issue save(IssueDashboardDTO issue);
	public IssueDashboardDTO getIssueById(int issueId);
	public IssueDashboardDTO assign(int issueId, int userId,int userAssign);
	public IssueDashboardDTO deleteIssue(int issueId);
	public Issue updateIssue(IssueDashboardDTO issue);
	public List<IssueDashboardDTO> getAllIssue(int projectId,int month,int year,int groupId);
}
