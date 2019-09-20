package com.cmc.dashboard.dto;

import javax.validation.constraints.NotNull;

public class IssueAssign {
	@NotNull
	private Integer issueId;
	
	@NotNull
	private Integer userId;

	@NotNull
	private Integer userAssign;
	
	public IssueAssign() {
		this.issueId = 0;
		this.userId = 0;
		this.userAssign=0;
	}

	public IssueAssign(Integer issueId, Integer userId,Integer userAssign) {
		super();
		this.issueId = issueId;
		this.userId = userId;
		this.userAssign=userAssign;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserAssign() {
		return userAssign;
	}

	public void setUserAssign(Integer userAssign) {
		this.userAssign = userAssign;
	}
	
	
	
}
