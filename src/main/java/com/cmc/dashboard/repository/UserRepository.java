package com.cmc.dashboard.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.type.TrueFalseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.UserDuDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.util.Constants;

/**
 * @author USER
 * @Modifier: Nvcong
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM users u where u.user_id=:userId", nativeQuery = true)
	public User getUserByUserId(@Param("userId") int userId);

	@Query(value = "SELECT u.user_id from users u where u.group_id = 1", nativeQuery = true)
	public List<Integer> findUserByGroup();

	@Query("FROM User u WHERE u.userName LIKE :userName")
	public User findByUserName(@Param("userName") String userName);

	@Query("FROM User u WHERE u.email LIKE :email")
	public User findByEmail(@Param("email") String email);

	@Query(value = "SELECT u.group from User u WHERE u.userId = :userId")
	public Optional<Group> getGroupByUser(@Param("userId") int userId);

	@Query(value = "SELECT * " + " FROM users u \r\n" + " LEFT JOIN groups g ON u.group_id = g.group_id"
			+ " WHERE u.user_name LIKE  %:userName%\r\n" + " AND u.full_name REGEXP :fullNames "
			+ " AND u.user_name REGEXP :userNames " + " AND u.status REGEXP :statusId "
			+ " AND IFNULL(g.group_id, '') REGEXP :groupId" 
			+ "\n#pageable\n", countQuery = "SELECT count(*) " + " FROM users u \r\n "
					+ " LEFT JOIN groups g ON u.group_id = g.group_id " + " WHERE u.user_name LIKE  %:userName%\r\n "
					+ " AND u.full_name REGEXP :fullNames " + " AND u.user_name REGEXP :userNames "
					+ " AND u.status REGEXP :statusId " + " AND IFNULL(g.group_id, '') REGEXP :groupId ", nativeQuery = true)
	public Page<User> findListUsers(Pageable pageable, @Param("userName") String userName,
			@Param("fullNames") String fullNames, @Param("userNames") String userNames,
			@Param("groupId") String groupId, @Param("statusId") String statusId);

	/**
	 * get users by list ids
	 * 
	 * @return List<User>
	 * @author: LXLinh
	 */
	public List<User> findByUserIdIn(Collection<Integer> userNames);

	/**
	 * get users by list userNAme
	 * 
	 * @return List<User>
	 * @author: LXLinh
	 */
	public List<User> findByUserNameIn(Collection<String> userNames);

	@Query(value = "SELECT US.full_name\r\n" + "	FROM users US WHERE US.user_id IN \r\n"
			+ "	(SELECT PRU.user_id FROM project_user PRU\r\n"
			+ "	 WHERE PRU.project_role_id = 1 )", nativeQuery = true)
	Set<String> getAllPM();

	@Query(value = "select s.skill_id, s.skill_name\r\n" + "from  skill s\r\n"
			+ "join user_skill uk on uk.skill_Id = s.skill_id\r\n" + "join users u on u.user_id = uk.user_Id\r\n"
			+ "where u.user_id=:id", nativeQuery = true)
	public List<Object> listSkillOfUser(@Param("id") int id);

	@Query(value = "select u.user_id,s.skill_id, s.skill_name\r\n" + " from  skill s\r\n" + "  ,users u\r\n"
			+ " group by u.user_id,s.skill_id, s.skill_name", nativeQuery = true)
	public List<Object> listSkillUser();

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from user_skill  where user_Id=:userId", nativeQuery = true)
	void removeUserSkill(@Param("userId") int userId);

	// @Query(value=" select *\r\n" +

	@Query(value = "SELECT u1.user_id as user_id\r\n" + "FROM   users u1 inner join group_user gu on u1.user_id=gu.user_id inner join groups g on gu.group_id = g.group_id \r\n"
			+ "WHERE u1.status = 1 and gu.start_date<=:endDate and (gu.end_date>=:startDate or gu.end_date is NULL) and g.development_unit=1", nativeQuery = true)
	public List<Integer> getAllUserIds(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "SELECT 	u1.user_id\r\n"
			+ "	FROM users u1\r\n"
			+ "	WHERE u1.status = 1  AND u1.full_name LIKE %:userNameSearch% AND u1.user_id IN (:userIds) " , nativeQuery = true)
	public List<Integer> getUserIdByUserName(@Param("userIds") List<Integer> userIds,
			@Param("userNameSearch") String userNameSearch);

	@Query(value = "SELECT user_id AS user_id \r\n" + "FROM users WHERE "
			+ " user_id IN (:userIds) AND group_id IN (SELECT group_id FROM groups WHERE group_name IN (:duNames))", nativeQuery = true)
	public List<Integer> getAllUserOfDU(@Param("duNames") List<String> duNames,
			@Param("userIds") List<Integer> userIds);

	@Query(value = "SELECT user_id FROM project_user WHERE "
			+ " project_id IN (:projectIds) AND user_id IN (:userIds) AND display = 1 AND (remove_at = '' OR ISNULL(remove_at) OR remove_at >= :removeDate)", nativeQuery = true)
	public Set<Integer> getAllUserOfProject(@Param("userIds") List<Integer> userIds,
			@Param("projectIds") List<Integer> projectIds,@Param("removeDate") String removeDate);

	@Query(value = "SELECT    u1.user_id as user_id, u1.full_name AS full_name\r\n" 
	        + "FROM    users u1\r\n"
			+ "WHERE   u1.status = 1 " + " AND u1.user_id IN (:userIds)\r\n"
		    + "LIMIT :limit  OFFSET :offset", nativeQuery = true)
	public List<Object> getAllUsers(@Param("limit") int limit, @Param("offset") int offset,
			@Param("userIds") List<Integer> userIds);

	@Query(value = "select *\r\n" + "from role_permission rp\r\n"
			+ "where rp.role_id in (select role_id from role r\r\n"
			+ "	join user_role ur on r.role_id = ur.roles_Id\r\n" + "    join users u on ur.users_Id = u.user_id\r\n"
			+ "    where u.user_id=:id)", nativeQuery = true)
	public List<RolePermission> listRolePermissionOfUser(@Param("id") int id);

	@Query(value = "SELECT u1.project_id AS project_id\r\n" + "  FROM  "
//			+ "project_user u1\r\n"
			+"user_plan u1"
			+ "  WHERE u1.user_id IN (:userIds) \r\n", nativeQuery = true)
	public List<Integer> getAllProjectIdOfUser(@Param("userIds") List<Integer> userIds);

	@Query(value = "SELECT DISTINCT temp.user_id, PR.project_name as project_name, temp.project_id, PR.status, PR.start_date, PR.end_date\r\n"
			+ "FROM " + "( SELECT US.user_id,UP.project_id FROM "
			+ 
			" users US LEFT JOIN "
//			+ "project_user PRU on US.user_id = PRU.user_id"
			+"user_plan UP on UP.user_id=US.user_id "
			
			+ " WHERE US.user_id IN (:userIds) AND UP.project_id IN (:projectIds)" + " ) AS temp "
			+ " LEFT JOIN project PR ON temp.project_id = PR.project_id ", nativeQuery = true)
	public List<Object> getAllProjectOfUser(@Param("projectIds") List<Integer> projectIds,
			@Param("userIds") List<Integer> userIds);

	@Query(value = "SELECT  count(1)\r\n" + " FROM    users u1\r\n" + " WHERE   u1.status = 1 \r\n"
			+ "  AND u1.user_id IN (:userIds)", nativeQuery = true)
	public Integer getCountAllUsers(@Param("userIds") List<Integer> userIds);

	@Query(value = "SELECT IF(COUNT(1) > 0, 'TRUE', 'FALSE') AS 'EXISTS'\r\n" + "FROM users u1\r\n"
			+ "WHERE   u1.status = 1 AND u1.user_id = :userId \r\n" + "LIMIT 1;", nativeQuery = true)
	public boolean isExistUserId(@Param("userId") int userId);

	@Query(value = "\"SELECT user_id,full_name,email, status,user_name,group_id,img FROM users u WHERE u.user_name=:username\"", nativeQuery = true)
	public UserInfoDTO loadInfo(@Param("username") String username);

	@Query(value = "", nativeQuery = true)
	public String getRoleByUserId(@Param("userId") int userId);

	@Query(value = "SELECT GR.group_name \r\n" + " FROM groups GR LEFT JOIN users US ON US.group_id = GR.group_id\r\n"
			+ " WHERE US.user_id =:userId ", nativeQuery = true)
	public String getDeliveryUnitByUserId(@Param("userId") int userId);

	@Query(value = "select * \r\n" + "from users u\r\n" + "inner join project_user pu on pu.user_id = u.user_id\r\n"
			+ "inner join project p on p.project_id = pu.project_id\r\n"
			+ "where u.user_id=:userId and p.project_id=:projectId", nativeQuery = true)
	public User getMemberOfProject(@Param("userId") int userId, @Param("projectId") int projectId);

	@Query(value = "Select u.user_id, u.full_name \r\n"
			+ "from project p inner join project_user pu on p.project_id = pu.project_id \r\n"
			+ "						inner join users u on pu.user_id = u.user_id\r\n"
			+ "where p.project_id=:projectId", nativeQuery = true)
	public List<Object> getListUserByProjectId(@Param("projectId") int projectId);

	@Query(value = "select a.user_name as user_name,b.role_name as role_Name " + "from users a,role b "
			+ " where a.user_id in (" + " select users_Id from user_role"
			+ " where users_Id=a.user_id and roles_Id=b.role_id) " + " and b.role_id in ( "
			+ "select roles_Id from user_role "
			+ " where users_Id=a.user_id and roles_Id=b.role_id)", nativeQuery = true)
	List<Object[]> getAllUserRole();

	@Query(value = "delete from user_role where users_Id=:userId and roles_Id=:roleId)", nativeQuery = true)
	void removeUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

	@Query(value = "select count from users where group_id = :duId", nativeQuery = true)
	public Integer GetUserCountOfDuByDuId(@Param("duId") int duId);

	@Query(value = "select G.group_id, count(*) as users from users u\r\n" + 
			"inner join (select g.group_id from groups g \r\n" + 
			"where g.development_unit = 1 or g.internal_du = 1) as G\r\n" + 
			"where u.status = 1 and u.group_id = G.group_id\r\n" + 
			"group by u.group_id", nativeQuery = true)
	public List<Object> GetUserCountAllDu();
	
	@Query(value = "select count(*) as totalUser from users u \r\n" + 
			"where  u.status = 1 and u.group_id =:groupId",nativeQuery = true)
	public int totalUserDu(@Param("groupId") int groupId);
	
	@Query(value = "select u.group_id, count(*) as booked_users\r\n" + 
			"from users u\r\n" + 
			"inner join project_user pu on pu.user_id = u.user_id\r\n" + 
			"inner join project p on p.project_id = pu.project_id\r\n" + 
			"inner join (select g.group_id from groups g \r\n" + 
			"where g.development_unit = 1 or g.internal_du = 1) as G\r\n" + 
			"where u.status = 1 and p.type =:type and u.group_id = G.group_id and u.user_id not in \r\n" + 
					"(select distinct up.user_id from user_plan up where Month(up.to_date) =:month and Year(up.to_date) =:year)\r\n" + 
			"group by u.group_id",nativeQuery=true)
	public List<Object> getBookedUserAllDu(@Param("month") int month, @Param("year") int year,@Param("type") int type);
	
	@Query(value = "select a.user_name as user_name,b.role_name as role_Name  from users a,role b \r\n"
			+ " where a.user_id in ( select users_Id from user_role\r\n"
			+ "where users_Id=a.user_id and roles_Id=b.role_id and a.user_name LIKE CONCAT('%',:userName,'%')) \r\n"
			+ "and b.role_id in ( \r\n" + "select roles_Id from user_role \r\n"
			+ " where users_Id=a.user_id and roles_Id=b.role_id and a.group_id=:groupId)", nativeQuery = true)
	public List<Object[]> searchUser(@Param("userName") String userName, @Param("groupId") int groupId);

	@Query(value = "select a.user_name as user_name,b.role_name as role_Name  from users a,role b \r\n"
			+ " where a.user_id in ( select users_Id from user_role\r\n"
			+ "where users_Id=a.user_id and roles_Id=b.role_id and a.user_name LIKE CONCAT('%',:userName,'%')) \r\n"
			+ "and b.role_id in ( \r\n" + "select roles_Id from user_role \r\n"
			+ " where users_Id=a.user_id and roles_Id=b.role_id )", nativeQuery = true)
	public List<Object[]> searchUserByUserName(@Param("userName") String userName);

	@Query(value = "select a.user_name as user_name,b.role_name as role_Name  from users a,role b \r\n"
			+ " where a.user_id in ( select users_Id from user_role\r\n"
			+ "where users_Id=a.user_id and roles_Id=b.role_id ) \r\n" + "and b.role_id in ( \r\n"
			+ "select roles_Id from user_role \r\n"
			+ " where users_Id=a.user_id and roles_Id=b.role_id and a.group_id=:groupId)", nativeQuery = true)
	public List<Object[]> searchUserByGroupId(@Param("groupId") int groupId);

	@Query(value = "select *\r\n" + "from users u where  u.token=:token", nativeQuery = true)
	public User findByToken(@Param("token") String token);

	@Query(value = "SELECT CASE WHEN UR.roles_Id IN ("
			+ Constants.Role.ADMIN + "," + Constants.Role.QA + "," + Constants.Role.PMO + ") THEN 1 "
			+ " WHEN UR.roles_Id = " + Constants.Role.PM +" THEN 2 "
			+ " WHEN UR.roles_Id = " +Constants.Role.DUL + " THEN 3 "
			+" ELSE 0 END "
			+ " FROM ( SELECT roles_Id AS roles_Id FROM user_role UR  WHERE UR.users_Id =:userId) UR", nativeQuery = true)
	public 	List<Integer> checkViewListProject(@Param("userId") int userId);
//nvtiep2
	@Query(value = "select user_id from project_user where project_id=:projectId", nativeQuery = true)
	public List<Integer> listUserIdByPojectId(@Param("projectId") int projectId);

	@Query(value = "select  count(*) from project_user where project_id=:projectId and user_id=:userId", nativeQuery = true)
	public Integer checkUserInProject(@Param("projectId") int projectId,@Param("userId") int userId);
	
	@Query(value = "select id from worklog where project_task_id=:issueId", nativeQuery = true)
	public List<Integer> listWorkLogIdByIssueIdId(@Param("issueId") int issueId);

	@Query(value = "select project_task_id from project_task where project_id=:projectId", nativeQuery = true)
	public List<Integer> listIssueIdByProjectId(@Param("projectId") int projectId);

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from project_task where project_task_id=:issueId", nativeQuery = true)
	public List<Integer> deleteProjectTask(@Param("issueId") int issueId);

	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO users(email,full_name,img,status,user_name,group_id,token) VALUES(:email,:full_name,:img,:status,:user_name,:group_id,:token)", nativeQuery = true)
	void insertUser(@Param("email") String email, @Param("full_name") String fullName, @Param("img") String img,
			@Param("status") Byte status, @Param("user_name") String userName, @Param("group_id") String groupId,
			@Param("token") String token);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users set email=:email,full_name=:fullName,img=:img,group_id=:group_id,token=:token WHERE user_name=:userName", nativeQuery = true)
	void updateUser(@Param("email") String email, @Param("fullName") String fullName, @Param("img") String img,
			@Param("group_id") String group_id, @Param("token") String token, @Param("userName") String userName);

	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT user_role(users_Id,roles_Id) VALUES(:userId,:roleId)", nativeQuery = true)
	void insertUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

	@Query(value = "SELECT COUNT(*) FROM user_role where users_Id =:userId", nativeQuery = true)
	Integer checkRoleIsExist(@Param("userId") int userId);
	
	@Query(value ="SELECT GR.group_name FROM groups GR LEFT JOIN users US ON GR.group_id = US.group_id\r\n"
			+ " WHERE US.user_id IN\r\n"
			+ " (SELECT PRU.user_id FROM project_user PRU WHERE PRU.project_id IN\r\n"
			+ " ( SELECT project_id FROM project_user WHERE project_role_id =" + Constants.Role.PM+" AND user_id =:userId))",nativeQuery=true)
	Set<String> getListUserInProject(@Param("userId") int userId);

	@Query(value ="SELECT U.user_id from users U inner join group_user gu on gu.user_id=U.user_id inner join groups g on gu.group_id=g.group_id\r\n"
			+ " WHERE gu.start_date<=:endDate and (gu.end_date>=:startDate or gu.end_date is NULL)  and g.development_unit=1 and gu.group_id = (SELECT group_id FROM users where user_id =:userId)",nativeQuery = true)
	List<Integer> getListUserOfDu(@Param("userId") int userId,@Param("startDate") String startDate, @Param("endDate") String endDate);
	@Query(value = "select u.user_id, u.user_name from users u left join groups g on u.group_id = g.group_id\r\n" + 
			"where g.development_unit = 1", nativeQuery = true)
	List<Object> getListUserOfDu();
	@Query(value = "SELECT PRU.user_id\r\n"
			+ " FROM project_user PRU inner join group_user gu on gu.user_id = PRU.user_id inner join groups g on g.group_id=gu.group_id WHERE gu.start_date<=:endDate and (gu.end_date>=:startDate or gu.end_date is NULL)  and g.development_unit=1 and PRU.project_id IN \r\n"
		      +"( SELECT project_id FROM project_user WHERE project_role_id = 1 AND user_id =:userId)", nativeQuery = true)
	 public List<Integer> getUserInProjectOfPm(@Param("userId") int userId,@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Query(value = "SELECT U.user_id, U.full_name\r\n" + 
			"			from project_user as pu left join users as U on pu.user_id = U.user_id\r\n" + 
			"			where pu.project_id=:projectId", nativeQuery = true)
	public List<Object> getUserByProject(@Param("projectId") int projectId);
	
	@Query(value = "SELECT CASE WHEN US.level =" + Constants.Level.Fresher + " THEN 'Fresher' \r\n"
			+ " WHEN US.level =" + Constants.Level.Junior+" THEN 'Junior'\r\n"
			+ " WHEN US.level =" + Constants.Level.Senior1+" THEN 'Senior1'\r\n"
			+ " WHEN US.level =" + Constants.Level.Senior2+" THEN 'Senior2'\r\n"
			+ " ELSE 'None' END\r\n"
			+ " FROM users US",nativeQuery = true)
	public Set<String> getListLevelUser();
	
//	@Query(value="SELECT u.user_id, u.full_name,gu.group_id, up.from_date, up.to_date, up.man_day, p.project_name, p.project_type_id, mp.man_month, mp.allocation_value FROM groups g \r\n" + 
//			"INNER JOIN group_user gu ON g.group_id = gu.group_id \r\n" + 
//			"INNER JOIN users u ON gu.user_id = u.user_id\r\n" + 
//			"LEFT JOIN user_plan up ON gu.user_id = up.user_id AND up.from_date <= :endDate AND up.to_date >= :startDate\r\n" + 
//			"LEFT JOIN project p ON up.project_id = p.project_id\r\n" + 
//			"LEFT JOIN man_power mp on u.user_id = mp.user_id AND man_month = :month\r\n" + 
//			"WHERE g.development_unit = 1 AND g.group_id = :groupId AND gu.start_date <= :endDate AND gu.end_date >= :startDate", nativeQuery = true)
//	public List<Object> getDUStatisticUser(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("month") String month, @Param("groupId") int groupId);
	
	@Query(value="SELECT u.user_id, u.full_name,gu.group_id, gu.start_date, gu.end_date, g.development_unit, g.internal_du FROM groups g \r\n" + 
			"INNER JOIN group_user gu ON g.group_id = gu.group_id \r\n" + 
			"INNER JOIN users u ON gu.user_id = u.user_id\r\n" + 
			"WHERE g.development_unit = 1 AND g.group_id = :groupId AND gu.start_date <= :endDate AND gu.end_date >= :startDate", nativeQuery = true)
	public List<Object> getDUStatisticUser(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("groupId") int groupId);
	
	@Query(value="SELECT project_id, effort_per_day, man_day, from_date, to_date FROM user_plan \r\n" + 
//			"INNER JOIN project p ON up.project_id = p.project_id \r\n" + 
//			"INNER JOIN project_type_log ptl ON p.project_id = ptl.project_id AND from_date <= :endDate AND to_date >= :startDate \r\n" + 
			"WHERE user_id = :userId AND from_date <= :endDate AND to_date >= :startDate", nativeQuery = true)
	public List<Object> getPlanUser(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") int userId);
	
	@Query(value="SELECT mp.user_id, mp.group_id, mp.allocation_value, mp.man_month, mp.from_date, mp.to_date, g.development_unit, g.internal_du FROM man_power mp \r\n" + 
			"INNER JOIN groups g ON mp.group_id = g.group_id \r\n" +
			"WHERE mp.user_id = :userId AND mp.man_month = :month", nativeQuery = true)
	public List<Object> getManPowerUser(@Param("month") String month, @Param("userId") int userId);
	
	@Query(value="SELECT DISTINCT u.user_id, u.full_name  FROM users u\r\n" + 
			"INNER JOIN group_user gu ON u.user_id = gu.user_id\r\n" + 
			"INNER JOIN groups g ON gu.group_id = g.group_id\r\n" + 
			"WHERE g.development_unit = 1 AND ((gu.start_date <=:endDate AND gu.end_date >=:startDate) OR  (gu.start_date <=:endDate AND gu.end_date IS NULL))", nativeQuery = true)
	public List<Object> getUsers(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Query(value = "SELECT user_id FROM users", nativeQuery = true)
	public List<Integer> getAllUserId();
	
	
}
