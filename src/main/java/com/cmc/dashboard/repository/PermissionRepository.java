package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.dashboard.model.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{

    @Query(value = "select permissions.permission_name from permissions", nativeQuery = true)
    List<String> getAllPermission();
    
    @Query(value="select *\r\n" + 
    		"from permissions where permission_name=:name",nativeQuery=true)
    public Permission findByName(@Param("name") String name);
    
    @Query(value="select *\r\n" + 
    		"from permissions p \r\n" + 
    		"where p.permission_id=:permissionId",nativeQuery=true)
    public Permission findById(@Param("permissionId") int permissionId);
    
    @Query(value="select p.permission_id from permissions p where g.parent_id=:parentId", nativeQuery = true)
	List<Integer> getAllSubId(@Param("parentId")int parentId);
}
