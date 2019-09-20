package com.cmc.dashboard.dto;

public class LastActivityDto {
	private String creationTracker;
	private int createdOn;
	private String updateTracker;
	private String oldStatus;
	private String newStatus;
	private int updatedOn;

	public String getCreationTracker() {
		return creationTracker;
	}

	public void setCreationTracker(String creationTracker) {
		this.creationTracker = creationTracker;
	}

	public int getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(int createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdateTracker() {
		return updateTracker;
	}

	public void setUpdateTracker(String updateTracker) {
		this.updateTracker = updateTracker;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public int getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(int updatedOn) {
		this.updatedOn = updatedOn;
	}

}
