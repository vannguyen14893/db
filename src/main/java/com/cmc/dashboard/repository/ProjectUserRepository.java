package com.cmc.dashboard.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.dto.MemberDTO;
import com.cmc.dashboard.model.ProjectUser;
@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser,Integer> {
 @Query(value= "select u.full_name from "
 		+ "users u inner join project_user pu on u.user_id = pu.user_id "
 		+ "where pu.project_id = :project_id and pu.project_role_id = :project_role_id",nativeQuery = true)
 List<String> getListNameofProjectRole(@Param("project_id") int projectId, @Param("project_role_id") int projectRoleId);
 
 @Query(value= "select distinct u.full_name from "
	 		+ "users u inner join project_user pu on u.user_id = pu.user_id "
	 		+ "where pu.project_role_id = :project_role_id",nativeQuery = true)
 Set<String> getListNamePM(@Param("project_role_id") int projectRoleId);
 @Query(value="select u.user_id,u.full_name,u.email,g.group_name,u.user_name,pu.display,pu.remove,u.status from"
	 		+ " project_user pu inner join users u on pu.user_id=u.user_id left outer join groups g on g.group_id=u.group_id"
	 		+ " where pu.project_id=:project_id",nativeQuery = true)
	 List<Object> getListUserOfProject(@Param("project_id") int projectId);
 
 @Query(value="select u.user_id,u.full_name,u.email,g.group_name,u.user_name,pu.display,pu.remove,u.status  from"
	 		+ " project_user pu inner join users u on pu.user_id=u.user_id left outer join groups g on g.group_id=u.group_id"
	 		+ " where pu.project_id=:project_id and pu.project_role_id=1",nativeQuery = true)
	 List<Object> getListPMOfProject(@Param("project_id") int projectId);
 @Query(value="select u.full_name from"
	 		+ " project_user pu inner join users u on pu.user_id=u.user_id left outer join groups g on g.group_id=u.group_id"
	 		+ " where pu.project_id=:project_id and pu.project_role_id=1",nativeQuery = true)
	 List<String> getListNamePMOfProject(@Param("project_id") int projectId);
 @Modifying(clearAutomatically = true)
 @Query(value = "update project_user set project_role_id =:projectRoleId where project_id=:projectId and user_id=:userId", nativeQuery =true)
 void updateUser(@Param("projectRoleId") int projectRoleId,@Param("projectId") int projectId ,@Param("userId") int userId  );

 
 @Query(value = "select display from project_user where project_id=:projectId and user_id=:userId", nativeQuery =true)
 Integer getDisplay(@Param("projectId") int projectId ,@Param("userId") int userId );
 
 @Modifying(clearAutomatically = true)
 @Query(value = "update project_user set display=:display where project_id=:projectId and user_id=:userId", nativeQuery =true)
 void updateDisplayUser(@Param("projectId") int projectId ,@Param("userId") int userId ,@Param("display") int display );
// @Query(value = "SELECT te.user_id AS user_id, te.project_id AS project_id,te.effort as effort\r\n" + 
//	      " FROM     \r\n" + 
//	      "  ( SELECT US.user_id AS user_id,PRU.project_id AS project_id,PRU.effort AS effort,PRU.start_date AS start_date,PRU.end_date AS end_date FROM \r\n" 
//	      + " users US LEFT JOIN project_user PRU on US.user_id = PRU.user_id \r\n"
//	      + " WHERE US.user_id IN (:userIds) AND PRU.project_id IN (:projectIds) ) As  te\r\n" + 
//	      " WHERE  te.start_Date <= :fromDate AND te.end_date <= :toDate \r\n" + 
//	      " ORDER BY  te.user_id ASC, te.project_id ASC", nativeQuery = true)
 @Query(value = "SELECT PRT.assignee_id AS user_id,W.id as spent_id,PRT.project_id AS project_id,DATE_FORMAT(W.create_at,'%Y-%m-%d') as spent_on,W.logWorkTime as spents_time\r\n"
 		+ "	FROM project_task AS PRT LEFT JOIN worklog W on PRT.project_task_id = W.project_task_id \r\n"
 		+ "	WHERE DATE_FORMAT(W.create_at,'%Y-%m-%d') >= :fromDate AND DATE_FORMAT(W.create_at,'%Y-%m-%d') <= :toDate \r\n"
 		+ " AND PRT.assignee_id IN (:userIds) AND PRT.project_id IN (:projectIds)\r\n"
 		+ " ORDER BY PRT.assignee_id ASC,PRT.project_id ASC,W.create_at ASC",nativeQuery = true)
 public List<Object> getSpentTimes(@Param("fromDate") String fromDate, @Param("toDate") String toDate, 
	      @Param("projectIds") List<Integer> projectIds, @Param("userIds") List<Integer> userIds);
 
 
 @Query(value = "SELECT IF(COUNT(1) > 0, 'TRUE', 'FALSE') AS 'EXISTS'\r\n" + 
         " FROM \r\n" + 
         " project_user u1\r\n" + 
         " WHERE   u1.user_id  = :userId AND u1.project_id = :projectId\r\n" +  
         " LIMIT 1;", nativeQuery = true)
     public boolean isExistProjectOfUser(@Param("userId") int userId, @Param("projectId") int projectId);
 
//nvtiep2
@Modifying(clearAutomatically = true)
@Query(value = "delete from  project_user  where project_id=:projectId and user_id=:userId", nativeQuery =true)
void deleteUser(@Param("projectId") int projectId ,@Param("userId") int userId  );

@Modifying(clearAutomatically = true)
@Query(value = "update project_user set remove=:status  where project_id=:projectId and user_id=:userId", nativeQuery =true)
void updateRemovedUser(@Param("projectId") int projectId ,@Param("userId") int userId,@Param("status") int status  );

@Query(value = "select * from project_user where project_id=:projectId and user_id=:userId", nativeQuery =true)
ProjectUser findByProjectAndUser(@Param("projectId") int projectId ,@Param("userId") int userId  );
}

