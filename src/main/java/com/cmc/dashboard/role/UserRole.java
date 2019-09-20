package com.cmc.dashboard.role;

public enum UserRole {
	MEMBER("MEMBER");
	private String role;
	UserRole(String role ){
		this.role = role;
	}
	
	@Override
	public String toString() {
		return this.role;
	}
}
