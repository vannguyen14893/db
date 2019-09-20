package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.HistoryDTO;

public interface HistoryDataService {

	public List<HistoryDTO> getHistoryByIssueId(int issueId);
	
	public List<HistoryDTO> getHistoryByRiskId(int riskId);
}
