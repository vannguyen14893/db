package com.cmc.dashboard.repository;

import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.util.Constants;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
	
	@Query(value = "select * \r\n" + 
			"from skill s\r\n" + 
			"inner join project_skill ps on ps.skill_id = s.skill_id\r\n" + 
			"inner join project p on p.project_id = ps.project_id\r\n" + 
			"where p.project_id=:projectId ", nativeQuery=true)
	public List<Skill> listSkillOfProject(@Param("projectId") int projectId);
	@Query(value = "select s.skill_id, s.skill_name\r\n" + "from  skill s\r\n"
			+ "join user_skill uk on uk.skill_Id = s.skill_id\r\n" + "join users u on u.user_id = uk.user_Id\r\n"
			+ "where u.user_id=:id", nativeQuery = true)
	public List<Skill> listSkillOfUser(@Param("id") int id);
	
	@Query(value="SELECT UP.skill FROM user_plan UP INNER JOIN project PR ON UP.project_id = PR.project_id\r\n"
			+ " WHERE PR.type =" + Constants.Numbers.PROJECT_TYPE_POOL,nativeQuery = true)
	public Set<String> getListSkillOfUserAndPlan();
	
	@Query(value = "SELECT user_id,skill_id FROM user_skill ORDER BY user_id",nativeQuery = true)
	public List<Object> getSkillOfUser();
	
	@Query(value = "SELECT skill_name FROM skill WHERE FIND_IN_SET(skill_id,:skills) > 0",nativeQuery = true)
	public Set<String> getSkillNameByListId(@Param("skills") String skills);
}
