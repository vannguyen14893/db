package com.cmc.dashboard.service.qms;

import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserUtilizationDTO;
import com.cmc.dashboard.qms.model.QmsUser;

public interface QmsUserService {

	public UserUtilizationDTO loadInfo(String username);

	public UserDTO getDeliverUnit(int userId);

	public QmsUser findUserByLogin(String username);

	public QmsUser findUserGroup(int userId);

	public QmsUser getUserById(int id);

  /**
   * check exist user id
   * 
   * @param userId
   * @return boolean 
   * @author: NNDuy
   */
  public boolean isExistUserId(int userId);

}
