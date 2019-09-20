package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.SolutionCommentDTO;
import com.cmc.dashboard.dto.SolutionDTO;
import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.model.SolutionComment;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.ProjectSolutionRepository;
import com.cmc.dashboard.repository.SolutionCommentRepository;
import com.cmc.dashboard.repository.SolutionRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {
	@Autowired
	private SolutionRepository solutionRepository;
	
	@Autowired
	private SolutionCommentRepository solutionCommentRepository;
	
	@Autowired
	private ProjectSolutionRepository projectSolutionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserQmsRepository userQmsRepository;

	@Override
	@Transactional(readOnly = true)
	public SolutionDTO getSolutionById(Integer solutionId) {
		return new SolutionDTO(solutionRepository.findOne(solutionId));
	}
	
	@Override
	public List<SolutionDTO> getSolutionByRisk(Integer riskId) {
		List<Solution> data = solutionRepository.getSolutionByRisk(riskId);
		List<SolutionDTO> solutionDTOs = new ArrayList<>();
		if (data == null) {
			return solutionDTOs;
		}
		for (Solution solution : data) {
			solutionDTOs.add(new SolutionDTO(solution));
		}
		return solutionDTOs;
	}

	@Override
	public List<SolutionCommentDTO> getCommentBySolutionId(Integer solutionId) {
		List<SolutionComment> data = solutionCommentRepository.getCommentBySolutionId(solutionId);
		List<SolutionCommentDTO> solutionCommentDTOs = new ArrayList<>();
		if(data == null) {
			return solutionCommentDTOs;
		} 
		for(SolutionComment solutionComment: data) {
			solutionCommentDTOs.add(new SolutionCommentDTO(solutionComment));
		}
		return solutionCommentDTOs;
	}

	@Override
	public SolutionCommentDTO save(SolutionCommentDTO comment) {
		SolutionComment solutionCmt = solutionCommentRepository.save(comment.toEntity());
		User user = userRepository.findOne(solutionCmt.getCreateBy().getUserId());
		solutionCmt.setCreateBy(user);
		return new SolutionCommentDTO(solutionCmt);
	}

	@Override
	public Solution deleteSolution(int solutionId) {
		Optional<Solution> solution = Optional.ofNullable(solutionRepository.findOne(solutionId));
		if (solution.isPresent()){
			solution.get().setStatus(Constants.Numbers.SOLUTION_STATUS_CANCELLED);
			return projectSolutionRepository.save(solution.get());
		}
		return null;
	}

	@Override
	public SolutionCommentDTO deleteComment(Integer solutionCommentId) {
		SolutionComment solutionComment = solutionCommentRepository.findOne(solutionCommentId);
		solutionComment.setIsDeleted(true);
		solutionCommentRepository.save(solutionComment);
		return new SolutionCommentDTO(solutionComment);
	}

	@Override
	public SolutionCommentDTO editComment(SolutionCommentDTO solutionCommnetDTO) {
		SolutionComment solutionComment = solutionCommentRepository.findOne(solutionCommnetDTO.getId());
		solutionComment.setComment(solutionCommnetDTO.getComment());
		solutionCommentRepository.save(solutionComment);
		return new SolutionCommentDTO(solutionComment);
	}

	public Solution assignSolution(int solutionId, int userId) {
		Optional<Solution> solution = Optional.ofNullable(solutionRepository.findOne(solutionId));
		if (solution.isPresent()){
			Optional<User> user= Optional.ofNullable(userRepository.findOne(userId));
			if(user.isPresent()) 
			solution.get().setUserAssignee(user.get());
			return projectSolutionRepository.save(solution.get());
		}	
		return null;
	}

	@Override
	public SolutionDTO updateStatusSolution(SolutionDTO solutionDTO) {
		Integer solutionID = solutionDTO.getId();
		Integer createdById = solutionDTO.getCreaterId();
		Solution solution = solutionRepository.findOne(solutionID);
		if(solution != null) {
			int oldStatus = solution.getStatus();
			int newStatus = solutionDTO.getStatus();
			solution.setStatus(newStatus);
			solutionRepository.save(solution);
			SolutionComment solutionComment = new SolutionComment();
			solutionComment.setSolutionId(solution);
			solutionComment.setCreateBy(new User(createdById));
			solutionComment.setIsDeleted(false);
			StringBuilder comment = new StringBuilder("changed status this solution from ");
			comment.append(MethodUtil.convertStatusSolutionToString(oldStatus))
				.append(" to ").append(MethodUtil.convertStatusSolutionToString(newStatus));
			solutionComment.setComment(comment.toString());
			solutionCommentRepository.save(solutionComment);
			return new SolutionDTO(solution);
		}
		return new SolutionDTO(solution);
	}
	
	
}
