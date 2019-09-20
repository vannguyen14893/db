package com.cmc.dashboard.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer>{
	
	@Query("select g from Group g where g.groupId = :groupId")
	public Group findById(@Param("groupId") int groupId);
	
	@Query("select g from Group g where g.groupName = :groupName")
	public Group findByName(@Param("groupName") String groupName);
	
	@Query(value = "SELECT g.group_id,g.created_on,g.group_desc,g.group_name,g.updated_on,g.group_rank, g.role_id FROM dashboard.groups AS g WHERE g.group_id IN (:groupId) ORDER BY group_rank DESC", nativeQuery = true)
	public List<Group> findMaxRank(@Param("groupId") List<Integer> groupIds);
	
	@Query(value="select g.group_name from groups g", nativeQuery = true)
	public Set<String> getListNameDu();
	
	@Query(value="select g.group_name from groups g", nativeQuery = true)
	public List<String> getListName();
	
	@Query(value="select g.group_id,g.group_name from groups g", nativeQuery = true)
	public List<Object> getListDu();
	
	@Query(value="SELECT * FROM groups g\r\n" + 
			"where g.development_unit = 1 or g.internal_du = 1",nativeQuery=true)
	public List<Group> getListDUAndInternalDu();
	
	@Query(value="select u.user_id from users u\r\n" + 
			"inner join groups g on u.group_id = g.group_id\r\n" + 
			"where g.group_id =:groupId  order by u.user_id",nativeQuery=true)
	public List<Object> getAllUserOfGroup(@Param("groupId") int groupId);
	
	@Query(value="select * from groups ORDER BY development_unit DESC",nativeQuery = true)
	public List<Group> getListGroup();
	
	
	@Query(value="select pb.group_id, pb.billable_value, pb.start_date, pb.end_date, pb.project_id from project_billable pb \r\n" + 
			"inner join groups g on pb.group_id = g.group_id AND g.development_unit = 1\r\n" + 
			"where pb.start_date <= :endDate AND pb.end_date >= :startDate",nativeQuery=true)
	public List<Object> getBillableOfGroup(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
}
