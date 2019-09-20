package com.cmc.dashboard.dto;

import java.util.List;

public class ManpowerOlderDTO {
	private String monthOff;
	private List<ListUserInfoDTO> listInfoUser;
	public String getMonthOff() {
		return monthOff;
	}
	public void setMonthOff(String monthOff) {
		this.monthOff = monthOff;
	}
	public List<ListUserInfoDTO> getListInfoUser() {
		return listInfoUser;
	}
	public void setListInfoUser(List<ListUserInfoDTO> listInfoUser) {
		this.listInfoUser = listInfoUser;
	}
}
