package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.model.User;

public class UserTableDTO {
	private int totalPages;
	
	private int currentPage;
	
	private long totalRecords;
	
	private List<User> userData;
	
	private List<Role> roles;
	
	private List<Group> groups;
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<User> getUserData() {
		return userData;
	}

	public void setUserData(List<User> userData) {
		this.userData = userData;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	
}
