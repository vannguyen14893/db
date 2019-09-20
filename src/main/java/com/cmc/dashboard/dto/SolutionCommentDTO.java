package com.cmc.dashboard.dto;
import java.util.Date;


import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.model.SolutionComment;
import com.cmc.dashboard.model.User;

public class SolutionCommentDTO {
	private Integer id;
	private Integer solutionId;
	private String comment;
	private Date createDate;
	private Integer createBy;
	private String fullName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SolutionCommentDTO() {
		super();
	}

	public SolutionCommentDTO(SolutionComment solutionComment) {
		super();
		this.id = solutionComment.getId();
		this.solutionId = solutionComment.getSolutionId().getId();
		this.comment = solutionComment.getComment();
		this.createDate = solutionComment.getCreateDate();
		this.createBy = solutionComment.getCreateBy().getUserId();
		this.fullName = solutionComment.getCreateBy().getFullName();
	}
	
	public SolutionComment toEntity() {
		SolutionComment commentCreate = new SolutionComment();
		commentCreate.setComment(this.comment);
		commentCreate.setCreateDate(new Date());
		commentCreate.setCreateBy(new User(this.createBy));
		commentCreate.setSolutionId(new Solution(this.solutionId));
		commentCreate.setIsDeleted(false);
		return commentCreate;
	}
}
