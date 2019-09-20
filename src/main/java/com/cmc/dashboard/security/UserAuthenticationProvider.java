package com.cmc.dashboard.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.role.UserRole;
import com.cmc.dashboard.service.GroupService;
import com.cmc.dashboard.service.UserService;
import com.cmc.dashboard.service.qms.QmsUserService;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author longl
 */
@Component
public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/**
	 * The Logger for this class.
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserService userService;
	@Autowired
	private QmsUserService qmsUserService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private PasswordEncoder passEncode;
	/**
	 * A Spring Security UserDetailsService implementation based upon the Account
	 * entity model.
	 */
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) {
		log.debug("> additionalAuthenticationChecks");

//		String[] pwds = userDetails.getPassword().split(" ");
		//String pwEnter = (String)token.getCredentials();
//		String password = userDetails.getPassword();
//		String username = userDetails.getUsername();
//		if(!passEncode.matches(pwEnter, password)) {
//			throw new BadCredentialsException("Password is invalid.");
//		}
//		if (userService.findByUserName(username).getStatus() == 0) {
//			throw new BadCredentialsException("Account is inactive.");
//		}
		// user already exists in redmine_db but not in dashboard create one in
		// dashboard
//		if (userService.findByUserName(userDetails.getUsername()) == null) {
//			createcreateUserAccounInDashboard(userDetails);
//		}

		log.debug("< additionalAuthenticationChecks");
	}

//	private void createcreateUserAccounInDashboard(UserDetails userDetails) {
//
//		QmsUser qmsUser = qmsUserService.findUserByLogin(userDetails.getUsername());
//		String[] pwds = userDetails.getPassword().split(" ");
//		User user = new User();
//		user.setUserName(userDetails.getUsername());
//		user.setSalt(pwds[1]);
//		user.setHashedPassword(pwds[0]);
//		user.setStatus((byte) 1);
//		user.setEmail(qmsUser.getQmsEmailAddress().getAddress());
//		user.setFullName(qmsUser.getLastname() + " " + qmsUser.getFirstname());
//
//		QmsUser findUserGroup = qmsUserService.findUserGroup(qmsUser.getId());
//
//		if (findUserGroup != null && findUserGroup.getLastname() != null) {
//			Group dashboardGroup = groupService.findByGroupName(findUserGroup.getLastname());
//			if (dashboardGroup != null) {
//				user.setGroup(dashboardGroup);
//			}
//		}
//		if (user.getGroup() == null) {
//			// default group
//			Group member = groupService.findByGroupName(UserRole.MEMBER.toString());
//			user.setGroup(member);
//		}
//		userService.createUser(user);
//
//	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token) {
		logger.debug("> retrieveUser");
		UserDetails userDetails = null;
        try {
		 userDetails = userDetailsService.loadUserByUsername(username);
		 System.out.println(userDetails.isEnabled());
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
		logger.debug("< retrieveUser");
		return userDetails;
	}

	/**
	 * So sanh password request va trong db
	 *
	 * @param reqPpwd
	 *            mat khau tu request cua user
	 * @param salt
	 *            chuoi dung de ma hoa mat khau
	 * @param dbPass
	 *            mat khau tu database
	 * @return
	 */
//	private boolean isValidPassword(String reqPpwd, String salt, String dbPass) {
//		String hashPass = MethodUtil.sha1(salt.concat(MethodUtil.sha1(reqPpwd)));
//		return hashPass.equals(dbPass);
//	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		httpSession.setAttribute(Constants.SESSION_USER, user);
		return super.createSuccessAuthentication(principal, authentication, user);
	}

}
