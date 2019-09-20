package com.cmc.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.repository.ProjectUserRepository;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {
    @Autowired ProjectUserRepository projectUserRepo;
    @Transactional
	@Override
	public void update(int projectRoleId, int skillId, String startDate, int projectId, int userId) {
		projectUserRepo.updateUser(projectRoleId, projectId, userId);
		return ;
		
	}

	

}
