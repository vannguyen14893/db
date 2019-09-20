package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.RolePermission;

/**
 * @author USER
 * modifier: Nvcong
 *
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
	
	@Query(value="select *\r\n" + 
			"from role_permission rp\r\n" + 
			"where rp.role_id in (select role_id from role r\r\n" + 
			"join user_role ur on r.role_id = ur.roles_Id \r\n" + 
			" join users u on ur.users_Id = u.user_id\r\n" + 
			" where u.user_id=:userId)\r\n" + 
			" and rp.permission_id in (\r\n" + 
			" select permission_id from permissions pe\r\n" + 
			" where pe.permission_id = rp.permission_id\r\n" + 
			" )",nativeQuery=true)
	public List<RolePermission> listRolePermissionOfUser(@Param("userId") int userId);

}
