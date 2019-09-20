package com.cmc.dashboard.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.repository.RolePermissionRepository;
import com.cmc.dashboard.repository.UserRepository;

/**
 * @author longl
 * @Modifier: NvCong
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterRequest extends AbstractAuthenticationProcessingFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(CorsFilterRequest.class);

	public CorsFilterRequest() {
		super("/api/**");
		log.info("SimpleCORSFilter init");
	}

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RolePermissionRepository rolePermissionRepo;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
		String url = request.getRequestURI();
		String[] urlOfRole = url.split("/api");
		if (url.startsWith("//oauth/token") || url.startsWith("//api/me") || url.startsWith("//api/me")
				|| url.startsWith("/api/user/ams/save")
				|| url.startsWith("/api/user/add/role")
				|| url.startsWith("/api/user/insert") || url.startsWith("/api/user/remove/role")
				|| url.startsWith("/api/add/role/permission") || url.startsWith("/api/role/removeRolePermission")
				|| url.startsWith("/api/permission/update") || url.startsWith("/api/permission/save")
				|| url.startsWith("/api/permission/id") || url.startsWith("/api/group/update")
				|| url.startsWith("/api/role/update") || url.startsWith("/api/update")
				|| url.startsWith("/api/project/group") || url.startsWith("/api/project/insert-evaluate-project")
				|| url.startsWith("/api/project/edit-evaluate-project") || url.startsWith("/api/project/group")
				|| url.startsWith("/api/project/changeStatus") || url.startsWith("/api/project/auth/css/update")
				|| url.startsWith("/api/role/save") || url.startsWith("/api/project/edit")
				|| url.startsWith("/api/project/update") || url.startsWith("/resources/**")
				|| url.startsWith("//oauth/logout")) {
			// response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
		} else {
			String authHeader = request.getParameter("token");
			String userName = request.getParameter("userNameDoFilter");
			if (userName != null) {
				User findByUserName = userRepo.findByUserName(userName);
				if (findByUserName == null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				List<RolePermission> listRolePermissionOfUser = new ArrayList<>();
				List<Permission> listPermisison = new ArrayList<>();
				Map<Integer, String> rolePermisison = new HashMap<>();
				List<Integer> listKey = new ArrayList<>();
				try {
					listRolePermissionOfUser = rolePermissionRepo.listRolePermissionOfUser(findByUserName.getUserId());
					for (RolePermission permission : listRolePermissionOfUser) {
						Permission per = permission.getPermission();
						listPermisison.add(per);
					}
					for (Permission permission : listPermisison) {
						int perId = permission.getPermissionId();
						String per_url = permission.getPermission_url();
						rolePermisison.put(perId, per_url);
						listKey.add(perId);
					}
					int ok = 0;
					for (Integer integer : listKey) {
						String actionOfRole = rolePermisison.get(integer);
						if ((actionOfRole.contains(urlOfRole[1]) && ok == 0)
								|| (this.checkPathvariable(actionOfRole) == true && ok == 0)) {
							ok = 1;
							chain.doFilter(request, response);
							response.setStatus(HttpServletResponse.SC_OK);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				// chain.doFilter(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
	}

	public boolean checkPathvariable(String sourse) {
		String[] elemOfParam = sourse.split("/");
		for (String ele : elemOfParam) {
			if (!sourse.contains(ele)) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

}