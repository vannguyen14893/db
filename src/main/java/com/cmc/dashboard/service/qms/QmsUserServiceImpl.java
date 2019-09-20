package com.cmc.dashboard.service.qms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserUtilizationDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.role.UserRole;

@Service
public class QmsUserServiceImpl implements QmsUserService {

	@Autowired
	UserQmsRepository qmsUserRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserQmsRepository userQmsRepository;
	
	@Autowired
	private UserRepository userDashBoardRepository;

	@Override
	public UserUtilizationDTO loadInfo(String username) {
		UserUtilizationDTO userInfo = null;

		QmsUser qmsUser = qmsUserRepository.findUserByLogin(username);

		if (qmsUser == null) {
			throw new UsernameNotFoundException("User not found exception");
		}
		userInfo = new UserUtilizationDTO();
		userInfo.setId(qmsUser.getId());
		userInfo.setLogin(qmsUser.getLogin());
		userInfo.setFirstname(qmsUser.getFirstname());
		userInfo.setLastname(qmsUser.getLastname());

		List<Integer> listIds = userQmsRepository.getGroupIdByQmsUserId(qmsUser.getId());
		if (!listIds.isEmpty()) {
			List<Group> groups = groupRepository.findMaxRank(listIds);
			if (!groups.isEmpty()) {
				userInfo.setRole(groups.get(0).getGroupName());
			}
		}
		if (userInfo.getRole() == null) {
			// default role
			userInfo.setRole(UserRole.MEMBER.toString());
		}

		return userInfo;

	}

	/**
	 * get infomation of user by id
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDTO getDeliverUnit(int userId) {
		String name = userQmsRepository.getNameUserById(userId);
		String du = userQmsRepository.getDeliveryUnitByUserId(userId);
		UserDTO userDTO = new UserDTO();
		userDTO.setFullName(name);
		userDTO.setDeliveryUnit(du);
		return userDTO;
	}

	@Transactional(readOnly = true)
	@Override
	public QmsUser findUserByLogin(String username) {
		return qmsUserRepository.findUserByLogin(username);
	}

	@Transactional(readOnly = true)
	@Override
	public QmsUser findUserGroup(int userId) {
		return qmsUserRepository.findGroup(userId);
	}

	@Override
	public QmsUser getUserById(int id) {
		return userQmsRepository.findOne(id);
	}

  /* (non-Javadoc)
   * @see com.cmc.dashboard.service.qms.QmsUserService#isExistUserId(java.lang.Integer)
   */
  @Override
  public boolean isExistUserId(int userId) {
    return userDashBoardRepository.isExistUserId(userId);
  }

}
