package com.cmc.dashboard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.ProjectSolutionRepository;
import com.cmc.dashboard.repository.UserRepository;

@Service
public class ProjectSolutionServiceInpl implements ProjectSolutionService{

	@Autowired
	private ProjectSolutionRepository projectSolutionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserQmsRepository userQmsRepository;
	
	/**
	 * 
	 * add solution
	 * @param solution
	 * @return 
	 * @author: tvdung
	 */
	@Override
	public void addSolution(Solution solution) {
		
		User userCreateBy = this.getUserByUserName(solution.getUserCreateBy().getUserId());
		solution.setUserCreateBy(userCreateBy);
		
		projectSolutionRepository.save(solution);
	}
	
	
	private User getUserByUserName(int id) {
		Optional<User> user=Optional.ofNullable(userRepository.getUserByUserId(id));
		if(user.isPresent())
			return user.isPresent()? user.get():null;
		return null;
	}
}
