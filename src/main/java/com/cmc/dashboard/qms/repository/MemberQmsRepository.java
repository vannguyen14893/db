package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsMember;
import com.cmc.dashboard.util.CustomValueUtil;

public interface MemberQmsRepository extends JpaRepository<QmsMember, Integer>{
	
	/**
	 * TODO description
	 * @return List<MemberQms> 
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT M.id, M.user_id, M.project_id FROM members M \r" +
			"JOIN member_roles MR ON MR.member_id = M.id\r" +
			"WHERE MR.role_id = " + CustomValueUtil.ROLE_PM, nativeQuery = true)
	public List<QmsMember> getAllMemberRolePM();
	
	/**
	 * TODO description
	 * @return List<Object> 
	 * @author: tvhuan
	 */
	@Query(value = "select m.created_on, concat(u.lastname,' ', u.firstname) "
			+ "from redmine_db.members as m join redmine_db.users as u "
			+ "on m.user_id = u.id where m.project_id=:projectId ", nativeQuery = true)
	public List<Object> getListProjectMemberByProjectId(@Param("projectId") int projectId);
	
	/**
	 * get memebers by project id
	 * @return List<String> 
	 * @author: lxlinh
	 */
	@Query(value = "select concat(u.lastname,' ', u.firstname) "
			+ "from redmine_db.members as m join redmine_db.users as u "
			+ "on m.user_id = u.id where m.project_id=:projectId ", nativeQuery = true)
	public List<String> getMembersByProjectId(@Param("projectId") int projectId);
	
	/**
	 * 
	 * check exist members of project
	 * @param projectId
	 * @return QmsMember 
	 * @author: LXLinh
	 */
	public QmsMember findByUserIdAndProjectId(int userId, int projectId);
	
	@Query(value = "select A.userId , A.name from( select m.user_id as userId , concat(u.lastname,' ', u.firstname) as name, u.id \r\n" + 
			"from redmine_db.members as m join redmine_db.users as u on m.user_id = u.id \r\n" + 
			"where m.project_id=:projectId and u.status = 1) A\r\n" + 
			"inner join (select du.user_id as userId from dashboard.users du where du.status = 1) B\r\n" + 
			"on A.userId = B.userId", nativeQuery = true)
	public List<Object> projectMemberSolutions(@Param("projectId") int projectId);
}
