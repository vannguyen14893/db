package com.cmc.dashboard.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.dto.AuthorizationUtilzationWrapper;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.service.RolePermissionService;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MessageUtil;

@Controller
@SessionAttributes("message")
public class PermissionController {

	@Autowired
	PermissionService permissionService;

	@Autowired
	RolePermissionService rolePermissionService;

	/**
	 * Send Permission Info to Permission Manage's Page
	 *
	 * @param model
	 * @param session
	 * @param status
	 * @return String
	 * @author: ngocdv
	 */
	@RequestMapping(value = { "/api/permission/set" })
	public ModelAndView index(Model model, HttpSession session, SessionStatus status) {

		Object user = session.getAttribute(Constants.SESSION_USER);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
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
			} else {
				return mav;
			}
		}
		return mav;
	}

	/**
	 * Get data and call Service to save RolePermission
	 *
	 * @param rolePermissions
	 * @param model
	 * @return String
	 * @author: ngocdv
	 */
	@RequestMapping(value = "/permission/update", method = RequestMethod.POST)
	public String update(@ModelAttribute(value = "rolePermissions") AuthorizationUtilzationWrapper rolePermissions,
			Model model) {
		String rMessage = "";
		rMessage = rolePermissionService.saveAllRolePermission(rolePermissions.getRolePermissions());
		String message;
		if (rMessage.equals(MessageUtil.SAVE_SUCCESS)) {
			message = "Successfully save!";
		} else {
			message = "Error save, check again";
		}
		model.addAttribute("message", message);
		return "redirect:/api/permission/set";
	}

	/**
	 * Log Out
	 *
	 * @return String
	 * @author: ngocdv
	 */
	@RequestMapping(value = "/admin/logout")
	public String logOut(HttpSession session) {
		session.removeAttribute(Constants.SESSION_USER);
		session.invalidate();
		return "redirect:/";
	}

}