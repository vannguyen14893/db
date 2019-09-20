package com.cmc.dashboard.qms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.ResourceAllocationQms;
import com.cmc.dashboard.util.CustomValueUtil;

public interface ResourceAllocationQmsRepository extends JpaRepository<ResourceAllocationQms, Integer>{

	@Query(value="select @curRank \\:= @curRank + 1 AS id, CVS.user_id,CVS.project_id,CVS.name,CVS.status, CVS.duPic, CVS.sdateP,\r\n" + 
			"CVS.edateP,CVS.projectCode,CVS.projectType,CVS.role,\r\n" + 
			"CVS.du, CVS.username, case when CVS.user_id then -1 end allocation, case when CVS.user_id then -2 end planAllocation \r\n" + 
			"from (SELECT * FROM (SELECT * FROM (SELECT P.id, P.name, P.status,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r\n" + 
			" AND CV.customized_id = P.id) AS duPic,\r\n" + 
			"    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r\n" + 
			" AND CV.customized_id = P.id) AS sdateP,\r\n" + 
			"    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r\n" + 
			" AND CV.customized_id = P.id) AS edateP,\r\n" + 
			" (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r\n" + 
			" AND CV.customized_id = P.id) AS projectCode,\r\n" + 
			" (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r\n" + 
			" AND CV.customized_id = P.id) AS projectType\r\n" + 
			"FROM redmine_db.projects P \r\n" + 
			" WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n" + 
			")TB1 WHERE (TB1.duPic IS NOT NULL) AND (\r\n" + 
			"	concat(year(TB1.sdateP),\"-\",LPAD(month(TB1.sdateP), 2, '0')) <= :dateym AND concat(year(TB1.edateP),\"-\",LPAD(month(TB1.edateP), 2, '0')) >= :dateym\r\n" + 
			"))as TB2  join\r\n" + 
			"( select bang1.user_id, bang1.project_id, bang2.role, concat( bang3.lastname,\" \",bang3.firstname) as username,\r\n" + 
			"	(select cv.value from redmine_db.custom_values cv where cv.custom_field_id = "+CustomValueUtil.DELIVERY_UNIT_USER_ID+" and \r\n" + 
			"		(cv.customized_id = bang1.user_id or cv.customized_id = bang2.user_id)) as du\r\n" + 
			"	 from (\r\n" + 
			"	select user_id, project_id FROM redmine_db.time_entries group by user_id, project_id) as bang1 left join\r\n" + 
			"	(select a.user_id, a.project_id, GROUP_CONCAT(c.name) as role from redmine_db.members a \r\n" + 
			"		left join redmine_db.member_roles b on a.id = b.member_id \r\n" + 
			"		left join redmine_db.roles c on b.role_id = c.id group by a.user_id, a.project_id) as bang2 on (bang1.user_id = bang2.user_id \r\n" + 
			"			and bang1.project_id = bang2.project_id) left join redmine_db.users bang3 on bang3.id = bang1.user_id\r\n" + 
			"	union \r\n" + 
			"	select bang2.user_id, bang2.project_id, bang2.role, concat( bang3.lastname,\" \",bang3.firstname) as username,\r\n" + 
			"	(select cv.value from redmine_db.custom_values cv where cv.custom_field_id = "+CustomValueUtil.DELIVERY_UNIT_USER_ID+" and \r\n" + 
			"		(cv.customized_id = bang1.user_id or cv.customized_id = bang2.user_id)) as du\r\n" + 
			"	 from (\r\n" + 
			"	select user_id, project_id FROM redmine_db.time_entries group by user_id, project_id) as bang1 right join\r\n" + 
			"	(select a.user_id, a.project_id, GROUP_CONCAT(c.name) as role from redmine_db.members a \r\n" + 
			"		left join redmine_db.member_roles b on a.id = b.member_id \r\n" + 
			"		left join redmine_db.roles c on b.role_id = c.id group by a.user_id, a.project_id) as bang2 on (bang1.user_id = bang2.user_id \r\n" + 
			"				and bang1.project_id = bang2.project_id) left join redmine_db.users bang3 on bang3.id = bang2.user_id) \r\n" + 
			"as MB on MB.project_id = TB2.id) CVS, (SELECT @curRank \\:= 0) r where CVS.username like %:resName% and CVS.du REGEXP :du \r\n" + 
			"and CVS.duPic REGEXP :duPic and CVS.project_id REGEXP :projectId"
			+ "\n#pageable\n",
			countQuery="select count(*)\r\n" + 
					"from (SELECT * FROM (SELECT * FROM (SELECT P.id, P.name, P.status,\r\n" + 
					"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r\n" + 
					" AND CV.customized_id = P.id) AS duPic,\r\n" + 
					"    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r\n" + 
					" AND CV.customized_id = P.id) AS sdateP,\r\n" + 
					"    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r\n" + 
					" AND CV.customized_id = P.id) AS edateP,\r\n" + 
					" (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r\n" + 
					" AND CV.customized_id = P.id) AS projectCode,\r\n" + 
					" (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r\n" + 
					" AND CV.customized_id = P.id) AS projectType\r\n" + 
					"FROM redmine_db.projects P \r\n" + 
					" WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n" + 
					")TB1 WHERE (TB1.duPic IS NOT NULL) AND (\r\n" + 
					"	concat(year(TB1.sdateP),\"-\",LPAD(month(TB1.sdateP), 2, '0')) <= :dateym AND concat(year(TB1.edateP),\"-\",LPAD(month(TB1.edateP), 2, '0')) >= :dateym\r\n" + 
					"))as TB2  join\r\n" + 
					"( select bang1.user_id, bang1.project_id, bang2.role, concat( bang3.lastname,\" \",bang3.firstname) as username,\r\n" + 
					"	(select cv.value from redmine_db.custom_values cv where cv.custom_field_id = "+CustomValueUtil.DELIVERY_UNIT_USER_ID+" and \r\n" + 
					"		(cv.customized_id = bang1.user_id or cv.customized_id = bang2.user_id)) as du\r\n" + 
					"	 from (\r\n" + 
					"	select user_id, project_id FROM redmine_db.time_entries group by user_id, project_id) as bang1 left join\r\n" + 
					"	(select a.user_id, a.project_id, GROUP_CONCAT(c.name) as role from redmine_db.members a \r\n" + 
					"		left join redmine_db.member_roles b on a.id = b.member_id \r\n" + 
					"		left join redmine_db.roles c on b.role_id = c.id group by a.user_id, a.project_id) as bang2 on (bang1.user_id = bang2.user_id \r\n" + 
					"			and bang1.project_id = bang2.project_id) left join redmine_db.users bang3 on bang3.id = bang1.user_id\r\n" + 
					"	union \r\n" + 
					"	select bang2.user_id, bang2.project_id, bang2.role, concat( bang3.lastname,\" \",bang3.firstname) as username,\r\n" + 
					"	(select cv.value from redmine_db.custom_values cv where cv.custom_field_id = "+CustomValueUtil.DELIVERY_UNIT_USER_ID+" and \r\n" + 
					"		(cv.customized_id = bang1.user_id or cv.customized_id = bang2.user_id)) as du\r\n" + 
					"	 from (\r\n" + 
					"	select user_id, project_id FROM redmine_db.time_entries group by user_id, project_id) as bang1 right join\r\n" + 
					"	(select a.user_id, a.project_id, GROUP_CONCAT(c.name) as role from redmine_db.members a \r\n" + 
					"		left join redmine_db.member_roles b on a.id = b.member_id \r\n" + 
					"		left join redmine_db.roles c on b.role_id = c.id group by a.user_id, a.project_id) as bang2 on (bang1.user_id = bang2.user_id \r\n" + 
					"				and bang1.project_id = bang2.project_id) left join redmine_db.users bang3 on bang3.id = bang2.user_id) \r\n" + 
					"as MB on MB.project_id = TB2.id) CV where CV.username like %:resName% and CV.du REGEXP :du \r\n" + 
					"and CV.duPic REGEXP :duPic and CV.project_id REGEXP :projectId",
			nativeQuery=true)
	public Page<ResourceAllocationQms> getRessourceAllocation(Pageable pageable, @Param("dateym") String dateym, 
					@Param("resName") String resName, @Param("du") String du, @Param("duPic") String duPic, @Param("projectId") String projectId);
	
}
