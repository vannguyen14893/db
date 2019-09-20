/**
 * 
 */
package com.cmc.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.dto.ProjectTimeDTO;
import com.cmc.dashboard.model.ProjectBillable;

/**
 * @author nahung
 *
 */
public interface ProjectBillableRepository extends JpaRepository<ProjectBillable, Integer> {

	/**
	 * get start date and end date of project
	 * 
	 * @param projectId
	 * @return ProjectTimeDTO
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT new com.cmc.dashboard.dto.ProjectTimeDTO(P.startDate,P.endDate)" + " FROM ProjectBillable P"
			+ " WHERE P.projectId = :projectId Group by P.projectId")
	public ProjectTimeDTO getDateProject(@Param("projectId")int projectId);
	

	/**
	 * @param deliveryUnit
	 * @param billableMonth
	 * @return
	 * @throws SQLException List<BillableDTO> 
	 * @author: NVKhoa
	 * @modifier: HungNC
	 * @modifyDate: 26-02-2018
	 */
	@Query(value="SELECT a.project_billable_id, \r\n" + 
			"   a.billable_month, \r\n" + 
			"    a.billable_value, a.issue_code, a.man_month FROM \r\n" + 
			"(\r\n" + 
			"SELECT \r\n" + 
			" ANY_VALUE(pb.project_billable_id) as project_billable_id,\r\n" + 
			" ANY_VALUE (DATE_FORMAT(STR_TO_DATE(pb.billable_month,'%m-%Y'),'%m-%Y')) as billable_month, \r\n" + 
			"    ANY_VALUE(pb.billable_value) as billable_value,\r\n" + 
			"   ANY_VALUE( pb.issue_code) as issue_code,\r\n" + 
			" ANY_VALUE (ROUND(SUM(up.man_day/22),2)) as man_month\r\n" + 
			"FROM \r\n" + 
			" user_plan up,project_billable pb\r\n" + 
			"WHERE \r\n" + 
			" up.project_id = :projectId \r\n" + 
			"AND\r\n" + 
			" up.project_id = pb.project_id\r\n" + 
			"AND \r\n" + 
			" DATE_FORMAT(STR_TO_DATE(pb.billable_month,'%m-%Y'), '%M %Y') = DATE_FORMAT(up.from_date, '%M %Y')\r\n" + 
			"GROUP BY DATE_FORMAT(up.from_date, '%M %Y')\r\n" + 
			"\r\n" + 
			"UNION\r\n" + 
			"\r\n" + 
			"SELECT \r\n" + 
			" pb.project_billable_id, \r\n" + 
			"   pb.billable_month,   \r\n" + 
			" pb.billable_value,\r\n" + 
			"    pb.issue_code,\r\n" + 
			"    NULL AS man_month\r\n" + 
			"FROM project_billable pb\r\n" + 
			"WHERE pb.project_id = :projectId \r\n" + 
			"AND pb.billable_month NOT IN\r\n" + 
			" (\r\n" + 
			"  SELECT ANY_VALUE (pb.billable_month) as billable_month   FROM \r\n" + 
			"  user_plan up, project_billable pb\r\n" + 
			"  WHERE \r\n" + 
			"   up.project_id = :projectId \r\n" + 
			"  AND \r\n" + 
			"   DATE_FORMAT(STR_TO_DATE(pb.billable_month,'%m-%Y'), '%M %Y') = DATE_FORMAT(from_date, '%M %Y')\r\n" + 
			"  GROUP BY DATE_FORMAT(up.from_date, '%M %Y')\r\n" + 
			" )\r\n" + 
			") AS a ORDER BY a.billable_month ASC", nativeQuery = true)
	public Optional<List<Object>> getListBillable(@Param("projectId") int projectId);
	//PNTHANH
	@Query(value = "Select * FROM project_billable pb WHERE pb.project_id=:projectId", nativeQuery = true)
	public List<ProjectBillable> getListBillableByProjectId(@Param("projectId") int projectId);
	//PNTHANH
	@Query(value = "SELECT IFNULL ((SELECT sum(upd.man_day) as man_month \r\n" + 
			"from user_plan up\r\n" + 
			"left join user_plan_detail upd on up.user_plan_id = upd.user_plan_id\r\n" + 
			"where up.project_id=:projectId and upd.plan_month=:planMonth\r\n" + 
			"group by up.project_id), 0)", nativeQuery = true)
	public int getManMonthByProjectIdAndBillableMonth(@Param("projectId") int projectId,@Param("planMonth") String planMonth);
	/**
	 * Get project billable by project
	 * 
	 * @param projectId
	 * @return List<ProjectBillable>
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT DP FROM ProjectBillable DP WHERE DP.projectId = :projectId")
	public List<ProjectBillable> getBillableByProject(@Param("projectId") int projectId);

	/**
	 * Get project billable by month
	 * 
	 * @param billableMonth
	 * @return List<ProjectBillable>
	 * @author: NVKhoa
	 */

	@Query(value = "SELECT * FROM project_billable DP WHERE (MONTH(DP.end_date) =:billableMonth OR MONTH(DP.start_date) =:billableMonth)",nativeQuery= true)
	public List<ProjectBillable> getBillableByMonth(@Param("billableMonth") String billableMonth);

	/**
	 * Get BillableEffort By ProjectId
	 * @param projectId
	 * @return Float
	 * @author: GiangTM
	 */
	@Query(value = "SELECT SUM(p.billable_value) AS billableValue FROM project_billable AS p" 
			+ " WHERE p.project_id = :projectId AND (YEAR(now()) > CONVERT(SUBSTRING(p.billable_month,4,4),UNSIGNED INTEGER)" 
			+ " OR YEAR(now()) = CONVERT(SUBSTRING(p.billable_month,4,4),UNSIGNED INTEGER)" 
			+ " AND MONTH(now()) >= CONVERT(SUBSTRING(p.billable_month,1,2),UNSIGNED INTEGER))", nativeQuery = true)
	public Float getBillableEffortByProjectId(@Param("projectId") int projectId);
	
	/**
	 * Get BillableEffort By ProjectId
	 * @param projectId
	 * @return Float
	 */
	@Query(value = "SELECT SUM(p.billable_value) AS billableValue FROM project_billable AS p join groups g on g.group_id = p.group_id" 
			+ " WHERE p.project_id = :projectId", nativeQuery = true)
	public Float getBillableEffortsByProjectId(@Param("projectId") int projectId);
	
	/**
	 * get list ProjectBillable by list billableId
	 * @param ltsBillableId
	 * @return List<ProjectBillable>
	 * @author LXLinh
	 */
	 @Modifying
	 @Transactional 
	 @Query(value="SELECT DP.project_billable_id,DP.billable_month,DP.billable_value, DP.delivery_unit,DP.issue_code,DP.pm_name,DP.project_id,"
	 		+ "DP.start_date,DP.end_date,DP.created_on,DP.updated_on "
	 		+ "FROM project_billable DP WHERE DP.project_billable_id IN(:ltsBillableId)",nativeQuery=true)
	 public List<ProjectBillable> getBillableByListId(@Param("ltsBillableId")List<Integer> ltsBillableId);
	
	/**
	 * Method get ProjectBillale by projectId
	 * @author HungNC
	 * @param projectId
	 * @return ProjectBillale
	 * @throws SQLException
	 */
	@Query(value = "SELECT pb.* FROM project_billable pb "
			+ "WHERE pb.project_id = :projectId", nativeQuery = true)
	public List<ProjectBillable> getProjectBillableByProjectId(@Param("projectId") int projectId);

	/**
	 * Get External Billable by DU
	 *
	 * @param time
	 * @return List<Object> 
	 * @author: HungNC
	 * @created: 2018-03-26
	 */
	@Query(value = "SELECT PB.delivery_unit, SUM(PB.billable_value) AS external_billable "
			+ "FROM project_billable PB WHERE PB.billable_month = :time "
			+ "GROUP BY PB.delivery_unit ", nativeQuery = true)
	public List<Object> getExternalBillableByDeliveryUnit(@Param("time") String time);
	
	/**
	 * get allocation and bill able of project
	 * @param projectId
	 * @param dateMonth
	 * @return
	 * @author HuanTV
	 */
	@Query(value="select bla.DU, bla.manMonth,(bla.SumBill/bla.sumAlloc*bla.manMonth) as billable from\r\n" + 
			"(Select a2.DU, a2.manMonth,\r\n" + 
			"	(SELECT sum(billable_value) as SumBillAble FROM project_billable where project_id = :projectId and billable_month=:dateMonth)as SumBill,\r\n" + 
			"    (select sum(upd.man_day) as SumAllocation FROM user_plan_detail upd join dashboard.user_plan up on up.user_plan_id = upd.user_plan_id \r\n" + 
			"     where up.project_id = :projectId and upd.plan_month = :dateMonth) as sumAlloc \r\n" + 
			"		from \r\n" + 
			"      (SELECT upd.res_delivery_unit as DU, sum(upd.man_day) as manMonth\r\n" + 
			"	FROM user_plan_detail upd join user_plan up on up.user_plan_id = upd.user_plan_id \r\n" + 
			"    where up.project_id = :projectId and upd.plan_month = :dateMonth\r\n" + 
			"    group by delivery_unit, res_delivery_unit) as a2 )  as bla",nativeQuery=true)
	  public List<Object> getDuAllocationbyProjectIdAndMonth(@Param("projectId") int projectId, @Param("dateMonth") String dateMonth);
	
	//PNTHANH
	@Query(value = "select p.project_billable_id, p.billable_value, p.issue_code, p.start_date, p.end_date, g.group_name, g.group_id from project_billable p join groups g on p.group_id = g.group_id where project_id=:projectId", nativeQuery= true)
	public List<Object> getBillableByProjectId(@Param("projectId") int projectId);
	
	@Query(value = "select g.group_id, g.group_name  \r\n" + 
			"from project p left join project_user pu on p.project_id = pu.project_id\r\n" + 
			"				left join users u on pu.user_id = u.user_id\r\n" + 
			"				left join groups g on u.group_id = g.group_id\r\n" + 
			"where p.project_id=:projectId group by g.group_id, g.group_name", nativeQuery = true)
	public List<Object> getGroupByProjectMember(@Param("projectId") int projectId);
	
	@Query(value = "Select coalesce(avg(pb.billable_value), 0) as avgbillable FROM project_billable pb WHERE pb.project_id=:projectId", nativeQuery = true)
	public Float getAvgBillableByProjectId(@Param("projectId") int projectId);
}
