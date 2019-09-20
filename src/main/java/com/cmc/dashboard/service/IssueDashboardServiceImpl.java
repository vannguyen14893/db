package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.IssueDashboardDTO;
import com.cmc.dashboard.dto.IssueHistoryDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.HistoryData;
import com.cmc.dashboard.model.Issue;
import com.cmc.dashboard.model.IssueCategory;
import com.cmc.dashboard.model.IssuePriority;
import com.cmc.dashboard.model.IssueServerity;
import com.cmc.dashboard.model.IssueSource;
import com.cmc.dashboard.model.IssueStatus;
import com.cmc.dashboard.model.IssueType;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.HistoryDataRepository;
import com.cmc.dashboard.repository.IssueCategoryRepository;
import com.cmc.dashboard.repository.IssueDashboardRepository;
import com.cmc.dashboard.repository.IssuePriorityRepository;
import com.cmc.dashboard.repository.IssueServerityRepository;
import com.cmc.dashboard.repository.IssueSourceRepository;
import com.cmc.dashboard.repository.IssueStatusRepository;
import com.cmc.dashboard.repository.IssueTypeRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.HistoryDataUtil;
import com.cmc.dashboard.util.MessageUtil;

@Service
public class IssueDashboardServiceImpl implements IssueDashboardService {

	@Autowired
	private IssueDashboardRepository issueDashboardRepository;

	@Autowired
	private IssueCategoryRepository issueCategoryRepository;

	@Autowired
	private IssuePriorityRepository issuePriorityRepository;

	@Autowired
	private IssueServerityRepository issueServerityRepository;

	@Autowired
	private IssueSourceRepository issueSourceRepository;

	@Autowired
	private IssueStatusRepository issueStatusRepository;

	@Autowired
	private IssueTypeRepository issueTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HistoryDataRepository historyDataRepository;
	@Autowired
	private UserQmsRepository userQmsRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public List<IssueDashboardDTO> getIssueByProject(int projectId) {
		List<Issue> data = issueDashboardRepository.getIssueByProject(projectId);
		List<IssueDashboardDTO> issueDTOs = new ArrayList<>();
		if (data == null) {
			return issueDTOs;
		}
		for (Issue issue : data) {
			issueDTOs.add(new IssueDashboardDTO(issue));
		}
		return issueDTOs;
	}

	@Override
	public Map<String, Object> getOptional() {
		Map<String, Object> map = new HashMap<>();
		map.put("issueCategory", issueCategoryRepository.getIssueCategory());
		map.put("issuePriority", issuePriorityRepository.getIssuePriority());
		map.put("issueServerity", issueServerityRepository.getIssueServerity());
		map.put("issueSource", issueSourceRepository.getIssueSource());
		map.put("issueStatus", issueStatusRepository.getIssueStatus());
		map.put("issueType", issueTypeRepository.getIssueType());
		return map;
	}

	@Override
	public Issue save(IssueDashboardDTO issue) {
		IssueCategory issueCategory = issueCategoryRepository.findOne(issue.getIssueCategoryId());
		IssuePriority issuePriority = issuePriorityRepository.findOne(issue.getIssuePriorityId());
		IssueServerity issueServerity = issueServerityRepository.findOne(issue.getIssueServerityId());
		IssueSource issueSource = issueSourceRepository.findOne(issue.getIssueSourceId());
		IssueStatus issueStatus = issueStatusRepository.findOne(Constants.Issue.ISSUE_STATUS_DEFAULT);
		IssueType issueType = issueTypeRepository.findOne(issue.getIssueTypeId());

		User userCreator = this.getUserByUserName(issue.getCreatedById());
		Issue issueCreate = new Issue();
		issueCreate.setIssueCategory(issueCategory);
		issueCreate.setIssuePriority(issuePriority);
		issueCreate.setIssueServerity(issueServerity);
		issueCreate.setIssueSource(issueSource);
		issueCreate.setIssueStatus(issueStatus);
		issueCreate.setIssueType(issueType);
		issueCreate.setDescription(issue.getDescription());
		issueCreate.setCause(issue.getCause());
		issueCreate.setUsersByCreatedBy(userCreator);
		issueCreate.setProjectId(issue.getProjectId());
		issueCreate.setDueDate(issue.getDueDate());
		issueCreate.setDeleted(false);
		issueCreate = issueDashboardRepository.save(issueCreate);
		Optional<Issue> issueProject = Optional.ofNullable(issueCreate);
		if (issueProject.isPresent())
			saveHistoryIssue(HistoryDataUtil.CREATE, issue.getUpdateBy(), null, new IssueHistoryDTO(issueCreate));
		return issueCreate;
	}

	@Override
	@Transactional(readOnly = true)
	public IssueDashboardDTO getIssueById(int issueId) {
		Issue issue = issueDashboardRepository.findOne(issueId);
		return new IssueDashboardDTO(issue);
	}

	/*
	 * (non-Javadoc) assginee is user id of readmine
	 * 
	 * @see com.cmc.dashboard.service.IssueDashboardService#assign(int, int, int)
	 */
	@Override
	public IssueDashboardDTO assign(int issueId, int userId, int userAssign) {

		Issue issue = issueDashboardRepository.findOne(issueId);
		if (issue == null) {
			throw new BusinessException(MessageUtil.NOT_EXIST);
		}
		IssueHistoryDTO historyDTOOld = IssueHistoryDTO.coppy(issue);
		issue.setAssignee(userId);
		IssueHistoryDTO historyDTONew = IssueHistoryDTO.coppy(issue);
		saveHistoryIssue(HistoryDataUtil.ASSIGN, userAssign, historyDTOOld, historyDTONew);
		return new IssueDashboardDTO(issueDashboardRepository.save(issue));
	}

	@Override
	public IssueDashboardDTO deleteIssue(int issueId) {
		Issue issue = issueDashboardRepository.findOne(issueId);
		issue.setDeleted(true);
		issueDashboardRepository.save(issue);
		return new IssueDashboardDTO(issue);
	}

	@Override
	public Issue updateIssue(IssueDashboardDTO issueDto) {
		IssueCategory issueCategory = issueCategoryRepository.findOne(issueDto.getIssueCategoryId());
		IssuePriority issuePriority = issuePriorityRepository.findOne(issueDto.getIssuePriorityId());
		IssueServerity issueServerity = issueServerityRepository.findOne(issueDto.getIssueServerityId());
		IssueSource issueSource = issueSourceRepository.findOne(issueDto.getIssueSourceId());
		IssueStatus issueStatus = issueStatusRepository.findOne(issueDto.getIssueStatusId());
		IssueType issueType = issueTypeRepository.findOne(issueDto.getIssueTypeId());
		Issue issueUpdate = issueDashboardRepository.findOne(issueDto.getIssueId());
		if (null == issueUpdate)
			throw new BusinessException(MessageUtil.NOT_EXIST);
		User closedBy = null;
		if (null != issueDto.getClosedById()) {
			closedBy = this.getUserByUserName(issueDto.getClosedById());
		}
		Issue issue = new Issue();
		issue.setIssueId(issueUpdate.getIssueId());
		issue.setAction(issueDto.getAction());
		issue.setAssignee(issueUpdate.getAssignee());
		issue.setCause(issueDto.getCause());
		issue.setComment(issueUpdate.getComment());
		issue.setDeleted(issueUpdate.getDeleted());
		issue.setDescription(issueDto.getDescription());
		issue.setDueDate(issueDto.getDueDate());
		issue.setImpact(issueUpdate.getImpact());
		issue.setProjectId(issueUpdate.getProjectId());
		issue.setUsersByApprovedBy(issueUpdate.getUsersByApprovedBy());
		issue.setUsersByCreatedBy(issueUpdate.getUsersByCreatedBy());
		issue.setUsersByClosedBy(closedBy);
		issue.setOpenDate(issueUpdate.getOpenDate());
		issue.setIssueCategory(issueCategory);
		issue.setIssuePriority(issuePriority);
		issue.setIssueServerity(issueServerity);
		issue.setIssueSource(issueSource);
		issue.setIssueStatus(issueStatus);
		issue.setIssueType(issueType);
		issue.setSuggestionOfQa(issueDto.getSuggestionOfQa());
//		saveHistoryIssue(HistoryDataUtil.UPDATE, issueDto.getUpdateBy(), new IssueHistoryDTO(issueUpdate),
//				new IssueHistoryDTO(issue));
		return issueDashboardRepository.save(issue);
	}

	private void saveHistoryIssue(String action, int updateBy, IssueHistoryDTO issueHistoryDTOOld,
			IssueHistoryDTO issueHistoryDTONews) {
		if (action.equals(HistoryDataUtil.ASSIGN)) {
			try {
				// user id of qms get user name
//			Optional<QmsUser> userAssign = Optional
//					.ofNullable(userQmsRepository.findOne(Integer.valueOf(issueHistoryDTONews.getAssignee())));
				Optional<User> userAssign = Optional
						.ofNullable(userRepository.findOne(Integer.valueOf(issueHistoryDTONews.getAssignee())));
				if (userAssign.isPresent()) {
					// issueHistoryDTONews.setAssignee(userAssign.get().getFirstname()+"
					// "+userAssign.get().getLastname());
					issueHistoryDTONews.setAssignee(userAssign.get().getFullName());
				}
			} catch (Exception e) {

			}
		}
//		User user =this.getUserByUserName(updateBy);
//		Optional<User> user = Optional.ofNullable(this.getUserByUserName(updateBy));
//		User userCreator = null;
//		try {
//			userCreator = userRepository.findOne(updateBy);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Optional<User> user = Optional.ofNullable(this.getUserByUserName(updateBy));
		List<HistoryData> historyDatas;
		historyDatas = HistoryDataUtil.getChangeHistorys(Issue.class.getSimpleName(),
				Long.valueOf(issueHistoryDTONews.getIssueId()), user.isPresent() ? updateBy : null,
				user.isPresent() ? user.get().getUserName() : null, action, issueHistoryDTOOld, issueHistoryDTONews);
//		historyDatas = HistoryDataUtil.getChangeHistorys(Issue.class.getSimpleName(), Long.valueOf(issueHistoryDTONews.getIssueId()), userCreator != null ? updateBy : null,
//				userCreator != null ? userCreator.getUserName() : null, action, issueHistoryDTOOld, issueHistoryDTONews);
		if (!historyDatas.isEmpty())
			historyDataRepository.save(historyDatas);

	}

	/**
	 * 
	 * get user id dashboard from user id of readmine
	 * 
	 * @author: LXLinh
	 */
//	private User getUserByUserName(int id) {
//		Optional<User>  user=Optional.empty();
//		Optional<QmsUser> userQms=Optional.ofNullable(userQmsRepository.findOne(id));
//		if(userQms.isPresent())
//		{
//			user=Optional.ofNullable( userRepository.findByUserName(userQms.get().getLogin())); 
//		}
//		return user.isPresent()? user.get():null;
//	}
	private User getUserByUserName(int id) {
		Optional<User> user = Optional.ofNullable(userRepository.findOne(id));
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public List<IssueDashboardDTO> getAllIssue(int projectId, int month, int year, int groupId) {
		String rexGroup = groupId == -1 ? ".*" : ("^" + groupId + "$");
		String rexProject = projectId == -1 ? ".*" : ("^" + projectId + "$");
		String rexMonth = month == -1 ? ".*" : ("^" + month + "$");
		String rexYear = year == -1 ? ".*" : ("^" + year + "$");
		List<Issue> data = issueDashboardRepository.getAllIssue(rexProject, rexMonth, rexYear, rexGroup);
		List<IssueDashboardDTO> issueDTOs = new ArrayList<>();
		if (data == null) {
			return issueDTOs;
		}
		String nameProject=null;
		for (Issue issue : data) {
			IssueDashboardDTO issues = new IssueDashboardDTO(issue);
			try {
			 nameProject = projectRepository.findById(issue.getProjectId()).getName();
			issues.setProjectName(nameProject);
			issueDTOs.add(issues);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return issueDTOs;
	}

}
