package com.cmc.dashboard.dto;

import java.util.HashMap;

import com.cmc.dashboard.util.Constants;

public class UserTimeSheetDTO {

	private int id;
	private String username;
	private HashMap<String, Float> lastWeek = new HashMap<>();
	private HashMap<String, Float> thisWeek = new HashMap<>();

	public UserTimeSheetDTO() {
		super();
		lastWeek.put(Constants.WeekDay.MONDAY, 0f);
		lastWeek.put(Constants.WeekDay.TUESDAY, 0f);
		lastWeek.put(Constants.WeekDay.WEDNESDAY, 0f);
		lastWeek.put(Constants.WeekDay.THURSDAY, 0f);
		lastWeek.put(Constants.WeekDay.FRIDAY, 0f);
		lastWeek.put(Constants.WeekDay.SATURDAY, 0f);
		lastWeek.put(Constants.WeekDay.SUNDAY, 0f);
		
		thisWeek.put(Constants.WeekDay.MONDAY, 0f);
		thisWeek.put(Constants.WeekDay.TUESDAY, 0f);
		thisWeek.put(Constants.WeekDay.WEDNESDAY, 0f);
		thisWeek.put(Constants.WeekDay.THURSDAY, 0f);
		thisWeek.put(Constants.WeekDay.FRIDAY, 0f);
		thisWeek.put(Constants.WeekDay.SATURDAY, 0f);
		thisWeek.put(Constants.WeekDay.SUNDAY, 0f);
	}

	public int getId() {
		return id;
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashMap<String, Float> getLastWeek() {
		return lastWeek;
	}

	public void setLastWeek(HashMap<String, Float> lastWeek) {
		this.lastWeek = lastWeek;
	}

	public HashMap<String, Float> getThisWeek() {
		return thisWeek;
	}

	public void setThisWeek(HashMap<String, Float> thisWeek) {
		this.thisWeek = thisWeek;
	}

}
