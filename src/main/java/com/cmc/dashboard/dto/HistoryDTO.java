package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.List;

import com.cmc.dashboard.model.HistoryData;

public class HistoryDTO {
	
	private Date updateOn;
	private UserInfoDTO userInfoDTO;
	private List<HistoryData> historyDatas;
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	
	public UserInfoDTO getUserInfoDTO() {
		return userInfoDTO;
	}
	public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
		this.userInfoDTO = userInfoDTO;
	}
	public List<HistoryData> getHistoryDatas() {
		return historyDatas;
	}
	public void setHistoryDatas(List<HistoryData> historyDatas) {
		this.historyDatas = historyDatas;
	}
	
}
