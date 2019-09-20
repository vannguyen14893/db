package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsUserUnallocation;

public interface UserUnallocationRepository extends JpaRepository<QmsUserUnallocation, Integer> {

	/**
	 * get all user and delivery unit.
	 * 
	 * @return List<Object>
	 * @author: Hoai-Nam
	 */
	@Query(value = "select TB2.id, TB2.duName as duName, TB2.resourceName as resourceName from (select RE.id as id , Concat(RE.lastname,' ',RE.firstname) as resourceName, Concat(TB1.duName1, '')  as duName \r\n"
			+ "FROM ( redmine_db.users AS RE INNER JOIN (SELECT cv.value AS duName1, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) AS TB1 ON RE.id=TB1.id)\r\n"
			+ "where RE.type='User' AND RE.admin !=1 AND RE.status = 1) as TB2\r\n"
			+ "where duName like %:deliveryUnit%\r\n" + "and resourceName like %:resourceName%"
			+ "\n#pageable\n", countQuery = "select count(*) from (select RE.id as id , Concat(RE.lastname,' ',RE.firstname) as resourceName, Concat(TB1.duName1, '')  as duName \r\n"
					+ "FROM ( redmine_db.users AS RE INNER JOIN (SELECT cv.value AS duName1, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) AS TB1 ON RE.id=TB1.id)\r\n"
					+ "where RE.type='User' AND RE.admin !=1 AND RE.status = 1) as TB2\r\n"
					+ "where duName like %:deliveryUnit%\r\n"
					+ "and resourceName like %:resourceName%", nativeQuery = true)
	public Page<QmsUserUnallocation> getAllUserAndDuNamesWithPaging(@Param("deliveryUnit") String deliveryUnit,
			@Param("resourceName") String resourceName, Pageable pageable);

	/**
	 * get all user and delivery unit.
	 * 
	 * @return List<Object>
	 * @author: Hoai-Nama
	 */
	@Query(value = "select TB2.id, TB2.duName as duName, TB2.resourceName as resourceName from (select RE.id as id , Concat(RE.lastname,' ',RE.firstname) as resourceName, Concat(TB1.duName1, '')  as duName \r\n"
			+ "FROM ( redmine_db.users AS RE INNER JOIN (SELECT cv.value AS duName1, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) AS TB1 ON RE.id=TB1.id)\r\n"
			+ "where RE.type='User' AND RE.admin !=1 AND RE.status = 1) as TB2\r\n"
			+ "where duName REGEXP :deliveryUnit\r\n" + "and resourceName like %:resourceName%", nativeQuery = true)
	public List<QmsUserUnallocation> getAllUserAndDuNames(@Param("deliveryUnit") String deliveryUnit,
			@Param("resourceName") String resourceName);

	/**
	 * get all user and delivery unit.
	 * 
	 * @return List<Object>
	 * @author: Hoai-Nam
	 */
	@Query(value = "select count(*) from (select RE.id as id , Concat(RE.lastname,' ',RE.firstname) as resourceName, Concat(TB1.duName1, '')  as duName \r\n"
			+ "FROM ( redmine_db.users AS RE INNER JOIN (SELECT cv.value AS duName1, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) AS TB1 ON RE.id=TB1.id)\r\n"
			+ "where RE.type='User' AND RE.admin !=1 AND RE.status = 1) as TB2\r\n", nativeQuery = true)
	public int countAllUserAndDuName();
}
