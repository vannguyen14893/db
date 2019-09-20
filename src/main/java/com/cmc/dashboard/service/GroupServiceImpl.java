package com.cmc.dashboard.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.GroupManagerDTO;
import com.cmc.dashboard.dto.HistoryUserGroupDTO;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.GroupManager;
import com.cmc.dashboard.model.GroupUser;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupManagerRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.GroupUserRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserQmsRepository userQmsRepository;

	@Autowired
	private GroupUserRepository groupUserRepository;

	@Autowired
	private GroupManagerRepository groupManagerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ManPowerService manPowerService;

	@Transactional(readOnly = true)
	@Override
	public List<GroupDTO> getAllGroup() {
		List<GroupDTO> listDu = new ArrayList<>();
		List<Object> listDuObj = groupRepository.getListDu();
		for (Object du : listDuObj) {
			Object[] temp = (Object[]) du;
			listDu.add(new GroupDTO(Integer.parseInt(temp[0].toString()), temp[1].toString()));
		}
		return listDu;
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Transactional(readOnly = true)
	@Override
	public Group getById(int id) {
		return groupRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Integer> getGroupIdByQmsUserId(int userId) {
		return userQmsRepository.getGroupIdByQmsUserId(userId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Group> findMaxRank(List<Integer> groupIds) {
		return groupRepository.findMaxRank(groupIds);
	}

	@Transactional(readOnly = true)
	@Override
	public Group findByGroupName(String groupName) {
		return groupRepository.findByName(groupName);
	}

	@Transactional(readOnly = true)
	@Override
	public Group findById(int id) {
		Group group = groupRepository.findOne(id);
		return group;
	}

	@Override
	public boolean insert(String groupName, String description) {
		// TODO Auto-generated method stub
		Group findByName = groupRepository.findByName(groupName);
		if (findByName != null) {
			new ResponseEntity<>("groupexisted", HttpStatus.OK);
			return false;
		} else {
			Group newGroup = new Group();
			newGroup.setGroupName(groupName);
			newGroup.setGroupDesc(description);
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			newGroup.setCreatedOn(sqlDate);
			newGroup.setStatus(false);
			newGroup.setDevelopmentUnit(false);
			newGroup.setInternalDu(false);
			groupRepository.save(newGroup);
			return true;
		}
	}

	@Override
	public boolean edit(int id, String groupName, String description, boolean status, boolean developmentUnit,
			boolean internalDu) {
		// TODO Auto-generated method stub
		Group findByName = groupRepository.findByName(groupName);
		if (findByName != null) {
			if (findByName.getGroupId() != id) {
				new ResponseEntity<Object>("groupexist", HttpStatus.OK);
				return false;
			}
		}
		Group fineById = groupRepository.findById(id);
		fineById.setGroupName(groupName);
		fineById.setGroupDesc(description);
		fineById.setStatus(status);
		fineById.setDevelopmentUnit(developmentUnit);
		fineById.setInternalDu(internalDu);
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		fineById.setUpdatedOn(sqlDate);
		groupRepository.save(fineById);
		return true;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		groupRepository.delete(id);
		return true;
	}

	@Override
	public List<Group> getListGroups() {
		// TODO Auto-generated method stub
		return groupRepository.getListGroup();
	}

	@Override
	public boolean saveHistoryGroup(int userId, List<HistoryUserGroupDTO> listHistoryUserGroup, String type) {
		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		GroupUser oldGroupUser = null;
		GroupUser updateGroupUser = null;
		LocalDate localDate = LocalDate.now();
		try {
			for (HistoryUserGroupDTO dto : listHistoryUserGroup) {
				oldGroupUser = groupUserRepository.findOne(dto.getIdHistory());
				Group group = groupRepository.findByName(dto.getDuName());
				if (oldGroupUser != null) {
					if ((oldGroupUser.getUpdateAt() == null && dto.getUpdateDate() != null
							&& "".equals(dto.getUpdateDate()) == false)
							|| (oldGroupUser.getUpdateAt() != null && dto.getUpdateDate() != null
									&& "".equals(dto.getUpdateDate()) == false && formatTime
											.format(oldGroupUser.getUpdateAt()).equals(dto.getUpdateDate()) == false)) {
						updateGroupUser = new GroupUser();
						updateGroupUser.setStartDate(formatDate.parse(dto.getStartDate()));
						updateGroupUser
								.setEndDate("".equals(dto.getEndDate()) ? null : formatDate.parse(dto.getEndDate()));
						updateGroupUser.setUpdateAt(
								"".equals(dto.getUpdateDate()) ? null : formatTime.parse(dto.getUpdateDate()));
						updateGroupUser.setGroupUserId(oldGroupUser.getGroupUserId());
						updateGroupUser.setGroupId(oldGroupUser.getGroupId());
						updateGroupUser.setUserId(oldGroupUser.getUserId());
						groupUserRepository.save(updateGroupUser);
						if (group.isDevelopmentUnit())
							manPowerService.updateManPowerByGroupUser(updateGroupUser, oldGroupUser);
					}

				} else {
					updateGroupUser = new GroupUser(dto.getIdHistory(), userId, formatDate.parse(dto.getStartDate()),
							(dto.getEndDate() != null && "".equals(dto.getEndDate()) == false)
									? formatDate.parse(dto.getEndDate())
									: null,
							group.getGroupId(), (dto.getUpdateDate() == null || "".equals(dto.getUpdateDate())) ? null
									: formatTime.parse(dto.getUpdateDate()));
					groupUserRepository.save(updateGroupUser);
					if (group.isDevelopmentUnit())
						manPowerService.updateManPowerByGroupUser(updateGroupUser, null);
				}
			}
			if (localDate.isEqual(localDate.withDayOfMonth(1)) && "update".equals(type))
				manPowerService.insertManPower3rdMonth(localDate, listHistoryUserGroup, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUserGroup(int userId, List<HistoryUserGroupDTO> listHistoryUserGroup) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public void updateGroupManager(GroupManagerDTO[] array) {
//		Group parentGroup = null;
//		List<Integer> listDelete = null;
//		List<Integer> listInsert = null;
//		List<Integer> listNew = null;
//		List<Integer> listOld = null;
//		for (GroupManagerDTO groupManager : array) {
//			listNew=new ArrayList<Integer>();
//			parentGroup = groupRepository.findByName(groupManager.getName());
//			if (parentGroup == null) {
//				parentGroup = new Group();
//				parentGroup.setGroupDesc(groupManager.getDesciption());
//				parentGroup.setGroupName(groupManager.getName());
//				parentGroup = groupRepository.saveAndFlush(parentGroup);
//			}
//			this.updateManager(parentGroup.getGroupId(), groupManager.getListChild());
//
//		}
//	}
	@Override
	public void updateGroupManager(int parentId, GroupManagerDTO[] listChild) {
		if (listChild != null && listChild.length > 0) {
			Group childGroup = null;
			CalculateClass cal = new CalculateClass();
			List<Integer> listDelete = null;
			List<Integer> listInsert = null;
			User user = null;
			GroupManagerDTO[] array = null;
			List<Integer> listOld = null;
			List<Integer> listNew = null;
			List<Integer> listNewGroup = new ArrayList<Integer>();
			List<Integer> listOldGroup = groupManagerRepository.listUserIdByGroup(parentId);
			try {
				for (GroupManagerDTO gm : listChild) {
					listNew = new ArrayList<Integer>();
					childGroup = groupRepository.findByName(gm.getName());
					if (childGroup == null) {
						childGroup = new Group();
						childGroup.setGroupName(gm.getName());
						childGroup.setGroupDesc(gm.getDesciption());
						childGroup.setParentId(parentId);
						childGroup = groupRepository.saveAndFlush(childGroup);
					} else {
						childGroup.setParentId(parentId);
						groupRepository.save(childGroup);
					}
					listOld = groupManagerRepository.listUserIdByGroup(childGroup.getGroupId());
					for (UserDTO us : gm.getManager())
						listNew.add(userRepository.findByUserName(us.getUserName()).getUserId());
					listDelete = cal.differentNumber(listOld, listNew);
					listInsert = cal.differentNumber(listNew, listOld);
					// Xóa manager cũ
					if (listDelete.size() > 0)
						groupManagerRepository.deleteGroupManager(listDelete);
					for (Integer num : listInsert) {
						user = userRepository.findOne(num);
						groupManagerRepository.save(new GroupManager(childGroup, user));
					}
					if (gm.getListChild() != null) {
						array = new GroupManagerDTO[gm.getListChild().size()];
						gm.getListChild().toArray(array);
						this.updateGroupManager(childGroup.getGroupId(), array);
					}
					listNewGroup.add(childGroup.getGroupId());
				}
				listDelete = cal.differentNumber(listOldGroup, listNewGroup);

				for (Integer groupId : listDelete) {
					if (parentId != 0) {
						childGroup = groupRepository.findById(groupId);
						childGroup.setParentId(0);
						groupRepository.save(childGroup);
					} else
						groupRepository.delete(groupId);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
//		List<Integer> listOld = groupManagerRepository.listUserIdByGroup(groupId)
		}
	}

}
