package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.DUStatistic;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.User;
@Repository
public interface DeliveryUnitRepository extends JpaRepository<Group, Integer> {
	 @Query(value ="SELECT GR.group_name from groups GR where group_status = 1",nativeQuery=true)
     List<String> getAllDeliveruUnitName();
	 @Query(value ="SELECT CONVERT(group_id,char(10)) from groups where group_status = 1",nativeQuery=true)
     List<String> getAllDeliveruUnitId();
	 @Query(value = "SELECT GR.group_name AS group_name \r\n"
	 		+ " FROM project PR LEFT JOIN groups GR ON GR.group_id = PR.group_id \r\n"
	 		+ " WHERE PR.project_id IS NOT NULL AND PR.project_id =:projectId", nativeQuery = true)
	 public String getDuNameByProjectId(@Param("projectId") int projectId);
	 
	 
	 @Query(value="select * from groups", nativeQuery=true)
	 List<Group> getAllDeliveryUnitIdName();
     
	 
	 @Query(value ="SELECT GR.group_name AS group_name \r\n"
	 		+ " FROM groups GR INNER JOIN users US ON GR.group_id = US.group_id\r\n"
	 		+ " WHERE US.user_id =:userId",nativeQuery=true)
	 String getGroupNameByUserId(@Param("userId") int userId);
	 
	 @Query(value ="select  u.user_id from users u \r\n" + 
	 		"inner join (select g.group_id from groups g where g.development_unit= 1 and g.internal_du=1 and g.group_id =:groupId) G on G.group_id = u.group_id\r\n" + 
	 		"where u.status = 1 group by u.user_id",nativeQuery = true)
	 public List<Object> getAllUserDuInternal(@Param("groupId") int groupId);
	 
	 @Query(value="select  count(*) from users u \r\n" + 
	 		"inner join (select g.group_id from groups g where g.development_unit= 1 and g.internal_du=0 and g.group_id =:groupId) G on G.group_id = u.group_id\r\n" + 
	 		"where u.status = 1  ",nativeQuery =true)
	 public int getAllUserDu(@Param("groupId") int groupId);
	 
	 @Query(value="select  u.user_id from users u \r\n" + 
	 		"inner join user_plan up on up.user_id = u.user_id\r\n" + 
	 		"inner join project p on p.project_id = up.project_id\r\n" + 
	 		"where u.status = 1 and u.group_id =:groupId and p.type =:type and (Month(up.from_date) =:month or Month(up.to_date) =:month) and (Year(up.from_date) =:year or Year(up.to_date) =:year)\r\n" + 
	 		"group by u.user_id",nativeQuery=true)
	 public List<Object> getAllUserDeliveryUnit(@Param("type") int type,@Param("groupId") int groupId,@Param("month") int month,@Param("year") int year);
	 
	 @Query(value="select distinct u.user_id from users u  \r\n" + 
	 		" where u.status = 1 and u.group_id =:groupId and ((Year(u.end_date) <:year or (Year(u.end_date) =:year and Month(u.end_date) >=:month)) OR Year(u.end_date) >:year) \r\n" + 
		 	"order by u.user_id",nativeQuery=true)
	 public List<Object> getAllUserDU(@Param("groupId") int groupId,@Param("month") int month,@Param("year") int year);
	 
	 @Query(value="select distinct u.user_id \r\n"
	 		    + " FROM users u INNER JOIN groups GR ON u.group_id = GR.group_id  \r\n" + 
		 		" where u.status = 1 and GR.development_unit = 1 and ((Year(u.end_date) <:year or (Year(u.end_date) =:year and Month(u.end_date) >=:month)) OR Year(u.end_date) >:year) \r\n" + 
			 	"order by u.user_id",nativeQuery=true)
	 public List<Object> getAllUser(@Param("month") int month,@Param("year") int year);
	 
	 @Query(value="select  sum(b.manpower) from\r\n" + 
	 		"(SELECT distinct mp.user_id, mp.allocation_value as manpower, u.group_id FROM man_power mp\r\n" + 
	 		"inner join users u on mp.user_id = u.user_id\r\n" + 
	 		"inner join project_user pu on mp.user_id = pu.user_id\r\n" + 
	 		"inner join project p on p.project_id = pu.project_id\r\n" + 
	 		"where p.type =:type and u.user_id =:userId and (Month(mp.from_date) =:month or Month(mp.to_date) =:month) and (Year(mp.from_date) =:year or Year(mp.to_date) =:year)) b", nativeQuery=true)
	 public Float getManpowerUserOfDu(@Param("type") int type,@Param("userId") int userId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value ="SELECT MP.user_id AS user_id,MP.allocation_value AS manpower\r\n"
	 		+ " FROM users US INNER JOIN man_power MP ON US.user_id = MP.user_id\r\n"
	 		+ " WHERE US.group_id =:groupId and ((Month(MP.from_date) =:month or Month(MP.to_date) =:month) and (Year(MP.from_date) =:year or Year(MP.to_date) =:year))\r\n"
	 		+ " ORDER BY US.user_id,MP.allocation_value",nativeQuery = true)
	 public List<Object> getListUserAndManpower(@Param("month") int month,@Param("year") int year,@Param("groupId") int groupId);
	 
	 @Query(value ="SELECT MP.user_id AS user_id,MP.allocation_value AS manpower\r\n"
		 		+ " FROM users US INNER JOIN man_power MP ON US.user_id = MP.user_id\r\n"
		 		+ " WHERE US.group_id =:groupId and MP.man_month=:monthDate\r\n"
		 		+ " ORDER BY US.user_id,MP.allocation_value",nativeQuery = true)
	 public List<Object> getListUserAndManpower1(@Param("monthDate") String monthDate,@Param("groupId") int groupId);
 
	 @Query(value = "select  sum(b.manday) from\r\n" + 
 		"(SELECT distinct up.user_id, up.man_day as manday, u.group_id FROM user_plan up\r\n" + 
 		"inner join users u on up.user_id = u.user_id\r\n" + 
 		"inner join project p on p.project_id = up.project_id\r\n" + 
 		"where p.type =:type and u.user_id =:userId and (Month(up.from_date) =:month or Month(up.to_date) =:month) and (Year(up.from_date) =:year or Year(up.to_date) =:year)) b",nativeQuery = true)
    public Float getManDayUserOfDu(@Param("type") int type,@Param("userId") int userId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value ="SELECT UP.user_id AS user_id,UP.project_id AS project_id,UP.man_day AS man_day\r\n"
	 		+ " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
	 		+ " WHERE US.group_id =:groupId and ((Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n"
	 		+ " ORDER BY UP.user_id,UP.project_id",nativeQuery = true)
	 public List<Object> getListUserAndManDay(@Param("month") int month,@Param("year") int year,@Param("groupId") int groupId);
	 
	 @Query(value ="SELECT UP.user_id AS user_id,UP.project_id AS project_id,UP.man_day AS man_day\r\n"
		 		+ " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
		 		+ " WHERE US.group_id =:groupId and MP.man_month=:monthDate\r\n"
		 		+ " ORDER BY UP.user_id,UP.project_id",nativeQuery = true)
	 public List<Object> getListUserAndManDay1(@Param("monthDate") String monthDate,@Param("groupId") int groupId);
	 
	 @Query(value="select u.group_id, sum(pl.effort_per_day) as allocate  from user_plan pl\r\n" + 
	 		"inner join users u on u.user_id = pl.user_id \r\n" + 
	 		"where (Month(pl.from_date) = :month or Month(pl.to_date) = :month) and (Year(pl.from_date) = :year or Year(pl.to_date) = :year or pl.to_date = NULL) group by u.group_id", nativeQuery=true)
	 List<Object> getDeliveryUnitAllocation(@Param("month") int month, @Param("year") int year);
	 
	 @Query(value="select p.group_id, sum(pb.billable_value) as billable  from project_billable pb\r\n" + 
	 		"inner join project p on p.project_id = pb.project_id \r\n" + 
	 		"where Month(pb.end_date) >= :month and (Year(pb.end_date) = :year or pb.end_date = NULL) group by p.group_id", nativeQuery=true)
	 List<Object> getDeliveryUnitBillable(@Param("month") int month, @Param("year") int year);
	 
//	 @Query(value="select  count(*) as available from users u \r\n" + 
//	 		"where u.user_id not in \r\n" + 
//	 		"(select distinct up.user_id from user_plan up \r\n" + 
//	 		"inner join project p on up.project_id = p.project_id\r\n" + 
//	 		"where Month(up.to_date) =:month and Year(up.to_date) =:year and p.type =:type) \r\n" + 
//	 		"and u.status = 1 and u.group_id =:groupId ", nativeQuery=true)
//    public Integer getDeliveryUnitAvailable(@Param("type") int type,@Param("groupId") int groupId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value="select  count(*) as users from users u\r\n" + 
	 		"inner join user_plan up on up.user_id = u.user_id\r\n" + 
	 		"inner join project p on p.project_id = up.project_id\r\n" + 
	 		"where u.user_id in (select distinct UP.user_id from user_plan UP where (Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n" + 
	 		"and u.status = 1 and u.group_id =:groupId and p.type =:type  ",nativeQuery=true)
		public Integer getBookedUserAllDu(@Param("type") int type,@Param("groupId") int groupId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value="select c.delivery_unit, avg(c.score_value) as css  from project_css c\r\n" + 
	 		"where (Month(c.start_date) = :month or Month(c.end_date) = :month) and (Year(c.start_date) = :year or Year(c.end_date) = :year or c.end_date = NULL) \r\n" + 
	 		"group by c.delivery_unit", nativeQuery=true)
     List<Object> getDeliveryUnitCss(@Param("month") int month, @Param("year") int year);	
	 
	 @Query(value="select b.group_id, avg(b.accumulated) from\r\n" + 
	 		"(select p.group_id, pp.accumulated, p.project_id, pp.start_time, pp.end_time from project_pcv_rate pp \r\n" + 
	 		"inner join project p on pp.project_id = p.project_id\r\n" + 
	 		"inner join (select pp.project_id, max(pp.end_time) as maxtime from project_pcv_rate pp \r\n" + 
	 		"where month(pp.start_time) <= month(now()) and month(now()) <= month(pp.end_time) and year(end_time) = :year group by pp.project_id) pp2\r\n" + 
	 		"on pp.project_id = pp2.project_id and pp.end_time = pp2.maxtime) b\r\n" + 
	 		"group by b.group_id;", nativeQuery=true)
     List<Object> getDeliveryUnitPCV(@Param("year") int year);
	 
	 @Query(value="select p.group_id, avg(pc.score_value) from project_css pc inner join project p on p.project_id = pc.project_id \r\n" + 
	 		"where (month(pc.start_date) = :month or month(pc.end_date) = :month) and (year(pc.start_date) = :year or year(pc.end_date) = :year) and p.type = :type  group by p.group_id", nativeQuery=true)
	  List<Object> getDeliveryUnitProjectCss(@Param("month") int month, @Param("year") int year, @Param("type") int type);
	 
	 @Query(value = "SELECT A.group_id, ((B.totalBillable/A.totalAllocation)*100) AS effort_efficiency FROM ( \r\n" + 
	 		"SELECT u.group_id, SUM(UP.man_day) AS totalAllocation  FROM user_plan UP\r\n" + 
	 		"INNER JOIN users u on u.user_id = UP.user_id\r\n" + 
	 		"INNER JOIN project p on p.project_id = UP.project_id\r\n" + 
	 		"WHERE Month(UP.from_date) =:month and (Year(UP.to_date) =:year or UP.to_date = null) and u.group_id =:groupId and p.type =:type\r\n" + 
	 		") as A \r\n" + 
	 		"INNER JOIN ( \r\n" + 
	 		"SELECT SUM(billable_value) AS totalBillable, PB.group_id FROM project_billable PB \r\n" + 
	 		"WHERE Month(PB.start_date) =4 and (Year(PB.end_date) =2019 or PB.end_date = null ) GROUP BY group_id \r\n" + 
	 		") as B\r\n" + 
	 		" ON A.group_id = B.group_id ", nativeQuery = true)
		public Float getEffortEfficiencyByDeliveryUnit(@Param("type") int type, @Param("groupId") int groupId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value=" select b.group_id, avg(b.accumulated) from\r\n" + 
	 		"(select p.group_id, pp.accumulated, p.project_id, p.type from project_pcv_rate pp \r\n" + 
	 		"inner join project p on pp.project_id = p.project_id\r\n" + 
	 		"inner join (select pp.project_id, max(pp.end_time) as maxtime from project_pcv_rate pp \r\n" + 
	 		"where month(pp.start_time) <= :month and :month <= month(pp.end_time) and year(end_time) = :year group by pp.project_id) pp2\r\n" + 
	 		"on pp.project_id = pp2.project_id and pp.end_time = pp2.maxtime) b\r\n" + 
	 		"where b.type = :type group by b.group_id;", nativeQuery=true)
	 List<Object> getDeliveryUnitProjectPcv(@Param("month") int month, @Param("year") int year, @Param("type") int type);
	 
	 @Query(value="select p.group_id, count(distinct pu.user_id) from project_user pu\r\n" + 
	 		"inner join project p on p.project_id = pu.project_id\r\n" + 
	 		"inner join users u on u.user_id = pu.user_id\r\n" + 
	 		"inner join (select g.group_id from groups g \r\n" + 
	 		"where g.development_unit = 1 or g.internal_du = 1) as G\r\n" + 
	 		"where p.type=:type and u.group_id = G.group_id and month(p.end_date) >= :month and (year(p.end_date) =:year ) group by p.group_id", nativeQuery=true)
     List<Object> getDeliveryUnitProjectUsers(@Param("month") int month, @Param("year") int year, @Param("type") int type);
	 
	 @Query(value="select b.group_id, sum(b.manpower) from\r\n" + 
	 		"(SELECT distinct mp.user_id, mp.allocation_value as manpower, u.group_id FROM man_power mp\r\n" + 
	 		"inner join users u on mp.user_id = u.user_id\r\n" + 
	 		"inner join (select g.group_id from groups g  \r\n" + 
	 		"where g.development_unit = 1 or g.internal_du = 1) as G on u.group_id = G.group_id\r\n" + 
	 		"inner join project_user pu on mp.user_id = pu.user_id\r\n" + 
	 		"inner join project p on p.project_id = pu.project_id\r\n" + 
	 		"where p.type =:type and (Month(mp.from_date) =:month or Month(mp.to_date) =:month) and (Year(mp.from_date) =:year or Year(mp.to_date) =:year)) b\r\n" + 
	 		"group by b.group_id", nativeQuery=true)
	 List<Object> getDeliveryUnitProjectManPower(@Param("month") int month, @Param("year") int year, @Param("type") int type);
	 
	 @Query(value="select u.group_id, sum(pl.effort_per_day) as allocate  from user_plan pl\r\n" + 
	 		"inner join users u on u.user_id = pl.user_id \r\n" + 
	 		"inner join project_user pu on pu.user_id = u.user_id\r\n" + 
	 		"inner join project p on p.project_id = pu.project_id\r\n" + 
	 		"where p.type = :type and (Month(pl.from_date) = :month or Month(pl.to_date) = :month) and (Year(pl.from_date) = :year or Year(pl.to_date) = :year or pl.to_date = NULL) group by u.group_id", nativeQuery=true)
	 List<Object> getDeliveryUnitProjectAllocation(@Param("month") int month, @Param("year") int year, @Param("type") int type);
	 
//	 @Query(value="select  sum(pb.billable_value) as billable  from project_billable pb\r\n" + 
//	 		"inner join project p on p.project_id = pb.project_id  \r\n" + 
//	 		"where pb.group_id =:groupId and p.type =:type and Month(pb.end_date) =:month and (Year(pb.end_date) =:year or pb.end_date = NULL) ", nativeQuery=true)
//	 public Float getDeliveryUnitProjectBillable( @Param("type") int type, @Param("groupId") int groupId,@Param("month") int month, @Param("year") int year);
	 
	 @Query(value ="select  ifnull(sum(pb.billable_value),0) as billable  from project_billable pb\r\n"+ 
	 		" inner join project p on p.project_id = pb.project_id  \r\n" + 
	 		" where pb.group_id =:groupId and pb.project_id =:projectId and Month(pb.end_date) =:month and (Year(pb.end_date) =:year or pb.end_date = NULL)",nativeQuery = true)
	 public Float getDeliveryUnitProjectBillable(@Param("groupId") int groupId,@Param("projectId") int projectId,@Param("month") int month,@Param("year") int year);
	 @Query(value ="SELECT distinct UP.user_id,UP.project_id\r\n"
	 		+ " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
	 		+ " WHERE US.group_id =:groupId and ((Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n"
	 		+ " ORDER BY UP.user_id,UP.project_id",nativeQuery = true)
	 public List<Object> getListUserInProject(@Param("groupId") int groupId,@Param("month") int month,@Param("year") int year);
	 
	 @Query(value ="SELECT distinct UP.user_id,UP.project_id,UP.skill\r\n"
		 		+ " FROM user_plan UP\r\n"
		 		+ " WHERE ((Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n"
		 		+ " ORDER BY UP.user_id,UP.project_id",nativeQuery = true)
	 public List<Object> getListAllUserInProject(@Param("month") int month,@Param("year") int year);
	 
	 @Query(value ="SELECT COUNT(*)\r\n"
	 		+ " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
	 		+ " WHERE US.group_id =:groupId AND UP.user_id =:userId AND (UP.from_date >= :fromDate AND UP.to_date <= :endDate)",nativeQuery = true)
	 public Integer checkLast7Day(@Param("fromDate") String fromDate,@Param("endDate") String endDate,@Param("groupId") int groupId,@Param("userId") int userId);
	 
	 @Query(value ="SELECT COUNT(*)\r\n"
		 		+ " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
		 		+ " WHERE UP.user_id =:userId AND (UP.from_date >= :fromDate AND UP.to_date <= :endDate)",nativeQuery = true)
	  public Integer checkAllLast7Day(@Param("fromDate") String fromDate,@Param("endDate") String endDate,@Param("userId") int userId);
	 
	 @Query(value ="SELECT COUNT(*) \r\n"
			 + " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
			 + " WHERE UP.user_id =:userId and ((Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n",nativeQuery = true)
	 public Integer checkNextMonthIsWork(@Param("month") int month,@Param("year") int year,@Param("userId") int userId);
	 
	 @Query(value ="SELECT COUNT(*) \r\n"
			 + " FROM user_plan UP INNER JOIN users US ON US.user_id = UP.user_id\r\n"
			 + " WHERE  UP.user_id =:userId and ((Month(UP.from_date) =:month or Month(UP.to_date) =:month) and (Year(UP.from_date) =:year or Year(UP.to_date) =:year))\r\n",nativeQuery = true)
	 public Integer checkAllNextMonthIsWork(@Param("month") int month,@Param("year") int year,@Param("userId") int userId);
	 
	 @Query(value = "SELECT i.risk_id,i.registered_date,i.risk_title,i.risk_indicator,u.full_name,rc.risk_category_desciption,rsc.risk_sub_category_desciption,ri.risk_impact_description,i.risk_description,rs.risk_status_desciption, pr.project_name, i.owner, i.risk_color, pr.project_id FROM risk AS i "
		 		+ " left join users u on i.registered_by=u.user_id"
		 		+ " left join risk_category rc on i.risk_category_id=rc.risk_category_id"
		 		+ " left join risk_sub_category rsc on i.risk_sub_category_id=rsc.risk_sub_category_id "
		 		+ " left join risk_impact ri on i.risk_impact_id=ri.risk_impact_id"
		 		+ " left join risk_status rs on i.risk_status_id=rs.risk_status_id"
		 		+" left join project pr on i.project_id = pr.project_id"
		 		+ " WHERE i.project_id REGEXP :projectId AND i.is_deleted = 0  and month(i.registered_date) REGEXP :month and year(i.registered_date) REGEXP :year and \r\n" 
		 		+ " i.project_id in (select project_id from project where group_id REGEXP :groupId) \r\n"
					+ " ORDER BY i.registered_date DESC", nativeQuery = true)
			public List<Object> getAllRisk(@Param("projectId") String projectId,@Param("month") String month,@Param("year") String year,@Param("groupId") String groupId);
}
