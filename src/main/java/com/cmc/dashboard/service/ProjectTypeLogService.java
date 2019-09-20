package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.ProjectTypeLogDTO;
import com.cmc.dashboard.model.ProjectTypeLog;

public interface ProjectTypeLogService {
  public ProjectTypeLog save(ProjectTypeLog ptl);
  public List<ProjectTypeLogDTO> getListByprojectId(int projectId);
  public void setProjectTypeLog();
}
