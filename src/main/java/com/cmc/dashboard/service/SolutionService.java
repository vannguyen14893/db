package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.SolutionCommentDTO;
import com.cmc.dashboard.dto.SolutionDTO;
import com.cmc.dashboard.model.Solution;

/**
 * 
 * @author nttha1
 *
 */

public interface SolutionService {
	public SolutionDTO getSolutionById(Integer solutionId);
	public List<SolutionDTO> getSolutionByRisk(Integer riskId);
	
	public List<SolutionCommentDTO> getCommentBySolutionId(Integer solutionId);
	public SolutionCommentDTO save(SolutionCommentDTO comment);
	
	/**
	 * @author duyhieu
	 * @param solutionId
	 */
	public Solution deleteSolution(int solutionId);
	
	/**
	 * @author nttha
	 * @param solutionCommentId
	 */
	public SolutionCommentDTO deleteComment(Integer solutionCommentId);
	
	/**
	 * Update solution comment.
	 * @author dtthuyen1
	 * @param solutionCommnetDTO
	 */
	public SolutionCommentDTO editComment(SolutionCommentDTO solutionCommnetDTO);
	
	/**
	 * @author duyhieu
	 * @param solutionId
	 */
	public Solution assignSolution(int solutionId, int userId);
	
	/**
	 * Change status of solution
	 * @param SolutionDTO
	 * @param SolutionDTO
	 */
	public SolutionDTO updateStatusSolution(SolutionDTO solutionDTO);
}
