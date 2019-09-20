package com.cmc.dashboard.qms.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;

public interface UserQmsRepository extends JpaRepository<QmsUser, Integer> {
	/**
	 * get role of user by list userid.
	 * 
	 * @param userId
	 * @return String
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT REDMINE_ROLE.name FROM redmine_db.users REDMINE_USER \r\n"
			+ "INNER JOIN redmine_db.members REDMINE_MEMBER ON REDMINE_USER.id = REDMINE_MEMBER.user_id \r\n"
			+ "INNER JOIN redmine_db.member_roles REDMINE_MEMBER_ROLE ON REDMINE_MEMBER_ROLE.member_id = REDMINE_MEMBER.user_id\r\n"
			+ "INNER JOIN redmine_db.roles REDMINE_ROLE ON REDMINE_ROLE.id = REDMINE_MEMBER_ROLE.role_id\r\n"
			+ "WHERE REDMINE_USER.id = :userId LIMIT 1;", nativeQuery = true)
	public String getRoleByUserId(@Param("userId") int userId);

	/**
	 * Get all user of project
	 * 
	 * @param projectId
	 * @return
	 * @throws SQLException
	 *             List<Object>
	 * @author: DuyHieu
	 */
	@Query(value = "select B.user_id, B.fullname, A.name from \r\n"
			+ "(SELECT u.id, r.name , u.firstname FROM redmine_db.users u\r\n"
			+ "inner join redmine_db.members m on u.id = m.user_id\r\n"
			+ "inner join redmine_db.member_roles mr on m.id = mr.member_id\r\n"
			+ "inner join redmine_db.roles r on mr.role_id = r.id\r\n" + "group by user_id) A ,\r\n" + "\r\n"
			+ "(SELECT M.user_id, CONCAT(U.lastname, ' ', U.firstname) fullname \r\n" + "FROM redmine_db.members M \r\n"
			+ "      INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n"
			+ "      WHERE M.project_id=:projectId) B \r\n" + "where A.id = B.user_id;", nativeQuery = true)
	public List<Object> getAllUserEachProject(@Param("projectId") int projectId);


	/**
	 * Get list of user is Project manager
	 * 
	 * @return List<QmsUser>
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT u.id, u.login, u.hashed_password, u.firstname, u.lastname, u.admin, u.status, u.salt, u.group_id \r"
			+ "FROM users u\r\n" + "INNER JOIN members m ON u.id = m.user_id\r\n"
			+ "INNER JOIN member_roles mr ON mr.member_id = m.id " + "WHERE  mr.role_id ="
			+ CustomValueUtil.ROLE_PM, nativeQuery = true)
	public List<QmsUser> getUserIsPm();

	@Query(value = "SELECT us.id, us.login, us.hashed_password, us.firstname, us.lastname, us.admin, us.status, us.salt, us.group_id \n" + 
			"FROM redmine_db.users us JOIN redmine_db.groups_users g on us.id = g.group_id\n" + 
			"JOIN redmine_db.users u on u.id = g.user_id\n" + 
			"WHERE u.id = :id AND us.lastname LIKE 'DA_%'\n" + 
			"ORDER BY (CASE us.lastname\n" + 
			"	WHEN 'DA_BOD' THEN 1\n" + 
			"    WHEN 'DA_QA' THEN 2\n" + 
			"    WHEN 'DA_DUL' THEN 3\n" + 
			"    WHEN 'DA_PM' THEN 4\n" + 
			"    END\n" + 
			") ASC LIMIT 1", nativeQuery = true)
	public QmsUser findGroup(@Param("id") int id);

	@Query(value = "SELECT id, login, firstname, lastname, admin, status, hashed_password, salt FROM users WHERE login =:username", nativeQuery = true)
	public QmsUser findUserByLogin(@Param("username") String username);

	/**
	 * Get all delivery unit
	 * 
	 * @return String
	 * @author: ngocdv
	 */
	@Query(value = "SELECT cf.possible_values FROM redmine_db.custom_fields cf WHERE id ="
			+ CustomValueUtil.DELIVERY_UNIT_USER_ID, nativeQuery = true)
	public String getAllUserDeliveryUnit();

	@Query(value = "SELECT CV.value AS duName\r\n" + "FROM redmine_db.custom_values CV\r\n"
			+ "WHERE CV.custom_field_id=" + CustomValueUtil.DELIVERY_UNIT_USER_ID
			+ "  AND CV.customized_id = :userId", nativeQuery = true)
	public String getDeliveryUnitByUserId(@Param("userId") int userId);


	@Query(value = "select distinct us.id as group_id from users as u inner join groups_users as gu on u.id = gu.user_id inner join users as us on gu.group_id = us.id where u.id =:id", nativeQuery = true)
	public List<Integer> getGroupIdByQmsUserId(@Param("id") int id);

	/**
	 * Get total User by Delivery Unit
	 *
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-03-29
	 */
	@Query(value = "SELECT CV.value, COUNT(CV.customized_id) totalPerson "
			+ "FROM redmine_db.custom_values CV WHERE CV.custom_field_id = 6 "
			+ "GROUP BY CV.value; ", nativeQuery = true)
	public List<Object> getTotalUserByDeliveryUnit();

	/**
	 * get users by projectId
	 * 
	 * @param projectId
	 * @return
	 * 
	 * 
	 * 
	 * 
	 * @author duyhieu
	 */
	@Query(value = "select TB2.user_id, TB2.fullname, TB1.DU_User, TB2.name from (select tb2.id, tb2.full_name,cv.value as DU_User  from \r\n"
			+ "(SELECT id, concat(u.lastname,' ',u.firstname) AS full_name \r\n" + "FROM redmine_db.users u\r\n"
			+ "inner join 	(\r\n" + "SELECT user_id, project_id FROM redmine_db.members ) m\r\n"
			+ "on u.id = m.user_id where m.project_id = :projectId) tb2 inner join ( \r\n"
			+ "select * from redmine_db.custom_values where custom_field_id = 6) cv\r\n"
			+ "on tb2.id = cv.customized_id) TB1\r\n" + "inner join \r\n"
			+ "(select B.user_id, B.fullname, A.name from \r\n"
			+ "(SELECT u.id, r.name , u.firstname FROM redmine_db.users u\r\n"
			+ "inner join redmine_db.members m on u.id = m.user_id\r\n"
			+ "inner join redmine_db.member_roles mr on m.id = mr.member_id\r\n"
			+ "inner join redmine_db.roles r on mr.role_id = r.id\r\n" + "group by user_id) A ,\r\n"
			+ "	    (SELECT M.user_id, CONCAT(U.lastname, ' ', U.firstname) fullname FROM redmine_db.members M \r\n"
			+ "        INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n"
			+ "        WHERE M.project_id=:projectId) B\r\n" + "        where A.id = B.user_id) TB2\r\n"
			+ "        on TB1.id = TB2.user_id", nativeQuery = true)
	public List<Object> getUserByProjectId(@Param("projectId") int projectId);

	/**
	 * 
	 * get name user by id
	 * 
	 * @param userId
	 * @return String
	 * @author: tvdung
	 */
	@Query(value = "SELECT concat(u.lastname, ' ', u.firstname) as fullname FROM redmine_db.users u WHERE u.id=:userId", nativeQuery = true)
	public String getNameUserById(@Param("userId") int userId);

	/**
	 * Get Resource by search's name and delivery Unit
	 * 
	 * @param name
	 * @param duName
	 * @param pageable
	 * @return Page<Integer>
	 * @author: LXLinh
	 */
	@Query(value = "SELECT * FROM (\r\n"
			+ " SELECT u.id,Concat(u.lastname,' ',u.firstname) as resourcename,TB1.duName as duname FROM redmine_db.users u \r\n"
			+ " INNER JOIN  (SELECT cv.value AS duName, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) TB1 \r\n"
			+ " WHERE u.id=TB1.id AND u.type='User' AND u.admin !=1 AND u.status = 1 order by u.id asc \r\n"
			+ " ) TB2\r\n"
			+ " where TB2.duname  LIKE %:duName% AND TB2.resourcename LIKE %:name%  \n#pageable\n", countQuery = "SELECT * FROM (\r\n"
					+ " SELECT u.id,Concat(u.lastname,' ',u.firstname) as resourcename,TB1.duName as duname FROM redmine_db.users u \r\n"
					+ " INNER JOIN  (SELECT cv.value AS duName, cv.customized_id AS id FROM redmine_db.custom_values cv WHERE cv.custom_field_id =6) TB1 \r\n"
					+ " WHERE u.id=TB1.id AND u.type='User' AND u.admin !=1 AND u.status = 1 order by u.id asc\r\n"
					+ " ) TB2\r\n"
					+ " where TB2.duname  LIKE %:duName% AND TB2.resourcename LIKE %:name% ", nativeQuery = true)
	public Page<Integer> getResourceByNameAndDU(@Param("name") String name, @Param("duName") String duName,
			Pageable pageable);

	/**
	 * Lay tat ca user_id va DU cua no
	 * 
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-04-05
	 */
	@Query(value = "SELECT CV.customized_id, CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id = "
			+ CustomValueUtil.DELIVERY_UNIT_USER_ID, nativeQuery = true)
	public List<Object> getAllUserByDeliveryUnit();

	/**
	 * get users by projectId
	 * 
	 * @param projectId
	 * @return
	 * @author tvhuan
	 */
	@Query(value = "select TB2.user_id, TB2.fullname, TB1.DU_User, TB2.name from (select tb2.id, tb2.full_name,cv.value as DU_User  from \r\n"
			+ "(SELECT id, concat(u.lastname,' ',u.firstname) AS full_name \r\n" + "FROM redmine_db.users u\r\n"
			+ "inner join 	(\r\n" + "SELECT user_id, project_id FROM redmine_db.members ) m\r\n"
			+ "on u.id = m.user_id where m.project_id = :projectId) tb2 inner join ( \r\n"
			+ "select * from redmine_db.custom_values where custom_field_id = " + CustomValueUtil.DELIVERY_UNIT_USER_ID
			+ ") cv\r\n" + "on tb2.id = cv.customized_id) TB1\r\n" + "inner join \r\n"
			+ "(select B.user_id, B.fullname, A.name from \r\n"
			+ "(SELECT u.id, r.name , u.firstname FROM redmine_db.users u\r\n"
			+ "inner join redmine_db.members m on u.id = m.user_id\r\n"
			+ "inner join redmine_db.member_roles mr on m.id = mr.member_id\r\n"
			+ "inner join redmine_db.roles r on mr.role_id = r.id\r\n" + "group by user_id) A ,\r\n"
			+ "	    (SELECT M.user_id, CONCAT(U.lastname, ' ', U.firstname) fullname FROM redmine_db.members M \r\n"
			+ "        INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n"
			+ "        WHERE M.project_id=:projectId) B\r\n" + "        where A.id = B.user_id) TB2\r\n"
			+ "        on TB1.id = TB2.user_id \r\n"
			+ "			where TB2.fullname like %:resourceName% and TB1.DU_User like %:du%", nativeQuery = true)
	public List<Object> filterUserByProjectId(@Param("projectId") int projectId, @Param("du") String du,
			@Param("resourceName") String resourceName);

	/**
	 * get list project by duname
	 * 
	 * @param du
	 * @return
	 * @author HuanTv
	 */
	@Query(value = "SELECT p.id, p.name FROM redmine_db.projects p join custom_values c on p.id= c.customized_id \r\n"
			+ "	where c.custom_field_id = " + CustomValueUtil.DELIVERY_UNIT_ID + " and c.value like %:du% \r\n"
			+ "	and p.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)", nativeQuery = true)
	public List<Object> getProjectlistByDu(@Param("du") String du);

	@Query(value = "SELECT distinct p.value as duName, p.custom_field_id FROM redmine_db.custom_values p where p.custom_field_id = "
			+ CustomValueUtil.DELIVERY_UNIT_USER_ID + " and (p.value is not null and p.value !='')", nativeQuery = true)
	public List<Object> getlistDu();
	
	@Query(value = "SELECT distinct p.value as duName, p.custom_field_id FROM redmine_db.custom_values p where p.custom_field_id = "
			+ CustomValueUtil.DELIVERY_UNIT_ID + " and (p.value is not null and p.value !='')", nativeQuery = true)
	public List<Object> getlistDuPic();
	
	
  /**
   * get all user not admin
   * paging: https://use-the-index-luke.com/sql/partial-results/fetch-next-page
   * 
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    u1.id as user_id, concat(u1.lastname,' ',u1.firstname) AS full_name\r\n" +
      "FROM    redmine_db.users u1\r\n" +
      "WHERE     u1.type = 'User' and u1.admin != 1  and u1.status = 1 \r\n" +
      "      -- filter\r\n" +
      "          AND u1.id IN (:userIds)\r\n" +
      "      -- paging\r\n" +
      "LIMIT :limit  OFFSET :offset", nativeQuery = true)
  public List<Object> getAllUsers(@Param("limit") int limit, @Param("offset") int offset,
      @Param("userIds") List<Integer> userIds);

	/**
	 * @author: overwrite by LinhGia
	 */
//	@Query(value = "SELECT    u1.id as user_id, concat(u1.lastname,' ',u1.firstname) AS full_name\r\n" +
//			"FROM    dashboard.users u1\r\n" +
//			"WHERE     u1.type = 'User' and u1.admin != 1  and u1.status = 1 \r\n" +
//			"      -- filter\r\n" +
//			"          AND u1.id IN (:userIds)\r\n" +
//			"      -- paging\r\n" +
//			"LIMIT :limit  OFFSET :offset", nativeQuery = true)
//	public List<Object> getAllUsers(@Param("limit") int limit, @Param("offset") int offset,
//									@Param("userIds") List<Integer> userIds);
	
  /**
   * get all project of user not admin
   * 
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT temp.user_id, p.name as project_name, temp.project_id, p.status, p.start_date, p.end_date\r\n" +
  		"FROM\r\n" +
  		"(SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" +
  		"	FROM    redmine_db.users u1\r\n" +
  		"	LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" +
  		"	WHERE     u1.id IN (:userIds) \r\n" +
  		"	 AND m1.project_id IN (:projectIds)\r\n" +
  		"	GROUP BY  u1.id, m1.project_id\r\n" +
  		"	UNION\r\n" +
  		"	SELECT    u2.id as user_id, te2.project_id AS project_id \r\n" +
  		"	FROM    redmine_db.users u2 \r\n" +
  		"	LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" +
  		"	WHERE     u2.id IN (:userIds) \r\n" +
  		"	AND te2.project_id IN (:projectIds)\r\n" +
  		"	GROUP BY  u2.id, te2.project_id) temp\r\n" +
  		"LEFT JOIN (\r\n" +
  		"	SELECT pr.id, pr.name, pr.status,\r\n" +
  		"    (SELECT cv.value FROM redmine_db.custom_values cv WHERE cv.customized_id = pr.id AND cv.custom_field_id = 35) as start_date,\r\n" +
  		"    (SELECT cv.value FROM redmine_db.custom_values cv WHERE cv.customized_id = pr.id AND cv.custom_field_id = 36) as end_date\r\n" +
  		"    FROM redmine_db.projects pr ) p ON p.id = temp.project_id\r\n" +
  		"ORDER BY  temp.user_id ASC, temp.project_id ASC", nativeQuery = true)
  public List<Object> getAllProjectOfUser(@Param("projectIds") List<Integer> projectIds,
      @Param("userIds") List<Integer> userIds);

  /**
   * count all user not admin
   * 
   * @return Integer
   * @author: NNDuy
   */
  @Query(value = "SELECT    count(1)\r\n" + 
      "FROM    redmine_db.users u1\r\n" +
      "WHERE     u1.type = 'User' and u1.admin != 1  and u1.status = 1 \r\n" + 
      "      -- filter\r\n" + 
      "            AND u1.id IN (:userIds)", nativeQuery = true)
  public Integer getCountAllUsers(@Param("userIds") List<Integer> userIds);
 
  /**
   * get all userId not admin
   * 
   * @return List<Integer>
   * @author: NNDuy
   */
  @Query(value = "SELECT user_id \r\n" +
      "FROM    dashboard.users \r\n" +
      "WHERE      status = 1", nativeQuery = true) //  u1.type = 'User' and u1.admin != 1 and
  public List<Integer> getAllUserIds();
  
  /**
   * get all userId not admin by userName
   * 
   * @return List<Integer>
   * @author: NNDuy
   */

//	/**
//	 * @author: Overwrite by LinhGia
//	 */
//	@Query(value = "SELECT 		ufn.id\r\n" +
//			"FROM 		(	SELECT 	u1.id, u1.full_name\r\n" +
//			"				FROM 	dashboard.users u1\r\n" +
//			"				WHERE   u1.status = 1 AND u1.id IN (:userIds)\r\n" +
//			"			) AS ufn\r\n" +
//			"WHERE 		ufn.full_name LIKE %:userNameSearch% -- search by field ", nativeQuery = true)
//	public List<Integer> getUserIdByUserName(@Param("userIds") List<Integer> userIds, @Param("userNameSearch") String userNameSearch);
  
  /**
   * get all user not admin to filter
   * 
   * @return List<Integer>
   * @author: NNDuy
   */
  @Query(value = "SELECT    u1.id as user_id, concat(u1.lastname,' ',u1.firstname) AS full_name\r\n" + 
      "FROM    redmine_db.users u1\r\n" + 
      "WHERE     u1.type = 'User' and u1.admin != 1  and u1.status = 1", nativeQuery = true)
  public List<Object> getAllUserToFilter();
  
  /**
   * get all project of user not admin
   * 
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    temp.project_id\r\n" + 
      "FROM\r\n" + 
      "  -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
      "    (SELECT   m1.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u1\r\n" + 
      "  LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
      "  WHERE     u1.id IN (:userIds) \r\n" + 
      "  GROUP BY  u1.id, m1.project_id\r\n" + 
      "  UNION\r\n" + 
      "  SELECT    te2.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u2\r\n" + 
      "  LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
      "  WHERE     u2.id IN (:userIds)\r\n" + 
      "  GROUP BY  u2.id, te2.project_id) temp", nativeQuery = true)
  public List<Integer> getAllProjectIdOfUser(@Param("userIds") List<Integer> userIds);
  
  /**
   * get all user not admin of project 
   * 
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    u1.id as user_id \r\n" + 
      "FROM    redmine_db.users u1\r\n" + 
      "LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
      "WHERE     u1.id IN (:userIds) \r\n" + 
      "      -- Filter\r\n" + 
      "      AND m1.project_id IN (:projectIds)\r\n" + 
      "GROUP BY  u1.id\r\n" + 
      "UNION\r\n" + 
      "SELECT    u2.id as user_id \r\n" + 
      "FROM    redmine_db.users u2\r\n" + 
      "LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
      "WHERE     u2.id IN (:userIds) \r\n" + 
      "      -- Filter\r\n" + 
      "      AND te2.project_id IN (:projectIds)\r\n" + 
      "GROUP BY  u2.id;", nativeQuery = true)
  public List<Integer> getAllUserOfProject(@Param("userIds") List<Integer> userIds, @Param("projectIds") List<Integer> projectIds);
  
  /**
   * get all project active of user not admin
   * 
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    temp.project_id\r\n" + 
      "FROM\r\n" + 
      "  -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
      "    (SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u1\r\n" + 
      "  LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
      "  WHERE     u1.id IN (:userIds)\r\n" + 
      "  GROUP BY  u1.id, m1.project_id\r\n" + 
      "  UNION\r\n" + 
      "  SELECT    u2.id as user_id, te2.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u2\r\n" + 
      "  LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
      "  WHERE     u2.id IN (:userIds)\r\n" + 
      "  GROUP BY  u2.id, te2.project_id) temp\r\n" + 
      "LEFT JOIN   redmine_db.projects p ON p.id = temp.project_id\r\n" + 
      "WHERE p.status = 1 -- 1 là active;\r\n" + 
      "ORDER BY  temp.user_id ASC, temp.project_id ASC", nativeQuery = true)
  public List<Integer> getAllProjectIdActiveOfUser(@Param("userIds") List<Integer> userIds);
  
  /**
   * get all userId not admin
   * 
   * @return List<Integer>
   * @author: NNDuy
   */
  @Query(value = "SELECT customized_id AS user_id \r\n" + 
      "FROM redmine_db.custom_values WHERE custom_field_id = " + CustomValueUtil.DELIVERY_UNIT_USER_ID +
      " AND customized_id IN (:userIds) AND value IN (:duNames);", nativeQuery = true)
  public List<Integer> getAllUserOfDU(@Param("duNames") List<String> duNames, @Param("userIds") List<Integer> userIds);
  
  /**
   * get all Du to filter
   * 
   * @return List<String>
   * @author: NNDuy
   */
  @Query(value = "SELECT DISTINCT(value)\r\n" + 
      "FROM redmine_db.custom_values WHERE custom_field_id = 6 AND value IS NOT NULL AND value != '';", nativeQuery = true)
  public List<String> getAllDuToFilter();

	/**
	 * Get user who join in project
	 * 
	 * @param id
	 * @return List<QmsUser>
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT U.id, concat(U.lastname, ' ', U.firstname) FROM users U \r\n" + 
			"JOIN redmine_db.members M ON M.user_id = U.id\r\n" + 
			"JOIN redmine_db.projects P ON M.project_id = P.id WHERE P.id = :id", nativeQuery = true)
	public List<Object> getUserByProject(@Param("id") int id);

	@Query(value = "SELECT T1.id, T1.login, GROUP_CONCAT(T4.name) AS role\r\n" + 
			"FROM redmine_db.users AS T1\r\n" + 
			"INNER JOIN redmine_db.members AS T2 ON T2.user_id = T1.id\r\n" + 
			"INNER JOIN redmine_db.member_roles T3 ON T3.member_id = T2.id\r\n" + 
			"INNER JOIN redmine_db.roles T4 ON T3.role_id = T4.id\r\n" + 
			"WHERE T2.project_id = :theProjectId GROUP BY T1.id", nativeQuery = true)
	public List<Object> getMembersWithRoleByProjectId(@Param("theProjectId") int theProjectId);

    /**
    * check exist user id
    * 
    * @param userId
    * @return boolean 
    * @author: NNDuy
    */
    @Query(value = "SELECT IF(COUNT(1) > 0, 'TRUE', 'FALSE') AS 'EXISTS'\r\n" + 
       "FROM redmine_db.users u1\r\n" + 
       "WHERE     u1.type = 'User' and u1.admin != 1  and u1.status = 1 AND u1.id = :userId \r\n" + 
       "LIMIT 1;", nativeQuery = true)
    public boolean isExistUserId(@Param("userId") int userId);
    
    @Query(value = "SELECT distinct substring_index(substring(us.lastname,4,length(us.lastname)), \".\", 1) as unit\r\n" + 
    		"FROM users us JOIN groups_users g on us.id = g.group_id\r\n" + 
    		"JOIN users u on u.id = g.user_id\r\n" + 
    		"WHERE u.id = :userId and substring(us.lastname, 1, 2) LIKE '" + CustomValueUtil.GROUP_PREFIX+"'", nativeQuery = true)
    public List<String> getDeliveryUnitByUser(@Param("userId") int userId);
    
    /**
     * Get user in project, result: user_id, username
     * @param projectId
     * @return List<Object>
     */
    @Query(value=" SELECT u.id AS userID, u.login AS userName FROM redmine_db.users AS u "
    		+ " INNER JOIN redmine_db.members AS m ON u.id = m.user_id "
    		+ " WHERE m.project_id = :projectId", nativeQuery = true)
    public List<Object> getUserInProject(@Param("projectId")int projectId);
}
