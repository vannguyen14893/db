package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: May 11, 2018
 */
public class ProjectTimesheetsDto {
	private int userId;
	private String username;
	private float yesterdayLogtime;
	private float thisWeekLogtime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getYesterdayLogtime() {
		return yesterdayLogtime;
	}

	public void setYesterdayLogtime(float yesterdayLogtime) {
		this.yesterdayLogtime = yesterdayLogtime;
	}

	public float getThisWeekLogtime() {
		return thisWeekLogtime;
	}

	public void setThisWeekLogtime(float thisWeekLogtime) {
		this.thisWeekLogtime = thisWeekLogtime;
	}

}
