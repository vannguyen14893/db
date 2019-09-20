package com.cmc.dashboard.service;

import org.springframework.data.repository.query.Param;

public interface ProjectUserService {
public void  update( int projectRoleId, int skillId,String startDate, int projectId , int userId  ) ;
}
