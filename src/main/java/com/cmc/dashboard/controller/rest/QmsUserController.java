package com.cmc.dashboard.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.service.UserService;
import com.cmc.dashboard.service.qms.QmsUserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class QmsUserController {

	@Autowired
	private QmsUserService qmsUserService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserService userService;
	/**
	 * Login by user
	 *
	 * @author: LVLong
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	@ResponseBody
	private ResponseEntity<?> getUserInfo(@RequestParam String username) {
		// Get user from Principal
//		Object princaipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (princaipal instanceof UserDetails) {
//			username = ((UserDetails) princaipal).getUsername();
//		} else {
//			username = String.valueOf(princaipal);
//		}
		//UserUtilizationDTO userInfo = qmsUserService.loadInfo(username);
		UserInfoDTO userInfo = null;
		try {
			userInfo =  userService.loadInfo(username);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (userInfo == null) {
			return new ResponseEntity<>(RestResponse.error(HttpStatus.NOT_FOUND, "User not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(RestResponse.success(userInfo), HttpStatus.OK);
	}

	@RequestMapping(value = "/oauth/logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String tokenValue = authHeader.substring(7, authHeader.length()).trim();
			boolean isRevoked = consumerTokenServices.revokeToken(tokenValue);
			if (isRevoked) {
				httpSession.invalidate();
				return new ResponseEntity<>(RestResponse.success("Logout successful"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot logout user"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(RestResponse.error(HttpStatus.BAD_REQUEST, "Token not found in request"),
					HttpStatus.BAD_REQUEST);
		}
	}

}
