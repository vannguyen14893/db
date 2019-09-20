package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.WorklogDTO;

public interface WorkLogService {
	 public boolean saveWorkLogJira(List<WorklogDTO> listWorklog,int projectTaskId);
	 
	 public int deleteWorkLogByProjectTaskId(int projectTaskId);
}
