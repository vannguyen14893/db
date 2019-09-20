package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.RolePermissionDTO;
import com.cmc.dashboard.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	@Query(value = "SELECT * FROM role r WHERE r.role_name=:name", nativeQuery=true)
	public Role findByRoleName(@Param("name") String name);
	
	@Query(value = "select *\r\n" + 
			"from role r\r\n" + 
			"inner join user_role ur on ur.roles_Id = r.role_id\r\n" + 
			"inner join users u on u.user_id = ur.users_Id\r\n" + 
			"where u.user_id=:userId", nativeQuery=true)
	public List<Role> listRoleOfUser(@Param("userId") int userId);
	
//	@Query(value="select a.role_name as role_Name,b.permission_name as permission_Name \r\n" + 
//			"from role a,permissions b \r\n" + 
//			" where a.role_id in (\r\n" + 
//			"select role_id from role_permission\r\n" + 
//			"where role_id=a.role_id and permission_id=b.permission_id)  and b.permission_id in ( select permission_id from role_permission \r\n" + 
//			"where role_id=a.role_id and permission_id=b.permission_id)",nativeQuery=true)
//	public List<RolePermissionDTO> getAllRolePermission();
	
	@Query(value = "select *\r\n" + 
			"from role r\r\n" + 
			"inner join user_role ur on ur.roles_Id = r.role_id\r\n" + 
			"inner join users u on u.user_id = ur.users_Id\r\n" + 
			"where u.user_username=:userName", nativeQuery=true)
	public List<Role> listRoleUser(@Param("userName") String userName);
	
	
//	@Query(value="select a.role_name as role_Name,b.permission_name as permission_Name \r\n" + 
//			"			from role a,permissions b \r\n" + 
//			"			 where a.role_id in (\r\n" + 
//			"			select role_id from role_permission\r\n" + 
//			"			where role_id=a.role_id and permission_id=b.permission_id and a.role_name LIKE CONCAT('%',:roleName,'%'))  \r\n" + 
//			"            and b.permission_id in ( select permission_id from role_permission \r\n" + 
//			"			where role_id=a.role_id and permission_id=b.permission_id and b.permission_name LIKE CONCAT('%',:permissionName,'%'))",nativeQuery=true)
//	public List<Object[]> searchRolePermission(@Param("roleName") String roleName, @Param("permissionName") String permissionName);
}
