package com.cmc.dashboard.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.dto.AuthorizationUtilzationWrapper;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.service.RolePermissionService;
import com.cmc.dashboard.util.Constants;

@Controller
public class LoginController {
	@Autowired
	PermissionService permissionService;

	@Autowired
	RolePermissionService rolePermissionService;

	@RequestMapping(value = "/")
	private ModelAndView home(Model model, HttpSession session, SessionStatus status) {
		Object user = session.getAttribute(Constants.SESSION_USER);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		if (user instanceof UserDetails) {
			Collection<? extends GrantedAuthority> auths = ((UserDetails) user).getAuthorities();
			if (auths != null && auths.stream()
					.anyMatch(ga -> ga.getAuthority().equals(com.cmc.dashboard.util.Role.ADMIN.getName()))) {
				status.setComplete();
				session.removeAttribute("message");
				List<AuthorizationUtilzationDTO> authorizationUtilzationDTOs = permissionService.getRolePermission();
				List<Role> roles = permissionService.getAllRole();
				AuthorizationUtilzationWrapper authorizationUtilzationWrapper = new AuthorizationUtilzationWrapper(
						rolePermissionService.getAllRolePermission());
				model.addAttribute("permissionTotals", authorizationUtilzationDTOs);
				model.addAttribute("roles", roles);
				model.addAttribute("rolePermissions", authorizationUtilzationWrapper);
				mav.setViewName("admin");
				return mav;
			}
		}
		return mav;
	}

}
