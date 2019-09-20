package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: May 25, 2018
 */
public class TeamHourByActivityDto {
	private int activityId;
	private String activityName;
	private float teamHour;

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public float getTeamHour() {
		return teamHour;
	}

	public void setTeamHour(float teamHour) {
		this.teamHour = teamHour;
	}

}
