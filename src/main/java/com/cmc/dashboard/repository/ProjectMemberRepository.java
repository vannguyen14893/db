package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ProjectUser;

public interface ProjectMemberRepository extends JpaRepository<ProjectUser, Integer>{
	@Query(value = "select u.full_name, min(up.from_date), max(up.to_date), u.user_id, sum(up.effort_per_day * up.man_day), pu.remove_at, u.group_id\r\n" + 
			"									from project_user as pu left join users as u on pu.user_id = u.user_id\r\n" + 
			"                                    left join user_plan as up\r\n" + 
			"									ON up.user_id = u.user_id \r\n" + 
			"									where pu.project_id=:projectId\r\n" + 
			"									group by up.user_id, u.full_name;", nativeQuery = true)
	public List<Object> getListProjectMemberByProjectId(@Param("projectId") int projectId);
	
	@Query(value="SELECT IFNULL ((SELECT sum(up.effort_per_day)\r\n" + 
			"			FROM dashboard.project_user pu left join dashboard.user_plan up on pu.user_id = up.user_id\r\n" + 
			"			WHERE pu.start_date <= up.from_date and pu.end_date >= up.to_date and up.project_id=:projectId and pu.user_id=:userId\r\n" + 
			"			group by up.user_id), 0)", nativeQuery= true)
	public int getEffortByUserId(@Param("projectId") int projectId, @Param("userId") int userId);
	
	@Query(value="select ifnull (( select sum(w.logWorkTime)\r\n" + 
			"from project_task as pt join worklog as w ON pt.project_task_id = w.project_task_id\r\n" + 
			"where pt.project_id=:projectId and w.user_id=:userId\r\n" + 
			"group by w.user_id), 0)", nativeQuery = true)
	public int getEffortActual(@Param("projectId") int projectId, @Param("userId") int userId);
}
