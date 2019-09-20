package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.GroupManagerDTO;
import com.cmc.dashboard.dto.HistoryUserGroupDTO;
import com.cmc.dashboard.model.Group;

public interface GroupService {
	public List<GroupDTO> getAllGroup();
	
	public List<Group> getListGroups();
 
	
	public Group getById(int id);

	public List<Integer> getGroupIdByQmsUserId(int userId);
	
	public List<Group> findMaxRank(List<Integer> groupIds);

	public Group findByGroupName(String string);
	
	public Group findById(int id);
	
	public boolean insert(String groupName,String description);
	
	public boolean edit(int id,String groupName,String description,boolean status,boolean developmentUnit,boolean internalDu);
	
	public boolean delete(int id);
	
	public Group save(Group group);
	
    public  boolean saveHistoryGroup(int userId, List<HistoryUserGroupDTO> listHistoryUserGroup,String type);
	
    public boolean updateUserGroup(int userId,List<HistoryUserGroupDTO> listHistoryUserGroup);
    
    public void updateGroupManager(int id,GroupManagerDTO[] array);
}
