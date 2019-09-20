
package com.cmc.dashboard.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.service.UserService;

/**
 * 
 * @author longl
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserQmsRepository userQmsRepository;

	@Autowired
	UserService userService;

	@Autowired
	public CustomUserDetailsService(UserQmsRepository userQmsRepository) {
		this.userQmsRepository = userQmsRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {

//		QmsUser account = null;
//		account = userQmsRepository.findUserByLogin(username);
//
//		if (account == null) {
//			throw new UsernameNotFoundException("User " + username + " not found.");
//		}
		
//		User user = findByUserName(account.getLogin());
		User findByUserName = userService.findByUserName(username);
		if(findByUserName ==  null) {
			User user = new User();
			user.setUserName(username);
			findByUserName = userService.saveAndFlush(user);
		} else {
			
		}
		Collection<GrantedAuthority> grantedAuthorities = getAuthorities(findByUserName);

		return new org.springframework.security.core.userdetails.User(
				findByUserName.getUserName(),
				null,
				findByUserName.getStatus() == 1,
				true,
				true, true, grantedAuthorities);
	}



	/**
	 * Cá»™ng chuá»—i salt vÃ o cÃ¹ng vá»›i pwd truyá»�n sang
	 *
	 * @param pwd
	 * @param salt
	 * @return
	 */
//	private String buildPasswordWithSalt(String pwd, String salt) {
//		return pwd + " " + salt;
//	}

	private final Collection<GrantedAuthority> getAuthorities(User user) {
		
		if(user == null) {
			return Collections.emptyList();
		}
		final List<GrantedAuthority> authorities = new ArrayList<>();
		List<RolePermission> listPermission = null;
		try {
			listPermission = userService.listRolePermissionOfUser(user.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		if (listPermission != null)
			for (RolePermission item : listPermission) {
				if (item.getEnable() == 1) {
					authorities.add(new SimpleGrantedAuthority(item.getPermission().getPermissionName()));
				}
			}
		return authorities;
	}

	/**
	 * Get user info from dashboard
	 * 
	 * @param userName
	 * @return
	 */
	private User findByUserName(String userName) {
		
		return userService.findByUserName(userName);
	}

}
