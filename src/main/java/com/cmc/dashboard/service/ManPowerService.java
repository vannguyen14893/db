package com.cmc.dashboard.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.cmc.dashboard.dto.HistoryUserGroupDTO;
import com.cmc.dashboard.dto.ManPowerDTO;
import com.cmc.dashboard.dto.ManpowerOlderDTO;
import com.cmc.dashboard.dto.UserManPowerDTO;
import com.cmc.dashboard.model.GroupUser;
import com.cmc.dashboard.model.ManPower;

public interface ManPowerService {
	List<UserManPowerDTO> getManPowerByUserIdOfAllDU(String time);
	ManPower update(ManPower mp);
	
	//PNTHANH
	public boolean getManPowerFromAms(ManPowerDTO[] manPowerDTOs, String manMonth);
	public boolean getManpower();
	public boolean getManpowerOlderFromAms(ManpowerOlderDTO[] manpowerOlderDTOs);
	public void generateManpowerAuto();
	public void deleteManpowerFromDateToDate(int userId,Date fromDate, Date toDate);
	public void deleteManPowerMonth(int userId,int month,int year);
	public void updateManPowerByGroupUser(GroupUser groupUser,GroupUser oldGroupUser);
	public void insertManPower3rdMonth(LocalDate currenDate, List<HistoryUserGroupDTO> listHistoryUserGroup,int userId);
	public void updateManPowerCustomDate(int userId,Date startDate,Date endDate);
}
