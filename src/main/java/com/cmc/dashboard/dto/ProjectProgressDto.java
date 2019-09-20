package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: May 4, 2018
 */
public class ProjectProgressDto {
	private float timelines;
	private float timelog;
	private float workingProgress;

	public ProjectProgressDto(float timelines, float timelog, float workingProgress) {
		this.timelines = timelines;
		this.timelog = timelog;
		this.workingProgress = workingProgress;
	}

	public float getTimelines() {
		return timelines;
	}

	public void setTimelines(float timelines) {
		this.timelines = timelines;
	}

	public float getTimelog() {
		return timelog;
	}

	public void setTimelog(float timelog) {
		this.timelog = timelog;
	}

	public float getWorkingProgress() {
		return workingProgress;
	}

	public void setWorkingProgress(float workingProgress) {
		this.workingProgress = workingProgress;
	}

}
