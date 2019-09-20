package com.cmc.dashboard.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ResourceAllocationDb;



public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocationDb, Integer>{
//	@Query(value= "select @curRank \\:= @curRank + 1 AS id, db.* from \r\n" + 
//			"(\r\n" + 
//			" select A.user_id, A.project_id,\r\n" + 
//			" case when A.sumAllo is null then -1 else A.sumAllo/A.sumworking*100 end as allocation,\r\n" + 
//			" case when B.Manpor is null or B.Manpor=0 then -2  when A.sumAllo is null then -1 else A.sumAllo/B.Manpor*100\r\n" + 
//			" end as planAllocation from (\r\n" + 
//			"select up.user_id, up.project_id, sum(upd.plan_month) sumAllo,\r\n" + 
//			"(DATEDIFF(Last_day(concat(:dateym,'-',1)), concat(:dateym,'-',1)))+1 -\r\n" + 
//			"        ((WEEK(Last_day(concat(:dateym,'-',1))) - WEEK(concat(:dateym,'-',1))) * 2) -\r\n" + 
//			"        (case when weekday(Last_day(concat(:dateym,'-',1))) = 6 or weekday(Last_day(concat(:dateym,'-',1))) = 5 then 1 else 0 end) -\r\n" + 
//			"        (case when weekday(concat(:dateym,'-',1)) = 6 or weekday(concat(:dateym,'-',1)) = 5 then 1 else 0 end) sumworking\r\n" + 
//			"FROM user_plan up  join user_plan_detail upd on up.user_plan_id = upd.user_plan_id\r\n" + 
//			"where upd.plan_month= :datemy group by up.user_id, up.project_id,upd.plan_month\r\n" + 
//			") A left join (\r\n" + 
//			"	select AB.user_id ,sum(AB.allocation_value*AB.workday) as Manpor from \r\n" + 
//			"	(select MP.user_id, MP.fromdate, MP.todate, MP.allocation_value,\r\n" + 
//			"	 case when MP.fromdate then (DATEDIFF(MP.todate, MP.fromdate)+1) -\r\n" + 
//			"			((WEEK(MP.todate) - WEEK(MP.fromdate)) * 2) -\r\n" + 
//			"			(case when weekday(MP.todate) = 6 or weekday(MP.todate) = 5 then 1 else 0 end) -\r\n" + 
//			"			(case when MP.todate != MP.fromdate and (weekday(MP.fromdate) = 6 or weekday(MP.fromdate) = 5) then 1 else 0 end\r\n" + 
//			"			) end  as workday \r\n" + 
//			"	 from(\r\n" + 
//			"		select *,\r\n" + 
//			"		case \r\n" + 
//			"			when date_format(a.from_date,'%Y-%m')  < :dateym then concat(:dateym,'-','01')\r\n" + 
//			"			else date_format(a.from_date,'%Y-%m-%d')\r\n" + 
//			"		end as fromdate\r\n" + 
//			"		,case \r\n" + 
//			"			when date_format(a.to_date,'%Y-%m')  > :dateym then Last_day(concat(:dateym,'-',01))\r\n" + 
//			"			when a.to_date is null then Last_day(concat(:dateym,'-',01))\r\n" + 
//			"			else date_format(a.to_date,'%Y-%m-%d')\r\n" + 
//			"		end as todate\r\n" + 
//			"		from man_power a \r\n" + 
//			"		where date_format(a.from_date,'%Y-%m') <= :dateym and date_format(a.to_date,'%Y-%m') >= :dateym\r\n" + 
//			"		or date_format(a.from_date,'%Y-%m') <= :dateym and a.to_date is null and date_format(curdate(),'%Y-%m') >= :dateym\r\n" + 
//			"	) as MP where MP.fromdate is not null and MP.todate is not null \r\n" + 
//			"	) as AB group by AB.user_id\r\n" + 
//			") B on A.user_id = B.user_id\r\n" + 
//			"union \r\n" + 
//			"select B.user_id, case when A.project_id is null then 0 else A.project_id end,\r\n" + 
//			" case when A.sumAllo is null then -1 else A.sumAllo/A.sumworking*100 end as allocation,\r\n" + 
//			" case when B.Manpor is null or B.Manpor=0 then -2  when A.sumAllo is null then -1 else A.sumAllo/B.Manpor*100\r\n" + 
//			" end as planAllocation from (\r\n" + 
//			"	select up.user_id, up.project_id, sum(upd.plan_month) sumAllo,\r\n" + 
//			"	(DATEDIFF(Last_day(concat(:dateym,'-',1)), concat(:dateym,'-',1)))+1 -\r\n" + 
//			"			(case when weekday(Last_day(concat(:dateym,'-',1))) = 6 or weekday(Last_day(concat(:dateym,'-',1))) = 5 then 1 else 0 end) -\r\n" + 
//			"			(case when weekday(concat(:dateym,'-',1)) = 6 or weekday(concat(:dateym,'-',1)) = 5 then 1 else 0 end) sumworking\r\n" + 
//			"	FROM user_plan up  join user_plan_detail upd on up.user_plan_id = upd.user_plan_id\r\n" + 
//			"	where upd.plan_month= :datemy group by up.user_id, up.project_id,upd.plan_month\r\n" + 
//			") A right join (\r\n" + 
//			"	select AB.user_id ,sum(AB.allocation_value*AB.workday) as Manpor from \r\n" + 
//			"	(select MP.user_id, MP.fromdate, MP.todate, MP.allocation_value,\r\n" + 
//			"	 case when MP.fromdate then (DATEDIFF(MP.todate, MP.fromdate)+1) -\r\n" + 
//			"			((WEEK(MP.todate) - WEEK(MP.fromdate)) * 2) -\r\n" + 
//			"			(case when weekday(MP.todate) = 6 or weekday(MP.todate) = 5 then 1 else 0 end) -\r\n" + 
//			"			(case when MP.todate != MP.fromdate and (weekday(MP.fromdate) = 6 or weekday(MP.fromdate) = 5) then 1 else 0 end\r\n" + 
//			"			) end  as workday \r\n" + 
//			"	 from(\r\n" + 
//			"		select *,\r\n" + 
//			"		case when date_format(a.from_date,'%Y-%m')  < :dateym then concat(:dateym,'-','01')\r\n" + 
//			"			else date_format(a.from_date,'%Y-%m-%d') end as fromdate\r\n" + 
//			"		,case when date_format(a.to_date,'%Y-%m')  > :dateym then Last_day(concat(:dateym,'-',01))\r\n" + 
//			"			when a.to_date is null then Last_day(concat(:dateym,'-',01)) \r\n" + 
//			"            else date_format(a.to_date,'%Y-%m-%d') end as todate\r\n" + 
//			"		from man_power a \r\n" + 
//			"		where date_format(a.from_date,'%Y-%m') <= :dateym and date_format(a.to_date,'%Y-%m') >= :dateym\r\n" + 
//			"		or date_format(a.from_date,'%Y-%m') <= :dateym and a.to_date is null and date_format(curdate(),'%Y-%m') >= :dateym\r\n" + 
//			"	) as MP where MP.fromdate is not null and MP.todate is not null \r\n" + 
//			"	) as AB group by AB.user_id\r\n" + 
//			") B on A.user_id = B.user_id\r\n" + 
//			") as db,(SELECT @curRank \\:= 0) cur" + 
//			" \n #pageable# \n" , 
//			countQuery= "select count(*) from \r\n" + 
//					"(\r\n" + 
//					" select A.user_id, A.project_id,\r\n" + 
//					" case when A.sumAllo is null then -1 else A.sumAllo/A.sumworking*100 end as allocation,\r\n" + 
//					" case when B.Manpor is null then -2  when A.sumAllo is null then -1 else A.sumAllo/B.Manpor*100\r\n" + 
//					" end as planAllocation from (\r\n" + 
//					"select up.user_id, up.project_id, sum(upd.plan_month) sumAllo,\r\n" + 
//					"(DATEDIFF(Last_day(concat(:dateym,'-',1)), concat(:dateym,'-',1)))+1 -\r\n" + 
//					"        ((WEEK(Last_day(concat(:dateym,'-',1))) - WEEK(concat(:dateym,'-',1))) * 2) -\r\n" + 
//					"        (case when weekday(Last_day(concat(:dateym,'-',1))) = 6 or weekday(Last_day(concat(:dateym,'-',1))) = 5 then 1 else 0 end) -\r\n" + 
//					"        (case when weekday(concat(:dateym,'-',1)) = 6 or weekday(concat(:dateym,'-',1)) = 5 then 1 else 0 end) sumworking\r\n" + 
//					"FROM user_plan up  join user_plan_detail upd on up.user_plan_id = upd.user_plan_id\r\n" + 
//					"where upd.plan_month= :datemy group by up.user_id, up.project_id,upd.plan_month\r\n" + 
//					") A left join (\r\n" + 
//					"	select AB.user_id ,sum(AB.allocation_value*AB.workday) as Manpor from \r\n" + 
//					"	(select MP.user_id, MP.fromdate, MP.todate, MP.allocation_value,\r\n" + 
//					"	 case when MP.fromdate then (DATEDIFF(MP.todate, MP.fromdate)+1) -\r\n" + 
//					"			((WEEK(MP.todate) - WEEK(MP.fromdate)) * 2) -\r\n" + 
//					"			(case when weekday(MP.todate) = 6 or weekday(MP.todate) = 5 then 1 else 0 end) -\r\n" + 
//					"			(case when MP.todate != MP.fromdate and (weekday(MP.fromdate) = 6 or weekday(MP.fromdate) = 5) then 1 else 0 end\r\n" + 
//					"			) end  as workday \r\n" + 
//					"	 from(\r\n" + 
//					"		select *,\r\n" + 
//					"		case \r\n" + 
//					"			when date_format(a.from_date,'%Y-%m')  < :dateym then concat(:dateym,'-','01')\r\n" + 
//					"			else date_format(a.from_date,'%Y-%m-%d')\r\n" + 
//					"		end as fromdate\r\n" + 
//					"		,case \r\n" + 
//					"			when date_format(a.to_date,'%Y-%m')  > :dateym then Last_day(concat(:dateym,'-',01))\r\n" + 
//					"			when a.to_date is null then Last_day(concat(:dateym,'-',01))\r\n" + 
//					"			else date_format(a.to_date,'%Y-%m-%d')\r\n" + 
//					"		end as todate\r\n" + 
//					"		from man_power a \r\n" + 
//					"		where date_format(a.from_date,'%Y-%m') <= :dateym and date_format(a.to_date,'%Y-%m') >= :dateym\r\n" + 
//					"		or date_format(a.from_date,'%Y-%m') <= :dateym and a.to_date is null and date_format(curdate(),'%Y-%m') >= :dateym\r\n" + 
//					"	) as MP where MP.fromdate is not null and MP.todate is not null \r\n" + 
//					"	) as AB group by AB.user_id\r\n" + 
//					") B on A.user_id = B.user_id\r\n" + 
//					"union \r\n" + 
//					"select B.user_id, case when A.project_id is null then 0 else A.project_id end,\r\n" + 
//					" case when A.sumAllo is null then -1 else A.sumAllo/A.sumworking*100 end as allocation,\r\n" + 
//					" case when B.Manpor is null then -2  when A.sumAllo is null then -1 else A.sumAllo/B.Manpor*100\r\n" + 
//					" end as planAllocation from (\r\n" + 
//					"	select up.user_id, up.project_id, sum(upd.plan_month) sumAllo,\r\n" + 
//					"	(DATEDIFF(Last_day(concat(:dateym,'-',1)), concat(:dateym,'-',1)))+1 -\r\n" + 
//					"			((WEEK(Last_day(concat(:dateym,'-',1))) - WEEK(concat(:dateym,'-',1))) * 2) -\r\n" + 
//					"			(case when weekday(Last_day(concat(:dateym,'-',1))) = 6 or weekday(Last_day(concat(:dateym,'-',1))) = 5 then 1 else 0 end) -\r\n" + 
//					"			(case when weekday(concat(:dateym,'-',1)) = 6 or weekday(concat(:dateym,'-',1)) = 5 then 1 else 0 end) sumworking\r\n" + 
//					"	FROM user_plan up  join user_plan_detail upd on up.user_plan_id = upd.user_plan_id\r\n" + 
//					"	where upd.plan_month= :datemy group by up.user_id, up.project_id,upd.plan_month\r\n" + 
//					") A right join (\r\n" + 
//					"	select AB.user_id ,sum(AB.allocation_value*AB.workday) as Manpor from \r\n" + 
//					"	(select MP.user_id, MP.fromdate, MP.todate, MP.allocation_value,\r\n" + 
//					"	 case when MP.fromdate then (DATEDIFF(MP.todate, MP.fromdate)+1) -\r\n" + 
//					"			((WEEK(MP.todate) - WEEK(MP.fromdate)) * 2) -\r\n" + 
//					"			(case when weekday(MP.todate) = 6 or weekday(MP.todate) = 5 then 1 else 0 end) -\r\n" + 
//					"			(case when MP.todate != MP.fromdate and (weekday(MP.fromdate) = 6 or weekday(MP.fromdate) = 5) then 1 else 0 end\r\n" + 
//					"			) end  as workday \r\n" + 
//					"	 from(\r\n" + 
//					"		select *,\r\n" + 
//					"		case when date_format(a.from_date,'%Y-%m')  < :dateym then concat(:dateym,'-','01')\r\n" + 
//					"			else date_format(a.from_date,'%Y-%m-%d') end as fromdate\r\n" + 
//					"		,case when date_format(a.to_date,'%Y-%m')  > :dateym then Last_day(concat(:dateym,'-',01))\r\n" + 
//					"			when a.to_date is null then Last_day(concat(:dateym,'-',01)) \r\n" + 
//					"            else date_format(a.to_date,'%Y-%m-%d') end as todate\r\n" + 
//					"		from man_power a \r\n" + 
//					"		where date_format(a.from_date,'%Y-%m') <= :dateym and date_format(a.to_date,'%Y-%m') >= :dateym\r\n" + 
//					"		or date_format(a.from_date,'%Y-%m') <= :dateym and a.to_date is null and date_format(curdate(),'%Y-%m') >= :dateym\r\n" + 
//					"	) as MP where MP.fromdate is not null and MP.todate is not null \r\n" + 
//					"	) as AB group by AB.user_id\r\n" + 
//					") B on A.user_id = B.user_id\r\n" + 
//					") as db",
//			nativeQuery= true)
//	public Page<ResourceAllocationDb> getResourceAllocation(Pageable pageable,@Param("dateym") String dateym, @Param("datemy") String datemy);
	
	@Query(value="select up.project_id,up.user_id,gu.group_name as DU,u.full_name as full_name,pr.name as role,gp.group_name as DUPIC,p.project_name as project_name,p.project_code as project_code,pt.name as project_type,p.status,up.role as role_id \r\n" + 
			" FROM \r\n" + 
			"user_plan up \r\n" + 
			"inner join users u on up.user_id=u.user_id \r\n" + 
			"inner join project p on p.project_id=up.project_id \r\n" + 
			"left outer  join project_type pt on pt.project_type_id=p.project_type_id\r\n" + 
			"left outer join groups gp on gp.group_id = p.group_id\r\n" + 
			"left outer join groups gu on gu.group_id = u.group_id\r\n" + 
			"left outer join project_role pr on pr.project_role_id= up.role\r\n" + 
			"where year(up.from_date) = :year and month(up.from_date)=:month\r\n"+
			"and u.full_name like %:fullName% \r\n"
			+ "and gu.group_name REGEXP :du \r\n"
			+ "and gp.group_name REGEXP :duPic \r\n"
			+ "and p.project_name REGEXP :projectName\r\n"
			+"group by up.project_id,up.user_id,gu.group_name ,u.full_name ,pr.name ,gp.group_name ,p.project_name ,p.project_code ,pt.name ,p.status,up.role\r\n"
			+ "\n#pageable#\n",countQuery="select count(*) \r\n" + 
					" FROM \r\n" + 
					"user_plan up \r\n" + 
					"inner join users u on up.user_id=u.user_id \r\n" + 
					"inner join project p on p.project_id=up.project_id \r\n" + 
					"left outer join project_type pt on pt.project_type_id=p.project_type_id\r\n" + 
					"left outer join groups gp on gp.group_id = p.group_id\r\n" + 
					"left outer join groups gu on gu.group_id = u.group_id\r\n" + 
					"left outer join project_role pr on pr.project_role_id= up.role\r\n" + 
					"where year(up.from_date) = :year and month(up.from_date)=:month\r\n"+
					"and u.full_name like %:fullName% \r\n"
					+ "and gu.group_name REGEXP :du \r\n"
					+ "and gp.group_name REGEXP :duPic \r\n"
					+ "and p.project_name REGEXP :projectName\r\n"
					+"group by up.project_id,up.user_id,gu.group_name ,u.full_name ,pr.name ,gp.group_name ,p.project_name ,p.project_code ,pt.name ,p.status\r\n"
					,nativeQuery= true)
    public Page<Object> getAllResourceAllocation(Pageable pageable,@Param("month") int month, @Param("year") int year,@Param("du") String du,@Param("fullName") String fullName,@Param("duPic") String duPic,@Param("projectName") String projectName);

	@Query(value="SELECT sum(effort_per_day*(date(to_date)-date(from_date)+1)/8) as sumAlo  "
			+ "FROM "
			+ "user_plan \r\n" + 
			"where year(from_date)=:year and month(from_date)=:month and user_id=:userId and project_id=:projectId and role=:role\r\n"
			,nativeQuery=true)
	public String sumAllocation(@Param("userId") int userId,@Param("projectId") int projectId,@Param("month") int month, @Param("year") int year,@Param("role") String role);
	
	@Query(value="SELECT sum(effort_per_day*(date(to_date)-date(from_date)+1)/8) as sumAlo  "
			+ "FROM "
			+ "user_plan \r\n" + 
			"where year(from_date)=:year and month(from_date)=:month and user_id=:userId and project_id=:projectId and role IS NULL\r\n"
			,nativeQuery=true)
	public String sumAllocation(@Param("userId") int userId,@Param("projectId") int projectId,@Param("month") int month, @Param("year") int year);
	
	@Query(value="SELECT sum(effort_per_day*(date(to_date)-date(from_date)+1)/8) as sumAlo  "
			+ "FROM "
			+ "user_plan \r\n" + 
			"where year(from_date)=:year and month(from_date)=:month and user_id=:userId  ",nativeQuery=true)
	public String sumAllocationInMonth(@Param("userId") int userId,@Param("month") int month, @Param("year") int year);
	
	@Query(value="SELECT  case when sum(date(to_date)-date(from_date)+1) <0 \r\n" + 
			"	   Then convert(0,char(100))\r\n" + 
			"	   Else convert(sum(date(to_date)-date(from_date)+1),char(100)) end as sumManpo  FROM man_power \r\n" + 
			"where year(from_date)=:year and month(from_date)=:month and user_id=:userId",
			nativeQuery=true)
	public String sumManPower(@Param("userId") int userId,@Param("month") int month, @Param("year") int year);
	
	@Query(value="select gu.group_name\r\n" + 
			" FROM \r\n" + 
			"user_plan up \r\n" + 
			"inner join users u on up.user_id=u.user_id \r\n" + 
			"inner join groups gu on gu.group_id = u.group_id\r\n" +
			"where year(up.from_date) = :year and month(up.from_date)=:month\r\n"+
			"group by gu.group_name ",nativeQuery=true)
	public List<String> getListDUMonth(@Param("month") int month, @Param("year") int year);
	
	@Query(value="select gp.group_name\r\n" + 
			" FROM \r\n" + 
			"user_plan up \r\n" + 
			"inner join project p on p.project_id=up.project_id \r\n" +
			"inner join groups gp on gp.group_id = p.group_id\r\n" + 
			"where year(up.from_date) = :year and month(up.from_date)=:month\r\n"+
			"group by gp.group_name ",nativeQuery=true)
	public List<String> getListDUPicMonth(@Param("month") int month, @Param("year") int year);
	@Query(value="select p.project_name \r\n" + 
			" FROM \r\n" + 
			"user_plan up \r\n" + 
			"inner join project p on p.project_id=up.project_id \r\n" +
			"where year(up.from_date) = :year and month(up.from_date)=:month\r\n"+
			"group by  p.project_name ",nativeQuery=true)
	public List<String> getListProjectNameMonth(@Param("month") int month, @Param("year") int year);
	
	//Unalocation
	@Query(value="select sumMan.user_id from \r\n" + 
			"(SELECT u.user_id, sum(date(mp.to_date)-date(mp.from_date)+1) as Sum_Manpower FROM man_power mp\r\n" + 
			"inner join users u on u.user_id = mp.user_id \r\n" + 
			"where month(from_date)=:month and year(from_date)=:year\r\n" + 
			"group by u.user_id\r\n" + 
			") as sumMan inner join\r\n" + 
			"(SELECT user_id, sum(date(to_date)-date(from_date)+1) as Sum_Allocation FROM user_plan up \r\n" + 
			"where month(up.from_date)=:month and year(up.from_date)=:year\r\n" + 
			"group by up.user_id) as sumAlo \r\n" + 
			"on sumMan.user_id=sumAlo.user_id\r\n" + 
			"where sumMan.Sum_Manpower < sumAlo.Sum_Allocation\r\n"
			+ "group by sumMan.user_id\r\n "
					,nativeQuery= true)
    public List<Integer> getListUnsatisfactoryUnAllocation(@Param("month") int month, @Param("year") int year);

	@Query(value="select u.user_id,u.full_name,g.group_name\r\n"
			+ "from users u "
			+" inner join groups g on g.group_id=u.group_id\r\n"
			+ " where u.full_name LIKE CONCAT('%',:fullName,'%') and g.development_unit = 1 \r\n"
			+ " and g.group_name REGEXP :du \r\n"
			+ "\n#pageable#\n",
			countQuery="select count(*)\r\n"
					+ "from users u "
					+" inner join groups g on g.group_id=u.group_id\r\n"
					+ "where \r\n"
					+ "u.full_name LIKE CONCAT('%',:fullName,'%') \r\n"
					+ "and g.group_name REGEXP :du and g.development_unit = 1 \r\n"
					,nativeQuery= true)
    public Page<Object> getAllResourceUnAllocation(Pageable pageable,@Param("fullName") String fullName, @Param("du") String du);

	@Query(value="select u.user_id,u.full_name,g.group_name\r\n"
			+ "from users u "
			+" inner join groups g on g.group_id=u.group_id\r\n"
			+ " where \r\n"
			+ " u.full_name like %:fullName% \r\n"
			+ " and g.group_name REGEXP :du and g.development_unit = 1 \r\n"
			+ " and u.user_id not in (select up.user_id from user_plan up  where (( month(up.from_date) =:month and year(up.from_date) =:year) or (month(up.to_date) = :month and year(up.to_date) = :year)) group by up.user_id)\r\n "
			+ "\n#pageable#\n",
			countQuery="select count(*)\r\n"
					+ "from users u "
					+" inner join groups g on g.group_id=u.group_id\r\n"
					+ "where \r\n"
					+ "u.full_name like %:fullName% \r\n"
					+ "and g.group_name REGEXP :du  and g.development_unit = 1 \r\n"
					+ " and u.user_id not in (select up.user_id from user_plan up  where ((month(up.from_date) =:month and year(up.from_date) =:year) or (month(up.to_date) = :month and year(up.to_date) = :year)) group by up.user_id)\r\n "
					,nativeQuery= true)
    public Page<Object> getAllResourceUnAllocationNoAllo(Pageable pageable,@Param("fullName") String fullName, @Param("du") String du,@Param("month") int month, @Param("year") int year);
	@Query(value="select u.user_id,u.full_name,g.group_name\r\n"
			+ "from users u "
			+" inner join groups g on g.group_id=u.group_id\r\n"
			+ " where \r\n"
			+ " u.full_name like %:fullName% and g.development_unit = 1 \r\n"
			+ " and g.group_name REGEXP :du \r\n"
			+ " and u.user_id in (select up.user_id from user_plan up where ((month(up.from_date) =:month and year(up.from_date) =:year) or (month(up.to_date) = :month and year(up.to_date) = :year)) group by up.user_id)\r\n "
			+ "\n#pageable#\n",
			countQuery="select count(*)\r\n"
					+ "from users u "
					+" inner join groups g on g.group_id=u.group_id\r\n"
					+ "where \r\n"
					+ "u.full_name like %:fullName% \r\n"
					+ "and g.group_name REGEXP :du  and g.development_unit = 1 \r\n"
					+ " and u.user_id in (select up.user_id from user_plan up where ((month(up.from_date) =:month and year(up.from_date) =:year) or (month(up.to_date) = :month and year(up.to_date) = :year)) group by up.user_id)\r\n "
					,nativeQuery= true)
    public Page<Object> getAllResourceUnAllocationNotFull(Pageable pageable,@Param("fullName") String fullName, @Param("du") String du,@Param("month") int month, @Param("year") int year);
	@Query(value="select sumMan.group_name from \r\n" + 
			"(SELECT u.user_id,u.full_name,g.group_name,u.status, sum(date(to_date)-date(from_date)+1) as Sum_Manpower FROM man_power mp\r\n" + 
			"inner join users u on u.user_id = mp.user_id \r\n" + 
			"inner join groups g on g.group_id=u.group_id\r\n" + 
			"where month(from_date)=:month and year(from_date)=:year \r\n" + 
			"group by u.user_id,u.full_name,g.group_name,u.status\r\n" + 
			") as sumMan left outer join\r\n" + 
			"(SELECT user_id, sum(date(to_date)-date(from_date)+1) as Sum_Allocation FROM user_plan up \r\n" + 
			"where month(up.from_date)=:month and year(up.from_date)=:year\r\n" + 
			"group by up.user_id) as sumAlo \r\n" + 
			"on sumMan.user_id=sumAlo.user_id\r\n" + 
			"where sumMan.Sum_Manpower > sumAlo.Sum_Allocation"
			,nativeQuery= true)
    public List<String> getDuUnAllocation(@Param("month") int month, @Param("year") int year);
	@Query(value="select user_plan_id from user_plan where user_id = :userId and month(from_date) =:month and year(from_date) =:year"
			,nativeQuery=true)
	public List<Integer> getUserPlanNextMonth(@Param("userId") int userId,@Param("month") int nextMonth, @Param("year") int year);
}